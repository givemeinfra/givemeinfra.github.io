package com.br.collaborativeAIMV.view.pages;

import collaborative.persistence.MemoryCollaborativeSourceminer;

import java.rmi.RemoteException;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.modeling.Node;
import aimv.views.ViewAIMV;

import com.br.collaborativeAIMV.control.ConcernsControl;
import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.control.NodesControl;
import com.br.collaborativeAIMV.control.SystemMessagesControl;
import com.br.collaborativeAIMV.control.ViewControl;
import com.br.collaborativeAIMV.delegate.ActivityDelegate;
import com.br.collaborativeAIMV.delegate.DeveloperDelegate;
import com.br.collaborativeAIMV.delegate.ProjectDelegate;
import com.br.collaborativeAIMV.log.Log;
import com.br.collaborativeAIMV.view.templates.TemplatePage;
import com.br.service.valueObject.ActivityVO;
import com.br.service.valueObject.DeveloperVO;
import com.br.service.valueObject.ProjectVO;


public class LoginPage extends TemplatePage{
	private Text txtLog, txtPass;
	private Combo cmbProj;
	private Button btnLog;
	DeveloperDelegate developerDelegate;
	ProjectDelegate projectDelegate;
	Collection<ProjectVO> projects;
	boolean login;
	DeveloperVO currentDeveloper;
	private boolean serverFound;
	private boolean modeToLog;
	
