package aimv.modeling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.ListenerList;

import aimv.events.RelationEvent;
import aimv.filters.Filter;
import aimv.listeners.IRelationListener;



public class Relation {
	
	private Node source;
	private Node target;
	private List<Filter> filters;
	private LinkedHashMap<String, Object> properties;
	private ListenerList listeners = new ListenerList();
	
	public LinkedHashMap<String, Object> getPropeties() {
		return this.properties;
	}
	
	public Relation(Node source, Node target) {
		
		this.source = source;
		this.target = target;
		this.properties = new LinkedHashMap<String, Object>();
		setProperty("filtered", false);
		
	}//Relation
	
	public Object getProperty(String property) {
		return properties.get(property);
	}

	public void setProperty(String property, Object value) {
		
		if (property == null)
			return;
		
		Object valor = properties.put(property, value);
		
		boolean flag = true;
		if (valor != null)
			flag = valor.equals(value);
		else if (value != null)
			flag = value.equals(valor);
	
		if (!flag) {
			RelationEvent event = new RelationEvent(this);
			event.property = property;
			event.previousValue = valor;
			event.afterValue = value;
			for (Object listener : listeners.getListeners())
				((IRelationListener) listener).changeProperty(event);
		}
		
	}//setProperty
	
	public Set<String> getAllProperties() {
		return properties.keySet();
	}
	
	/*public void setAllProperties(Object percent) {
		properties.clear();
		Map<String, Object> mapAux = new HashMap<String, Object>();
		mapAux.put("false", percent);
		properties.put("filtered=false", percent);
	}*/
	
	public boolean isFiltered() {
		return filters != null;
	}
	
	public void addFilter(Filter filter) {
		
		if (filter == null)
			return;
		
		if (filters == null)
			filters = new ArrayList<Filter>();
		if (filters.contains(filter))
			return;
		
		filters.add(filter);
		if (isFiltered())
			setProperty("filtered", true);
		
		RelationEvent event = new RelationEvent(this);
		event.filter = filter;
		for (Object listener : listeners.getListeners())
			((IRelationListener) listener).addFilter(event);
	
	}//addFilter
	
	public void removeFilter(Filter filter) {
		
		if (filter == null || filters == null)
			return;
		
		if (!filters.contains(filter))
			return;
		
		filters.remove(filter);
		if (filters.size() == 0)
			filters = null;
		
		if (!isFiltered())
			setProperty("filtered", false);
		
		RelationEvent event = new RelationEvent(this);
		event.filter = filter;
		for (Object listener : listeners.getListeners())
			((IRelationListener) listener).removeFilter(event);
		
	}//removeFilter

	public Node getSource() {
		return source;
	}

	public Node getTarget() {
		return target;
	}
	
	public synchronized void addListener(IRelationListener listener) {
        listeners.add(listener);
    }//addListener

    public synchronized void removeListener(IRelationListener listener) {
    	listeners.remove(listener);
    }//removeListener
    
    public synchronized void removeAllListeners() {
		listeners.clear();
    }//removeAllListeners
	

}//class
