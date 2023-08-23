package givemeviews.views;

import givemeviews.exportation.Report;
import givemeviews.givemerepository.GiveMeRepository;
import givemeviews.importation.DriverCEODate;
import givemeviews.importation.MantisDriver;
import givemeviews.model.MaintenanceType;
import givemeviews.model.Projeto;
import givemeviews.persistence.MemoryApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

import aimv.controllers.SessionProgram;
import aimv.views.UtilityView;

public class vpGiveMeViewsMain extends UtilityView {

	public static final String ID = "givemeviews.views.vpGiveMeViewsMain"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text txtComponent;
	
	private List ListModelMain ;
	private List list_1;
	private List list_2;
	private List list_3;
	private Button btnFilter;
	private Button btnStart;
	private Boolean btnStartStatus = false;
	private Label lblProject1;
	private Label lblProject2;
	private Label lblMsg;
	private String path = "";
	private Boolean reportFound = false;
	private String msg;
	
	private String component = "";
	
	
	private void loadingProject()
	{
		if(MemoryApplication.getProject().getNomeProjeto() != null){
			lblProject1.setText(MemoryApplication.getProject().getNomeProjeto());
			lblProject2.setText(MemoryApplication.getProject().getNomeProjeto());
		}
	}
	
	private Boolean loadingMaintenanceTypes()
	{
		File report = getReportPath(MemoryApplication.getProject().getNomeProjeto());		
		if(report != null)
		{			
			// Então existe um relatório de dados.			
			String pathToReadReport = report.getAbsolutePath();	
			
			// Persistir o path onde se encontra o relatório
			Projeto aux = MemoryApplication.getProject();
			aux.setPathLogReport(pathToReadReport);
			MemoryApplication.setProject(aux);
			
			Boolean xmlRead = verifyFilteredMaintenanceTypes(pathToReadReport);
			
			if(xmlRead == false) // Então NÃO há um XML associado ao report mais atual.
			{
				if(processMaintenanceTypes(pathToReadReport, 1) == true)
					return true;
				else
					return false;
			}
			else // HÁ um XML associado ao report mais atual.
			{
				if(processMaintenanceTypes(pathToReadReport, 2) == true)
				{					
					btnStartStatus = true;
					return true;	
				}					
				else
					return false;
			} 
			
		}
		else // fim report
		{
			return false;
		}
	}
	
	private Boolean verifyFilteredMaintenanceTypes(String pathToReadReport) 
	{
		String pattern = Pattern.quote(System.getProperty("file.separator"));
		String[] splittedFileName = pathToReadReport.split(pattern);		
		String file = splittedFileName[splittedFileName.length - 1];
		file = file.replaceAll(".xls", ".xml");
		file = "(1)" + file;
		splittedFileName[splittedFileName.length - 1] = file;
		pathToReadReport = "";
		for(int i = 0; i < splittedFileName.length; i++)
		{
			if(i == 0)
				pathToReadReport += splittedFileName[i];
			else
			{
				pathToReadReport += "\\" +splittedFileName[i];
			}
		}
		MaintenanceType type = new MaintenanceType();
		return type.tryFoundMaintenanceTypes(pathToReadReport);
	}
	
	private Boolean processMaintenanceTypes(String pathParam, int typeStatus)
	{
		path = pathParam;
		Projeto project = new Projeto();
		if(project.verifyTool(pathParam))
		{
			switch(MemoryApplication.getProject().getFerramentaGMM())
			{
				case "Driver Empresa 1":
					Projeto objProjeto = new Projeto(MemoryApplication.getProject().getNomeProjeto(), MemoryApplication.getProject().getFerramentaGMM(), MemoryApplication.getProject().getNomeComponente(), MemoryApplication.getProject().getPathLogReport());
					MemoryApplication.setProject(objProjeto);
					DriverCEODate driver = new DriverCEODate();
					if(typeStatus == 1)
						return driver.readReportMode1(pathParam);
					else
						return driver.readReportMode2(pathParam);
				case "Mantis":
					Projeto objProjeto2 = new Projeto(MemoryApplication.getProject().getNomeProjeto(), MemoryApplication.getProject().getFerramentaGMM(), MemoryApplication.getProject().getNomeComponente(), MemoryApplication.getProject().getPathLogReport());
					//Projeto objProjeto2 = new Projeto("Calima", MemoryApplication.getProject().getFerramentaGMM(), "RazaoNormalSubRel.jrxml", MemoryApplication.getProject().getPathLogReport());
					
					MemoryApplication.setProject(objProjeto2);
					MantisDriver driver2 = new MantisDriver();
					if(typeStatus == 1)
						return driver2.readReportMode1(pathParam);
					else
						return driver2.readReportMode2(pathParam);			
					
				default:
					//System.out.println("GiveMe Views say: Impossible Read Report");
					JOptionPane.showMessageDialog(null, "Impossible Read Report");
					break;
			}
			return true;
		}
		else
		{
			return false;
		}
	}	

