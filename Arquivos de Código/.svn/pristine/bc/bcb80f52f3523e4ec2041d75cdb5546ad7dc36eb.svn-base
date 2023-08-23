package views.treemap.actions;

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

import views.treemap.TreeMap;
import views.treemap.utilities.Properties;
import aimv.controllers.AIMV;

public class AreaAction implements IViewActionDelegate, IMenuCreator, SelectionListener {
	
	private Menu menu;
	private TreeMap view;
	
	@Override
	public void run(IAction action) {}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		action.setMenuCreator(this);
	}

	@Override
	public void init(IViewPart view) {
		this.view = (TreeMap) view;
	}
	
	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}
	
	@Override
	public Menu getMenu(Control parent) {
		
		dispose();
		menu = new Menu(parent);
		
		if (view.properties != null) {
			for (String property : view.properties)
				createItem(menu, property);
		}
		return menu;
		
	}//getMenu(Control parent) 
	
	@Override
	public void dispose() {
		if (menu != null)
			menu.dispose();
	}//dispose
	
	private void createItem(Menu parent, String text){
		
		MenuItem item = new MenuItem(parent, SWT.CHECK);		
		item.setText(text);
		item.addSelectionListener(this);
		
		if (text.equals(AIMV.getProperty(Properties.TREEMAP_AREA)))
			item.setSelection(true);
		
	}//createItem

	@Override
	public void widgetSelected(SelectionEvent e) {
		AIMV.setProperty(Properties.TREEMAP_AREA, ((MenuItem) e.getSource()).getText());
	}//widgetSelected

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}	
	

}//class

