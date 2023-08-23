package tools;

import aimv.controllers.AIMV;
import aimv.events.ControllerEvent;
import aimv.listeners.IControllerListener;
import aimv.tools.Tool;
import aimv.utilities.Properties;

public class ColorsTool extends Tool implements IControllerListener {

	public ColorsTool() {
		AIMV.addListener(this);
	}
	
	@Override
	protected void activeTool(Object[] args) {
		String color = (String) args[0];	
		AIMV.setProperty(Properties.COLOR_SELECTED, color);
	}//activeTool

	@Override
	public void changeProperty(ControllerEvent event) {}

	@Override
	public void startImport(ControllerEvent event) {
		AIMV.setProperty(Properties.COLOR_SELECTED, "");
	}

	@Override
	public void stopImport(ControllerEvent event) {
		
		String color = "";
		if (AIMV.getProperty(Properties.COLORS) instanceof String[]) {
			String[] colors = (String[]) AIMV.getProperty(Properties.COLORS);
			if (colors.length != 0)
				color = colors[0];
		}
		
		AIMV.setProperty(Properties.COLOR_SELECTED, color);
		
	}//stopImport
	

}//class
