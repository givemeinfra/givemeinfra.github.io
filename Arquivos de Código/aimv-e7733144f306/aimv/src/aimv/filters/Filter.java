package aimv.filters;

import org.eclipse.core.runtime.ListenerList;

import aimv.events.FilterEvent;
import aimv.listeners.IFilterListener;

public abstract class Filter {
	
	private ListenerList listeners = new ListenerList();
	
	protected abstract Object[] applyFilter(Object[] args);
    protected abstract Object[] removeFilter(Object[] args);
	
       
	public Object[] apply(Object[] args) {
		
		Object[] ret = applyFilter(args);                
		FilterEvent event = new FilterEvent(this);
		for (Object listener : listeners.getListeners())
			((IFilterListener) listener).applyFilter(event);
		return ret;	
		
	}//apply
	
	public Object[] remove(Object[] args) {
		
		Object[] ret = removeFilter(args);
		FilterEvent event = new FilterEvent(this);                
		for (Object listener : listeners.getListeners())
			((IFilterListener) listener).removeFilter(event);
		return ret;
		
	}//remove
	
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
