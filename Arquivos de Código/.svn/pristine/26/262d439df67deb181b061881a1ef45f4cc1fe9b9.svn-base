package sourceminer.modules;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

import sourceminer.concerns.Concern;
import sourceminer.concerns.ConcernModel;
import sourceminer.concerns.io.ModelIOException;
import sourceminer.concerns.io.ModelReader;
import sourceminer.utilities.Groups;
import sourceminer.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.modeling.Node;
import aimv.modules.Module;

public class ConcernsModule extends Module {

	@Override
	protected void start(Object fonte, IProgressMonitor monitor) {
		
		if (!(fonte instanceof IJavaProject) || Nodes.getGroup(Groups.ALL) == null) 
			return;
		
		monitor.subTask("Analyzing concerns the project");
		
		for (Node node : Nodes.getGroup(Groups.ALL).getNodes()) {
			node.setProperty(Properties.CONCERNS, new String[0]);
			node.setProperty(Properties.NUMBER_CONCERNS, 0);
		}
		
		IJavaProject project = (IJavaProject) fonte;
		String path = project.getProject().getLocationURI().getPath();
		String separator = System.getProperty("file.separator");
		File folder = new File(path);		
		
		try {
			
			LinkedHashMap<String, Concern> concerns = new LinkedHashMap<String, Concern>();
			for (int n = 0; n < folder.list().length; n++) {				
				String fileName = folder.list()[n];					
				if (fileName.endsWith(".cm")) {						
					File file = new File(folder.getAbsolutePath()+separator+fileName);							
					ModelReader lReader = new ModelReader(new ConcernModel());					
					ConcernModel concernModel = lReader.read(file,project);
					concerns.putAll(concernModel.getConcerns());
				}
			}
			
			String[] list = new String[concerns.size()];
			AIMV.setProperty(Properties.CONCERNS, list);
			for (int i = 0; i < concerns.size(); i++)
				list[i] = (String) concerns.keySet().toArray()[i];
			addConcerns(concerns);
			
		} catch (ModelIOException e) {
			e.printStackTrace();
		}		
		
	}//start
	
	private void addConcerns(LinkedHashMap<String, Concern> concerns) {
		
		try {
			for (Node node : Nodes.getGroup("method").getNodes()) {
				for (String concern : concerns.keySet()) {			
					Set<Object> concernElements = concerns.get(concern).getElements();		
					for (Object cElement : concernElements)
							if (addConcernMethod(node, cElement, concern))
								break;
				}
			}
		
			for (Node node : Nodes.getGroup("class").getNodes()) {
				for (String concern : concerns.keySet()) {			
					Set<Object> concernElements = concerns.get(concern).getElements();		
					for (Object cElement : concernElements)
						if (addConcernClass(node, cElement, concern))
							break;
				}
			}
			
		} catch (JavaModelException e) {
			
			e.printStackTrace();
		}
		
	}//addConcerns
	
	private boolean addConcernMethod(Node node, Object cElement, String selectedConcern) throws JavaModelException {
	
		IMethod method = (IMethod)node.getProperty(Properties.JAVA_ELEMENT);	
		if (cElement instanceof IMethod ) {		
			IMethod  javaConcernElement = (IMethod) cElement;	
			String fullNameMethod = ((IType) method.getParent()).getFullyQualifiedName() + "." + Signature.toString(method.getSignature(), method.getElementName(), method.getParameterNames(), true, false);
			String s1 =((IType) javaConcernElement.getParent()).getFullyQualifiedName();
			String fullNameConcernMethod = s1 + "." + Signature.toString(javaConcernElement.getSignature(), javaConcernElement.getElementName(), javaConcernElement.getParameterNames(), true, false);

			if (fullNameMethod.equals(fullNameConcernMethod)) {
				addConcern(node, selectedConcern);
				addConcernParent(node, selectedConcern);
				return true;
			}
		}
		return false;
	
	}//addConcernMethod

	private boolean addConcernClass(Node node, Object cElement, String selectedConcern) throws JavaModelException {
	
		IType type = (IType) node.getProperty(Properties.JAVA_ELEMENT);
		if( cElement instanceof IJavaElement ){		
			IJavaElement  javaConcernElement = (IJavaElement) cElement;				
			IJavaElement  javaConcernType = (IJavaElement) javaConcernElement.getParent();

			if( javaConcernType instanceof IType ){					
				String fullNameType = ((IType)type).getFullyQualifiedName();
				String fullNameConcernType = ((IType)javaConcernType).getFullyQualifiedName();

				if(fullNameType.equals(fullNameConcernType)){
					addConcern(node, selectedConcern);
					addConcernParent(node, selectedConcern);
					return true;
				}						
			}
		}
		return false;
				
	}//addConcernClass

	private void addConcernParent(Node node, String concern) {
		
		while (node.getProperty(Properties.PARENT) != null) {
			node = (Node) node.getProperty(Properties.PARENT);
			addConcern(node, concern);
		}
		
	}//addConcernParent
	
	private void addConcern(Node node, String selectedConcern) {
		
		String[] list = (String[]) node.getProperty(Properties.CONCERNS);
		int oldSize = list.length;
		for (int i = 0; i < oldSize; ++i) {
			String concern = list[i];
			if (selectedConcern.equals(concern))
				return;
		}
		String[] newList = new String[oldSize + 1];
		System.arraycopy(list, 0, newList, 0, oldSize);
		newList[list.length] = selectedConcern;
		node.setProperty(Properties.NUMBER_CONCERNS, newList.length);
		node.setProperty(Properties.CONCERNS, newList);
		
	}//addConcern
	

}//class
