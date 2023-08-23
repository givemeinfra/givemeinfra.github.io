package sourceminer.tools.actions;

import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;

import sourceminer.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.modeling.Node;

public class SourceCodeAction implements IViewActionDelegate {


	@SuppressWarnings("unchecked")
	@Override
	public void run(IAction action) {

		BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
			public void run() {
				
				List<Node> nodes = (List<Node>) AIMV.getProperty("aimv.views.propertiesview_nodes_selected"); 
		  		if (nodes == null)
		  			return;
		  		
		  		for (Node node : nodes){
		  			IJavaElement element = ((IJavaElement) node.getProperty(Properties.JAVA_ELEMENT));		 
		  			try {
		  				JavaUI.openInEditor(element, true, true);
		  			} catch (PartInitException e) {
		  				e.printStackTrace();
		  			} catch (JavaModelException e) {
		  				e.printStackTrace();
		  			}				
		  		}
		  		
			}
		});	
	}//run

	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

	@Override
	public void init(IViewPart view) {}
	

}//class

