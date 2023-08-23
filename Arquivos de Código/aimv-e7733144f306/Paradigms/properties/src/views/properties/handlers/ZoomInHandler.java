package views.properties.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.events.ControllerEvent;
import aimv.events.ViewAIMVEvent;
import aimv.listeners.IControllerListener;
import aimv.listeners.IViewAIMVListener;
import aimv.modeling.Group;
import aimv.modeling.Node;
import aimv.utilities.Properties;

public class ZoomInHandler extends AbstractHandler implements IControllerListener, IViewAIMVListener {

	public ZoomInHandler() {
		AIMV.getView("aimv.views.propertiesview").addListener(this);
		AIMV.setProperty("gruposZoom", new ArrayList<Group>());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
			
		List<Node> nodes = (List<Node>) AIMV.getProperty("aimv.views.propertiesview_nodes_selected");
		if (nodes == null)
			return null;
		
		List<Group> groupsZoom = (List<Group>) AIMV.getProperty("gruposZoom");
		if (groupsZoom.size() == 0) {
			AIMV.setProperty("groupPrevious", AIMV.getProperty(Properties.GROUP_SELECTED));
			AIMV.addListener(this);
		}
		
		Group groupZoom = new Group();
		for (Node node : nodes)
			groupZoom.addNode(node);
			
 		groupsZoom.add(groupZoom);
		Nodes.setGroup("groupZoom", groupZoom);
 		AIMV.setProperty(Properties.GROUP_SELECTED, "groupZoom");
 		
		return null;
	}
	
	@Override
	public void startImport(ControllerEvent event) {}

	@Override
	public void stopImport(ControllerEvent event) {}

	@Override
	public void open(ViewAIMVEvent event) {}

	@Override
	public void focus(ViewAIMVEvent event) {}

	@Override
	public void closed(ViewAIMVEvent event) {
		AIMV.removeListener(this);
		Nodes.removeGroup("groupZoom");
		AIMV.setProperty("gruposZoom", null);
	}

	@Override
	public void layout(ViewAIMVEvent event) {}

	@Override
	public void resize(ViewAIMVEvent event) {}

	@SuppressWarnings("unchecked")
	@Override
	public void changeProperty(ControllerEvent event) {
		if (event.property.equals(Properties.GROUP_SELECTED) && !event.afterValue.equals("groupZoom")) {
			AIMV.removeListener(this);
			Nodes.removeGroup("groupZoom");
			List<Group> groupsZoom = (List<Group>) AIMV.getProperty("gruposZoom");
			groupsZoom.clear();
		}
	}

}
