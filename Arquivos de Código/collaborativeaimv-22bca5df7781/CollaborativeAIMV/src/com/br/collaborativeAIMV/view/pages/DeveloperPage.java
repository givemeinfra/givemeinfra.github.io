package com.br.collaborativeAIMV.view.pages;

import java.rmi.RemoteException;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.delegate.ActivityDelegate;
import com.br.collaborativeAIMV.delegate.DeveloperDelegate;
import com.br.collaborativeAIMV.delegate.ProjectDelegate;
import com.br.collaborativeAIMV.log.Log;
import com.br.collaborativeAIMV.view.templates.TemplatePage;
import com.br.service.valueObject.DeveloperVO;
import com.br.service.valueObject.ProjectVO;


public class DeveloperPage extends TemplatePage{
	private Text txtName, txtUser, txtPass, txtPass2;
	private Combo cmbProj;
	private Button chkCoord, btnRem, btnCad, btnAtu, btnPesq, btnCancel;
	private Group grpMan;
	private Table tblDev;
	private int itemInt, projectIndex;
	Collection<ProjectVO> projects;
	ProjectDelegate projectDelegate;
	DeveloperDelegate developerDelegate;
	ActivityDelegate activityDelegate;
	private ProjectVO currentProject;
	private DeveloperVO currentDeveloper;
	
