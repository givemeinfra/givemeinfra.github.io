package aimv.views;

import org.eclipse.swt.widgets.Control;


public abstract class FilterView extends ViewAIMV {
	
	public void layout() {
		for (Control children : getComposite().getChildren())
			children.dispose();
		super.layout();
		getComposite().layout(true);
	}//layout
	
	
}//abstract class
