package aimv.events;

import aimv.modeling.Group;
import aimv.modeling.Node;


public class GroupEvent {
	
	private Group group;
	public Node node;
	public String id;
	public Group previousGroup;
	
	public GroupEvent(Group group) {
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}

	
}//class
