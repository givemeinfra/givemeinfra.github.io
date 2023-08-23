package aimv.listeners;

import aimv.events.UtilityViewEvent;

public interface IUtilityViewListener {
	
	public void utilityViewFocus(UtilityViewEvent event);
	
	public void utilityViewClose(UtilityViewEvent event);
	
	public void utilityViewLayout(UtilityViewEvent event);
	

}
