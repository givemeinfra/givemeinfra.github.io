package com.br.collaborativeAIMV.view.pages;

import java.rmi.RemoteException;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.br.service.valueObject.ProjectVO;
import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.delegate.ProjectDelegate;
import com.br.collaborativeAIMV.log.Log;
import com.br.collaborativeAIMV.view.templates.TemplatePage;


public class ProjectPage extends TemplatePage{
	private Text txtProj;
	private Button btnRem, btnCad, btnAtu, btnPesq, btnCancel;
	private Table tblProj;
	private int itemInt;
	private ProjectDelegate projectDelegate;
	private ProjectVO currentProject;
	
	public ProjectPage() {
		super("Project");
		setTitle("Manage Projects");
		setDescription("Register, update and remove projects");
		
		projectDelegate = new ProjectDelegate();
		currentProject = LoginControl.GET_INSTANCE().currentProject;
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
		setTitle("Manage Projects");
		comp.setLayoutData(compGD);
		
		Label space = new Label(comp, SWT.NONE);
		GridData spaceGD = new GridData();
		spaceGD.horizontalSpan = 3;
		space.setLayoutData(spaceGD);
		
		Label lblName = new Label(comp, SWT.NONE);
		lblName.setText("Project name: ");
		
		txtProj = new Text(comp, SWT.BORDER);
		txtProj.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtProj.setToolTipText("Register a new project on the server");
		
		btnPesq = new Button(comp, SWT.PUSH);
		btnPesq.setText("Search");
		btnPesq.setEnabled(true);
		btnPesq.setToolTipText("Search for registered projects");
		
		Composite comp2 = new Composite(parent,SWT.NONE);
		comp2.setLayout(new GridLayout(4, false));
		comp2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true , false));
		
		GridData btnGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
		
		tblProj = new Table(comp2, SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tblProj.setHeaderVisible(true);
		tblProj.setLinesVisible(true);
		GridData tblGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
		tblGD.horizontalSpan = 4;
		tblGD.verticalSpan = 5;
		tblGD.heightHint = 130;
		tblGD.widthHint = 280;
		tblProj.setLayoutData(tblGD);
		
		TableColumn projColumn = new TableColumn(tblProj, SWT.NONE);
		projColumn.setAlignment(SWT.CENTER);
		projColumn.setText("Projects");
		projColumn.setWidth(240);
		
		TableColumn activeColumn = new TableColumn(tblProj, SWT.NONE);
		activeColumn.setAlignment(SWT.CENTER);
		activeColumn.setText("Active");
		activeColumn.setWidth(240);
		
		btnCad = new Button(comp2, SWT.PUSH);
		btnCad.setText("Register");
		btnCad.setEnabled(true);
		btnCad.setLayoutData(btnGD);
		btnCad.setToolTipText("Register a new project");
		
		btnAtu = new Button(comp2, SWT.PUSH);
		btnAtu.setText("Update");
		btnAtu.setLayoutData(btnGD);
		btnAtu.setEnabled(false);
		btnAtu.setToolTipText("Update the selected project");
		
		btnRem = new Button(comp2, SWT.PUSH);
		btnRem.setText("Remove");
		btnRem.setLayoutData(btnGD);
		btnRem.setEnabled(false);
		btnRem.setToolTipText("Activate or deactivate the selected project");
		
		btnCancel = new Button(comp2, SWT.PUSH);
		btnCancel.setText("Cancel Edit");
		btnCancel.setLayoutData(btnGD);
		btnCancel.setToolTipText("Cancel edit mode");
		btnCancel.setEnabled(false);
		
		
		
		
		//Evento do botão de SEARCH
		btnPesq.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				tblProj.removeAll();
				boolean found = false;
				
				Collection<ProjectVO> projects = null;
				try {
					projects = projectDelegate.findProjects();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (projects != null) {
					if (txtProj.getText() == "") {

						for (ProjectVO projectTemp : projects) {
							TableItem item = new TableItem(tblProj, SWT.NONE);
							item.setText(new String[]{projectTemp.getName(),Boolean.toString(projectTemp.isActive())});
						}
						found = true;
					} else {

						for (ProjectVO projectTemp : projects) {
							if (projectTemp.getName().contains(txtProj.getText())) {
								TableItem item = new TableItem(tblProj,SWT.NONE);
								item.setText(new String[]{projectTemp.getName(), Boolean.toString(projectTemp.isActive())});
								found = true;
							}
						}

					}
				}
				if(found==true){
					setDescription("Projects found");
				}else{
					setDescription("No projects found");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Evento do botão REGISTER
		btnCad.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (!txtProj.getText().isEmpty()) {

					int cont = 0;

					ProjectVO project = new ProjectVO();
					project.setName(txtProj.getText());
					project.setActive(true);
					Collection<ProjectVO> projects = null;
					try {
						projects = projectDelegate.findProjects();
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					if (projects != null) {
						for (ProjectVO projectTemp : projects) {
							if (projectTemp.getName().equals(project.getName())) {
								cont++;
							}
						}
					}
					if (cont == 0) {
						try {
							projectDelegate.addProject(project);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						TableItem item = new TableItem(tblProj, SWT.NONE);
						item.setText(new String[] { project.getName(), "true"});

						txtProj.setText("");
						
						Log.print("PROJECT: " + project.getName() + " CREATED");
						setDescription("Project registered successfully!");
					} else {
						setDescription("Project already exists");
					}
				} else {
					setDescription("Field must be filled");
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//Habilita botões e captura seleção da tabela
		tblProj.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				itemInt = tblProj.getSelectionIndex();
				txtProj.setText(tblProj.getItem(itemInt).getText(0));
				if(tblProj.getItem(itemInt).getText(1).equals("true"))
					btnRem.setText("Deactivate");
				else
					btnRem.setText("Activate");
				btnCad.setEnabled(false);
				btnRem.setEnabled(true);
				btnAtu.setEnabled(true);
				btnCancel.setEnabled(true);
				
				setDescription("Update or remove selected project");
			}
		});
		
		//Evento do botão REMOVE
		btnRem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (!tblProj.getItem(itemInt).getText(0).equals(currentProject.getName())) {
					Collection<ProjectVO> projects = null;
					try {
						projects = projectDelegate.findProjects();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (projects != null) {
						boolean removed = false;
						for (ProjectVO projectTemp : projects) {
							if (tblProj.getItem(itemInt).getText(0).equals(projectTemp.getName())) {
								try {
									removed = projectDelegate.removeProject(projectTemp);
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								if (removed == true) {
									boolean value = !Boolean.parseBoolean(tblProj.getItem(itemInt).getText(1));
									tblProj.getItem(itemInt).setText(1, Boolean.toString(value));
									cancelEdit();
									if(value){
										setDescription("Project activated successfully!");
										Log.print("PROJECT " + projectTemp.getName() + " ACTIVATED");
									}else{
										setDescription("Project deactivated successfully!");
										Log.print("PROJECT " + projectTemp.getName() + " DEACTIVATED");
									}
									
								} else {
									setDescription("Remove failed");
								}
								break;
							}
						}
					}
				} else {
					setDescription("You can not deactivate the current project");
				}
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
				
				Collection<ProjectVO> projects = null;
				try {
					projects = projectDelegate.findProjects();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				boolean updateDone = false;
				
				if (projects != null) {
					for (ProjectVO projectTemp : projects) {
						if (tblProj.getItem(itemInt).getText(0).equals(projectTemp.getName())) {
							ProjectVO project = new ProjectVO();
							project.setName(txtProj.getText());
							project.setActive(projectTemp.isActive());

							try {
								updateDone = projectDelegate.updateProject(projectTemp, project);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							if (updateDone == true){
								setDescription("Update done");
								Log.print("PROJECT " + projectTemp.getName() + " UPDATED TO " + project.getName());
							}else
								setDescription("Update failed");

							break;
						}
					}
				}
				if(updateDone==true){
					Collection<ProjectVO> projects2 = null;
					try {
						projects2 = projectDelegate.findProjects();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tblProj.removeAll();
					for(ProjectVO projectTemp : projects2){
						TableItem item = new TableItem(tblProj, SWT.NONE);
						item.setText(new String[] {  projectTemp.getName(), Boolean.toString(projectTemp.isActive())});
					}
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
				cancelEdit();
				tblProj.removeAll();
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
	
	public void cancelEdit(){
		btnAtu.setEnabled(false);
		btnRem.setEnabled(false);
		btnCancel.setEnabled(false);
		btnCad.setEnabled(true);
		txtProj.setText("");
		
		setDescription("Register, update and remove projects");
	}
	
	
}
