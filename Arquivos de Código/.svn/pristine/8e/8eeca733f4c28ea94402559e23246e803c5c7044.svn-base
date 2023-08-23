package sourceminer.modules;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.Document;

import sourceminer.utilities.Groups;
import sourceminer.utilities.Properties;
import aimv.controllers.Nodes;
import aimv.modeling.Node;
import aimv.modules.Module;

public class SizeModule extends Module {


	protected void start(Object fonte, IProgressMonitor monitor) {
		
		if (!(fonte instanceof IJavaProject)) 
			return;
		
		for (Node node : Nodes.getGroup(Groups.METHOD).getNodes()) {
			node.setProperty(Properties.SIZE, 1);
			monitor.subTask("Calculating the size method " + node.getID());
			try {
				IMethod method = (IMethod) node.getProperty(Properties.JAVA_ELEMENT);
				node.setProperty(Properties.SIZE, new Document(method.getSource()).getNumberOfLines());
			} catch (JavaModelException e) {}
		}
		
		for (Node node : Nodes.getGroup(Groups.CLASS).getNodes()) {
			node.setProperty(Properties.SIZE, 1);
			monitor.subTask("Calculating the size class " + node.getID());
			try {
				IType type = (IType) node.getProperty(Properties.JAVA_ELEMENT);
				node.setProperty(Properties.SIZE, new Document(type.getSource()).getNumberOfLines());
			} catch (JavaModelException e) {}
		}
		
		for (Node node : Nodes.getGroup(Groups.PACKAGE).getNodes()) {
			Node[] list = (Node[]) node.getProperty(Properties.CHILDRENS);
			monitor.subTask("Calculating the size package " + node.getID());
			int size = 0;
			for (Node filho : list)
				size += (Integer) filho.getProperty(Properties.SIZE);
			node.setProperty(Properties.SIZE, size);
		}
		
		for (Node node : Nodes.getGroup(Groups.PROJECT).getNodes()) {
			try
			{
				Node[] list = (Node[]) node.getProperty(Properties.CHILDRENS);
				monitor.subTask("Calculating the size project " + node.getID());
				int size = 0;
				for (Node filho : list)
					size += (Integer) filho.getProperty(Properties.SIZE);
				node.setProperty(Properties.SIZE, size);
			}
			catch(Exception e){}
		}	
		
		
	}//start
	

}//class
