package views.graph.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import aimv.controllers.AIMV;

import views.graph.utilities.Properties;

public class CouplingOptionsAction implements IViewActionDelegate, IMenuCreator, SelectionListener {

	private Menu menu;
	private int option = 2;
	
	@Override
	public void run(IAction action) {}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		action.setMenuCreator(this);
	}

	@Override
	public void init(IViewPart view) {}
	
	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}
	
	@Override
	public Menu getMenu(Control parent) {
		
		dispose();
		menu = new Menu(parent);
		createItem(menu, "Afferent Coupling of Selected Nodes");
		createItem(menu, "Efferent Coupling of Selected Nodes");
		createItem(menu, "Afferent and Efferent Coupling of All Nodes");
		createItem(menu, "Afferent and Efferent Coupling of Selected Nodes");
		createItem(menu, "Afferent and Efferent Coupling Among Selected Nodes");
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
			
		if (menu.indexOf(item) == option)
			item.setSelection(true);
			
	}//createItem

	@Override
	public void widgetSelected(SelectionEvent e) {
		option = menu.indexOf((MenuItem) e.getSource());
		AIMV.setProperty(Properties.GRAPH_OPTIONS, option);
	}//widgetSelected

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

}//class
