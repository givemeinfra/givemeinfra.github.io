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


public class GroupsToolAction implements IWorkbenchWindowPulldownDelegate, SelectionListener {

	private Menu menu;
	private String group;
	
	
	@Override
	public Menu getMenu(Control parent) {
		
		if (menu != null)
			menu.dispose();
		
		if (Nodes.getGroups().size() == 0) {
			JOptionPane.showMessageDialog(null, "There are no group to be selected!");
		}
		else {
			group = (String) AIMV.getProperty(Properties.GROUP_SELECTED);	
			menu = new Menu(parent);
			for (String group : Nodes.getGroups())
				createItem(menu, group);
		}
		
		return menu;
		
	}//getMenu

	private void createItem(Menu parent, String text){
			
		MenuItem item = new MenuItem(parent, SWT.CHECK);		
		item.setText(text);	
			
		if (group.equals(text))
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
		group = ((MenuItem) e.getSource()).getText();
		Tool tool = AIMV.getTool("tools.groupstool");
		tool.active(group.split("/n"));
	}//widgetSelected

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	@Override
	public void init(IWorkbenchWindow window) {}


}//class
