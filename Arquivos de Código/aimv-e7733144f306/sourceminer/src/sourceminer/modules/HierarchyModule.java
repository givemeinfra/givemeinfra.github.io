package sourceminer.modules;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import sourceminer.utilities.Groups;
import sourceminer.utilities.Properties;
import aimv.controllers.Nodes;
import aimv.modeling.Node;
import aimv.modules.Module;

public class HierarchyModule extends Module {
	

	@Override
	protected void start(Object fonte, IProgressMonitor monitor) {
		
		if (!(fonte instanceof IJavaProject) || Nodes.getGroup(Groups.ALL) == null) 
			return;
						
		for (Node node : Nodes.getGroup(Groups.CLASS).getNodes()) {
			monitor.subTask("Analyzing hierarchy the " + node.getID());
			IType type = (IType) node.getProperty(Properties.JAVA_ELEMENT); 
			try {
				IType[] list = type.newTypeHierarchy(null).getSubclasses(type);
				ArrayList<Node> nodes = new ArrayList<Node>();
				for (IType t : list) {
					Node sub = Nodes.getGroup(Groups.CLASS).getNode(t.getFullyQualifiedName('.'));
					if (sub != null) {
						nodes.add(sub);
						sub.setProperty(Properties.SUPERCLASS, node);
					}
				}
				node.setProperty(Properties.SUBCLASSES, nodes.toArray(new Node[0]));	
			} catch (JavaModelException e) {}
		}		
		
	}//start

	
}//class
