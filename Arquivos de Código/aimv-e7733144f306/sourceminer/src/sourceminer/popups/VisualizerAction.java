package sourceminer.popups;


import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import aimv.controllers.AIMV;
import aimv.controllers.SessionProgram;

public class VisualizerAction implements IActionDelegate {

	private IJavaProject project;
	
	@Override
	public void run(IAction action) {
		//Start SourceMiner Session
		SessionProgram session =  SessionProgram.getInstance();
		session.setUsuario(0);
		
		AIMV.setFonte(getProject());		    
	}//run
	
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
