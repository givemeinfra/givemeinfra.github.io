package views.polymetric.actions;

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

import views.polymetric.utilities.Properties;
import aimv.controllers.AIMV;


public class RelationAction implements IViewActionDelegate, IMenuCreator, SelectionListener {

	private Menu menu;
	private int option = 0;
	
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
		String[] textos = {"childrens","subclasses"};
		
		//AIMV.setProperty(Properties.POLIMETRIC_RELATION, item.getText());
		for (int i = 0; i < textos.length; i++)
			createItem(menu, textos[i]);
		
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
		MenuItem item = (MenuItem) e.getSource();
		option = menu.indexOf(item);
		AIMV.setProperty(Properties.POLIMETRIC_RELATION, item.getText());
	}//widgetSelected

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

}//class
