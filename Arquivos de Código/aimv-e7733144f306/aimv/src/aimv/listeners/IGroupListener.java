	package aimv.listeners;

import aimv.events.GroupEvent;

public interface IGroupListener {
	
	public void setGroup(GroupEvent event);
	
	public void removeGroup(GroupEvent event);
	
	public void addNode(GroupEvent event);
	
	public void removeNode(GroupEvent event);


}//interface