	public LoginPage() {
		super("Login");
		setTitle("Login");
		setDescription("Inform username, password and project to perform login");
		// TODO Auto-generated constructor stub
		
		developerDelegate = new DeveloperDelegate();
		projectDelegate = new ProjectDelegate();
		
		
		
		try {
			projects = projectDelegate.findProjects();
			serverFound = true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			serverFound = false;
			e.printStackTrace();
		}
		
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite comp = new Composite(parent, SWT.NONE);

		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;

		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(gd);
		setTitle("Login");
		
		
		//Constrói janela para logar
		if (LoginControl.GET_INSTANCE().logado == false) {

			// GridData gd = new GridData();
			// gd.grabExcessHorizontalSpace= true;
			// gd.horizontalAlignment=SWT.FILL;

			// Composite comp = new Composite(parent, SWT.NONE);
			// comp.setLayout(new GridLayout(2, false));
			// comp.setLayoutData(gd);
			// setTitle("Login");

			modeToLog = true;
			Label space = new Label(comp, SWT.NONE);
			GridData spaceGD = new GridData();
			spaceGD.horizontalSpan = 2;
			space.setLayoutData(spaceGD);

			Label lblLog = new Label(comp, SWT.NONE);
			lblLog.setText("Login: ");
			this.txtLog = new Text(comp, SWT.BORDER);
			this.txtLog.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			this.txtLog.setToolTipText("Insert your username here");

			Label lblPass = new Label(comp, SWT.NONE);
			lblPass.setText("Password: ");
			this.txtPass = new Text(comp, SWT.BORDER);
			this.txtPass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			this.txtPass.setEchoChar('*');
			this.txtPass.setToolTipText("Insert your password");

			Label lblProj = new Label(comp, SWT.NONE);
			lblProj.setText("Project: ");

			this.cmbProj = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
			this.cmbProj.add("", 0);
			this.cmbProj.select(0);
			this.cmbProj.setToolTipText("Select project that you're registered");

			GridData gdLog = new GridData();
			gdLog.horizontalSpan=2;
			gdLog.grabExcessHorizontalSpace=true;
			btnLog = new Button(comp, SWT.PUSH);
			btnLog.setSize(40, 10);
			btnLog.setLayoutData(gdLog);
			btnLog.setText("Login");
			btnLog.setVisible(false);
			
			if (projects != null) {
				for (ProjectVO projectTemp : projects) {
					if(projectTemp.isActive())
						cmbProj.add(projectTemp.getName());
				}
			}else{
				if(serverFound==true)
					setDescription("Projects could not be found");
				else
					setDescription("It wasn't possible to find any server");
			}
			
			btnLog.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					login();
					
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			// setControl(comp);
			// comp.isVisible();

			
			
			//Constrói janela para desconectar
		} else {
			modeToLog = false;
			setDescription("Already Logged - Click button to disconect");
			currentDeveloper = LoginControl.GET_INSTANCE().currentDeveloper;

			final Label lblCurrentUser = new Label(comp, SWT.NONE);
			lblCurrentUser.setText("Logged as: \n\n" + "Name: "
							+ currentDeveloper.getName() + "\n" + "Username: "
							+ currentDeveloper.getUserName()
							+ "\nCoordinator: "
							+ currentDeveloper.getCoordination().toString()
							+ "\nProject: "
							+ currentDeveloper.getProjectVO().getName());
			
			Label lblAux = new Label(comp, SWT.NONE);
			
			final Button btnDisconect = new Button(comp, SWT.PUSH);
			btnDisconect.setText("Disconect");
			
			btnDisconect.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					modeToLog = true;
					LoginControl.GET_INSTANCE().disconect();
					setDescription("Disconected");
					btnDisconect.setEnabled(false);
					lblCurrentUser.setText("User disconected");
					
					LoginPage.REFRESH_VIEWS();
					
					if(Nodes.getGroup("project")!=null){
						ViewControl.GET_INSTANCE().removeAllListenersOnViews();
						NodesControl.GET_INSTANCE().removeAllListenersOnViewPropertyOfNodes();
						ConcernsControl.GET_INSTANCE().removeAllMessageConcern();
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}

		setControl(comp);
		comp.isVisible();

	}

	@Override
	public boolean performFinish() {

		if(modeToLog){
			login();
			if(LoginControl.GET_INSTANCE().logado){
				
				Runnable run = new Runnable(){
					@Override
					public void run() {
						generateChooseActivityWindow();
					}
				};
				PlatformUI.getWorkbench().getDisplay().syncExec(run);
				
				return true;
			}else{
				return false;
			}
		}else{
			
			modeToLog = true;
			
			LoginControl.GET_INSTANCE().disconect();
			setDescription("Disconected");
			
			LoginPage.REFRESH_VIEWS();
			
			if(Nodes.getGroup("project")!=null){
				ViewControl.GET_INSTANCE().removeAllListenersOnViews();
				NodesControl.GET_INSTANCE().removeAllListenersOnViewPropertyOfNodes();
				ConcernsControl.GET_INSTANCE().removeAllMessageConcern();
			}
			
			return true;
		}
	}

	private void generateChooseActivityWindow() {
		final Shell dialog = new Shell(SWT.DIALOG_TRIM);
		dialog.setLayout(new GridLayout(3, false));
		dialog.setText("Activity Selection");

		Label lbl = new Label(dialog, SWT.NONE);
		lbl.setText("Select an activity to start the collaborative session \n(the first activity will be selected as default if you do not select" +
				" anyone)");
		
		final Combo cmbAtiv = new Combo(dialog, SWT.DROP_DOWN | SWT.READ_ONLY);
		ActivityDelegate activityDelegate = new ActivityDelegate();
		try {
			for(ActivityVO activityTemp : activityDelegate.findActivitiesOnProject(LoginControl.GET_INSTANCE().currentProject)){
				if(activityTemp.isActive())
					cmbAtiv.add(activityTemp.getName());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmbAtiv.select(0);
		SystemMessagesControl.activitySelected = cmbAtiv.getItem(cmbAtiv.getSelectionIndex());

		Button btnOk = new Button(dialog, SWT.PUSH);
		btnOk.setText("OK");
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		
		btnOk.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {				
				
				SystemMessagesControl.activitySelected = cmbAtiv.getItem(cmbAtiv.getSelectionIndex());
				
				// persist on GiveMe Views
				MemoryCollaborativeSourceminer.setCurrentActivity(true, SystemMessagesControl.activitySelected);
				
				SystemMessagesControl.sendSystemMessage(LoginControl.GET_INSTANCE().currentProject.getName(),
						"", "", "Initiated the activity execution.");
				LoginPage.REFRESH_VIEWS();
				dialog.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		dialog.addShellListener(new ShellListener() {
			
			@Override
			public void shellIconified(ShellEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeiconified(ShellEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeactivated(ShellEvent arg0) {
				
				SystemMessagesControl.activitySelected = cmbAtiv.getItem(cmbAtiv.getSelectionIndex());
				
				// persist on GiveMe Views
				MemoryCollaborativeSourceminer.setCurrentActivity(true, SystemMessagesControl.activitySelected);
				
				SystemMessagesControl.sendSystemMessage(LoginControl.GET_INSTANCE().currentProject.getName(),
						"", "", "Initiated the activity execution.");
				LoginPage.REFRESH_VIEWS();
				dialog.dispose();
			}
			
			@Override
			public void shellClosed(ShellEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellActivated(ShellEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		dialog.setDefaultButton(btnOk);
		dialog.pack();
		dialog.open();
	}
	
	public void futucandoAimv(){
		
		//TESTANTO CONTROLLER
		//AIMV.addListener(new ControllerListener());
		//TESTANDO EVENTOS DOS NÓS
		//FAZER NO MOMENTO EM QUE UMA VISÃO ABRIR - USAR VIEW LISTENER
		
		NodesControl.GET_INSTANCE().addListenerOnViewPropertyOfNodes();
		/*
		Collection<Node> nodes = Nodes.getGroup("all").getNodes();
		for(Node nodeTemp : nodes){
			Image image = SWTResourceManager.getImage("resources/icons/Chat-icon.png");
			nodeTemp.setProperty("Message", );
			
		}
			*/
		if(Nodes.getGroup("project")==null){
			System.out.println("Grupo project nulo");
		}else{
			System.out.println("Grupo project não nulo");
		}
		
		//if(LoginControl.GET_INSTANCE().logado)
			//System.out.println(Nodes.getGroup("project").getNode("Project " + LoginControl.GET_INSTANCE().currentProject.getName()).toString().contains("Paint com problemas") + "\n\n");
		
		//ANALISANDO TODOS OS NODES
		/*
		Collection<Node> nodesAll = Nodes.getGroup("all").getNodes();
		for(Node nodeTemp : nodesAll){
			System.out.println("ID: "+ nodeTemp.getID() + " :: "+ nodeTemp.toString() +" :: "+ nodeTemp.getAllProperties().toString() +"\n");
		}
		*/
		//Concerns, Number Concerns
		
		
		if (LoginControl.GET_INSTANCE().isRightProjectAnalysed()) {
			//ConcernsControl.GET_INSTANCE().addMessagesConcern();
			
			//ConcernsControl.GET_INSTANCE().addMessageConcernFromXml();
			
			Collection<Node> nodes = Nodes.getGroup("all").getNodes();
			for (Node nodeTemp : nodes) {				
				String[] aux = (String[]) nodeTemp.getProperty("Concerns");
				System.out.println(aux.length);
				System.out.println(nodeTemp.getProperty("Number Concerns"));
				for(int i=0; i<aux.length; i++){
					System.out.println(aux[i]);
				}
				
				/*
				System.out.println("Concern: "
						+ nodeTemp.getProperty("Concerns")
						+ "::Number Concerns: "
						+ nodeTemp.getProperty("Number Concerns")
						+ "::color_concern: "
						+ nodeTemp.getProperty("color_concern"));

				
				Collection<Relation> relations = nodeTemp.getRelations();
				for (Relation relationTemp : relations) {
					System.out.println("Source: " + relationTemp.getSource()
							+ " Target: " + relationTemp.getTarget());
				}
				*/
			}	
		}
	}
	
	public void login(){
		if (txtLog.getText().isEmpty() || txtPass.getText().isEmpty() || cmbProj.getSelectionIndex() == 0) {
			setDescription("Fields doesn't filled correctly");
		} else {

			try {
				LoginControl.GET_INSTANCE().logging(txtLog.getText(),txtPass.getText(),cmbProj.getItem(cmbProj.getSelectionIndex()));
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (LoginControl.GET_INSTANCE().logado) {
				modeToLog = false;
				
				Log.registerLogin(LoginControl.GET_INSTANCE().currentDeveloper.getName(),
						LoginControl.GET_INSTANCE().currentDeveloper.getUserName(),
						LoginControl.GET_INSTANCE().currentProject.getName());
				
				SystemMessagesControl.sendSystemMessage(LoginControl.GET_INSTANCE().currentProject.getName(),
						"", "", "Logged on project.");
				
				LoginControl.GET_INSTANCE().verifyAnalysedProject();
				
			} else {
				setDescription("Login not performed");
			}
		}
	}
	
	public static void REFRESH_VIEWS(){
		Runnable run = new Runnable(){

			@Override
			public void run() {
				for (ViewAIMV paradigm : AIMV.getActiveViews())
					paradigm.layout();				
			}
		
		};
		//BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), run);
		PlatformUI.getWorkbench().getDisplay().syncExec(run);
	}
	
}
