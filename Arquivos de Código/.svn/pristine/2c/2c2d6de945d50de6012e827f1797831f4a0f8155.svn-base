package com.br.collaborativeAIMV.control;

import java.util.ArrayList;
import java.util.Collection;

import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.views.ViewAIMV;

import com.br.collaborativeAIMV.listeners.ViewListener;

public class ViewControl {
	private static ViewControl viewControl;
	private String focusedViewString;
	private ViewAIMV focusedView;
	private Collection<ViewAIMV> auxiliarViews;
	
	public static ViewControl GET_INSTANCE(){
		if(ViewControl.viewControl==null){
			ViewControl.viewControl = new ViewControl();
			ViewControl.viewControl.auxiliarViews = new ArrayList<ViewAIMV>();
		}
		return ViewControl.viewControl;
	}
	
	public void setFocusedView(ViewAIMV view){
		String viewString = view.toString();
		String[] viewStrings = viewString.split("@");
		this.focusedView = view;
		this.focusedViewString = viewStrings[0];
	}
	
	public ViewAIMV getFocusedView(){
		return this.focusedView;
	}
	
	public String getFocusedViewString(){
		return this.focusedViewString;
	}
	
	public void addListenerOnViews(){
		if (Nodes.getGroup("project")!=null && LoginControl.GET_INSTANCE().isRightProjectAnalysed()) {
			Collection<ViewAIMV> views = AIMV.getActiveViews();
			for (ViewAIMV viewTemp : views) {
				viewTemp.addListener(new ViewListener());
			}
		}
	}
	
	public void addListenerOnViewsThread(){
		
		ThreadsControl.runThread(new Runnable() {
			
			@Override
			public void run() {
				Collection<ViewAIMV> views = AIMV.getActiveViews();
				if (views != null && views.size()!=0) {
					for (ViewAIMV viewTemp : views) {
						if(!auxiliarViews.contains(viewTemp)){
							auxiliarViews.add(viewTemp);
							viewTemp.addListener(new ViewListener());
						}
					}
				}
			}
		}); 
	}
	
	
	
	public void removeViewFromAuxiliarViews(ViewAIMV view){
		auxiliarViews.remove(view);
	}
	
	public void removeAllListenersOnViews(){
		Collection<ViewAIMV> views = AIMV.getActiveViews();
		if (views != null && views.size()!=0) {
			for (ViewAIMV viewTemp : views) {
				viewTemp.removeListener(new ViewListener());
				auxiliarViews.clear();
			}
		}
	}
}