	public DeveloperPage() {
		super("desenvolvedor");
		setTitle("Manage Developers");
		setDescription("Register, update and remove developers");
		
		developerDelegate = new DeveloperDelegate();
		activityDelegate = new ActivityDelegate();
		projectDelegate = new ProjectDelegate();
		try {
			projects = projectDelegate.findProjects();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		currentProject = LoginControl.GET_INSTANCE().currentProject;
		currentDeveloper = LoginControl.GET_INSTANCE().currentDeveloper;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void createControl(Composite parent){
		parent.setLayout(new GridLayout(1, true));
		
		GridData compGD = new GridData();
		compGD.grabExcessHorizontalSpace = true;
		compGD.horizontalAlignment = SWT.FILL;
		
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(3, false));
		setTitle("Manage Developers");
		comp.setLayoutData(compGD);
		
		Label space = new Label(comp, SWT.NONE);
		GridData spaceGD = new GridData();
		spaceGD.horizontalSpan = 3;
		space.setLayoutData(spaceGD);
		
		Label lblName = new Label(comp, SWT.NONE);
		lblName.setText("Name: ");
		
		txtName = new Text(comp, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtName.setToolTipText("Insert developer's name");
		
		btnPesq = new Button(comp, SWT.PUSH);
		btnPesq.setText("Search");
		btnPesq.setEnabled(true);
		btnPesq.setToolTipText("Search for registered developers");
		
		Label lblUser = new Label(comp, SWT.NONE);
		lblUser.setText("UserName: ");
		txtUser = new Text(comp, SWT.BORDER);
		txtUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtUser.setToolTipText("The username will be used to perform login");
		
		Label lblPass = new Label(comp, SWT.NONE);
		lblPass.setText("Password: ");
		txtPass = new Text(comp, SWT.BORDER);
		txtPass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtPass.setEchoChar('*');
		txtPass.setToolTipText("Insert developer's password");
		
		Label lblPass2 = new Label(comp, SWT.NONE);
		lblPass2.setText("Repeat Password: ");
		txtPass2 = new Text(comp, SWT.BORDER);
		txtPass2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtPass2.setEchoChar('*');
		txtPass2.setToolTipText("Repeat password");
		
		Label lblProj = new Label(comp, SWT.NONE);
		lblProj.setText("Project: ");
		GridData cmbGD = new GridData();
		cmbGD.horizontalSpan = 2;
		cmbProj = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmbProj.setLayoutData(cmbGD);
		cmbProj.add("",0);
        cmbProj.select(0);
        cmbProj.setToolTipText("Select project that developer will be registered");
		
		chkCoord = new Button(comp, SWT.CHECK);
		chkCoord.setText("Coordinator");
		chkCoord.setToolTipText("Check box if developer must be a project coordinator");
		
		grpMan = new Group(parent, SWT.SHADOW_ETCHED_IN);
		grpMan.setText("Management");
		
	
		grpMan.setLayout(new GridLayout(4, false));
		grpMan.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		GridData btnGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
		
		
		
		tblDev = new Table(grpMan, SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tblDev.setHeaderVisible(true);
		tblDev.setLinesVisible(true);
		GridData tblGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
		tblGD.horizontalSpan = 4;
		tblGD.verticalSpan = 5;
		tblGD.heightHint = 130;
		tblGD.widthHint = 280;
		tblDev.setLayoutData(tblGD);
		
		TableColumn devColumn = new TableColumn(tblDev, SWT.NONE);
		devColumn.setAlignment(SWT.LEFT);
		devColumn.setText("Developer");
		devColumn.setWidth(109);
		
		TableColumn userColumn = new TableColumn(tblDev, SWT.NONE);
		userColumn.setAlignment(SWT.LEFT);
		userColumn.setText("UserName");
		userColumn.setWidth(109);
		
		TableColumn projColumn = new TableColumn(tblDev, SWT.NONE);
		projColumn.setAlignment(SWT.LEFT);
		projColumn.setText("Project");
		projColumn.setWidth(109);
		
		TableColumn coordColumn = new TableColumn(tblDev, SWT.NONE);
		coordColumn.setAlignment(SWT.LEFT);
		coordColumn.setText("Coordinator");
		coordColumn.setWidth(109);
		
		TableColumn activeColumn = new TableColumn(tblDev, SWT.NONE);
		activeColumn.setAlignment(SWT.LEFT);
		activeColumn.setText("Active");
		activeColumn.setWidth(109);
		
		btnCad = new Button(grpMan, SWT.PUSH);
		btnCad.setText("Register");
		btnCad.setLayoutData(btnGD);
		btnCad.setEnabled(true);
		btnCad.setToolTipText("Register a new developer");
		
		btnAtu = new Button(grpMan, SWT.NONE);
		btnAtu.setText("Update");
		btnAtu.setLayoutData(btnGD);
		btnAtu.setEnabled(false);
		btnAtu.setToolTipText("Update dates from the selected develeoper");
		
		btnRem = new Button(grpMan, SWT.NONE);
		btnRem.setText("Remove");
		btnRem.setLayoutData(btnGD);
		btnRem.setEnabled(false);
		btnRem.setToolTipText("Active or deactive the selected developer");
		
		btnCancel = new Button(grpMan, SWT.PUSH);
		btnCancel.setText("Cancel Edit");
		btnCancel.setLayoutData(btnGD);
		btnCancel.setToolTipText("Cancel edit mode");
		btnCancel.setEnabled(false);
	
		if (projects != null) {
			for (ProjectVO projectTemp : projects) {
				if(projectTemp.isActive())
					cmbProj.add(projectTemp.getName());
			}
		}
		cmbProj.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				projectIndex = cmbProj.getSelectionIndex();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
		//Habilita botões e captura seleção da tabela
		tblDev.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				itemInt = tblDev.getSelectionIndex();
				txtName.setText(tblDev.getItem(itemInt).getText(0));
				txtUser.setText(tblDev.getItem(itemInt).getText(1));
				
				Collection<DeveloperVO> developers = null;
				try {
					developers = developerDelegate.findDevelopersOnProject(currentProject);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				for(DeveloperVO developerTemp : developers){
					if(tblDev.getItem(itemInt).getText(1).equals(developerTemp.getUserName())){
						txtPass.setText(developerTemp.getPassWord());
						txtPass2.setText(developerTemp.getPassWord());
						for(int cont=0;cont<=cmbProj.getItemCount();cont++){
							if(developerTemp.getProjectVO().getName().equals(cmbProj.getItem(cont).toString())){
								cmbProj.select(cont);
								break;
							}
						}
			
						chkCoord.setSelection(developerTemp.getCoordination());
						
					}
				}
				
				if(tblDev.getItem(itemInt).getText(4).equals("true"))
					btnRem.setText("Deactivate");
				else
					btnRem.setText("Activate");
				
				btnCad.setEnabled(false);
				btnRem.setEnabled(true);
				btnAtu.setEnabled(true);
				btnCancel.setEnabled(true);
				setDescription("Edit dates from selected developer");
				
			}
		});
		
		//Evento de remoção
		btnRem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (!tblDev.getItem(itemInt).getText(1).equals(currentDeveloper.getUserName())) {

					Collection<DeveloperVO> developers = null;
					try {
						developers = developerDelegate.findDevelopersOnProject(currentProject);
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					for (DeveloperVO developerTemp : developers) {
						if (tblDev.getItem(itemInt).getText(1).equals(developerTemp.getUserName())) {
							try {
								developerDelegate.removeDeveloperOnProject(currentProject, developerTemp);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							setDescription("Developer removed successfully!");

							boolean value = !Boolean.parseBoolean(tblDev.getItem(itemInt).getText(4));
							tblDev.getItem(itemInt).setText(4, Boolean.toString(value));
							
							if(value){
								setDescription("Developer activated successfully!");
								Log.print("DEVELOPER " + developerTemp.getName() + " ACTIVATED");
							}else{
								setDescription("Developer deactivated successfully!");
								Log.print("DEVELOPER " + developerTemp.getName() + " DEACTIVATED");
							}
							
							btnAtu.setEnabled(false);
							btnRem.setEnabled(false);
							btnCancel.setEnabled(false);
							btnCad.setEnabled(true);
							txtName.setText("");
							txtUser.setText("");
							txtPass.setText("");
							txtPass2.setText("");
							cmbProj.select(0);
							chkCoord.setSelection(false);
							break;
						}
					}
				}else{
					setDescription("You can not deactivate the current user");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//Evento do botão SEARCH
		btnPesq.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				tblDev.removeAll();
				boolean found = false;
				
				Collection<DeveloperVO> developers = null;
				try {
					developers = developerDelegate.findDevelopersOnProject(currentProject);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (developers != null) {
					if (txtName.getText() == "") {
						ProjectVO auxProject = new ProjectVO(false, "");
						for (DeveloperVO developerTemp : developers) {
							
							if(developerTemp.getProjectVO() == null)
								developerTemp.setProjectVO(auxProject);
							
							TableItem item = new TableItem(tblDev, SWT.NONE);
							item.setText(new String[] {
									developerTemp.getName(),
									developerTemp.getUserName(),
									developerTemp.getProjectVO().getName(),									
									developerTemp.getCoordination().toString(),
									Boolean.toString(developerTemp.isActive())});
						}
						found = true;
					} else {

						for (DeveloperVO developerTemp : developers) {
							if (developerTemp.getName().contains(txtName.getText())) {
								TableItem tblItem = new TableItem(tblDev,SWT.NONE);
								tblItem.setText(new String[] {
										developerTemp.getName(),
										developerTemp.getUserName(),
										developerTemp.getProjectVO().getName(),
										developerTemp.getCoordination().toString(),
										Boolean.toString(developerTemp.isActive())});
								found = true;

							}
						}
					}
				}
				
				if (found == true) {
					setDescription("Developers found");
				} else {
					setDescription("No developers found");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//Evento de botão REGISTER
		btnCad.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
	 			// TODO Auto-generated method stub
				
				if (!anyEmptyFields()) {
					// Prepara novo desenvolvedor
					DeveloperVO developer = new DeveloperVO();
					developer.setName(txtName.getText());
					developer.setUserName(txtUser.getText());
					developer.setCoordination(chkCoord.getSelection());
					developer.setActive(true);

					ProjectVO projectDev = new ProjectVO();
					Collection<ProjectVO> projects2 = null;
					try {
						projects2 = projectDelegate.findProjects();
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					for (ProjectVO projectTemp : projects2) {
						if (cmbProj.getItem(projectIndex).equals(projectTemp.getName())) {
							projectDev = projectTemp;
							break;
						}
					}

					developer.setProjectVO(projectDev);
					// Cadastrar desenvolvedor
					if (txtPass.getText().equals(txtPass2.getText())) {
						developer.setPassWord(txtPass.getText());
						try {
							developerDelegate.addDeveloperOnProject(projectDev, developer);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						TableItem item = new TableItem(tblDev, SWT.NONE);
						item.setText(new String[] { developer.getName(),
								developer.getUserName(),
								developer.getProjectVO().getName(),
								developer.getCoordination().toString(),
								"true"});

						setFieldsDefault();
						setDescription("Developer registered successfully!");
						Log.print("DEVELOPER: " + developer.getName() + " CREATED");

					} else {
						setDescription("Passwords doesn't match");

					}
				}else{
					setDescription("Fields must be filled");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		//Evento de botão CANCEL EDIT
		btnCancel.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				setFieldsDefault();
				setDescription("Register, update and remove developers");
				
				tblDev.removeAll();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Evento do botão UPDATE
		btnAtu.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (!anyEmptyFields()) {

					Collection<DeveloperVO> developers = null;
					try {
						developers = developerDelegate.findDevelopersOnProject(currentProject);
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					boolean updateDone = false;

					for (DeveloperVO developerTemp : developers) {
						if (tblDev.getItem(itemInt).getText(1).equals(developerTemp.getUserName())) {
							DeveloperVO developer = new DeveloperVO();
							developer.setName(txtName.getText());
							developer.setUserName(txtUser.getText());
							developer.setCoordination(chkCoord.getSelection());
							developer.setActive(developerTemp.isActive());

							for (ProjectVO projectTemp : projects) {
								if (projectTemp.getName().equals(cmbProj.getSelection().toString())) {
									developer.setProjectVO(projectTemp);

									break;
								}
							}

							if (txtPass.getText().equals(txtPass2.getText())) {
								developer.setPassWord(txtPass.getText());
								try {
									updateDone = developerDelegate.updateDeveloperOnProject(currentProject,developerTemp, developer);
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								setDescription("Update done");
								Log.print("DEVELOPER " + developerTemp.getName() + " UPDATED TO " + developer.getName());
								break;

							} else {
								setDescription("Passwords doesn't match, try again.");
							}

						}
					}

					if (updateDone == true) {
						Collection<DeveloperVO> developers2 = null;
						try {
							developers2 = developerDelegate.findDevelopersOnProject(currentProject);
						} catch (RemoteException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						tblDev.removeAll();
						for (DeveloperVO developerTemp : developers2) {
							TableItem item = new TableItem(tblDev, SWT.NONE);
							item.setText(new String[] {
									developerTemp.getName(),
									developerTemp.getUserName(),
									developerTemp.getProjectVO().getName(),
									developerTemp.getCoordination().toString(),
									Boolean.toString(developerTemp.isActive())});
						}
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
		setControl(comp);
		comp.isVisible();
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setFieldsDefault(){
		btnAtu.setEnabled(false);
		btnRem.setEnabled(false);
		btnCancel.setEnabled(false);
		btnCad.setEnabled(true);
		txtName.setText("");
		txtUser.setText("");
		txtPass.setText("");
		txtPass2.setText("");
		cmbProj.select(0);
		chkCoord.setSelection(false);
	}
	
	public boolean anyEmptyFields(){
		int cont=0;
		for(int i=0;i<5;i++){
			switch(i){
			case 0:
				if(txtName.getText().equals(""))
					cont++;
				break;
			case 1:
				if(txtUser.getText().equals(""))
					cont++;
				break;
			case 2:
				if(txtPass.getText().equals(""))
					cont++;
				break;
			case 3:
				if(txtPass2.getText().equals(""))
					cont++;
				break;
			case 4:
				if(cmbProj.getSelectionIndex()==0)
					cont++;
				break;
			}
		}
		
		if(cont!=0)
			return true;
		else
			return false;
	}
	
	
}