	private Boolean verifyComponentField()
	{
		if(txtComponent.getText().length() == 0){
			//System.out.println("GiveMe Views say: You need select one Component");
			JOptionPane.showMessageDialog(null, "You need select one Component");
			return false;
		}
		return true;
	}	
	
	public File getReportPath(String projectName)
	{
		//Objetivo: 
		// > recuperar todas as subpastas de GMMRepository, que contenham uma pasta com o valor contido no parametro projectName
		// > Percorrer as subpastas encontradas em busca de um relatório de dados em um dos formatos válidos pelo GMM. Se encontrado mais de um, retorne o path + nome do relatório mais recente.
		
		File reportFound = null;	
		
		ArrayList<File> listPaths = GiveMeRepository.getListPaths(projectName, new File (MemoryApplication.getGmmRepositoryPath())); 			
        if(listPaths.size() == 0)
        {
        	msg = "Project Folder not found in GMMRepository";
        	return null;
        }
        else
        {   
        	reportFound = GiveMeRepository.getLastFileModified(listPaths);
        	if(reportFound == null)
        	{
        		msg = "Any Historical Data was found in GMMRepository";
        		return null;
        	}
        	else
        	{
        		listPaths = null;
        		return reportFound;
        	}
        }
	}
	
	public void loadingTypes()
	{
		ListModelMain.removeAll();
		list_1.removeAll();
		list_2.removeAll();
		list_3.removeAll();
		if(MemoryApplication.getListTypesMaintenance() != null)
		{
			for(int i = 0; i < MemoryApplication.getListTypesMaintenance().size(); i++)
			{
				if(MemoryApplication.getListTypesMaintenance().get(i).getType() == 0)
					ListModelMain.add(MemoryApplication.getListTypesMaintenance().get(i).getName());
				if(MemoryApplication.getListTypesMaintenance().get(i).getType() == 1)
					list_1.add(MemoryApplication.getListTypesMaintenance().get(i).getName());
				if(MemoryApplication.getListTypesMaintenance().get(i).getType() == 2)
					list_2.add(MemoryApplication.getListTypesMaintenance().get(i).getName());
				if(MemoryApplication.getListTypesMaintenance().get(i).getType() == 3)
					list_3.add(MemoryApplication.getListTypesMaintenance().get(i).getName());
			}
		}
	}
	
