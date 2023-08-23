package com.br.collaborativeAIMV.listeners;

import org.eclipse.ui.PlatformUI;

import aimv.controllers.Nodes;
import aimv.events.ControllerEvent;
import aimv.listeners.IControllerListener;

import com.br.collaborativeAIMV.control.LoginControl;

public class ControllerListener implements IControllerListener{
	

	@Override
	public void changeProperty(ControllerEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startImport(ControllerEvent e) {
		// TODO Auto-generated method stub
//		System.out.println(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage());
	}

	@Override
	public void stopImport(ControllerEvent e) {
		// TODO Auto-generated method stub
		if(LoginControl.GET_INSTANCE().logado && Nodes.getGroup("project")!=null){
			LoginControl.GET_INSTANCE().verifyAnalysedProject();
		}
		
	}
	
}
