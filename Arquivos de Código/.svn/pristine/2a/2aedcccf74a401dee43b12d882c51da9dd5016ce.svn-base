package aimv.modeling;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.eclipse.core.runtime.ListenerList;
import org.osgi.framework.hooks.service.ListenerHook;

import aimv.events.GroupEvent;
import aimv.listeners.IGroupListener;

public class Group {
	
	private LinkedHashMap<String, Node> nodes;
	private ListenerList listeners = new ListenerList();
	
	public Group() {
		nodes = new LinkedHashMap<String, Node>();
	}
	
	public Node getNode(String id) {
		return nodes.get(id);
	}
	
	public void addNode(Node node) {
		if (!nodes.containsKey(node.getID())) {
			nodes.put(node.getID(), node);
			GroupEvent event = new GroupEvent(this); 
			event.node = node;
			for (Object listener : listeners.getListeners())
				((IGroupListener) listener).addNode(event);
		}
	}//addNode
	
	public void removeNode(Node node) {
		if (!nodes.containsKey(node.getID())) {
			nodes.remove(node.getID());
			GroupEvent event = new GroupEvent(this); 
			event.node = node;
			for (Object listener : listeners.getListeners())
				((IGroupListener) listener).removeNode(event);
		}
	}//removeNode
	
	public Group removeAllNodes(Group groupCopy) {		
		LinkedHashMap<String, Node> nodes  = new LinkedHashMap<>();
		groupCopy.nodes = nodes;
		return groupCopy;
	}//remove all Nodes from the group
	
	public boolean contains(Node node) {
		return nodes.containsValue(node);
	}
	
	public Collection<Node> getNodes() {
		return nodes.values();
	}
	
	public synchronized void addListener(IGroupListener listener) {
        listeners.add(listener);
    }//addListener

    public synchronized void removeListener(IGroupListener listener) {
    	listeners.remove(listener);
    }//removeListener
    
    public synchronized void removeAllListeners() {
		listeners.clear();
    }//removeAllListeners

}//class