	private void getTypesFromListsModels()
	{		
		ArrayList<MaintenanceType> listGetTypesArrayList = new ArrayList<>();
		for(int i = 0; i < ListModelMain.getItems().length; i++)
		{
			listGetTypesArrayList.add(new MaintenanceType((String)ListModelMain.getItem(i), 0));
		}
		for(int i = 0; i < list_1.getItems().length; i++)
		{
			listGetTypesArrayList.add(new MaintenanceType((String)list_1.getItem(i), 1));
		}
		for(int i = 0; i < list_2.getItems().length; i++)
		{
			listGetTypesArrayList.add(new MaintenanceType((String)list_2.getItem(i), 2));
		}
		for(int i = 0; i < list_3.getItems().length; i++)
		{
			listGetTypesArrayList.add(new MaintenanceType((String)list_3.getItem(i), 3));
		}
		MemoryApplication.setMaintenanceTypes(listGetTypesArrayList);		
		
		// create XML Maintenance Types
		String pattern = Pattern.quote(System.getProperty("file.separator"));
		String pathToReadReport = path;
		String[] splittedFileName = pathToReadReport.split(pattern);		
		String file = splittedFileName[splittedFileName.length - 1];
		file = file.replaceAll(".xls", ".xml");
		file = "(1)" + file;
		splittedFileName[splittedFileName.length - 1] = file;
		pathToReadReport = "";
		for(int i = 0; i < splittedFileName.length; i++)
		{
			if(i == 0)
				pathToReadReport += splittedFileName[i];
			else
			{
				pathToReadReport += "\\" +splittedFileName[i];
			}
		}
		Report xml = new Report();
		xml.createMaintenanceTypeReport(pathToReadReport, listGetTypesArrayList);
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
		if(session.getUsuario() == 1)
		{
			reportFound = loadingMaintenanceTypes(); 
			
			// Garbage Collection
			System.gc();
			
			Group grpStep = new Group(container, SWT.NONE);
			grpStep.setText("Step 2/2 : Select one Component");
			grpStep.setBounds(10, 450, 547, 82);
			toolkit.adapt(grpStep);
			toolkit.paintBordersFor(grpStep);
			
			Label lblProjectName = new Label(grpStep, SWT.NONE);
			lblProjectName.setBounds(10, 25, 103, 15);
			toolkit.adapt(lblProjectName, true, true);
			lblProjectName.setText("Project Name :");
			
			Label lblComponentName = new Label(grpStep, SWT.NONE);
			lblComponentName.setBounds(10, 49, 121, 15);
			toolkit.adapt(lblComponentName, true, true);
			lblComponentName.setText("Component Name :");
			
			txtComponent = new Text(grpStep, SWT.BORDER);
			txtComponent.setEditable(true);
			txtComponent.setBounds(132, 46, 340, 21);
			toolkit.adapt(txtComponent, true, true);
			
			btnStart = new Button(grpStep, SWT.NONE);
			btnStart.setEnabled(true);
			btnStart.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) 
				{
					Boolean read = false;
					if(verifyComponentField())
					{
						if(btnStartStatus && reportFound == true)
						{
						    setComponent("");
							switch(MemoryApplication.getProject().getFerramentaGMM())
							{
								case "Driver Empresa 1":
									// verificar se component existe					
									setComponent(txtComponent.getText()); 
									DriverCEODate driver1 = new DriverCEODate();
									read = driver1.verifyComponentOccur(path, txtComponent.getText());
									if(read)
									{
										read =  false;
										driver1.startGiveMeViews();										
									}
									else
									{
										//System.out.println("GiveMe Views say: Selected Component not found in the Data Report");
										JOptionPane.showMessageDialog(null, "Selected Component not found in the Data Report");
									}
									
									break;
								case "Mantis":
									// verificar se component existe
									setComponent(txtComponent.getText());
									
									Projeto auxProject = MemoryApplication.getProject();
									auxProject.setNomeComponente(getComponent());
									MemoryApplication.setProject(auxProject);
									
									MantisDriver driver2 = new MantisDriver();
									read = driver2.verifyComponentOccur(getComponent());
									if(read)
									{
										read =  false;
										driver2.startGiveMeViews();										
									}
									else
									{
										//System.out.println("GiveMe Views say: Selected Component not found in the Data Report");
										JOptionPane.showMessageDialog(null, "Selected Component not found in the Data Report");
									}
									break;
								
								default:
									break;						
							}
							
						}
						else
						{
							//System.out.println("GiveMe Views say: First, you need filter Maintenance Types");
							JOptionPane.showMessageDialog(null, "First, you need filter Maintenance Types");
						}
					}
				}
			});
			btnStart.setBounds(478, 25, 59, 39);
			toolkit.adapt(btnStart, true, true);
			btnStart.setText("Start");
			
			lblProject2 = new Label(grpStep, SWT.NONE);
			lblProject2.setBounds(132, 25, 340, 15);
			toolkit.adapt(lblProject2, true, true);
			
			Label lblNewLabel = new Label(container, SWT.NONE);
			lblNewLabel.setImage(SWTResourceManager.getImage(vpGiveMeViewsMain.class, "/givemeviews/givemeviews.png"));
			lblNewLabel.setBounds(10, 0, 547, 97);
			toolkit.adapt(lblNewLabel, true, true);
			
			Group grpStep_1 = new Group(container, SWT.NONE);
			grpStep_1.setText("Step 1/2 : Rank the Maintenance Types");
			grpStep_1.setBounds(10, 125, 547, 319);
			toolkit.adapt(grpStep_1);
			toolkit.paintBordersFor(grpStep_1);
			
			if(reportFound == false)
			{
				lblMsg = new Label(grpStep_1, SWT.NONE);
				lblMsg.setBounds(10, 31, 515, 15);
				lblMsg.setText(msg);
				toolkit.adapt(lblMsg, true, true);
			}
			else
			{				
				Label lblFoundTypes = new Label(grpStep_1, SWT.NONE);
				lblFoundTypes.setBounds(10, 23, 84, 15);
				toolkit.adapt(lblFoundTypes, true, true);
				lblFoundTypes.setText("Found Types");
				
				ListModelMain = new List(grpStep_1, SWT.BORDER);
				ListModelMain.setBounds(10, 44, 212, 235);
				toolkit.adapt(ListModelMain, true, true);
				
				Label lblCorrectiveMaintenance = new Label(grpStep_1, SWT.NONE);
				lblCorrectiveMaintenance.setBounds(355, 10, 153, 15);
				toolkit.adapt(lblCorrectiveMaintenance, true, true);
				lblCorrectiveMaintenance.setText("Corrective Maintenance");
				
				list_1 = new List(grpStep_1, SWT.BORDER);
				list_1.setBounds(325, 28, 212, 79);
				toolkit.adapt(list_1, true, true);
				
				list_2 = new List(grpStep_1, SWT.BORDER);
				list_2.setBounds(325, 134, 212, 79);
				toolkit.adapt(list_2, true, true);
				
				Label lblAdaptativeMaintenance = new Label(grpStep_1, SWT.NONE);
				lblAdaptativeMaintenance.setText("Adaptative Maintenance");
				lblAdaptativeMaintenance.setBounds(355, 113, 153, 15);
				toolkit.adapt(lblAdaptativeMaintenance, true, true);
				
				list_3 = new List(grpStep_1, SWT.BORDER);
				list_3.setBounds(325, 240, 212, 70);
				toolkit.adapt(list_3, true, true);
				
				Label lblEvolutionaryMaintenance = new Label(grpStep_1, SWT.NONE);
				lblEvolutionaryMaintenance.setText("Evolutive Maintenance");
				lblEvolutionaryMaintenance.setBounds(355, 219, 153, 15);
				toolkit.adapt(lblEvolutionaryMaintenance, true, true);
				
				Button button = new Button(grpStep_1, SWT.NONE);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(ListModelMain.getSelectionIndex() > -1)
						{
							String aux[] = ListModelMain.getSelection();
							String typeselected = "";
							for (int i = 0; i < aux.length; i++)
								typeselected += aux[i]; 
							list_1.add(typeselected);					
							ListModelMain.remove(ListModelMain.getSelectionIndex());
						}
					}
				});
				button.setBounds(244, 31, 75, 25);
				toolkit.adapt(button, true, true);
				button.setText(">>");
				
				Button button_1 = new Button(grpStep_1, SWT.NONE);
				button_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(ListModelMain.getSelectionIndex() > -1)
						{
							String aux[] = ListModelMain.getSelection();
							String typeselected = "";
							for (int i = 0; i < aux.length; i++)
								typeselected += aux[i]; 
							list_2.add(typeselected);					
							ListModelMain.remove(ListModelMain.getSelectionIndex());
						}
					}
				});
				button_1.setText(">>");
				button_1.setBounds(244, 139, 75, 25);
				toolkit.adapt(button_1, true, true);
				
				Button button_2 = new Button(grpStep_1, SWT.NONE);
				button_2.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(ListModelMain.getSelectionIndex() > -1)
						{
							String aux[] = ListModelMain.getSelection();
							String typeselected = "";
							for (int i = 0; i < aux.length; i++)
								typeselected += aux[i]; 
							list_3.add(typeselected);					
							ListModelMain.remove(ListModelMain.getSelectionIndex());
						}
					}
				});
				button_2.setText(">>");
				button_2.setBounds(244, 240, 75, 25);
				toolkit.adapt(button_2, true, true);
				
				btnFilter = new Button(grpStep_1, SWT.NONE);
				btnFilter.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {					
						if(list_1.getItems().length == 0 && list_2.getItems().length == 0 && list_3.getItems().length == 0)
						{
							//System.out.println("GiveMe Views say: You need filter dates to continue");
							JOptionPane.showMessageDialog(null, "You need filter dates to continue");
						}
						else 
						{
							MemoryApplication.clearMaintenanceTypes();							
							// Aqui deverá ter um switch com base na ferramenta que está na memória.							
							try
							{
								getTypesFromListsModels();	
								btnStartStatus = true;
								//System.out.println("GiveMe Views say: Filter Successfully");
								JOptionPane.showMessageDialog(null, "Filter Successfully");
							}
							catch(Exception a)
							{
								//System.out.println("GiveMe Views say: GiveMe Views not Started.");
								JOptionPane.showMessageDialog(null, "GiveMe Views not Started");
							}							
						}
					}
				});
				btnFilter.setBounds(41, 285, 153, 25);
				toolkit.adapt(btnFilter, true, true);
				btnFilter.setText("Filter");
				
				Button button_3 = new Button(grpStep_1, SWT.NONE);
				button_3.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(list_1.getSelectionIndex() > -1)
						{
							String aux[] = list_1.getSelection();
							String typeselected = "";
							for (int i = 0; i < aux.length; i++)
								typeselected += aux[i]; 
							ListModelMain.add(typeselected);					
							list_1.remove(list_1.getSelectionIndex());
						}
					}
				});
				button_3.setText("<<");
				button_3.setBounds(244, 62, 75, 25);
				toolkit.adapt(button_3, true, true);
				
				Button button_4 = new Button(grpStep_1, SWT.NONE);
				button_4.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(list_2.getSelectionIndex() > -1)
						{
							String aux[] = list_2.getSelection();
							String typeselected = "";
							for (int i = 0; i < aux.length; i++)
								typeselected += aux[i]; 
							ListModelMain.add(typeselected);					
							list_2.remove(list_2.getSelectionIndex());
						}
					}
				});
				button_4.setText("<<");
				button_4.setBounds(244, 170, 75, 25);
				toolkit.adapt(button_4, true, true);
				
				Button button_5 = new Button(grpStep_1, SWT.NONE);
				button_5.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(list_3.getSelectionIndex() > -1)
						{
							String aux[] = list_3.getSelection();
							String typeselected = "";
							for (int i = 0; i < aux.length; i++)
								typeselected += aux[i]; 
							ListModelMain.add(typeselected);					
							list_3.remove(list_3.getSelectionIndex());
						}
					}
				});
				button_5.setText("<<");
				button_5.setBounds(244, 271, 75, 25);
				toolkit.adapt(button_5, true, true); 
				
				loadingTypes();	
			}
			
			Label lblAnalizedProject = new Label(container, SWT.NONE);
			lblAnalizedProject.setBounds(20, 103, 110, 16);
			lblAnalizedProject.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			toolkit.adapt(lblAnalizedProject, true, true);
			lblAnalizedProject.setText("Analyzed Project :");
			
			lblProject1 = new Label(container, SWT.NONE);
			lblProject1.setBounds(134, 103, 408, 15);
			toolkit.adapt(lblProject1, true, true);
			
			createActions();
			initializeToolBar();
			initializeMenu();			
					
			loadingProject();			
			
			// LISTEN TO THE WORKBENCH
			selectionListener= new WorkbenchSelectionListener();
			ISelectionService selectionService = getViewSite().getWorkbenchWindow().getSelectionService();
			selectionService.addPostSelectionListener(selectionListener);
		}
		else
		{			
			Label lblGivemeViewsNot = new Label(container, SWT.CENTER);
			lblGivemeViewsNot.setBounds(312, 152, 325, 40);
			toolkit.adapt(lblGivemeViewsNot, true, true);
			lblGivemeViewsNot.setText("To start Giveme Views, Right-Click on desired Project \r\n and then click 'Visualizer with GiveMe Views'");
			
			createActions();
			initializeToolBar();
			initializeMenu();
		}
	}
	
	private final class WorkbenchSelectionListener implements ISelectionListener 
	{
		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection ) 
		{
			if( selection instanceof IStructuredSelection )
			{
				getSelection( (IStructuredSelection) selection );
		    }
			else
			{
				getSelection(null);
			}
		}
		
	}
	private ISelectionListener selectionListener;	
	
	public vpGiveMeViewsMain()
	{
		
	}

	@Override
	public void	init( IViewSite site, IMemento memento ) throws PartInitException 
	{
		super.init(site, memento);
	}
	
	@Override
	public void dispose() 
	{
		if( selectionListener!= null)
		{
			ISelectionService selectionService = getViewSite().getWorkbenchWindow().getSelectionService();
			selectionService.removePostSelectionListener(selectionListener
		);
	    selectionListener= null;
		}
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
	
	protected void getSelection( IStructuredSelection selection ) 
	{
		String method = "";
		String selectedClass = "";
		txtComponent.setText("");
		
		try
		{	
			// get general text
			Object object = selection.getFirstElement().toString();
			String text = object.toString();
			
			// get method name from general text
			String listTokensMethodAux[] = text.split(" ");
			
			if(!listTokensMethodAux[1].equals("[in"))
			{
				method = listTokensMethodAux[1];			
				method = method.replaceAll("[(]", " ");
				String listTokensMethod[] = method.split(" ");
				method = listTokensMethod[0];
			}
			
			// get class name from general text
			String listTokens[] = text.split("\\[in ");
			selectedClass = listTokens[2];
			
			// clear spaces
			method = method.replaceAll(" ", "");
			selectedClass = selectedClass.replaceAll(" ", "");
			
			// final verify
			if(method.length() > 0 && selectedClass.length() > 0)
			{
				component = selectedClass + "|" + method;
				txtComponent.setText(component);
			}
		}
		catch(Exception e){}		
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

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}
}
