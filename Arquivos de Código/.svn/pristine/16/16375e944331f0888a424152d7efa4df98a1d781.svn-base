package givemeviews.views;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import jxl.write.WriteException;
import givemeviews.persistence.MemoryApplication;
import givemeviews.views.provider.MetricViewProvider;
import givemeviews.views.provider.OutputProvider;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import aimv.controllers.SessionProgram;
import aimv.views.Paradigm;
import givemeviews.exportation.Report;
import givemeviews.importation.FileDialogOpen;
import givemeviews.metrics.Metric;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class vpMetricView extends Paradigm {

	public static final String ID = "givemeviews.views.vpMetricView"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Table table;

	public vpMetricView() {
	}
	
	public void loadingMetrics()
	{
		MetricViewProvider provider = new MetricViewProvider();
		ArrayList<Metric> listMetrics = provider.providerMetrics();
		if(listMetrics != null)
		{
			for(int i = 0; i < listMetrics.size(); i++)
			{
				TableItem item = new TableItem(table, SWT.NONE);
					item.setText(new String[] 
					{ 
						listMetrics.get(i).getDescricao(), listMetrics.get(i).getMetrica() // define cada uma das colunas
					}
				);
			}
		}
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		
		SessionProgram session = SessionProgram.getInstance();
		if(session.getUsuario() == 1 && MemoryApplication.getGeneratedSource() == true)
		{
		
			Group grpListOfCalculated = new Group(container, SWT.NONE);
			grpListOfCalculated.setText("List of Calculated Metrics");
			grpListOfCalculated.setBounds(10, 10, 674, 401);
			
			table = new Table(grpListOfCalculated, SWT.BORDER | SWT.FULL_SELECTION);
			table.setBounds(10, 49, 654, 342);
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			
			TableColumn tblclmnDescription = new TableColumn(table, SWT.NONE);
			tblclmnDescription.setWidth(533);
			tblclmnDescription.setText("Description");
			
			TableColumn tblclmnMetricValue = new TableColumn(table, SWT.NONE);
			tblclmnMetricValue.setWidth(112);
			tblclmnMetricValue.setText("Metric Value");
			
			Button btnExport = new Button(grpListOfCalculated, SWT.NONE);
			btnExport.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Report objMetric = new Report();
					OutputProvider provider = new OutputProvider();
					try 
					{
						if(provider.providerListMetrics() == null)
						{
							//System.out.println("GiveMe Views say: Have not yet been generated metrics with GiveMe Views");
							JOptionPane.showMessageDialog(null, "Have not yet been generated metrics with GiveMe Views");
						}
						else
						{
							String reportMetricName = "gimemeviews_metric_report.xls";
							FileDialogOpen objDialog = new FileDialogOpen();
							String pathToSave = objDialog.getPathToSaveFile(reportMetricName);
															
							if(pathToSave.length() > 0)
							{
								objMetric.buildMetricReport(pathToSave, provider.providerListMetrics());
								//System.out.println("GiveMe Views say: Metrics Report created with sucess!");
								JOptionPane.showMessageDialog(null, "Metrics Report created with sucess!");
							}
						}							
					} 
					catch (WriteException | IOException e1) 
					{
						//System.out.println("GiveMe Views say: Impossible create Metrics Report");
						JOptionPane.showMessageDialog(null, "Impossible create Metrics Report");
					}
				}
			});
			btnExport.setBounds(564, 18, 66, 25);
			toolkit.adapt(btnExport, true, true);
			btnExport.setText("Export");
			
			createActions();
			initializeToolBar();
			initializeMenu();
			loadingMetrics();
		}
		else
		{					  
		    org.eclipse.swt.widgets.Label lblGivemeViewsNot = new org.eclipse.swt.widgets.Label(container, SWT.NONE);
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
