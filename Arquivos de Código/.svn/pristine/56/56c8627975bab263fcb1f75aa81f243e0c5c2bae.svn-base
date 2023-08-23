package tools;

import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.events.ControllerEvent;
import aimv.listeners.IControllerListener;
import aimv.tools.Tool;
import aimv.utilities.Properties;

public class GroupsTool extends Tool implements IControllerListener {

	public GroupsTool() {
		AIMV.addListener(this);
	}
	
	@Override
	protected void activeTool(Object[] args) {
		String group = (String) args[0];
		AIMV.setProperty(Properties.GROUP_SELECTED, group);	
	}//activeTool

	@Override
	public void changeProperty(ControllerEvent event) {}

	@Override
	public void startImport(ControllerEvent event) {
		AIMV.setProperty(Properties.GROUP_SELECTED, "");
	}

	@Override
	public void stopImport(ControllerEvent event) {
		
		String group = "";
		String[] groups = Nodes.getGroups().toArray(new String[0]);
		if (groups.length != 0)
			group = groups[0];
		
		AIMV.setProperty(Properties.GROUP_SELECTED, group);
		
	}//stopImport
	

}//class
