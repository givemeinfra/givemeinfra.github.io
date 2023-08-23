package aimv.core;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.PlatformUI;

import aimv.controllers.AIMV;
import aimv.events.ControllerEvent;
import aimv.listeners.IControllerListener;
import aimv.modules.Module;
import aimv.views.ViewAIMV;

public class ImportJob extends Job {
	
	private Object fonte;
	private ListenerList listeners;

	public ImportJob(String name, Object fonte, ListenerList listeners) {
		
		super(name);
		this.fonte = fonte;
		this.listeners = listeners;
		setUser(true);
		setPriority(Job.DECORATE);
			
	}//ImportJob

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		IStatus status = Status.OK_STATUS;
		monitor.beginTask("Importing ", AIMV.getModules().size() + 1);
		
		AIMV.flag = false;
			
		for (Module mod : AIMV.getModules()) {
			mod.run(fonte, monitor);
			monitor.worked(1);
		}

		ControllerEvent event = new ControllerEvent(AIMV.getDefault());  
		for (Object listener : listeners.getListeners())
			((IControllerListener) listener).stopImport(event);
		
		AIMV.flag = true;
		monitor.subTask("Refresh Views");
		refreshParadigms();
		monitor.worked(1);
		
		monitor.done();
		return status;
		
	}//run
	
	private void refreshParadigms() {
		
		
		
		Runnable run = new Runnable(){

			@Override
			public void run() {
				for (ViewAIMV paradigm : AIMV.getActiveViews())
					paradigm.layout();				
			}
		
		};
		//BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), run);
		PlatformUI.getWorkbench().getDisplay().syncExec(run);
		
		
				
	}//refreshParadigms

	
}//class
