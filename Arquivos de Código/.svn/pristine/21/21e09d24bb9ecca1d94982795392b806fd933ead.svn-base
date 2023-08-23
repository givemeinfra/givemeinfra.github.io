package givemeviews.views;

import givemeviews.model.Arquivo;
import givemeviews.model.SetModules;
import givemeviews.model.SortModules;
import givemeviews.persistence.MemoryApplication;
import givemeviews.views.provider.ModulesComponentsProvider;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.util.SortOrder;

import aimv.controllers.SessionProgram;
import aimv.views.Paradigm;
import collaborative.controller.ActivityController;
import collaborative.controller.MessageController;
import collaborative.persistence.MemoryCollaborativeSourceminer;
import collaborative.valueObject.ActivityVO;
import collaborative.valueObject.MessageVO;


public class vpModulesComponentsView extends Paradigm {

	public static final String ID = "givemeviews.views.vpModulesComponents"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Table tableModules;
	private Table tableComponents;
	
	public static final String DATE_FORMAT = "E dd/MM/yyyy HH:mm:ss";
	//private String CHAT_VIEW = "CHAT_VIEW";
	
	private JPanel panelPlot;
	private Table table;
	private Text txtMsg;
	private Combo combo;

	private String selectedModule = "";
	private String moduleANDcomponent = "";
	
	public vpModulesComponentsView() {
	}
	
	private void loadingModules()
	{
		tableModules.clearAll();
		ModulesComponentsProvider provider = new ModulesComponentsProvider();
		ArrayList<SetModules> listModules = null;
		
		switch (MemoryApplication.getProject().getFerramentaGMM()) {
		case "Driver Empresa 1":			
			listModules = provider.providerSetModules();
			if(listModules != null)
			{
				for(int i = 0; i < listModules.size(); i++)
				{
					TableItem item = new TableItem(tableModules, SWT.NONE);
						item.setText(new String[] 
						{ 
							listModules.get(i).getNome(), Integer.toString(listModules.get(i).getOcorrencia())  // define cada uma das colunas
						}
					);
				}
			}
			listModules = null;
		case "Mantis":
			tableModules.clearAll();
			listModules = provider.providerProjectList();
			if(listModules != null)
			{
				for(int i = 0; i < listModules.size(); i++)
				{
					TableItem item = new TableItem(tableModules, SWT.NONE);
						item.setText(new String[] 
						{ 
							listModules.get(i).getNome(), Integer.toString(listModules.get(i).getOcorrencia())  // define cada uma das colunas
						}
					);
				}
			}
			listModules = null;
		default:
			break;
		}
		
	}
	
	private void loadingComponents(String selectedModule)
	{	ModulesComponentsProvider provider = new ModulesComponentsProvider();
		Arquivo arquivo = new Arquivo();
		ArrayList<SetModules> listComponents = null;
		SortModules objSort = new SortModules();
		
		switch (MemoryApplication.getProject().getFerramentaGMM()) 
		{
			case "Driver Empresa 1":			
				listComponents = arquivo.getListComponentsByModule(provider.providerChangeRequestList(), selectedModule);
				Collections.sort(listComponents, objSort);
				for(int i = 0; i < listComponents.size(); i++)
				{
					TableItem item = new TableItem(tableComponents, SWT.NONE);
					item.setText(new String[] 
					{ 
						"[" + i + "] " + listComponents.get(i).getComponenteAssociado(), Integer.toString(listComponents.get(i).getOcorrencia())  // define cada uma das colunas
					}
					);
				}
				listComponents = null;
			case "Mantis":
				listComponents = provider.providerProjectComponentsList(selectedModule);
				Collections.sort(listComponents, objSort);
				for(int i = 0; i < listComponents.size(); i++)
				{
					TableItem item = new TableItem(tableComponents, SWT.NONE);
					item.setText(new String[] 
					{ 
						"[" + i + "] " + listComponents.get(i).getComponenteAssociado(), Integer.toString(listComponents.get(i).getOcorrencia())  // define cada uma das colunas
					}
					);
				}
				listComponents = null;
			default:
				break;
		}
	}
	
