package givemetrace.views;

import givemetrace.implementations.GiveMeTraceXML;
import givemetrace.implementations.InfoRepository;
import givemetrace.implementations.MemoryOutput;
import givemetrace.implementations.Utils;
import givemetrace.provider.MasterProvider;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jdom2.JDOMException;

import aimv.views.UtilityView;

public class vpGiveMeTraceMain extends UtilityView {

	public static final String ID = "givemetrace.views.vpGiveMeTraceMain"; //$NON-NLS-1$
	private Label lblProject1;
	private Label lStartClass;
	Group grpProjectArea;
	public static final String dirPath = "C:"+File.separator+"GiveMeRepository"+File.separator+"Brain";
	public static final String filePath = "C:"+File.separator+"GiveMeRepository"+File.separator+"Brain"+File.separator+"givemetrace.xml";
	private Button btnStartClass;
	private Text tCommitClass;
	private Button btnStartMethod;
	private Text tCommitMethod;
	private Text lOutput;
	Composite container;
	public vpGiveMeTraceMain() {
		setTitleImage(SWTResourceManager.getImage(vpGiveMeTraceMain.class, "/givemetrace/givemetraceicon.png"));
	}
	
	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	public void createPartControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setToolTipText("");
		
		grpProjectArea = new Group(container, SWT.NONE);
		grpProjectArea.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpProjectArea.setText("Select a Project :");
		grpProjectArea.setBounds(10, 103, 446, 221);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(vpGiveMeTraceMain.class, "/givemetrace/givemetrace.png"));		
		lblNewLabel.setBounds(10, 0, 547, 97);
		
		Group grpGenerateLogmethod = new Group(grpProjectArea, SWT.NONE);
		grpGenerateLogmethod.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpGenerateLogmethod.setText("Generate Log (Method Mode) :");
		grpGenerateLogmethod.setBounds(21, 137, 295, 68);
		
		Group grpGenerateLogclass = new Group(grpProjectArea, SWT.NONE);
		grpGenerateLogclass.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpGenerateLogclass.setText("Generate Log (Class Mode) :");
		grpGenerateLogclass.setBounds(21, 49, 295, 68);
		
		btnStartMethod = new Button(grpGenerateLogmethod, SWT.NONE);
		btnStartMethod.setText("Start");
		btnStartMethod.setEnabled(false);
		btnStartMethod.setBounds(207, 24, 75, 25);
		
		btnStartClass = new Button(grpGenerateLogclass, SWT.NONE);
		btnStartClass.setBounds(207, 24, 75, 25);
		btnStartClass.setEnabled(false);
		btnStartClass.setText("Start");
		
		lStartClass = new Label(grpGenerateLogclass, SWT.NONE);
		lStartClass.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lStartClass.setBounds(207, 29, 75, 25);
		lStartClass.setVisible(true);
		lStartClass.setText("Loading..");
		
		
		lblProject1 = new Label(grpProjectArea, SWT.NONE);
		lblProject1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblProject1.setBounds(135, 27, 286, 15);
		
		Label lblAnalizedProject = new Label(grpProjectArea, SWT.NONE);
		lblAnalizedProject.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAnalizedProject.setBounds(21, 27, 110, 16);
		lblAnalizedProject.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblAnalizedProject.setText("Project Name  :");
		
		tCommitClass = new Text(grpGenerateLogclass, SWT.BORDER);
		tCommitClass.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tCommitClass.setBounds(114, 31, 76, 21);
		tCommitClass.setEnabled(false);
		
		Label lblCommit = new Label(grpGenerateLogclass, SWT.NONE);
		lblCommit.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCommit.setBounds(10, 30, 100, 20);
		lblCommit.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblCommit.setText("Commit Number:");
		
		tCommitMethod = new Text(grpGenerateLogmethod, SWT.BORDER);
		tCommitMethod.setBounds(114, 31, 76, 21);
		tCommitMethod.setEnabled(false);
		
		Label label = new Label(grpGenerateLogmethod, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setText("Commit Number:");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		label.setBounds(10, 30, 100, 20);
		
		Group group_1 = new Group(grpProjectArea, SWT.NONE);
		group_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		group_1.setBounds(322, 69, 114, 111);
		group_1.setText("Legend :");
		
		Label lblSimple = new Label(group_1, SWT.NONE);
		lblSimple.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSimple.setText("Simple: 9");
		lblSimple.setBounds(10, 20, 55, 15);
		
		Label lblRange_1 = new Label(group_1, SWT.NONE);
		lblRange_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblRange_1.setText("Range: 9-100");
		lblRange_1.setBounds(10, 41, 80, 15);
		
		Label lblRange = new Label(group_1, SWT.NONE);
		lblRange.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblRange.setText("Range: 9-0");
		lblRange.setBounds(10, 62, 80, 15);
		
		Label lblAll = new Label(group_1, SWT.NONE);
		lblAll.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAll.setText("All: 9-11;13;100-0");
		lblAll.setBounds(10, 83, 94, 15);
		
		Group grpConsole = new Group(container, SWT.NONE);
		grpConsole.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpConsole.setText("Output :");
		grpConsole.setBounds(10, 330, 446, 128);
		
		lOutput = new Text(grpConsole, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		lOutput.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lOutput.setEditable(false);
		lOutput.setBounds(10, 24, 422, 94);
		
		
		btnStartClass.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//lOutput.setText("GiveMe Trace started\nWait the finish proccess...\n");
				
				// persista o nome do projeto selecionado no givemetracexml.xml
				if(tCommitClass==null||tCommitClass.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Inform a Commit Number and/or Range to proceed.");
					return;
				}
				File f = new File(filePath);
				if(!f.exists() || !f.isFile()){//resolvo o problema do File não existir;
					if(!Utils.sucessCreateFile(filePath, dirPath)){
						JOptionPane.showMessageDialog(null, "Can not create the File. Maybe permission denied.");
						return;
					}
				}
				InfoRepository irToTrace = null;
				try {
					irToTrace = GiveMeTraceXML.tryFoundInfoRepository(filePath, lblProject1.getText());
				} catch (JDOMException e2) {
					JOptionPane.showMessageDialog(null, "Found an error in givemetrace.xml while trying to found Repository Information.");
					e2.printStackTrace();
					return;
				} catch (IOException e2) {
					JOptionPane.showMessageDialog(null, "Error!\nThe registered URL is not a valid Repository.");
					e2.printStackTrace();
					return;
				}
				if(irToTrace==null){
					JOptionPane.showMessageDialog(null, "Project not assigned yet!\nOpen givemetrace.xml and follow the model to assign it.");
					return;
				}
				if(irToTrace.getRepositoryPath().isEmpty()){JOptionPane.showMessageDialog(null, "Error!\nAssign a Repository URL to Project");return;}
				if(irToTrace.getRepositoryType().isEmpty()){JOptionPane.showMessageDialog(null, "Error!\nAssign a Repository Type to Project");return;}
				String type = irToTrace.getRepositoryType();
				String url = irToTrace.getRepositoryPath().replaceAll("\\\\", "/");
				
				btnStartClass.setText("Loading...");
				
				switch (type) {
				case "GIT"://GIT
					if(!url.contains(".git")){
						if(url.endsWith("/")){url+=".git";}else{url+="/.git";}
					}
					String[] nameProjectGIT = url.split("/");
					String filenameGit = dirPath+File.separator+"gitLog_class_"+nameProjectGIT[nameProjectGIT.length-2]+".txt";
					MasterProvider mpGit = new MasterProvider();
					boolean fileCreatedGit = mpGit.gitToLogClass(url, filenameGit, tCommitClass.getText());
					if(fileCreatedGit){
						JOptionPane.showMessageDialog(null, "File "+filenameGit+" successfully created!");
						break;
					}else{
						JOptionPane.showMessageDialog(null, "Some error occurred:\n 1-Permission denied to create the File\n 2-Invalid URL to Repository.");
						break;
					}
				case "SVN"://SVN
					String name= "anonymous";
			        String password = "anonymous";
			        if(!url.startsWith("file:///")){
						url="file:///"+url;
					}
					String[] nameProjectSVN = url.split("/");
					String filenameSvn = dirPath+File.separator+"svnLog_class_"+nameProjectSVN[nameProjectSVN.length-1]+".txt";
					MasterProvider mpSvn = new MasterProvider();
					boolean fileCreatedSvn=mpSvn.svnToLogClass(url, filenameSvn, tCommitClass.getText(), name, password);
					if(fileCreatedSvn){
						JOptionPane.showMessageDialog(null, "File "+filenameSvn+" successfully created!");
						break;
					}else{
						JOptionPane.showMessageDialog(null, "Some error occurred:\n 1-Permission denied to create the File\n 2-Invalid URL to Repository.");
						break;
					}
				default:
					JOptionPane.showMessageDialog(null, "Unknow repository Type informed. Change type and try again.");
					break;
				}
				//lStartClass.forceFocus();
				btnStartClass.setText("Start");
				//lOutput.setText("Proccess Finished!\n");
				StringBuilder output = new StringBuilder();
				for(int i=0;i<MemoryOutput.getListCases().size();i++){
					output.append(MemoryOutput.getListCases().get(i));
					output.append("\n");
				}
				lOutput.setText(output.toString());
				MemoryOutput.clear();
				System.gc();
			}
		});

		btnStartMethod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//btnStartMethod.setVisible(false);
				//lOutput.setText("GiveMe Trace started\nWait the finish proccess...\n");
				// persista o nome do projeto selecionado no givemetracexml.xml
				if(tCommitMethod==null||tCommitMethod.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Inform a Commit Number and/or Range to proceed.");
					return;
				}
				File f = new File(filePath);
				if(!f.exists() || !f.isFile()){//resolvo o problema do File não existir;
					if(!Utils.sucessCreateFile(filePath, dirPath)){
						JOptionPane.showMessageDialog(null, "Can not create the File. Maybe permission denied.");
						return;
					}
				}
				InfoRepository irToTrace = null;
				try {
					irToTrace = GiveMeTraceXML.tryFoundInfoRepository(filePath, lblProject1.getText());
				} catch (JDOMException e2) {
					JOptionPane.showMessageDialog(null, "Found an error in givemetrace.xml while trying to found Repository Information.");
					e2.printStackTrace();
					return;
				} catch (IOException e2) {
					JOptionPane.showMessageDialog(null, "Error!\nThe registered URL is not a valid Repository.");
					e2.printStackTrace();
					return;
				}
				if(irToTrace==null){
					JOptionPane.showMessageDialog(null, "Project not assigned yet!\nOpen givemetrace.xml and follow the model to assign it.");
					return;
				}
				if(irToTrace.getRepositoryPath().isEmpty()){JOptionPane.showMessageDialog(null, "Error!\nAssign a Repository URL to Project");btnStartMethod.setVisible(true);return;}
				if(irToTrace.getRepositoryType().isEmpty()){JOptionPane.showMessageDialog(null, "Error!\nAssign a Repository Type to Project");btnStartMethod.setVisible(true);return;}
				String type = irToTrace.getRepositoryType();
				String url = irToTrace.getRepositoryPath().replaceAll("\\\\", "/");
				
				btnStartMethod.setText("Loading...");
				
				switch (type) {
				case "GIT"://GIT
					if(!url.contains(".git")){
						if(url.endsWith("/")){url+=".git";}else{url+="/.git";}
					}
					String[] nameProjectGIT = url.split("/");
					String filenameGit = dirPath+File.separator+"gitLog_methods_"+nameProjectGIT[nameProjectGIT.length-2]+".txt";
					MasterProvider mpGit = new MasterProvider();
					boolean fileCreatedGit = mpGit.gitToLogMethod(url, filenameGit, tCommitMethod.getText());
					if(fileCreatedGit){
						JOptionPane.showMessageDialog(null, "File "+filenameGit+" successfully created!");
						break;
					}else{
						JOptionPane.showMessageDialog(null, "Some error occurred:\n 1-Permission denied to create the File\n 2-Invalid URL to Repository.");
						break;
					}
				case "SVN"://SVN
					String name= "anonymous";
			        String password = "anonymous";
			        if(!url.startsWith("file:///")){
						url="file:///"+url;
					}
					String[] nameProjectSVN = url.split("/");
					String filenameSvn = dirPath+File.separator+"svnLog_methods_"+nameProjectSVN[nameProjectSVN.length-1]+".txt";
					MasterProvider mpSvn = new MasterProvider();
					boolean fileCreatedSvn = mpSvn.svnToLogMethod(url, filenameSvn, tCommitMethod.getText(), name, password);
					if(fileCreatedSvn){
						JOptionPane.showMessageDialog(null, "File "+filenameSvn+" successfully created!");
						break;
					}else{
						JOptionPane.showMessageDialog(null, "Some error occurred:\n 1-Permission denied to create the File\n 2-Invalid URL to Repository.");
						break;
					}
				default:
					JOptionPane.showMessageDialog(null, "Unknow repository Type informed. Change type and try again.");
					break;
				}
				btnStartMethod.setText("Start");
				//btnStartMethod.setVisible(true);
				//lOutput.setText("Proccess Finished!\n");
				StringBuilder output = new StringBuilder();
				for(int i=0;i<MemoryOutput.getListCases().size();i++){
					output.append(MemoryOutput.getListCases().get(i));
					output.append("\n");
				}
				lOutput.setText(output.toString());
				MemoryOutput.clear();
				System.gc();
			}
		});
		tCommitClass.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if(!tCommitClass.getText().isEmpty()){
					btnStartClass.setEnabled(true);
				}else{
					btnStartClass.setEnabled(false);
				}
			}
		});
		tCommitMethod.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if(!tCommitMethod.getText().isEmpty()){
					btnStartMethod.setEnabled(true);
				}else{
					btnStartMethod.setEnabled(false);
				}
			}
		});

		createActions();
		initializeToolBar();
		initializeMenu();
		
		// LISTEN TO THE WORKBENCH
		selectionListener = new WorkbenchSelectionListener();
		ISelectionService selectionService = getViewSite().getWorkbenchWindow().getSelectionService();
		selectionService.addPostSelectionListener(selectionListener);
	}
	
	private final class WorkbenchSelectionListener implements ISelectionListener 
	{
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
	
	
	public void	init( IViewSite site, IMemento memento ) throws PartInitException 
	{
		super.init(site, memento);
	}
	
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
	
	protected void getSelection( IStructuredSelection selection ) 
	{
		try
		{
			Object object = selection.getFirstElement().toString();
			String aux = object.toString();
			String listTokens[] = aux.split("");
			String component = "";
			for(int i = 0; i < listTokens.length; i++)
			{
				if(listTokens[i].equals(" ")){
					break;
				}
				else{
					component = component + listTokens[i];
				}
			}
			if(component.contains("P/"))
				component = component.replace("P/", "");
			lblProject1.setText(component);
			//btnStartClass.setEnabled(true);
			//btnStartMethod.setEnabled(true);
			tCommitClass.setEnabled(true);
			tCommitMethod.setEnabled(true);
			
		}
		catch(Exception e){}		
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
		@SuppressWarnings("unused")
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		@SuppressWarnings("unused")
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

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
