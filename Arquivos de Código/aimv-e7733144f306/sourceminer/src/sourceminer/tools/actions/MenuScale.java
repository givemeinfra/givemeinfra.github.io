package sourceminer.tools.actions;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;


public class MenuScale extends ControlContribution {	

	public MenuScale(String id) {
		super(id);
	}

	@Override
	protected Control createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		//GridLayout layout = new GridLayout();
		//composite.setLayout(layout);
		composite.setLayout(null);
		Combo combo = new Combo(composite, SWT.NONE);
		
		combo.setBounds(0, 0, 40, 15);
		return composite;
	}

	
	
}
