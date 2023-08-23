package views.polymetric.actions;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import views.polymetric.Polymetric;
import views.polymetric.utilities.Properties;
import aimv.controllers.AIMV;


public class ZoomAction extends ControlContribution implements SelectionListener {	

	
	public ZoomAction(Polymetric view) {
		super(view.getID() + "zoomaction");
		AIMV.setProperty(Properties.POLIMETRIC_SCALE, 1.0);
	}//GridZoomAction

	protected Control createControl(Composite parent) {
		
		Composite comp = new Composite(parent, SWT.NONE);
		String items[] = {"25%","50%","75%","100%","125%","150%","200%"};
		
		Combo comboBox = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboBox.setItems(items);
		comboBox.select(3);
		comboBox.setBounds(0, 0, 52, 15);
		comboBox.setToolTipText("Geometric Zoom");
		comboBox.addSelectionListener(this);
		
		return comp;
		
	}//createControl
	
	public void widgetSelected(SelectionEvent e) {
		
		Combo comboBox = (Combo) e.getSource();
		String valor = comboBox.getItem(comboBox.getSelectionIndex()).split("%")[0];
		double number = Double.parseDouble(valor) / 100; 
		AIMV.setProperty(Properties.POLIMETRIC_SCALE, number);
		
    }//widgetSelected

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}
	
	
}//class
