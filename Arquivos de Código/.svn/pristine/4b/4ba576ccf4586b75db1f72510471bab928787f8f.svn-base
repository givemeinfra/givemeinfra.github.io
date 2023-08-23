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
import aimv.views.Paradigm;
import aimv.views.ViewAIMV;

public class DataViewsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		try {

			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			
			ArrayList<ViewAIMV> list = new  ArrayList<ViewAIMV>();
			for (ViewAIMV view : AIMV.getViews()) {
				if (view instanceof Paradigm)
				{
					if(SessionProgram.getInstance().getUsuario() == 1 &&  verifyAcceptedViews(1, view.getTitle()))
						list.add(view);
					if(SessionProgram.getInstance().getUsuario() == 0 &&  verifyAcceptedViews(0, view.getTitle()))
						list.add(view);
				}
			}

			ElementListSelectionDialog dialog = new ElementListSelectionDialog(window.getShell(), new ViewLabel());
			dialog.setSize(50, 20);
			dialog.setTitle("Data Views");
			dialog.setMultipleSelection(true);
			dialog.setElements(list.toArray());
			dialog.open();
			Object[] r = dialog.getResult();
			if (r != null) {
				for (Object ob : r)
					window.getActivePage().showView(((ViewAIMV)ob).getID());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error opening data view");
		}

		return null;
		
	}
	
	private Boolean verifyAcceptedViews(int idSession, String viewName)
	{
		if(idSession == 1)
		{
		    if(viewName.equals("Polymetric") || viewName.equals("TreeMap"))
		    	return false;
		    return true;
		}
		else
		{
			if(viewName.equals("Metrics") || viewName.equals("Modules and Components") || viewName.equals("Radial View") || viewName.equals("Tree View") || viewName.equals("Comparison View"))
		    	return false;
		    return true;
		}
	}
	

}
