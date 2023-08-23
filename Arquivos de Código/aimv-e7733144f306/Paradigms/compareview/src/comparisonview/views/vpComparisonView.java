package comparisonview.views;

import givemeviews.filtering.FilterStatistics;
import givemeviews.givemerepository.GiveMeRepository;
import givemeviews.persistence.MemoryApplication;
import givemeviews.views.provider.ComparisonViewProvider;

import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import aimv.controllers.SessionProgram;
import aimv.views.Paradigm;
import org.eclipse.swt.widgets.Combo;

import collaborative.controller.ActivityController;
import collaborative.controller.MessageController;
import collaborative.persistence.MemoryCollaborativeSourceminer;
import collaborative.valueObject.ActivityVO;
import collaborative.valueObject.MessageVO;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class vpComparisonView extends Paradigm {

	private Text text;
	private Table tableIndication;
	private Table tableModifications;
	private Composite compositeStatistics;
	private Label lblNumberOfIndications;
	private Label lblNumberOfModifications;
	private Label lblPercentAssert;
	private Group grpExportation;
	private Label lblOverview;
	private Group grpStatistics;
	private Frame frameLine;
	private Frame frameBar;
	private LineChart objChart;
	private JComponent lineChart = null;
	private Text txtMsg;
	private Table table;
	private Combo comboActivity;
	private Combo comboPeriod;
	
	public vpComparisonView() {
	}
	
	private void loadingItens(ArrayList<String[]> listIndications, ArrayList<String[]> listModifications) 
	{
		//RGB rgb1 = new RGB(255, 0, 0);
		RGB rgb2 = new RGB(0, 0, 255);
	    //Color red = new Color(Display.getCurrent(), rgb1);
	    Color blue = new Color(Display.getCurrent(), rgb2);
	    
		for(int i = 0; i < listIndications.size(); i++)
		{
			
			TableItem item = new TableItem(tableIndication, SWT.NONE);
				item.setText(new String[] 
				{ 
						listIndications.get(i)[0]
				}
			);
			if(listIndications.get(i)[1].equals("1"))
				item.setForeground(blue);
		}
		
		for(int i = 0; i < listModifications.size(); i++)
		{
			TableItem item = new TableItem(tableModifications, SWT.NONE);
				item.setText(new String[] 
				{ 
					listModifications.get(i)[0]
				}
			);
			item.setForeground(blue);
		}
	}
	
	private ArrayList<String[]> setColorIndicationsItens(ArrayList<String[]> listIndications, ArrayList<String[]> listModifications) 
	{
		for(int i = 0; i < listModifications.size(); i++)
		{
			for(int j = 0; j < listIndications.size(); j++)
			{
				if(listModifications.get(i)[0].equals(listIndications.get(j)[0]))
					listIndications.get(j)[1] = "1";
			}
		}
		return listIndications;
	}
	


	private Boolean saveCompareResultInXML(int modificationLength, int indicationLength, float percent) 
	{
		Date date = new Date();						
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");		
		String atualDate = "[" + sdf.format(date) + " " + percent +"%" + "]";
		
		GiveMeRepository repo = new GiveMeRepository();
		String item[] = new String[3];
		item[0] = atualDate;
		item[1] = Integer.toString(modificationLength);
		item[2] = Integer.toString(indicationLength);		
		
		// verifica se arquivo existe
		if(repo.verifyifComparisonViewXMLExist()) // then update xml
		{
			return repo.updateCompareViewXML(item);
		}
		else // then create xml
		{
			if(repo.createCompareViewXML(item))
			{
				return true;
			}
			else
				return false;
		}	
		
	}
	
	private ArrayList<String[]> formatIndicationListColor(ArrayList<String> listIndications) 
	{
		ArrayList<String[]> listAux = new ArrayList<>();
		for(int i = 0; i < listIndications.size(); i++)
		{
			String indication[] = new String[2];
			indication[0] = listIndications.get(i); indication[1] = "0";
			listAux.add(indication);
		}
		return listAux;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NO_REDRAW_RESIZE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setLayout(null);
		
		SessionProgram session = SessionProgram.getInstance();
		if(session.getUsuario() == 1 && MemoryApplication.getGeneratedSource() == true)
		{
			Composite compositeMain = new Composite(container, SWT.NONE);
			compositeMain.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			compositeMain.setBounds(0, 0, 755, 321);
			
			Group grpModificationsByCommit = new Group(compositeMain, SWT.NONE);
			grpModificationsByCommit.setBounds(10, 61, 217, 250);
			grpModificationsByCommit.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			grpModificationsByCommit.setText("Modified by Commit");
			
			tableModifications = new Table(grpModificationsByCommit, SWT.BORDER | SWT.FULL_SELECTION);
			tableModifications.setBounds(10, 23, 197, 217);
			tableModifications.setHeaderVisible(true);
			tableModifications.setLinesVisible(true);
			
			TableColumn tblclmnModifications = new TableColumn(tableModifications, SWT.NONE);
			tblclmnModifications.setWidth(189);
			tblclmnModifications.setText("Modifications");
			Group grpAs = new Group(compositeMain, SWT.NONE);
			grpAs.setBounds(233, 61, 217, 250);
			grpAs.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			grpAs.setText("GiveMe Views Indication");
			
			tableIndication = new Table(grpAs, SWT.BORDER | SWT.FULL_SELECTION);
			tableIndication.setBounds(10, 24, 197, 216);
			tableIndication.setHeaderVisible(true);
			tableIndication.setLinesVisible(true);
			
			TableColumn tblclmnIndications = new TableColumn(tableIndication, SWT.NONE);
			tblclmnIndications.setWidth(189);
			tblclmnIndications.setText("Indications");
			
			Group grpCommitNumber = new Group(compositeMain, SWT.NONE);
			grpCommitNumber.setBounds(10, 10, 440, 45);
			grpCommitNumber.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			grpCommitNumber.setText("Commit Number");
			
			text = new Text(grpCommitNumber, SWT.BORDER);
			text.setBounds(10, 16, 76, 21);
			
			Button btnCompare = new Button(grpCommitNumber, SWT.NONE);
			btnCompare.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					
					try
					{
						tableIndication.removeAll();
						tableModifications.removeAll();
						
						// get list Indications from masterProbability Table
						FilterStatistics filter = new FilterStatistics();
						ArrayList<String> listIndications = filter.filterStatisticByRange(MemoryApplication.getProject().getNomeComponente(), MemoryApplication.getStatisticRange());
						
						// Get list Modifications from GiveMe Trace
						int commitNumber = Integer.parseInt(text.getText());
						ArrayList<String[]> listModifications = filter.filterModificationsByCommit(commitNumber);
											
						if(listIndications != null && listModifications != null)
						{
							// format indications list by patern color.
							ArrayList<String[]> listIndicationsColored = formatIndicationListColor(listIndications);
							
							// clear listIndication
							listIndications.removeAll(listIndications);
							listIndications = null;
							
							//set colors of itens
							listIndicationsColored = setColorIndicationsItens(listIndicationsColored, listModifications);
							
							loadingItens(listIndicationsColored, listModifications);
							
							int indicationsCount = 0;
							for(int i = 0; i < listIndicationsColored.size(); i++)
							{
								if(listIndicationsColored.get(i)[1].equals("1"))
									indicationsCount++;
							}
							
							float percent = getPercent(indicationsCount, listModifications.size());
							
							loadingBarChart(listModifications.size(), indicationsCount, percent);
							
							saveCompareResultInXML(listModifications.size(), indicationsCount, percent);
							text.setText("");
						}
						else
							lblOverview.setText("Impossible generate Overview");
					}
					catch(Exception a)
					{
						lblOverview.setText("Impossible generate Overview");
					}
					
				}						
				
			});
			btnCompare.setBounds(344, 14, 86, 25);
			btnCompare.setText("Compare");
			
			Label lblLegendOr = new Label(grpCommitNumber, SWT.NONE);
			lblLegendOr.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblLegendOr.setBounds(114, 19, 212, 15);
			lblLegendOr.setText("Legend: 1 or 1;10 or 1,3,7");
			
			grpExportation = new Group(compositeMain, SWT.NONE);
			grpExportation.setBounds(456, 10, 294, 301);
			grpExportation.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			grpExportation.setText("Overview");
			
			Button btnExportReport = new Button(grpExportation, SWT.NONE);
			btnExportReport.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// Chamar o provider dele que está no provider do givemeviews. Como retorno, receberá todas as indicações do xml compareview e criará o report.
				}
			});
			btnExportReport.setBounds(186, 14, 94, 25);
			btnExportReport.setText("Export Report");			
			
			lblOverview = new Label(grpExportation, SWT.NONE);
			lblOverview.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblOverview.setBounds(67, 91, 164, 55);
			lblOverview.setText("Have not yet been generated \nstatistics with GiveMe Views");
			
			grpStatistics = new Group(container, SWT.NONE);
			grpStatistics.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			grpStatistics.setText("Statistics by Period");
			grpStatistics.setBounds(0, 324, 1247, 256);
			
			compositeStatistics = new Composite(grpStatistics, SWT.EMBEDDED
		            | SWT.NO_BACKGROUND);
			compositeStatistics.setBounds(10, 16, 1227, 230);
			compositeStatistics.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));			
			
			Group grpColaborationPoint = new Group(container, SWT.NONE);
			grpColaborationPoint.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			grpColaborationPoint.setText("Colaboration Point");
			grpColaborationPoint.setBounds(761, 8, 486, 301);		
			
			if(MemoryCollaborativeSourceminer.getLogged() != null)
			{
				Label lblSelectOnePeriod = new Label(grpColaborationPoint, SWT.NONE);
				lblSelectOnePeriod.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lblSelectOnePeriod.setBounds(10, 55, 211, 15);
				lblSelectOnePeriod.setText("Select one period to collaboration :");
				
				comboPeriod = new Combo(grpColaborationPoint, SWT.NONE);
				comboPeriod.setBounds(238, 52, 238, 23);
				
				txtMsg = new Text(grpColaborationPoint, SWT.BORDER | SWT.MULTI);
				txtMsg.setToolTipText("Write a collaboration message here");
				txtMsg.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						txtMsg.setText("");
						if(!comboPeriod.getText().equals(""))
							txtMsg.setText(comboPeriod.getText());
					}
				});
				txtMsg.setBounds(10, 81, 389, 83);
				
				Button btnSend = new Button(grpColaborationPoint, SWT.NONE);
				btnSend.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// Envia a mensagem passando o getText e a atividade selecionada. O nome da entidade será obtida atraves do collaborative.provider
						MessageController controller = new MessageController();
						Boolean status = controller.sendCollaborationMsgByEntity(txtMsg.getText(), MemoryCollaborativeSourceminer.getComparisonViewEntityPatternName(), (String)comboActivity.getText());
						if(status){
							txtMsg.setText("");
							loadingCollaborationMesssages();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Impossible send Message");
						}
					}
				});
				btnSend.setBounds(401, 105, 75, 39);
				btnSend.setText("Send");
				
				table = new Table(grpColaborationPoint, SWT.BORDER | SWT.FULL_SELECTION);
				table.setBounds(10, 170, 466, 121);
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
				
				TableColumn tblclmnCollaborations = new TableColumn(table, SWT.NONE);
				tblclmnCollaborations.setWidth(456);
				tblclmnCollaborations.setText("Collaborations");
				
				Label lblSelectOneActivity = new Label(grpColaborationPoint, SWT.NONE);
				lblSelectOneActivity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lblSelectOneActivity.setBounds(10, 25, 183, 15);
				lblSelectOneActivity.setText("Select one Activity :");
				
				comboActivity = new Combo(grpColaborationPoint, SWT.NONE);
				comboActivity.setBounds(237, 23, 239, 23);			
				
				loadingActivities();			
				loadingPeriods();			
				loadingCollaborationMesssages();
			}
			else
			{
				Label lblNewLabel = new Label(grpColaborationPoint, SWT.NONE);
				lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lblNewLabel.setBounds(128, 117, 259, 15);
				lblNewLabel.setText("Make login to enable Collaboration Point");
			}		
			
			
			loadingLineChart();
			
			createActions();
			initializeToolBar();
			initializeMenu();
		}
		else
		{					  
		    org.eclipse.swt.widgets.Label lblGivemeViewsNot = new org.eclipse.swt.widgets.Label(container, SWT.NONE);
		    lblGivemeViewsNot.setText("First, go to 'Utility Views / GiveMe Views'");
		    lblGivemeViewsNot.setBounds(402, 152, 249, 15);
		  
		    createActions();
		    initializeToolBar();
	        initializeMenu();
		}
	}
	
	private void loadingPeriods() 
	{
		// lê xml e cria os datasets
		ComparisonViewProvider provider = new ComparisonViewProvider();
		ArrayList<String[]> listFinal = provider.getXMLContent();	
		if(listFinal != null)
		{
			comboPeriod.add("");
			for(int i = 0; i < listFinal.size(); i++)
				comboPeriod.add(listFinal.get(i)[0]);
		}
	}

	private void loadingActivities() 
	{
		try
		{
			ActivityController control = new ActivityController();
			Collection<ActivityVO> listActivity = control.getActivitysByProject();
			
			// add activities on combobox
			if(listActivity != null)
			{
				comboActivity.add("");
				for(ActivityVO activityTemp : listActivity)
					comboActivity.add(activityTemp.getName());
			}
		}
		catch(Exception e){}
	}

	private float getPercent(int indicationsCount, int modificationCount) 
	{		
		float percent = ((indicationsCount / modificationCount) * 100);
		if (percent > 100)
		{
			percent = 100;
		}
		return percent;
	}

	private Boolean loadingLineChart() 
	{		
		ComparisonViewProvider provider = new ComparisonViewProvider();
		Boolean exist = provider.verifyComparisonViewXML();
		if(exist)
		{
			// lê xml e cria os datasets			
			ArrayList<String[]> listFinal = provider.getXMLContent();			
			
			if(listFinal != null)
			{
				// cria o chart passando a lista final
				int end = listFinal.size();
				int begin = listFinal.size() - 4;
				if(begin < 0)
					begin = 0;
				
				objChart = new LineChart();				
				lineChart = objChart.getLineChart(listFinal, begin, end);
				lineChart.setBackground(java.awt.Color.white);
				
				frameLine = SWT_AWT.new_Frame(compositeStatistics);
				frameLine.setBackground(java.awt.Color.WHITE);
				frameLine.add(lineChart);	
				
			}
			else
			{
				Label lblStatistics = new Label(compositeStatistics, SWT.NONE);
				lblStatistics.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lblStatistics.setBounds(465, 103, 342, 15);
			lblStatistics.setText("Have not yet been generated statistics with GiveMe Views");
				return false;
			}
			return true; 
			
		}
		else
		{
			Label lblStatistics = new Label(compositeStatistics, SWT.NONE);
			lblStatistics.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblStatistics.setBounds(465, 103, 342, 15);
			lblStatistics.setText("Have not yet been generated statistics with GiveMe Views");
			return false;
		}		
	}
	
	private void loadingBarChart(float modificationLength, float indicationLength, float percent)
	{	
		lblOverview.setVisible(false);
		lblPercentAssert = new Label(grpExportation, SWT.NONE);
		lblPercentAssert.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblPercentAssert.setBounds(10, 87, 115, 25);
		lblPercentAssert.setText("Percent Hit : " + percent + "%");
		
		lblNumberOfIndications = new Label(grpExportation, SWT.NONE);
		lblNumberOfIndications.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNumberOfIndications.setBounds(10, 66, 219, 15);
		lblNumberOfIndications.setText("Number of Indications: " + indicationLength);
		
		lblNumberOfModifications = new Label(grpExportation, SWT.NONE);
		lblNumberOfModifications.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNumberOfModifications.setBounds(10, 45, 219, 15);
		lblNumberOfModifications.setText("Number of Modified : " + modificationLength);
		
		Composite compositeOverview = new Composite(grpExportation, SWT.EMBEDDED
	            | SWT.NO_BACKGROUND);
		compositeOverview.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeOverview.setBounds(10, 120,270, 162);	
		Bar3DChart objChartBar = new Bar3DChart();
		JComponent barChart = objChartBar.getBarChart(modificationLength, indicationLength);
		barChart.setBackground(java.awt.Color.white);
		
		frameBar = SWT_AWT.new_Frame(compositeOverview);
		frameBar.setBackground(java.awt.Color.WHITE);
		frameBar.add(barChart);
	}
	
	private void loadingCollaborationMesssages() {
		table.removeAll();
		MessageController controller = new MessageController();
		try
		{
			String entity = MemoryCollaborativeSourceminer.getComparisonViewEntityPatternName();
			
			ArrayList listMessages = controller.getCollaborationSystemAndUserMsgsByEntity(entity);
			if(listMessages != null && listMessages.size() > 0)
			{
				if(listMessages.get(0) != null)
				{
					Collection<MessageVO> collection = (Collection<MessageVO>)listMessages.get(0);
					for(MessageVO message3 : collection)
					{
						if (message3.getEntity()!=null && message3.getEntity().contains(entity)) 
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
						if (systemmessage3.getEntity()!=null && systemmessage3.getEntity().contains(entity)) 
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
		catch(Exception e){
		}
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
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
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
