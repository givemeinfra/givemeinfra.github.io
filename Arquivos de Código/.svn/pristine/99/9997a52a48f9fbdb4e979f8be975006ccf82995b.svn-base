package com.br.collaborativeAIMV.view.actionSetCalls;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.view.templates.TemplateWizard;


public class DeveloperScreen implements IWorkbenchWindowActionDelegate{
	protected IWorkbenchWindow window;
	
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		if (LoginControl.GET_INSTANCE().logado == true) {
			
			if (LoginControl.GET_INSTANCE().currentDeveloper.getCoordination() == true) {
				Shell shell = this.window.getShell();
				WizardDialog wd = new WizardDialog(shell, new TemplateWizard("desenvolvedor"));
				wd.setHelpAvailable(false);
				wd.setPageSize(600, 400);
				wd.open();
			} else {
				Shell shell = this.window.getShell();
				WizardDialog wd = new WizardDialog(shell, new TemplateWizard("accessDenied"));
				wd.setHelpAvailable(false);
				wd.setPageSize(200, 100);
				wd.open();
			}
			
		}else{
			Shell shell = this.window.getShell();
			WizardDialog wd = new WizardDialog(shell, new TemplateWizard("login"));
			wd.setHelpAvailable(false);
	        wd.setPageSize(200, 140);
	        wd.open();
		}
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		this.window = window;
	}

}
