package aimv.events;

import aimv.views.UtilityView;


public class UtilityViewEvent {
	
	private UtilityView utilityView;
	
	public UtilityViewEvent(UtilityView utilityView) {
		this.utilityView = utilityView;
	}

	public UtilityView getUtilityView() {
		return utilityView;
	}

	
}//class
