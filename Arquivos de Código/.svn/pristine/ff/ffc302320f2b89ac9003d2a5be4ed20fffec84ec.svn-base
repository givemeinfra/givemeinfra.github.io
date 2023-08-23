package aimv.controllers;

import java.util.LinkedHashMap;
import java.util.Set;

import org.eclipse.core.runtime.ListenerList;

import aimv.events.GroupEvent;
import aimv.listeners.IGroupListener;
import aimv.modeling.Group;


public class Nodes {

	
	private static final Nodes controller = new Nodes();
	private static LinkedHashMap<String, Group> groups;
	private static ListenerList listeners = new ListenerList();
	
	public Nodes() {
		groups = new LinkedHashMap<String, Group>();
	}
	
	public static Nodes getDefault() {
		return controller;
	}//getDefault
	
	public static Group getGroup(String id) {
		return groups.get(id);
	}
	
	public static Set<String> getGroups() {
		return groups.keySet();
	}

	public static void setGroup(String id, Group group) {
		
		if (id == null)
			return;
		
		Group valor = groups.put(id, group);
		
		boolean flag = true;
		if (valor != null)
			flag = valor.equals(group);
		else if (group != null)
			flag = group.equals(valor);
	
		if (!flag) {
			GroupEvent event = new GroupEvent(group); 
			event.id = id;
			event.previousGroup = valor;
			for (Object listener : listeners.getListeners())
				((IGroupListener) listener).setGroup(event);
		}
		
	}//setGroup
	
	public static void removeGroup(String id) {
		if (groups.containsKey(id)) {
			GroupEvent event = new GroupEvent(groups.remove(id)); 
			event.id = id;
			for (Object listener : listeners.getListeners())
				((IGroupListener) listener).removeGroup(event);
		}
	}//removeGroup
	
	public static void removeAllGroups() {
		groups.clear();
	}//removeGroup
	
	public static synchronized void addListener(IGroupListener listener) {
        listeners.add(listener);
    }//addListener

    public static synchronized void removeListener(IGroupListener listener) {
    	listeners.remove(listener);
    }//removeListener
    
    public static synchronized void removeAllListeners() {
		listeners.clear();
    }//removeAllListeners
	
	
}//class
