package sourceminer.filtersview;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import sourceminer.utilities.Properties;
import sourceminer.utilities.RelationType;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.filters.Filter;
import aimv.modeling.Node;
import aimv.modeling.Relation;
import aimv.utilities.Imagens;
import aimv.views.FilterView;

public class DependenciesFilter extends FilterView {
	
	private Button[] checkBoxs;	
	private String[] names = {"Inheritance","Object","Method","Field","Interface Implementation","Interface Inheritance"};
	private String[] dependencies = {RelationType.HIERARCHICAL, RelationType.OBJECT, RelationType.METHOD,
			RelationType.FIELD, RelationType.INTERFACE_IMPL, RelationType.INTERFACE_INHERITANCE};

	private Label logResults;
	
	@Override
	protected void createLayout() {
		
		Composite parent = getComposite();
		GridLayout gridLayout = new GridLayout();		
		GridData data = new GridData(GridData.CENTER);
		gridLayout.marginLeft = 20;
		gridLayout.marginTop = 10;
		parent.setLayout(gridLayout);	
		parent.setLayoutData(data);
		
		checkBoxs = new Button[names.length];		
			
		for (int i = 0; i < names.length; i++) {						
			Button btn = new Button(parent, SWT.CHECK);
			btn.setSelection(true);
			btn.setText(names[i]);
			checkBoxs[i] = btn;
		}
			
			
		Composite grp = new Composite(parent, SWT.NONE);
		
		logResults = new Label(grp, SWT.LEFT);
		logResults.setText("");
		logResults.setBounds(10, 50, 215, 45);

			
		Button btnResult = new Button(grp, SWT.NONE);
		btnResult.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/filter.png"));
		btnResult.setToolTipText("Filter Results");
		btnResult.setBounds(50, 10, 22, 22);
		btnResult.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						
						ArrayList<String> list = new ArrayList<String>();
						for (int i = 0; i < checkBoxs.length; i++) {
							if (checkBoxs[i].getSelection())
								list.add(dependencies[i]);
						}
						Filter filter = AIMV.getFilter("sourceminer.filters.dependencyfilter");
						filter.apply(list.toArray());
						
						int cont = 0;
						int total = 0;
						for(Node node : Nodes.getGroup("all").getNodes()){
							for (Relation relation : node.getRelations()) {
								total++;
								if(relation.isFiltered()){
									cont++;
								}
							}
						}
						logResults.setText(cont+" of "+total+" relations filtered");
						
					
					}
				});				
			
			}
		});
			
		Button btnReset = new Button(grp, SWT.NONE);
		btnReset.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/refresh.png"));
		btnReset.setToolTipText("Reset Filter");
		btnReset.setBounds(75, 10, 22, 22);
		btnReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						for (Button btn : checkBoxs)	
							btn.setSelection(true);
						Filter filter = AIMV.getFilter("sourceminer.filters.dependencyfilter");
						filter.apply(dependencies);
						logResults.setText("");
					}
				});
			}
		});
		
	}//createLayout

	@Override
	protected void open() {}

	@Override
	protected void closed() {}
	
	
}//class
