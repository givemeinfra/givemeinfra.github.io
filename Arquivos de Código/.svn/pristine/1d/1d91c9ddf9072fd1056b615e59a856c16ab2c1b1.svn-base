package givemeviews.views;

import givemeviews.exportation.Report;
import givemeviews.importation.FileDialogOpen;
import givemeviews.model.SetModules;
import givemeviews.persistence.MemoryApplication;
import givemeviews.views.provider.OutputProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import jxl.write.WriteException;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

import aimv.controllers.SessionProgram;
import aimv.views.UtilityView;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;


public class vpOutputView extends UtilityView {

	public static final String ID = "givemeviews.views.vpOutputView"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	public vpOutputView() {
	}
	
	

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		container.setLayout(null);
		
		SessionProgram session = SessionProgram.getInstance();
		if(session.getUsuario() == 1 && MemoryApplication.getGeneratedSource() == true)
		{
			Group grpGenerateHereAll = new Group(container, SWT.NONE);
			grpGenerateHereAll.setText("Generate here all reports suported by GiveMe Views");
			grpGenerateHereAll.setBounds(10, 10, 761, 225);
			toolkit.adapt(grpGenerateHereAll);
			toolkit.paintBordersFor(grpGenerateHereAll);
			{
				Button btnGenerateMetricReport = new Button(grpGenerateHereAll, SWT.NONE);
				btnGenerateMetricReport.addSelectionListener(new SelectionAdapter() {
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
									//System.out.println("GiveMe Views say: Metrics Report created with sucess");
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
				btnGenerateMetricReport.setBounds(21, 52, 134, 25);
				toolkit.adapt(btnGenerateMetricReport, true, true);
				btnGenerateMetricReport.setText("Generate and Export");
			}
			
			Label lblMetricReport = new Label(grpGenerateHereAll, SWT.NONE);
			lblMetricReport.setBounds(21, 31, 510, 15);
			toolkit.adapt(lblMetricReport, true, true);
			lblMetricReport.setText("Metric Report : export all metrics calculated by GiveMe Views in processed Project");
			
			Label lblFrequenciesReport = new Label(grpGenerateHereAll, SWT.NONE);
			lblFrequenciesReport.setText("Frequencies Report : export all statistic calculated about selected Module and Component, by GiveMe Views in processed Project");
			lblFrequenciesReport.setBounds(21, 89, 730, 15);
			toolkit.adapt(lblFrequenciesReport, true, true);
			
			Button btnGenerateFrequenciesReport = new Button(grpGenerateHereAll, SWT.NONE);
			btnGenerateFrequenciesReport.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					OutputProvider provider =  new OutputProvider();
					if(provider.providerProgramDataSource() == true)
					{
						ArrayList<SetModules> listFinal = provider.returnFrequenciesByModuleAndComponent();
						String reportMetricName = "gimemeviews_percents_report.xls";
						FileDialogOpen objDialog = new FileDialogOpen();
						String pathToSave = objDialog.getPathToSaveFile(reportMetricName);
														
						if(pathToSave.length() > 0)
						{
							Report report = new Report();
							try {
								report.buildFrequenciesReport(pathToSave, listFinal);
								//System.out.println("GiveMe Views say: Frequency Distribution Report created with sucess");
								//JOptionPane.showMessageDialog(null, "Frequency Distribution Report created with sucess");
							} catch (WriteException e1) {
								//System.out.println("GiveMe Views say: Impossible create Frequency Distribution Report");
								JOptionPane.showMessageDialog(null, "Impossible create Frequency Distribution Report");
							} catch (IOException e1) {
								//System.out.println("GiveMe Views say: Impossible create Frequency Distribution Report");
								//JOptionPane.showMessageDialog(null, "Impossible create Frequency Distribution Report");
							}							
						}
						
						
					}
					else{
							//System.out.println("GiveMe Views say: First, you need select one Module and one Component");
							JOptionPane.showMessageDialog(null, "First, you need select one Module and one Component");
						}
					}
			});
			btnGenerateFrequenciesReport.setText("Generate and Export");
			btnGenerateFrequenciesReport.setBounds(21, 110, 134, 25);
			toolkit.adapt(btnGenerateFrequenciesReport, true, true);
			{
				Label lblComparisonStatisticsExport = new Label(grpGenerateHereAll, SWT.NONE);
				lblComparisonStatisticsExport.setBounds(21, 151, 657, 15);
				toolkit.adapt(lblComparisonStatisticsExport, true, true);
				lblComparisonStatisticsExport.setText("Comparison Statistics: export all statistics stored by Comparison View");
			}
			{
				Button btnGenerateAndExport = new Button(grpGenerateHereAll, SWT.NONE);
				btnGenerateAndExport.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						//
					}
				});
				btnGenerateAndExport.setBounds(21, 172, 134, 25);
				toolkit.adapt(btnGenerateAndExport, true, true);
				btnGenerateAndExport.setText("Generate and Export");
			}
		

			createActions();
			initializeToolBar();
			initializeMenu();
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
