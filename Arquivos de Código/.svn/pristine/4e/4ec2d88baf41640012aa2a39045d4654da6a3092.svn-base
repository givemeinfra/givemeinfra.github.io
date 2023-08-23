package tools.actions;

import javax.swing.JOptionPane;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;

import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.tools.Tool;
import aimv.utilities.Properties;


public class ColorsToolAction implements IWorkbenchWindowPulldownDelegate, SelectionListener {

	private Menu menu;
	private String color = "";
	
	@Override
	public Menu getMenu(Control parent) {
		
		if (menu != null)
			menu.dispose();
		
		if (!(AIMV.getProperty(Properties.COLORS) instanceof String[]))
			JOptionPane.showMessageDialog(null, "There are no colors to be selected!");
		else {
			String[] colors = (String[]) AIMV.getProperty(Properties.COLORS);
			if (Nodes.getGroups().size() == 0)
				JOptionPane.showMessageDialog(null, "There are no colors to be selected!");
			else {
				color = (String) AIMV.getProperty(Properties.COLOR_SELECTED);	
				menu = new Menu(parent);
				for (String color : colors)
					createItem(menu, color);
			}
		}
		
		return menu;
		
	}//getMenu

	private void createItem(Menu parent, String text){
			
		MenuItem item = new MenuItem(parent, SWT.CHECK);		
		item.setText(text);	
			
		if (color.equals(text))
			item.setSelection(true);
				
		item.addSelectionListener(this);
			
	}//createItem
	
	@Override
	public void run(IAction action) {}
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

	@Override
	public void dispose() {}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		color = ((MenuItem) e.getSource()).getText();
		Tool tool = AIMV.getTool("tools.colorstool");
		tool.active(color.split("/n"));
	}//widgetSelected

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	@Override
	public void init(IWorkbenchWindow window) {}


}//class
