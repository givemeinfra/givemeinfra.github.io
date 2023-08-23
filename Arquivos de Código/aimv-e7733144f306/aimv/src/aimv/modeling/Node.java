package aimv.modeling;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.eclipse.core.runtime.ListenerList;

import aimv.events.NodeEvent;
import aimv.filters.Filter;
import aimv.listeners.INodeListener;


public class Node {
	
	private String id;
	private List<Filter> filters;
	private List<Relation> relations;
	private ListenerList listeners = new ListenerList();
	private LinkedHashMap<String, Object> properties;
	
	public Node(String id) {
		this.id = id;
		this.properties = new LinkedHashMap<String, Object>();
		setProperty("filtered", false);
	}//Node

	public String getID() {
		return id;
	}
	
	public void setID(String newID)
	{
		this.id = newID;
	}
	
	public String toString() {
		return "Node: "+id; 
	}
	
	public Object getProperty(String property) {
		return properties.get(property);
	}
	
	public Set<String> getAllProperties() {
		return properties.keySet();
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
			NodeEvent event = new NodeEvent(this);
			event.property = property;
			event.previousValue = valor;
			event.afterValue = value;
			for (Object listener : listeners.getListeners())
				((INodeListener) listener).changeProperty(event);
		}
		
	}//setProperty
	
	public void addRelation(Relation relation) {
		
		if (relation == null)
			return;
		
		if (relations == null)
			relations = new ArrayList<Relation>();
		if (relations.contains(relation))
			return;
		
		relations.add(relation);
		NodeEvent event = new NodeEvent(this);
		event.relation = relation;
		for (Object listener : listeners.getListeners())
			((INodeListener) listener).addRelation(event);
	
	}//addRelation
	
	public void removeRelation(Relation relation) {
		
		if (relation == null || relations == null)
			return;
		
		if (!relations.contains(relation))
			return;
		
		relations.remove(relation);
		if (relations.size() == 0)
			relations = null;
		
		NodeEvent event = new NodeEvent(this);
		event.relation = relation;
		for (Object listener : listeners.getListeners())
			((INodeListener) listener).removeRelation(event);
		
	}//removeRelation
	
	public List<Relation> getRelations() {
		if (relations == null)
			return new ArrayList<Relation>();
		return relations;
	}
	
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
		NodeEvent event = new NodeEvent(this);
		event.filter = filter;
		for (Object listener : listeners.getListeners())
			((INodeListener) listener).addFilter(event);
		
		for (Relation relation : getRelations())
			relation.addFilter(filter);
	
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
		
		NodeEvent event = new NodeEvent(this);
		event.filter = filter;
		for (Object listener : listeners.getListeners())
			((INodeListener) listener).removeFilter(event);
		
		for (Relation relation : getRelations())
			relation.removeFilter(filter);
		
	}//removeFilter

	public synchronized void addListener(INodeListener listener) {
		listeners.add(listener);
    }//addListener

    public synchronized void removeListener(INodeListener listener) {
    	listeners.remove(listener);
    }//removeListener
    
    public synchronized void removeAllListeners() {
		listeners.clear();
    }//removeAllListeners
    

}//class
