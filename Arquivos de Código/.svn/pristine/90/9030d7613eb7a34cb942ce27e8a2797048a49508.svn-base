package views.grid.actions;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import views.grid.GridView;
import views.grid.utilities.Icons;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.events.ControllerEvent;
import aimv.events.ViewAIMVEvent;
import aimv.listeners.IControllerListener;
import aimv.listeners.IViewAIMVListener;
import aimv.modeling.Node;

public class GridPropertyAction extends Action implements IViewAIMVListener, IControllerListener, IMenuCreator, SelectionListener {

	private Menu menu;
	private GridView view;
	private String property;
	private String[] properties;
	
	public GridPropertyAction(GridView v){
		
		view = v;
		findProperties();
		setToolTipText("Grid Property");
		setMenuCreator(this);
		setImageDescriptor(Icons.GRID_PROPERTY);
		AIMV.setProperty(getToolTipText(), property);
		AIMV.addListener(this);
		view.addListener(this);
		
	}//GridPropertyAction
	
	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}
	
	@Override
	public Menu getMenu(Control parent) {
		
		dispose();
		menu = new Menu(parent);
		if (properties != null) {
			for (String text : properties)
				createItem(menu, text);
		}
		return menu;
		
	}//getMenu(Control parent) 
	
	@Override
	public void dispose() {
		if (menu != null)
			menu.dispose();
	}
	
	private void createItem(Menu parent, String text){
		
		MenuItem item = new MenuItem(parent, SWT.CHECK);		
		item.setText(text);
		item.addSelectionListener(this);
			
		if (property.equals(text))
			item.setSelection(true);
			
	}//createItem

	@Override
	public void widgetSelected(SelectionEvent e) {
		property = ((MenuItem) e.getSource()).getText();
		view.setProperty(property);
		AIMV.setProperty(getToolTipText(), property);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}
	
	private void findProperties() {
		
		int cont;
		ArrayList<String> list = new ArrayList<String>();
		for (String group : Nodes.getGroups()) {
			cont = 0;
			for (Node node : Nodes.getGroup(group).getNodes()) {
				for (String prop : node.getAllProperties()) {
					if (node.getProperty(prop) instanceof Integer && !list.contains(prop))
						list.add(prop);
				}
				cont++;
				if (cont == 100)
					break;
			}
		}
	
		Collections.sort(list);
		properties = list.toArray(new String[0]);
		
		if (properties.length == 0)
			properties = null;
		
		if (list.contains(property))
			return;
		else {
			if (properties != null)
				property = properties[0];
			else
				property = null;
		}
		
	}//findProperties
	
	@Override
	public void changeProperty(ControllerEvent event) {}//changeProperty
	
	@Override
	public void startImport(ControllerEvent event) {}//startImport

	@Override
	public void stopImport(ControllerEvent event) {
		findProperties();
		view.setProperty(property);
		AIMV.setProperty(getToolTipText(), property);
	}//stopImport

	@Override
	public void closed(ViewAIMVEvent event) {
		AIMV.removeListener(this);
		view.removeListener(this);
	}
	
	@Override
	public void open(ViewAIMVEvent event) {}

	@Override
	public void focus(ViewAIMVEvent event) {}

	@Override
	public void layout(ViewAIMVEvent event) {}

	@Override
	public void resize(ViewAIMVEvent event) {}
	
	
}//class
