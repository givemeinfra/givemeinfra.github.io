package com.br.collaborativeAIMV.listeners;

import aimv.events.ViewAIMVEvent;
import aimv.listeners.IViewAIMVListener;

import com.br.collaborativeAIMV.control.ViewControl;

public class ViewListener implements IViewAIMVListener{

	@Override
	public void closed(ViewAIMVEvent e) {
		// TODO Auto-generated method stub
		System.out.println("CLOSED");
		ViewControl.GET_INSTANCE().removeViewFromAuxiliarViews(e.getView());
		//e.getView().removeAllListeners();
	}

	@Override
	public void focus(ViewAIMVEvent e) {
		// TODO Auto-generated method stub
		ViewControl.GET_INSTANCE().setFocusedView(e.getView());
		System.out.println("FOCUS");
	}

	@Override
	public void layout(ViewAIMVEvent e) {
		// TODO Auto-generated method stub
		System.out.println("LAYOUT");
	}

	@Override
	public void open(ViewAIMVEvent e) {
		// TODO Auto-generated method stub
		System.out.println("OPEN");
	}

	@Override
	public void resize(ViewAIMVEvent e) {
		// TODO Auto-generated method stub
		System.out.println("RESIZE");
	}

}
