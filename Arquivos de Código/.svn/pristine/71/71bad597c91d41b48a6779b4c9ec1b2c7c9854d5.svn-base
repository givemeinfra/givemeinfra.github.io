package sourceminer.filtersview;

import java.util.ArrayList;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import sourceminer.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.filters.Filter;
import aimv.utilities.Colors;
import aimv.utilities.Imagens;
import aimv.views.FilterView;

public class ConcernsFilter extends FilterView {

	private String[] concerns;

	@Override
	protected void createLayout() {
		
		Composite parent = getComposite();
		
		boolean flag = true;
		if (AIMV.getProperty(Properties.CONCERNS) instanceof String[]) {
			concerns = (String[]) AIMV.getProperty(Properties.CONCERNS);
			if (concerns.length !=  0)
				flag = false;
		}
		
		if (flag) {
			Label label = new Label(parent, SWT.SINGLE);
			label.setText("The project under analysis does not have ConcernMapper files.");
			return;
		}
	
		GridLayout gridLayout = new GridLayout();		
		GridData data = new GridData(GridData.CENTER);
		gridLayout.numColumns = 3;		
		parent.setLayout(gridLayout);	
		parent.setLayoutData(data);

		
		final Button[] checkBoxs = new Button[concerns.length];		
		final ColorSelector[] colors = new ColorSelector[concerns.length];
		final Label[] labels = new Label[concerns.length];			

		Label colorLabel = new Label(parent, SWT.BOLD);
		colorLabel.setText("Colors");
		colorLabel.setForeground(Colors.BLACK);
			
		Label concernLabel = new Label(parent, SWT.BOLD);
		concernLabel.setText("Concerns");	
		concernLabel.setForeground(Colors.BLACK);

		Label nullLabel = new Label(parent, SWT.BOLD);
		nullLabel.setText("");

		for (int i = 0; i < concerns.length; i++) {			
			colors[i] = new ColorSelector(parent);
			colors[i].setColorValue(Colors.WHITE.getRGB());				
			
			labels[i] = new Label(parent, SWT.BOLD);		
			labels[i].setText(concerns[i]+"     ");			
			
			checkBoxs[i] = new Button(parent, SWT.CHECK);							
		}
			
		nullLabel = new Label(parent, SWT.BOLD);
		nullLabel.setText("");
			
		nullLabel = new Label(parent, SWT.BOLD);
		nullLabel.setText("");
			
		Composite grp = new Composite(parent, SWT.NONE);
			
		Button btnResult = new Button(grp, SWT.NONE);
		btnResult.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/filter.png"));
		btnResult.setToolTipText("Filter Results");
		btnResult.setBounds(0, 10, 22, 22);
		btnResult.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ArrayList<String> list = new ArrayList<String>();
				ArrayList<Color> cl = new ArrayList<Color>();
				for (int i = 0; i < checkBoxs.length; i++) {	
					if (checkBoxs[i].getSelection()) {
						list.add(concerns[i]);
						cl.add(Colors.getColorFromRGB(colors[i].getColorValue()));
					}
				}
				Object[] args = {list, cl};
				Filter filter = AIMV.getFilter("sourceminer.filters.concernfilter");
				filter.apply(args);
			}
		});
			
		Button btnReset = new Button(grp, SWT.NONE);
		btnReset.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/refresh.png"));
		btnReset.setToolTipText("Reset Filter");
		btnReset.setBounds(25, 10, 22, 22);
		btnReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (ColorSelector colorSelector : colors)
					colorSelector.setColorValue(Colors.WHITE.getRGB());
				for (Button check : checkBoxs)
					check.setSelection(false);
				Filter filter = AIMV.getFilter("sourceminer.filters.concernfilter");
				filter.remove(null);
			}
		});
		
	}//createLayout

	@Override
	protected void open() {}

	@Override
	protected void closed() {}
	

}//class
