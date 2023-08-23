package aimv.events;

import aimv.filters.Filter;
import aimv.modeling.Node;
import aimv.modeling.Relation;


public class NodeEvent {
	
	private Node node;
	public String property;
	public Object previousValue;
	public Object afterValue;
	public Relation relation;
	public Filter filter;
	
	public NodeEvent(Node node) {
		this.node = node;
	}

	public Node getNode() {
		return node;
	}
	
	
}//class
