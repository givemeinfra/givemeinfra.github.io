package views.properties.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.modeling.Group;
import aimv.utilities.Properties;

public class ZoomOutHandler extends AbstractHandler {

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		List<Group> gruposZoom = (List<Group>) AIMV.getProperty("gruposZoom");
		if (gruposZoom.size() == 0)
			return null;
		
		gruposZoom.remove(gruposZoom.size() - 1);	
		if (gruposZoom.size() == 0) {			
			Nodes.removeGroup("groupZoom");
			AIMV.setProperty(Properties.GROUP_SELECTED, AIMV.getProperty("groupPrevious"));
		}
		else {
			Nodes.setGroup("groupZoom", gruposZoom.get(gruposZoom.size() - 1));
	 		AIMV.setProperty(Properties.GROUP_SELECTED, "groupZoom");
		}
		return null;
		
	}//execute

}
