package views.properties.handlers;

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import views.properties.PropertiesView;
import aimv.controllers.AIMV;
import aimv.utilities.Imagens;
import aimv.views.ViewAIMV;

public class SelectionPropertiesHandler extends AbstractHandler {

	
	@Override
	@SuppressWarnings("unchecked")
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		ViewAIMV view = null;
		for (ViewAIMV v : AIMV.getActiveViews()) {
			if (v.getID().equals("aimv.views.propertiesview")) {
				view = v;
				break;
			}
		}
			
		if (view == null)
			return null;
		
		DialogProperties dialog = new DialogProperties(view.getViewSite().getShell());
		dialog.properties = (ArrayList<String>) AIMV.getProperty(PropertiesView.PROPERTIES);
		dialog.propertiesFocus = (ArrayList<String>) AIMV.getProperty(PropertiesView.PROPERTIESFOCUS);
		dialog.propertiesVisible = (ArrayList<String>) AIMV.getProperty(PropertiesView.PROPERTIESVISIBLE);
		
		dialog.open();
		return null;
	}

}//class

class DialogProperties extends Dialog {

	private Shell shell;
	private ArrayList<Button> checkFocus;
	private ArrayList<Button> checkVisibles;
	
	protected ArrayList<String> properties;
	protected ArrayList<String> propertiesFocus;
	protected ArrayList<String> propertiesVisible;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DialogProperties(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.RESIZE);
		setText("Selection Properties");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public void open() {
		
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}//open

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		
		shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		shell.setLayout(new GridLayout(3, false));
		shell.setImage(Imagens.getPluginImage("org.sourceminer.views.properties", "icons/filterView.png"));
		
		String[] textos = {"Property","Visible","Highlight","","",""};
		for (String text : textos) {
			Label label = new Label(shell, SWT.NONE);
			label.setText(text);
		}
		
		checkFocus = new ArrayList<Button>();
		checkVisibles = new ArrayList<Button>();
		
		Label label;
		Button button;
		
		for (String property : properties) {
			label = new Label(shell, SWT.NONE);
			label.setText(property);
			
			//check visible
			button = new Button(shell, SWT.CHECK);
			checkVisibles.add(button);
			button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
			if (propertiesVisible.contains(property))
				button.setSelection(true);
			
			button.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					Button btnVisible = (Button) e.getSource();
					Button btnFocus = checkFocus.get(checkVisibles.indexOf(btnVisible));
					if (btnVisible.getSelection()) {
						btnFocus.setEnabled(true);
					}
					else {
						btnFocus.setSelection(false);
						btnFocus.setEnabled(false);
					}	
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {}
				
			});
			
			//check focus
			button = new Button(shell, SWT.CHECK);
			checkFocus.add(button);
			button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
			
			if (!propertiesVisible.contains(property))
				button.setEnabled(false);
			else if (propertiesFocus.contains(property))
				button.setSelection(true);
			
		}
		
		label = new Label(shell, SWT.NONE);
		label = new Label(shell, SWT.NONE);
		button = new Button(shell, SWT.NONE);
		button.setText("OK");
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				propertiesVisible.clear();
				for (int i = 0; i < properties.size(); i++) {
					if (checkVisibles.get(i).getSelection())
						propertiesVisible.add(properties.get(i));
				}
				
				propertiesFocus.clear();
				for (int i = 0; i < properties.size(); i++) {
					if (checkFocus.get(i).getSelection())
						propertiesFocus.add(properties.get(i));
				}
				
				AIMV.setProperty(PropertiesView.PROPERTIESFOCUS, null);
				AIMV.setProperty(PropertiesView.PROPERTIESVISIBLE, null);
				
				AIMV.setProperty(PropertiesView.PROPERTIESFOCUS, propertiesFocus);
				AIMV.setProperty(PropertiesView.PROPERTIESVISIBLE, propertiesVisible);
					
				shell.dispose();		
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
			
		});
		
		shell.pack();
		Point point = Geometry.centerPoint(getParent().getBounds());
		shell.setLocation(point.x - shell.getSize().x / 2, point.y - shell.getSize().y/2);
		
	}//createContents
	
}//class
