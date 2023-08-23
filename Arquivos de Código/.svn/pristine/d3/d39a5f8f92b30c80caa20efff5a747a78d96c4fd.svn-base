package sourceminer.modules;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;

import sourceminer.utilities.Groups;
import sourceminer.utilities.Properties;
import aimv.controllers.Nodes;
import aimv.modeling.Node;
import aimv.modules.Module;

public class NumElementsModule extends Module {

	
	protected void start(Object fonte, IProgressMonitor monitor) {
		
		if (!(fonte instanceof IJavaProject)) 
			return;
		
		for (Node node : Nodes.getGroup(Groups.METHOD).getNodes()) {
			monitor.subTask("Calculating the number methods " + node.getID());
			node.setProperty(Properties.NUMBER_METHODS, 0);
			node.setProperty(Properties.NUMBER_CLASSES, 0);
			node.setProperty(Properties.NUMBER_PACKAGES, 0);
		}
		
		for (Node node : Nodes.getGroup(Groups.CLASS).getNodes()) {
			monitor.subTask("Calculating the number methods " + node.getID());
			Node[] list = (Node[]) node.getProperty(Properties.CHILDRENS);
			node.setProperty(Properties.NUMBER_METHODS, list.length);
			node.setProperty(Properties.NUMBER_CLASSES, 0);
			node.setProperty(Properties.NUMBER_PACKAGES, 0);
		}
		
		for (Node node : Nodes.getGroup(Groups.PACKAGE).getNodes()) {
			Node[] list = (Node[]) node.getProperty(Properties.CHILDRENS);
			monitor.subTask("Calculating the number methods " + node.getID());
			int num = 0;
			for (Node filho : list)
				num += (Integer) filho.getProperty(Properties.NUMBER_METHODS);
			node.setProperty(Properties.NUMBER_METHODS, num);
			node.setProperty(Properties.NUMBER_CLASSES, list.length);
			node.setProperty(Properties.NUMBER_PACKAGES, 0);
		}
		
		for (Node node : Nodes.getGroup(Groups.PROJECT).getNodes()) {
			try
			{
				Node[] list = (Node[]) node.getProperty(Properties.CHILDRENS);
				monitor.subTask("Calculating the number methods " + node.getID());
				int num = 0;
				for (Node filho : list)
					num += (Integer) filho.getProperty(Properties.NUMBER_METHODS);
				node.setProperty(Properties.NUMBER_METHODS, num);
				num = 0;
				for (Node filho : list)
					num += (Integer) filho.getProperty(Properties.NUMBER_CLASSES);
				node.setProperty(Properties.NUMBER_CLASSES, num);
				node.setProperty(Properties.NUMBER_PACKAGES, list.length);
			}catch(Exception e){}
		}	
		
		
	}//start

	
}//class
