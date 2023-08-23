package aimv.tools;

import org.eclipse.core.runtime.ListenerList;

import aimv.events.ToolEvent;
import aimv.listeners.IToolListener;

public abstract class Tool {
	
	private ListenerList listeners = new ListenerList();
	protected abstract void activeTool(Object[] args);
       
	public void active(Object[] args) {
		
		activeTool(args);
		ToolEvent event = new ToolEvent(this);
		for (Object listener : listeners.getListeners())
			((IToolListener) listener).activeTool(event);
		
	}//apply
	
	public synchronized void addListener(IToolListener listener) {
		listeners.add(listener);
    }//addListener

    public synchronized void removeListener(IToolListener listener) {
    	listeners.remove(listener);
    }//removeListener
    
    public synchronized void removeAllListeners() {
		listeners.clear();
    }//removeAllListeners
	

}//abstract class