	public void plot(String module)
	{		
		ModulesComponentsProvider provider = new ModulesComponentsProvider();
		Arquivo arquivo = new Arquivo();
		SortModules objSort = new SortModules();
		ArrayList<SetModules> listComponents = null;
		
		switch (MemoryApplication.getProject().getFerramentaGMM()) 
		{
			case "Driver Empresa 1":
				listComponents = arquivo.getListComponentsByModule(provider.providerChangeRequestList(), module);
				Collections.sort(listComponents, objSort);
			case "Mantis":
				listComponents = provider.providerProjectComponentsList(MemoryApplication.getProject().getNomeProjeto());
				Collections.sort(listComponents, objSort);
			default:
				break;
		}		
		
		final DefaultKeyedValues data = new DefaultKeyedValues();
		int count = 15;		
		if(listComponents.size() < 15)
			count = listComponents.size();
		
		for(int i = 0; i < count; i++){
			//data.addValue(listComponents.get(i).getComponenteAssociado(), listComponents.get(i).getOcorrencia());
			data.addValue("[" + i + "]", listComponents.get(i).getOcorrencia());
		}
		
		listComponents = null;
	
		data.sortByValues(SortOrder.DESCENDING);
		final KeyedValues cumulative = DataUtilities.getCumulativePercentages(data);
		final CategoryDataset dataset = DatasetUtilities.createCategoryDataset("Component", data);

	    // Garbage Collection
	    System.gc();
		
		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart("Components from " + module + " ", "ID Component", "% Maintenances", dataset, PlotOrientation.VERTICAL, true, true, false);

		
		// set the background color for the chart...
		chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 3000, 0, Color.blue));
		Color c = new Color(248,248,255);
		chart.getPlot().setBackgroundPaint(c);
		

		// get a reference to the plot for further customization...
		//final CategoryPlot plot = chart.getCategoryPlot(); // original
		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.getRenderer().setSeriesPaint(5, Color.RED);
		
		
		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLowerMargin(0.02);
		domainAxis.setUpperMargin(0.02);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		
		final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
		
		
		final CategoryDataset dataset2 = DatasetUtilities.createCategoryDataset(
		"Pareto", cumulative 
		);
		final NumberAxis axis2 = new NumberAxis("Percent");
		axis2.setNumberFormatOverride(NumberFormat.getPercentInstance());
		plot.setRangeAxis(1, axis2);
		plot.setDataset(1, dataset2);
		plot.setRenderer(1, renderer2);
		plot.mapDatasetToRangeAxis(1, 1);

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		// OPTIONAL CUSTOMIZATION COMPLETED.
		
		
		// add the chart to a panel...
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(550, 270));
		chartPanel.setBounds(0, 0, 600, 555);
		chartPanel.setMaximumDrawWidth(600);
		chartPanel.setMaximumDrawHeight(555);
		panelPlot.removeAll();
		panelPlot.setLayout(null);
		panelPlot.add(chartPanel);
        panelPlot.setBackground(Color.WHITE);
		chartPanel.setAutoscrolls(true);
		chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.X_AXIS));
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(final Composite parent) {      		
        Composite container = toolkit.createComposite(parent, SWT.NO_REDRAW_RESIZE);
        container.setBounds(112, 70, 268, 69);
		toolkit.paintBordersFor(container);
		container.setLayout(null);
        		
		SessionProgram session = SessionProgram.getInstance();
		if(session.getUsuario() == 1 && MemoryApplication.getGeneratedSource() == true)
		{		
			Group grpListOfModules = new Group(container, SWT.NONE);
			grpListOfModules.setBounds(10, 10, 440, 114);
			grpListOfModules.setText("Modules that have set maintenance");
			toolkit.adapt(grpListOfModules);
			toolkit.paintBordersFor(grpListOfModules);
			
			tableModules = new Table(grpListOfModules, SWT.BORDER | SWT.FULL_SELECTION);	
			
			tableModules.setBounds(10, 22, 415, 84);
			toolkit.adapt(tableModules);
			toolkit.paintBordersFor(tableModules);
			tableModules.setHeaderVisible(true);
			tableModules.setLinesVisible(true);
			
			TableColumn tblclmnModules = new TableColumn(tableModules, SWT.NONE);
			tblclmnModules.setWidth(313);
			tblclmnModules.setText("Name");
			
			TableColumn tblclmnMaintenances = new TableColumn(tableModules, SWT.NONE);
			tblclmnMaintenances.setWidth(93);
			tblclmnMaintenances.setText("Maintenances");
			
			tableModules.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					// Insere o modulo selecionado no txtModule
					int selectedModuleRow = tableModules.getSelectionIndex();//pegar a linha selecionada  
				    TableItem selectedItem1 = tableModules.getItem(selectedModuleRow);
				    selectedModule = selectedItem1.getText();
				   
				    // carrega automaticamente os componentes na tableComponents
				    tableComponents.removeAll();
					int selectedComponentRow = tableModules.getSelectionIndex();//pegar a linha selecionada  
					if(selectedComponentRow > -1)
					{
						TableItem selectedItem2 = tableModules.getItem(selectedComponentRow);
						loadingComponents(selectedItem2.getText(0));
						 
						plot(selectedItem1.getText(0));
						panelPlot.repaint();
					}
					else{
							//System.out.println("GiveMe Views say: You need select one Module");
							JOptionPane.showMessageDialog(null, "You need select one Module");
						}
				}
			});			
			
			Group grpListOfComponents = new Group(container, SWT.NONE);
			grpListOfComponents.setBounds(10, 130, 440, 120);
			grpListOfComponents.setText("Components that have set maintenance");
			toolkit.adapt(grpListOfComponents);
			toolkit.paintBordersFor(grpListOfComponents);
			
			tableComponents = new Table(grpListOfComponents, SWT.BORDER | SWT.FULL_SELECTION);
			tableComponents.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					int selectedRow = tableComponents.getSelectionIndex();
				    TableItem selectedItem = tableComponents.getItem(selectedRow);
				    String component[] = selectedItem.getText().split(" ");
				    moduleANDcomponent = selectedModule + "/" + component[1];
				    if(MemoryCollaborativeSourceminer.getLogged() != null && MemoryCollaborativeSourceminer.getLogged() == true)
				    	loadingCollaborationMesssages();
				}
			});
			tableComponents.setLinesVisible(true);
			tableComponents.setHeaderVisible(true);
			tableComponents.setBounds(10, 26, 416, 86);
			toolkit.adapt(tableComponents);
			toolkit.paintBordersFor(tableComponents);
			
			TableColumn tableColumn = new TableColumn(tableComponents, SWT.NONE);
			tableColumn.setWidth(316);
			tableColumn.setText("Name");
			
			TableColumn tableColumn_1 = new TableColumn(tableComponents, SWT.NONE);
			tableColumn_1.setWidth(93);
			tableColumn_1.setText("Maintenances");
			
			if(MemoryCollaborativeSourceminer.getLogged() != null && MemoryCollaborativeSourceminer.getLogged() == true)
			{			
				try
				{
					ActivityController control = new ActivityController();
					Collection<ActivityVO> listActivity = control.getActivitysByProject();
					
					Group grpCollaborationPoint = new Group(container, SWT.NONE);
					grpCollaborationPoint.setText("Collaboration Point - Send Message about Module or Component");
					grpCollaborationPoint.setBounds(10, 256, 440, 202);
					toolkit.adapt(grpCollaborationPoint);
					toolkit.paintBordersFor(grpCollaborationPoint);
					
					table = new Table(grpCollaborationPoint, SWT.BORDER | SWT.FULL_SELECTION);
					table.setLinesVisible(true);
					table.setHeaderVisible(true);
					table.setBounds(10, 109, 420, 83);
					toolkit.adapt(table);
					toolkit.paintBordersFor(table);
					
					TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
					tableColumn_2.setWidth(410);
					tableColumn_2.setText("Messages");
					
					Button btnSend = new Button(grpCollaborationPoint, SWT.NONE);
					btnSend.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							String selectedActivity = "";
							if(combo.getSelectionIndex() > 0)
								selectedActivity = combo.getItem(combo.getSelectionIndex());
							MessageController controller = new MessageController();
							Boolean status = controller.sendCollaborationMsgByEntity(txtMsg.getText(), moduleANDcomponent, selectedActivity);
							if(status){
								txtMsg.setText("");
								loadingCollaborationMesssages();
							}
						}					
					});
					btnSend.setBounds(378, 20, 52, 25);
					toolkit.adapt(btnSend, true, true);
					btnSend.setText("Send");
					
					combo = new Combo(grpCollaborationPoint, SWT.NONE);
					combo.setBounds(10, 80, 420, 23);
					toolkit.adapt(combo);
					toolkit.paintBordersFor(combo);
					combo.setText("Select one Activity");
					// add activities on combobox
					combo.add("");
					for(ActivityVO activityTemp : listActivity)
						combo.add(activityTemp.getName());
					
					txtMsg = new Text(grpCollaborationPoint, SWT.MULTI | SWT.BORDER| SWT.WRAP | SWT.V_SCROLL);
					txtMsg.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseDown(MouseEvent e) {
							txtMsg.setText("");
							txtMsg.setText("[Message about:" + moduleANDcomponent + "] ");
						}
					});
					txtMsg.setText("Select one component and write a mensage");
					txtMsg.setBounds(10, 20, 362, 54);
					toolkit.adapt(txtMsg, true, true);
				
				}
				catch(Exception e){}
			
			}
			
			Composite composite = new Composite(parent, SWT.EMBEDDED
	                | SWT.NO_BACKGROUND);
	        composite.setLayout(null); 
	        final Frame frame = SWT_AWT.new_Frame(composite);
	        panelPlot = new JPanel();
	        panelPlot.setBounds(10, 10, 900, 400);
	        frame.add(panelPlot);
	        
        
			createActions();
			initializeToolBar();
			initializeMenu();
			
			loadingModules();
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
	
	private void loadingCollaborationMesssages() {
		table.removeAll();
		MessageController controller = new MessageController();
		try
		{
			ArrayList<?> listMessages = controller.getCollaborationSystemAndUserMsgsByEntity(moduleANDcomponent);
			if(listMessages != null && listMessages.size() > 0)
			{
				if(listMessages.get(0) != null)
				{
					Collection<MessageVO> collection = (Collection<MessageVO>)listMessages.get(0);
					for(MessageVO message3 : collection)
					{
						if (message3.getEntity()!=null && message3.getEntity().contains(moduleANDcomponent)) 
						{
							if (message3.getActivity().equals("")) 
							{
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] 
								{ 
									"  " + message3.getDate() + ", " + message3.getDeveloperVO().getName() +  "said: " + message3.getText() + "  " + "\n\n\n"
								}
								);
								
							} 
							else 
							{
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] 
								{ 
										"  " +  message3.getDate() + ", Activity: " + message3.getActivity() + ", " 
										+ message3.getDeveloperVO().getName() + " said: "+ message3.getText() + "  "
								}
								);
							}
						}
					}
				}
				if(listMessages.get(1) != null)
				{
					Collection<MessageVO> collection2 = (Collection<MessageVO>) listMessages.get(1);
					for(MessageVO systemmessage3 : collection2)
					{
						if (systemmessage3.getEntity()!=null && systemmessage3.getEntity().contains(moduleANDcomponent)) 
						{
														
							if (systemmessage3.getActivity().equals("")) {
								
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] 
								{														
									"  " +  systemmessage3.getDate() + ", "  + systemmessage3.getText() + "  "
								}
								);
							} 
							else 
							{					
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] 
								{
									"  " +  systemmessage3.getDate() + ", Activity: "
										+ systemmessage3.getActivity() +  ", " + systemmessage3.getText() + "  "
								}
								);
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
