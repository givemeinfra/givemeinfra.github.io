package aimv.listeners;

import aimv.events.FilterEvent;

public interface IFilterListener {
	
	public void applyFilter(FilterEvent event);
	
	public void removeFilter(FilterEvent event);

}
