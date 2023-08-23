package views.grid.actions;

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

public class GridSortAction extends Action implements IMenuCreator, SelectionListener {

	private Menu menu;
	private String sort;
	private GridView view;
	
	public GridSortAction(GridView v){
		
		view = v;
		sort = "Decrescent";
		setToolTipText("Grid Sort");
		setMenuCreator(this);
		setImageDescriptor(Icons.GRID_SORT);
		AIMV.setProperty(getToolTipText(), sort);
		
	}//GridPropertyAction
	
	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}
	
	@Override
	public Menu getMenu(Control parent) {
		
		dispose();
		menu = new Menu(parent);
		createItem(menu, "Empty");
		createItem(menu, "Crescent");
		createItem(menu, "Decrescent");
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
			
		if (sort.equals(text))
			item.setSelection(true);
			
	}//createItem

	@Override
	public void widgetSelected(SelectionEvent e) {
		sort = ((MenuItem) e.getSource()).getText();
		view.setSort(sort);
		AIMV.setProperty(getToolTipText(), sort);
	}//widgetSelected

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	
}//class
