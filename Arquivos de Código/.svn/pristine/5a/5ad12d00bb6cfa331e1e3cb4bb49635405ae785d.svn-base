package givemeviews.popup.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import aimv.controllers.AIMV;
import aimv.controllers.SessionProgram;
import givemeviews.model.Projeto;
import givemeviews.persistence.MemoryApplication;

public class VisualizerAction implements IActionDelegate {

	private IJavaProject project;
	
	/**
	 * Constructor for Action1.
	 */
	public VisualizerAction() {
		super();
	}	

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		// Start Session
		SessionProgram session = SessionProgram.getInstance();
		session.setUsuario(1);
		
		
		// Set a fonte e cria o group.		    		    
		AIMV.setFonte(getProject());		
				
		MemoryApplication.setGeneratedSource(false);
		
		System.gc();
		
		// Save the Project Name
		Projeto project = new Projeto(getProject().getElementName(), "", "", "");
		MemoryApplication.setProject(project);
		
	}	

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		setProject(JavaCore.create((IProject) ((IStructuredSelection) selection).getFirstElement()));
	}

	private void setProject(IJavaProject project){
		this.project = project;
	}
	
	public IJavaProject getProject(){
		return project;
	}

}
