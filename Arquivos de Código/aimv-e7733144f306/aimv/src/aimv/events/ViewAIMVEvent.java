package aimv.events;

import aimv.views.ViewAIMV;


public class ViewAIMVEvent {
	
	private ViewAIMV view;
	
	public ViewAIMVEvent(ViewAIMV view) {
		this.view = view;
	}

	public ViewAIMV getView() {
		return view;
	}

	
}//class
