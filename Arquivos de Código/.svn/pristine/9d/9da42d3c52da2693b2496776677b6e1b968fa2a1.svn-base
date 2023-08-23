package aimv.handlers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import aimv.controllers.AIMV;
import aimv.controllers.SessionProgram;
import aimv.core.ViewLabel;
import aimv.views.UtilityView;
import aimv.views.ViewAIMV;

public class UtilityViewsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		try {

			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			
			ArrayList<ViewAIMV> list = new  ArrayList<ViewAIMV>();
			for (ViewAIMV view : AIMV.getViews()) {
				if (view instanceof UtilityView)
				{
					if(SessionProgram.getInstance().getUsuario() == 1 && verifyAceptedGiveMeViews(view.getTitle()))
						list.add(view);
					if(SessionProgram.getInstance().getUsuario() == 0 &&  verifyAcceptedViews(view.getTitle()))
						list.add(view);
				}
			}
			
			ElementListSelectionDialog dialog = new ElementListSelectionDialog(window.getShell(), new ViewLabel());
			dialog.setSize(50, 20);
			dialog.setTitle("Utility Views");
			dialog.setMultipleSelection(true);
			dialog.setElements(list.toArray());
			dialog.open();
			Object[] r = dialog.getResult();
			if (r != null) {
				for (Object ob : r)
					window.getActivePage().showView(((ViewAIMV)ob).getID());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error opening utility view");
		}

		return null;
		
	}
	
	private Boolean verifyAcceptedViews(String viewName)
	{
	    if(viewName.equals("Output View") || viewName.equals("GiveMe Views") || viewName.equals("GiveMe Trace"))
	    	return false;
	    return true;
	}
	
	private Boolean verifyAceptedGiveMeViews(String viewName)
	{
		if(viewName.equals("SourceCompare View"))
			return false;
		return true;
	}
	

}
