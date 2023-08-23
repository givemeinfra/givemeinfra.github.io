package aimv.views;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import aimv.controllers.AIMV;
import aimv.events.ViewAIMVEvent;
import aimv.listeners.IViewAIMVListener;

public abstract class ViewAIMV extends ViewPart {
	
	protected abstract void createLayout();
	protected abstract void open();
	protected abstract void closed();
	
	
	private Composite composite;
	private ListenerList listeners = new ListenerList();
	

	@Override
	public void createPartControl(Composite parent) {
		composite = parent;
		AIMV.getActiveViews().add(this);
		open();
		ViewAIMVEvent event = new ViewAIMVEvent(this);                
		for (Object listener : listeners.getListeners())
			((IViewAIMVListener) listener).open(event);
		layout();
	}//createPartControl
    
    public void layout() {
		if (!AIMV.flag)
			return;
    	createLayout();
		ViewAIMVEvent event = new ViewAIMVEvent(this);                
		for (Object listener : listeners.getListeners())
			((IViewAIMVListener) listener).layout(event);
	}//layout
	
	@Override
	public void setFocus() {
		ViewAIMVEvent event = new ViewAIMVEvent(this);                
		for (Object listener : listeners.getListeners())
			((IViewAIMVListener) listener).focus(event);
	}//setFocus
	
	@Override
	public void dispose() {
		super.dispose();
		closed();
		AIMV.getActiveViews().remove(this);
		ViewAIMVEvent event = new ViewAIMVEvent(this);                
		for (Object listener : listeners.getListeners())
			((IViewAIMVListener) listener).closed(event);
		removeAllListeners();
	}//dispose
	
	public void controlResize() {
		ViewAIMVEvent event = new ViewAIMVEvent(this);                
		for (Object listener : listeners.getListeners())
			((IViewAIMVListener) listener).resize(event);
	}//dispose

	public String getID() {
		return this.getConfigurationElement().getAttribute("id");
	}
	
	public Composite getComposite() {
		return composite;
	}
    
    public synchronized void addListener(IViewAIMVListener listener) {
		listeners.add(listener);
    }//addListener

    public synchronized void removeListener(IViewAIMVListener listener) {
    	listeners.remove(listener);
    }//removeListener
    
    public synchronized void removeAllListeners() {
		listeners.clear();
    }//removeAllListeners

    
}//abstract class

