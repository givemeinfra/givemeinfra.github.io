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

import com.br.service.valueObject.ActivityVO;
import com.br.service.valueObject.ProjectVO;
import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.delegate.ActivityDelegate;
import com.br.collaborativeAIMV.log.Log;
import com.br.collaborativeAIMV.view.templates.TemplatePage;


public class ActivityPage extends TemplatePage{
	private Text txtAtiv;
	private Button btnRem, btnCad, btnAtu, btnPesq, btnCancel;
	private Table tblAtiv;
	private int itemInt;
	private ActivityDelegate activityDelegate;
	private ProjectVO currentProject;
	
	
	public ActivityPage() {
		super("Atividade");
		setTitle("Manage Activities");
		setDescription("Register, update and remove activities from the current project");
		
		activityDelegate = new ActivityDelegate();
		currentProject = LoginControl.GET_INSTANCE().currentProject;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void createControl(Composite parent){
		parent.setLayout(new GridLayout(1, true));
		
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(3, false));
		setTitle("Manage Activities");
		
		GridData compGD = new GridData();
		compGD.grabExcessHorizontalSpace = true;
		compGD.horizontalAlignment = SWT.FILL;
		comp.setLayoutData(compGD);
		
		Label space = new Label(comp, SWT.NONE);
		GridData spaceGD = new GridData();
		spaceGD.horizontalSpan = 3;
		space.setLayoutData(spaceGD);
		
		Label lblAtiv = new Label(comp, SWT.NONE);
		lblAtiv.setText("Activity name: ");
		
		txtAtiv = new Text(comp, SWT.BORDER);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = false;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.CENTER;
		txtAtiv.setLayoutData(gd);
		txtAtiv.setToolTipText("Register an activity for the current project");
		
		btnPesq = new Button(comp, SWT.PUSH);
		btnPesq.setText("Search");
		btnPesq.setEnabled(true);
		btnPesq.setToolTipText("Search for registered activities");
		
		Composite comp2 = new Composite(parent,SWT.NONE);
		comp2.setLayout(new GridLayout(4, false));
		comp2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true , false));
		
		GridData btnGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
		
		tblAtiv = new Table(comp2, SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tblAtiv.setHeaderVisible(true);
		tblAtiv.setLinesVisible(true);
		GridData tblGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
		tblGD.horizontalSpan = 4;
		tblGD.verticalSpan = 5;
		tblGD.heightHint = 130;
		tblGD.widthHint = 280;
		tblAtiv.setLayoutData(tblGD);
		
		TableColumn ativColumn = new TableColumn(tblAtiv, SWT.NONE);
		ativColumn.setAlignment(SWT.CENTER);
		ativColumn.setText("Activities");
		ativColumn.setWidth(240);
		//era 480 
		TableColumn activeColumn = new TableColumn(tblAtiv, SWT.NONE);
		activeColumn.setAlignment(SWT.CENTER);
		activeColumn.setText("Active");
		activeColumn.setWidth(240);
		
		btnCad = new Button(comp2, SWT.PUSH);
		btnCad.setText("Register");
		btnCad.setLayoutData(btnGD);
		btnCad.setToolTipText("Register a new activity on the current project");
		btnCad.setEnabled(true);
		
		
		btnAtu = new Button(comp2, SWT.PUSH);
		btnAtu.setText("Update");
		btnAtu.setLayoutData(btnGD);
		btnAtu.setEnabled(false);
		btnAtu.setToolTipText("Edit activity text field and click here to update the selected activity");
		
		btnRem = new Button(comp2, SWT.PUSH);
		btnRem.setText("Activate");
		btnRem.setLayoutData(btnGD);
		btnRem.setEnabled(false);
		btnRem.setToolTipText("Active or deactive the selected actvity");
		
		btnCancel = new Button(comp2, SWT.PUSH);
		btnCancel.setText("Cancel Edit");
		btnCancel.setLayoutData(btnGD);
		btnCancel.setToolTipText("Cancel edit mode");
		btnCancel.setEnabled(false);
		

		

		
		//Habilita botões e captura seleção da tabela
		tblAtiv.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				itemInt = tblAtiv.getSelectionIndex();
				txtAtiv.setText(tblAtiv.getItem(itemInt).getText(0));
				if(tblAtiv.getItem(itemInt).getText(1).equals("true"))
					btnRem.setText("Deactivate");
				else
					btnRem.setText("Activate");
				btnCad.setEnabled(false);
				btnRem.setEnabled(true);
				btnAtu.setEnabled(true);
				btnCancel.setEnabled(true);
				
				setDescription("Update ou remove selected activity");
				
			}
		});
		
		//Evento de botão SEARCH
		btnPesq.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				tblAtiv.removeAll();
				boolean found = false;
				
				Collection<ActivityVO> activities = null;
				try {
					activities = activityDelegate.findActivitiesOnProject(currentProject);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (activities != null) {
					if (txtAtiv.getText() == "") {

						for (ActivityVO activityTemp : activities) {
							TableItem item = new TableItem(tblAtiv, SWT.NONE);
							item.setText(new String[]{ activityTemp.getName(), Boolean.toString(activityTemp.isActive())});
						}
						found = true;
					} else {

						for (ActivityVO activityTemp : activities) {
							if (activityTemp.getName().contains(txtAtiv.getText())) {
								TableItem item = new TableItem(tblAtiv,SWT.NONE);
								item.setText(new String[]{ activityTemp.getName(), Boolean.toString(activityTemp.isActive())});
								found = true;
							}
						}
					}
				}
				
				if(found==true){
					setDescription("Activities found");
				}else{
					setDescription("No activities found");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		
		
		//Evento do botão REGISTER
		btnCad.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (!txtAtiv.getText().isEmpty()) {

					int cont = 0;

					ActivityVO activity = new ActivityVO();
					activity.setName(txtAtiv.getText());
					activity.setActive(true);
					Collection<ActivityVO> activities = null;
					try {
						activities = activityDelegate.findActivitiesOnProject(currentProject);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(activities!=null){
						for (ActivityVO activityTemp : activities) {
							if (activityTemp.getName().equals(activity.getName())) {
								cont++;
							}
						}
					}
					if (cont == 0) {
						try {
							activityDelegate.addActivityOnProject(currentProject, activity);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						TableItem item = new TableItem(tblAtiv, SWT.NONE);
						item.setText(new String[]{activity.getName(), "true"});
						txtAtiv.setText("");

						Log.print("ACTIVITY: " + activity.getName() + " CREATED");
						
						setDescription("Activity registered successfully!");
					} else {
						setDescription("Activity already exists");
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
		
		
		
		//Evento de remoção
		btnRem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				Collection<ActivityVO> activities = null;
				try {
					activities = activityDelegate.findActivitiesOnProject(currentProject);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				for(ActivityVO activityTemp : activities){
					if(tblAtiv.getItem(itemInt).getText(0).equals(activityTemp.getName())){
						boolean removed=false;
						
						try {
							removed = activityDelegate.removeActivityOnProject(currentProject, activityTemp);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if(removed==true){
							//tblAtiv.remove(itemInt);
							boolean value = !Boolean.parseBoolean(tblAtiv.getItem(itemInt).getText(1));
							tblAtiv.getItem(itemInt).setText(1, Boolean.toString(value));
							cancelEdit();
							if(value){
								setDescription("Activity activated successfully!");
								Log.print("ACTIVITY " + activityTemp.getName() + " ACTIVATED");
							}else{
								setDescription("Activity deactivated successfully!");
								Log.print("ACTIVITY " + activityTemp.getName() + " DEACTIVATED");
							}
							
						}else{
							setDescription("Remove fail");
						}
						break;
					}
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
				
				Collection<ActivityVO> activities = null;
				try {
					activities = activityDelegate.findActivitiesOnProject(currentProject);
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				boolean updateDone = false;
				
				if (activities != null) {
					for (ActivityVO activityTemp : activities) {
						if (tblAtiv.getItem(itemInt).getText(0).equals(activityTemp.getName())) {
							ActivityVO activity = new ActivityVO();
							activity.setName(txtAtiv.getText());
							activity.setActive(activityTemp.isActive());

							boolean updated = false;
							try {
								updated = activityDelegate.updateActivityOnProject(currentProject, activityTemp,activity);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							if (updated == true) {
								setDescription("Update done");
								Log.print("ACTIVITY " + activityTemp.getName() + " UPDATED TO " + activity.getName());
							}else{
								setDescription("Update failed");
							}
							break;
						}
					}
				}
				if(updateDone==true){
					Collection<ActivityVO> activities2 = null;
					try {
						activities2 = activityDelegate.findActivitiesOnProject(currentProject);
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					tblAtiv.removeAll();
					for(ActivityVO activityTemp : activities2){
						TableItem item = new TableItem(tblAtiv, SWT.NONE);
						item.setText(new String[]{activityTemp.getName(), Boolean.toString(activityTemp.isActive())});
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
				tblAtiv.removeAll();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setControl(parent);
		parent.isVisible();
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
		txtAtiv.setText("");
		
		setDescription("Register, update and remove activities from the current project");
	}

}
