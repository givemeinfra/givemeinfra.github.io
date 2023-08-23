package givemeviews.views;

import givemeviews.persistence.MemoryApplication;

import java.awt.Frame;

import javax.swing.JComponent;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import aimv.controllers.SessionProgram;
import aimv.views.Paradigm;

import prefuse.demosTree.TreeView;


import java.awt.Color;

public class vpTreeView extends Paradigm {

	public static final String ID = "givemeviews.views.vpTreeView"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());	

	public vpTreeView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) 
	{
		// Embed the Frame into Composite
		Composite composite = new Composite(parent, SWT.EMBEDDED | SWT.NO_BACKGROUND);
		composite.setBounds(0, 0, 594, 468);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
		SessionProgram session = SessionProgram.getInstance();
		if(session.getUsuario() == 1 && MemoryApplication.getGeneratedSource() == true)
		{
		
			String aux = MemoryApplication.getGmmRepositoryPath();
			aux = aux.replace("\\", "/");	    
			
			String pathXML = aux + "/Brain/treeview.xml";
		    String label = "name";
			
		    // Garbage Collection
		    System.gc();
		    
			// Create the Tree
			JComponent treeview = TreeView.demo(pathXML, label);
			
			Frame frame = SWT_AWT.new_Frame(composite);
			frame.setBackground(Color.WHITE);
			frame.add(treeview);
			
			createActions();
			initializeToolBar();
			initializeMenu();
		}		
		else
		{					  
		    org.eclipse.swt.widgets.Label lblGivemeViewsNot = new org.eclipse.swt.widgets.Label(composite, SWT.NONE);
		    lblGivemeViewsNot.setText("First, go to 'Utility Views / GiveMe Views'");
		    lblGivemeViewsNot.setBounds(402, 152, 249, 15);
		    toolkit.adapt(lblGivemeViewsNot, true, true);
		  
		    createActions();
		    initializeToolBar();
	        initializeMenu();
		}
	}

	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	protected void createLayout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void open() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void closed() {
		// TODO Auto-generated method stub
		
	}
}
