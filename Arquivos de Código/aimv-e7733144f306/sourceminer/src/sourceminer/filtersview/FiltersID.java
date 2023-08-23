package sourceminer.filtersview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import sourceminer.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.filters.Filter;
import aimv.modeling.Node;
import aimv.utilities.Imagens;
import aimv.views.FilterView;


public class FiltersID extends FilterView {
	
	private Text txtPackages;
	private Text txtClasses;
	private Text txtInterfaces;
	private Text txtMethods;
	private Label logResults;
	
	private Composite composite;


	@Override
	protected void createLayout() {
		
		composite = new Composite(getComposite(), SWT.NONE);
		
		logResults = new Label(composite, SWT.LEFT);
		logResults.setText("");
		logResults.setBounds(10, 214, 215, 45);
		
		Group grpPackages = new Group(composite, SWT.NONE);
		grpPackages.setText("Packages");
		grpPackages.setBounds(10, 10, 215, 45);
		
		txtPackages = new Text(grpPackages, SWT.BORDER);
		txtPackages.setBounds(5, 16, 150, 21);
		
		Button btnResult = new Button(grpPackages, SWT.NONE);
		btnResult.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/filter.png"));
		btnResult.setToolTipText("Filter Results");
		btnResult.setBounds(161, 15, 22, 22);
		btnResult.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						Filter filter = AIMV.getFilter("sourceminer.filters.filterid");
						if (filter != null && !txtPackages.getText().equals("")) {
							String[] list = {txtPackages.getText(), "package"};
							filter.apply(list);
							
							int cont = 0;
							for(Node node : Nodes.getGroup("all").getNodes()){
								if(node.isFiltered()){
									cont++;
								}
							}
							logResults.setText(cont+" of "+Nodes.getGroup("all").getNodes().size()+" nodes filtered");
						}
					
					}
				});
			}
		});
		
		Button btnReset = new Button(grpPackages, SWT.NONE);
		btnReset.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/refresh.png"));
		btnReset.setToolTipText("Reset Filter");
		btnReset.setBounds(185, 15, 22, 22);
		btnReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						txtPackages.setText("");
						Filter filter = AIMV.getFilter("sourceminer.filters.filterid");
						if (filter != null) {
							String[] list = {txtPackages.getText(), "package"};
							filter.remove(list);
							logResults.setText("");
						}
					}
				});
			}
		});
		
		Group grpClasses = new Group(composite, SWT.NONE);
		grpClasses.setText("Classes");
		grpClasses.setBounds(10, 61, 215, 45);
		
		txtClasses = new Text(grpClasses, SWT.BORDER);
		txtClasses.setBounds(5, 16, 150, 21);
		
		Button button = new Button(grpClasses, SWT.NONE);
		button.setToolTipText("Filter Results");
		button.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/filter.png"));
		button.setBounds(161, 15, 22, 22);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						Filter filter = AIMV.getFilter("sourceminer.filters.filterid");
						if (filter != null && !txtClasses.getText().equals("")) {
							String[] list = {txtClasses.getText(), "class"};
							filter.apply(list);
						
							int cont = 0;
							for(Node node : Nodes.getGroup("all").getNodes()){
								if(node.isFiltered()){
									cont++;
								}
							}
							logResults.setText(cont+" of "+Nodes.getGroup("all").getNodes().size()+" nodes filtered");
						}
					
					}
				});
			}
		});
		
		Button button_1 = new Button(grpClasses, SWT.NONE);
		button_1.setToolTipText("Reset Filter");
		button_1.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/refresh.png"));
		button_1.setBounds(185, 15, 22, 22);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						txtClasses.setText("");
						Filter filter = AIMV.getFilter("sourceminer.filters.filterid");
						if (filter != null) {
							String[] list = {txtClasses.getText(), "class"};
							filter.remove(list);
							logResults.setText("");
						}						
					}
				});
				
			}
		});
		
		Group grpInterfaces = new Group(composite, SWT.NONE);
		grpInterfaces.setText("Interfaces");
		grpInterfaces.setBounds(10, 112, 215, 45);
		
		txtInterfaces = new Text(grpInterfaces, SWT.BORDER);
		txtInterfaces.setBounds(5, 16, 150, 21);
		
		Button button_2 = new Button(grpInterfaces, SWT.NONE);
		button_2.setToolTipText("Filter Results");
		button_2.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/filter.png"));
		button_2.setBounds(161, 15, 22, 22);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						Filter filter = AIMV.getFilter("sourceminer.filters.filterid");
						if (filter != null && !txtInterfaces.getText().equals("")) {
							String[] list = {txtInterfaces.getText(), "class"};
							filter.apply(list);
						
							int cont = 0;
							for(Node node : Nodes.getGroup("all").getNodes()){
								if(node.isFiltered()){
									cont++;
								}
							}
							logResults.setText(cont+" of "+Nodes.getGroup("all").getNodes().size()+" nodes filtered");
						}
					
					}
				});
				
			}
		});
		
		Button button_3 = new Button(grpInterfaces, SWT.NONE);
		button_3.setToolTipText("Reset Filter");
		button_3.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/refresh.png"));
		button_3.setBounds(185, 15, 22, 22);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						txtInterfaces.setText("");
						Filter filter = AIMV.getFilter("sourceminer.filters.filterid");
						if (filter != null) {
							String[] list = {txtInterfaces.getText(), "class"};
							filter.remove(list);
							logResults.setText("");
						}						
					}
				});
				
			}
		});
		
		Group grpMethods = new Group(composite, SWT.NONE);
		grpMethods.setText("Methods");
		grpMethods.setBounds(10, 163, 215, 45);
		
		txtMethods = new Text(grpMethods, SWT.BORDER);
		txtMethods.setBounds(5, 16, 150, 21);
		
		Button button_4 = new Button(grpMethods, SWT.NONE);
		button_4.setToolTipText("Filter Results");
		button_4.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/filter.png"));
		button_4.setBounds(161, 16, 22, 22);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						Filter filter = AIMV.getFilter("sourceminer.filters.filterid");
						if (filter != null && !txtMethods.getText().equals("")) {
							String[] list = {txtMethods.getText(), "method"};
							filter.apply(list);
						
							int cont = 0;
							for(Node node : Nodes.getGroup("all").getNodes()){
								if(node.isFiltered()){
									cont++;
								}
							}
							logResults.setText(cont+" of "+Nodes.getGroup("all").getNodes().size()+" nodes filtered");
						}
					
					}
				});
			}
		});
		
		Button button_5 = new Button(grpMethods, SWT.NONE);
		button_5.setToolTipText("Reset Filter");
		button_5.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/refresh.png"));
		button_5.setBounds(185, 16, 22, 22);
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						txtMethods.setText("");
						Filter filter = AIMV.getFilter("sourceminer.filters.filterid");
						if (filter != null) {
							String[] list = {txtMethods.getText(), "method"};
							filter.remove(list);
							logResults.setText("");
						}						
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

