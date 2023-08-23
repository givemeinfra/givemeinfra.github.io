package aimv.modules;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;

import aimv.listeners.IFilterListener;

public abstract class Module {
	
	private ListenerList listeners = new ListenerList();
	
	protected abstract void start(Object fonte, IProgressMonitor monitor);
	
	public void run(Object fonte, IProgressMonitor monitor) {
		start(fonte, monitor);
	}
	
	public synchronized void addListener(IFilterListener listener) {
		listeners.add(listener);
    }//addListener

    public synchronized void removeListener(IFilterListener listener) {
    	listeners.remove(listener);
    }//removeListener
    
    public synchronized void removeAllListeners() {
		listeners.clear();
    }//removeAllListeners

}//abstract class
