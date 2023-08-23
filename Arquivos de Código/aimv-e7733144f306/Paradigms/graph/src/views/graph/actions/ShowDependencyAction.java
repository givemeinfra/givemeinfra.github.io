package views.graph.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import views.graph.utilities.Properties;

import aimv.controllers.AIMV;

public class ShowDependencyAction implements IViewActionDelegate {
	
	@Override
	public void run(IAction action) {
		Object property = AIMV.getProperty(Properties.GRAPH_SHOW);
		if (property instanceof Boolean)
			AIMV.setProperty(Properties.GRAPH_SHOW, !((Boolean)property));
		else
			AIMV.setProperty(Properties.GRAPH_SHOW, true);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

	@Override
	public void init(IViewPart view) {}
	

}//class
