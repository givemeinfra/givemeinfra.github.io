package com.br.collaborativeAIMV.view.actionSetCalls;

import javax.swing.JOptionPane;

import givemeviews.views.provider.MasterProvider;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import aimv.controllers.SessionProgram;

import com.br.collaborativeAIMV.view.templates.TemplateWizard;


public class LoginScreen implements IWorkbenchWindowActionDelegate{
	protected IWorkbenchWindow window;
	
	@Override
	public void run(IAction action) {
		
		SessionProgram session = SessionProgram.getInstance();
		MasterProvider provider = new MasterProvider();
		if(session.getUsuario() == 1 && provider.providerProgramDataSource() == false) // giveme views not started completely
		{
			JOptionPane.showMessageDialog(null, "First, go to 'Utility Views / GiveMe Views'");
		}
		else
		{		
			// TODO Auto-generated method stub
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
