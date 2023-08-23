package givemeviews.actions;

import javax.swing.JOptionPane;

import givemeviews.persistence.MemoryApplication;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import aimv.controllers.SessionProgram;

import prefuse.demosRadial.*;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class RecursionAnalysisAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private RadialGraphView radial;
	/**
	 * The constructor.
	 */
	public RecursionAnalysisAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) 
	{
		SessionProgram session = SessionProgram.getInstance();
		if(session.getUsuario() == 1 && MemoryApplication.getGeneratedSource() == true)
		{	
			String aux = MemoryApplication.getGmmRepositoryPath();
			aux = aux.replace("\\", "/");			
			String pathXML = aux + "/Brain/radialview.xml";
			
			String param[] = new String[2];
			param[0] = pathXML;
			param[1] = "name";
			
			radial = new RadialGraphView();			
			RadialGraphView.main(param);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "First, go to 'Utility Views / GiveMe Views'");
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}