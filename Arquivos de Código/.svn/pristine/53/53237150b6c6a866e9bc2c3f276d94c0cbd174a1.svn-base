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

import aimv.controllers.AIMV;
import aimv.utilities.Imagens;

import views.polymetric.Polymetric;
import views.polymetric.utilities.Properties;


public class SizeMetricsAction implements IViewActionDelegate, IMenuCreator, SelectionListener {

	private Menu menu;
	private Polymetric view;
	
	private String selecionadoWidth;
	private String selecionadoHeight;
	
	
	@Override
	public void run(IAction action) {}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		action.setMenuCreator(this);
	}

	@Override
	public void init(IViewPart view) {
		this.view = (Polymetric) view; 
	}
	
	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}
	
	@Override
	public Menu getMenu(Control parent) {
		
		dispose();
		menu = new Menu(parent);
		
		String[] textosMenu = {"Width","Height"};
		String[] imagensMenu = {"icons/size_width.png","icons/size_height.png"};
		
		selecionadoWidth = (String) AIMV.getProperty(Properties.POLIMETRIC_WIDTH);
		selecionadoHeight = (String) AIMV.getProperty(Properties.POLIMETRIC_HEIGHT);
		
		if (view.properties != null) {
			for (int i = 0; i < textosMenu.length; i++)
				createItem(parent, menu, textosMenu[i],imagensMenu[i],i);
		}
		
		return menu;
		
	}//getMenu(Control parent) 
	
	@Override
	public void dispose() {
		if (menu != null)
			menu.dispose();
	}
	
	private void createItem(Control control, Menu parent, String text,String imagem, int index){
		
		MenuItem item = new MenuItem(parent, SWT.CASCADE);		
		item.setText(text);
		item.setID(index);
		item.setImage(Imagens.getPluginImage("org.sourceminer.views.polymetric", imagem));
		
		Menu submenu = new Menu(control.getShell(), SWT.DROP_DOWN );	
		item.setMenu(submenu);
		
		for (int i = 0; i < view.properties.length; i++)
			createSubItem(submenu, view.properties[i], index);	
			
	}//createItem

	private void createSubItem(Menu parent, String text, int index){
	
		MenuItem item = new MenuItem(parent, SWT.CHECK);		
		item.setText(text);
		item.setID(index);	
		item.addSelectionListener(this);
		
		if(index == 0 ){
			if (text.equals(selecionadoWidth))
				item.setSelection(true);
		} else{
			if (text.equals(selecionadoHeight))
				item.setSelection(true);
		}
	
	}//createItem
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		
		MenuItem it = (MenuItem) e.getSource();
		if(it.getID() == 0){
			selecionadoWidth = it.getText();
			AIMV.setProperty(Properties.POLIMETRIC_WIDTH, selecionadoWidth);
		} else{
			selecionadoHeight = it.getText();
			AIMV.setProperty(Properties.POLIMETRIC_HEIGHT, selecionadoHeight);
		}
		
	}//widgetSelected

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

}//class
