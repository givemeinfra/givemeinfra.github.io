package aimv.events;

import aimv.filters.Filter;
import aimv.modeling.Relation;


public class RelationEvent {
	
	private Relation relation;
	public Filter filter;
	public String property;
	public Object afterValue;
	public Object previousValue;
	
	public RelationEvent(Relation relation) {
		this.relation = relation;
	}

	public Relation getRelation() {
		return relation;
	}

	
}//class
