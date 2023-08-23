package com.br.collaborativeAIMV.views;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import aimv.views.UtilityView;

import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.control.SystemMessagesControl;
import com.br.collaborativeAIMV.delegate.ActivityDelegate;
import com.br.collaborativeAIMV.delegate.DeveloperDelegate;
import com.br.collaborativeAIMV.delegate.MessageDelegate;
import com.br.collaborativeAIMV.log.Log;
import com.br.collaborativeAIMV.messageFilter.MessageFilter;
import com.br.collaborativeAIMV.util.CollaborativeFilter;
import com.br.collaborativeAIMV.util.SWTResourceManager;
import com.br.service.valueObject.ActivityVO;
import com.br.service.valueObject.DeveloperVO;
import com.br.service.valueObject.MessageVO;

public class CollaborationView extends UtilityView {
	Label lblAti, lblFil, lblDes, lblAtiv, lblDatIni, lblDatFin, lblExib;
	Button chkSisMsg, btnCalIni, btnCalEra1, btnCalFin, btnCalEra2, btnPesq ;
	Combo cmbSis, cmbDes, cmbAtiv, cmbMsg;
	Text txtDatIni, txtDatFin;
	Table tblMsg; 
	String MSG_USU = "User Messages";
	String MSG_SIST = "System Messages";
	private static final String ICON_COMPARE = "resources/icons/editor_compare.png";
	private static final String SOURCE_COMPARE = "aimv.views.SourceCompareView";
	
	DeveloperDelegate developerDelegate;
	ActivityDelegate activityDelegate;
	MessageDelegate messageDelegate;
	
	MessageFilter filter;
	

	public CollaborationView(){
		developerDelegate = new DeveloperDelegate();
		activityDelegate = new ActivityDelegate();
		messageDelegate = new MessageDelegate();
	}
	
	@Override
	protected void closed() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void createLayout() {
		Log.collaborativeViewOpen(this.getID());
		Composite parent = getComposite();

		if (LoginControl.GET_INSTANCE().logado) {

			Collection<DeveloperVO> developers=null;
			try {
				developers = developerDelegate.findDevelopersOnProject(LoginControl.GET_INSTANCE().currentProject);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Collection<ActivityVO> activities=null;
			try {
				activities = activityDelegate.findActivitiesOnProject(LoginControl.GET_INSTANCE().currentProject);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			parent.setLayout(new GridLayout(1, true));

			//System messages send
			Composite compMsgSis = new Composite(parent, SWT.BORDER);
			compMsgSis.setLayout(new GridLayout(2, true));
			
			lblAti = new Label(compMsgSis, SWT.BOLD);
			lblAti.setText("System messages sending");
			GridData gridData = new GridData();
			gridData.horizontalSpan = 2;
			lblAti.setLayoutData(gridData);
			
			Label lblspace1 = new Label(compMsgSis, SWT.BOLD);
			lblspace1.setText("");
			GridData gridDataSpace1 = new GridData();
			gridDataSpace1.horizontalSpan = 2;
			lblspace1.setLayoutData(gridDataSpace1);
			
			Label lblSisMsg = new Label(compMsgSis, SWT.NONE);
			lblSisMsg.setText("Activity execution: ");
			cmbSis = new Combo(compMsgSis, SWT.DROP_DOWN | SWT.READ_ONLY);
			cmbSis.setEnabled(false);
			cmbSis.setToolTipText("Choose activity");
			
			if(activities!=null){
				for(ActivityVO activityTemp : activities){
					if(activityTemp.isActive())
						cmbSis.add(activityTemp.getName());
				}
			}
			cmbSis.select(0);
			cmbSis.setEnabled(true);
			for(int i=0; i<cmbSis.getItemCount();i++){
				if(cmbSis.getItem(i).equals(SystemMessagesControl.activitySelected)){
					cmbSis.select(i);
				}
			}
			/*
			chkSisMsg.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					if(chkSisMsg.getSelection()){
						cmbSis.setEnabled(true);
						SystemMessagesControl.sendSystemMessagesActivated = true;
					}else{
						cmbSis.setEnabled(false);
						SystemMessagesControl.sendSystemMessagesActivated = false;
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			*/
			cmbSis.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					SystemMessagesControl.activitySelected = cmbSis.getItem(cmbSis.getSelectionIndex());
					SystemMessagesControl.sendSystemMessage(LoginControl.GET_INSTANCE().currentProject.getName(),
							"", "", "Initiated the activity execution.");
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			//END System messages send

			//START FILTERs
			Composite compFil = new Composite(parent, SWT.BORDER);
			compFil.setLayout(new GridLayout(6, true));
			
			lblFil = new Label(compFil, SWT.BOLD);
			lblFil.setText("Filters");
			GridData gridDataLblFil = new GridData();
			gridDataLblFil.horizontalSpan = 6;
			lblFil.setLayoutData(gridDataLblFil);
			
			Label lblspace2 = new Label(compFil, SWT.BOLD);
			lblspace2.setText("");
			GridData gridDataSpace2 = new GridData();
			gridDataSpace2.horizontalSpan = 6;
			lblspace2.setLayoutData(gridDataSpace2);
			
			//FILTRO DE DESENVOLVEDOR(INTEGRANTE)
			lblDes = new Label(compFil, SWT.NONE);
			lblDes.setText("Developer:");
			
			cmbDes = new Combo(compFil, SWT.DROP_DOWN | SWT.READ_ONLY);
			GridData gridDataCmbDes = new GridData();
			gridDataCmbDes.horizontalSpan = 2;
			gridDataCmbDes.horizontalAlignment = SWT.FILL;
			gridDataCmbDes.verticalAlignment = SWT.LEFT;
			gridDataCmbDes.grabExcessHorizontalSpace = true;
			cmbDes.setLayoutData(gridDataCmbDes);
			cmbDes.add("", 0);
			if(developers!=null){
				
				for(DeveloperVO developerTemp : developers){
					cmbDes.add(developerTemp.getName());
				}
			}
			cmbDes.select(0);
			cmbDes.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					CollaborativeFilter.setDEVELOPER(cmbDes.getItem(cmbDes.getSelectionIndex()));
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			//FIM DO FILTRO DE DESENVOLVEDOR(INTEGRANTE)
			
			//FILTRO DE ATIVIDADE
			lblAtiv = new Label(compFil, SWT.NONE);
			lblAtiv.setText("Activity:");
			
			cmbAtiv = new Combo(compFil, SWT.DROP_DOWN | SWT.READ_ONLY);
			GridData gridDataCmbAtiv = new GridData();
			gridDataCmbAtiv.horizontalSpan = 2;
			gridDataCmbAtiv.horizontalAlignment = SWT.FILL;
			gridDataCmbAtiv.verticalAlignment = SWT.LEFT;
			gridDataCmbAtiv.grabExcessHorizontalSpace = true;
			cmbAtiv.setLayoutData(gridDataCmbAtiv);
			cmbAtiv.add("", 0);
			if(activities!=null){
				for(ActivityVO activityTemp : activities){
					cmbAtiv.add(activityTemp.getName());
				}
			}
			cmbAtiv.select(0);
			cmbAtiv.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					CollaborativeFilter.setACTIVITY(cmbAtiv.getItem(cmbAtiv.getSelectionIndex()));
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			//FIM DO FILTRO DE ATIVIDADE
			
			//DATA INICIAL
			lblDatIni = new Label(compFil, SWT.NONE);
			lblDatIni.setText("Initial Date:");
			txtDatIni = new Text(compFil, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
			GridData gridDataTxtDatIni = new GridData();
			gridDataTxtDatIni.horizontalAlignment = SWT.FILL;
			gridDataTxtDatIni.verticalAlignment = SWT.LEFT;
			gridDataTxtDatIni.grabExcessHorizontalSpace = true;
			txtDatIni.setLayoutData(gridDataTxtDatIni);
			txtDatIni.addModifyListener(
					new ModifyListener() {
						@Override
						public void modifyText(ModifyEvent arg0) {
							CollaborativeFilter.setINITIAL_DATE(txtDatIni.getText());
						}
					});
					
			Composite compBtsDataIni = new Composite(compFil, SWT.NONE);
			compBtsDataIni.setLayout(new GridLayout(2, true));
			btnCalIni = new Button(compBtsDataIni, SWT.PUSH);
			btnCalIni.setImage(SWTResourceManager.getImage("resources/icons/calendar.png"));
			btnCalEra1 = new Button(compBtsDataIni, SWT.PUSH);
			btnCalEra1.setImage(SWTResourceManager.getImage("resources/icons/clean.png"));
			//FIM DATA INICIAL
			
			//DATA FINAL
			lblDatFin = new Label(compFil, SWT.NONE);
			lblDatFin.setText("Final Date:");
			txtDatFin = new Text(compFil, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
			GridData gridDataTxtDatFim = new GridData();
			gridDataTxtDatFim.horizontalAlignment = SWT.FILL;
			gridDataTxtDatFim.verticalAlignment = SWT.LEFT;
			gridDataTxtDatFim.grabExcessHorizontalSpace = true;
			txtDatFin.setLayoutData(gridDataTxtDatFim);
			txtDatFin.addModifyListener(
					new ModifyListener() {
						@Override
						public void modifyText(ModifyEvent arg0) {
							CollaborativeFilter.setFINAL_DATE(txtDatFin.getText());
						}
					});
			
			Composite compBtsDataFim = new Composite(compFil, SWT.NONE);
			compBtsDataFim.setLayout(new GridLayout(2, true));
			btnCalFin = new Button(compBtsDataFim, SWT.PUSH);
			btnCalFin.setImage(SWTResourceManager.getImage("resources/icons/calendar.png"));
			btnCalEra2 = new Button(compBtsDataFim, SWT.PUSH);
			btnCalEra2.setImage(SWTResourceManager.getImage("resources/icons/clean.png"));
			//FIM DATA FINAL
			
			
			Composite compExib = new Composite(parent, SWT.BORDER);
			compExib.setLayout(new GridLayout(1, true));
			
			lblExib = new Label(compExib, SWT.NONE);
			lblExib.setText("Messages viewing");
			Label lblspace3 = new Label(compExib, SWT.NONE);
			lblspace3.setText("");
			
			Composite compExib2 = new Composite(compExib, SWT.NONE);
			GridLayout gl2 = new GridLayout(2, false);
			compExib2.setLayout(gl2);
			btnPesq = new Button(compExib2, SWT.PUSH);
			btnPesq.setText("Search");
			cmbMsg = new Combo(compExib2, SWT.DROP_DOWN | SWT.READ_ONLY);
			cmbMsg.add(MSG_USU, 0);
			cmbMsg.add(MSG_SIST, 1);
			cmbMsg.select(0);

			Composite compExib3 = new Composite(compExib, SWT.BORDER);
			GridLayout compExib3GD = new GridLayout();
			compExib3GD.numColumns = 1;
			compExib3GD.verticalSpacing = 200;
			compExib3.setLayout(compExib3GD);

			tblMsg = new Table(compExib3, SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
			tblMsg.setHeaderVisible(true);
			tblMsg.setLinesVisible(true);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 300;
			data.widthHint = 1230;
			tblMsg.setLayoutData(data);
			
			TableColumn userColumn = new TableColumn(tblMsg, SWT.NONE);
			userColumn.setAlignment(SWT.LEFT);
			userColumn.setText("Developer");
			userColumn.setWidth(120);

			TableColumn dataColumn = new TableColumn(tblMsg, SWT.NONE);
			dataColumn.setAlignment(SWT.LEFT);
			dataColumn.setText("Date");
			dataColumn.setWidth(190);
			dataColumn.setResizable(false);

			TableColumn atividadeColumn = new TableColumn(tblMsg, SWT.NONE);
			atividadeColumn.setAlignment(SWT.LEFT);
			atividadeColumn.setText("Activity");
			atividadeColumn.setWidth(160);

			TableColumn entidadeColumn = new TableColumn(tblMsg, SWT.NONE);
			entidadeColumn.setAlignment(SWT.LEFT);
			entidadeColumn.setText("Entity");
			entidadeColumn.setWidth(290);

			TableColumn mensagemColumn = new TableColumn(tblMsg, SWT.NONE);
			mensagemColumn.setAlignment(SWT.LEFT);
			mensagemColumn.setText("Message");
			mensagemColumn.setWidth(290);

			TableColumn viewColumn = new TableColumn(tblMsg, SWT.NONE);
			viewColumn.setAlignment(SWT.LEFT);
			viewColumn.setText("Paradigm");
			viewColumn.setWidth(150);

			TableColumn contentColumn = new TableColumn(tblMsg, SWT.NO_REDRAW_RESIZE);
			contentColumn.setAlignment(SWT.CENTER);
			contentColumn.setWidth(30);
			contentColumn.setResizable(false);

			TableColumn conteudoInicialColumn = new TableColumn(tblMsg, SWT.NO_REDRAW_RESIZE);
			conteudoInicialColumn.setWidth(0);
			conteudoInicialColumn.setResizable(false);
			
			TableColumn conteudoFinalColumn = new TableColumn(tblMsg, SWT.NO_REDRAW_RESIZE);
			conteudoFinalColumn.setWidth(0);
			conteudoFinalColumn.setResizable(false);
			
			Listener tableListener = new Listener() {

				@Override
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					// TODO Auto-generated method stub
					Point coords = new Point(event.x, event.y);
					TableItem item = tblMsg.getItem(coords);
					if(item!=null){
						Image imagem = item.getImage(6);
						if(item.getBounds(6).contains(coords) && imagem!=null){
							
							SystemMessagesControl.old_content = item.getText(7);
							SystemMessagesControl.new_content = item.getText(8);
							showSourceCompareView();
						}
					}
				}
				
				private void showSourceCompareView(){
					IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					SourceCompareView sourceCompare = null;
					
					if(workbenchWindow!=null){
						try {
							sourceCompare = (SourceCompareView) workbenchWindow.getActivePage().showView(SOURCE_COMPARE);
							sourceCompare.layout();
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						try {
							sourceCompare = (SourceCompareView) getSite().getPage().showView(SOURCE_COMPARE);
							sourceCompare.layout();
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			
			tblMsg.addListener(SWT.MouseDoubleClick, tableListener);
			
			
			btnCalIni.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					final Shell dialog = new Shell(SWT.DIALOG_TRIM);
					dialog.setLayout(new GridLayout(3, false));
					dialog.setText("Initial date");

					final DateTime calendar = new DateTime(dialog, SWT.CALENDAR | SWT.BORDER);
					final DateTime time = new DateTime(dialog, SWT.TIME | SWT.SHORT);

					new Label(dialog, SWT.NONE);
					new Label(dialog, SWT.NONE);

					Button btnOk = new Button(dialog, SWT.PUSH);
					btnOk.setText("OK");
					btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

					dialog.setDefaultButton(btnOk);
					dialog.pack();
					dialog.open();

					btnOk.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							// TODO Auto-generated method stub
						
							int aux = calendar.getMonth() + 1;
							txtDatIni.setText(calendar.getDay() + "/"
									+ aux + "/"
									+ calendar.getYear() + " "
									+ time.getHours() + ":" + time.getMinutes()
									+ ":" + time.getSeconds());
							dialog.close();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {
							// TODO Auto-generated method stub

						}
					});
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});

			btnCalEra1.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					txtDatIni.setText("");
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});

			btnCalFin.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					final Shell dialog = new Shell(SWT.DIALOG_TRIM);
					dialog.setLayout(new GridLayout(3, false));
					dialog.setText("Final date");

					final DateTime calendar = new DateTime(dialog, SWT.CALENDAR | SWT.BORDER);
					final DateTime time = new DateTime(dialog, SWT.TIME | SWT.SHORT);

					new Label(dialog, SWT.NONE);
					new Label(dialog, SWT.NONE);

					Button btnOk = new Button(dialog, SWT.PUSH);
					btnOk.setText("OK");
					btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

					dialog.setDefaultButton(btnOk);
					dialog.pack();
					dialog.open();

					btnOk.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							// TODO Auto-generated method stub
						
							int aux = calendar.getMonth() + 1;
							txtDatFin.setText(calendar.getDay() + "/"
									+ aux + "/"
									+ calendar.getYear() + " "
									+ time.getHours() + ":"
									+ time.getMinutes() + ":"
									+ time.getSeconds());
							dialog.close();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
							// TODO Auto-generated method stub

						}
					});
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});

			btnCalEra2.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					txtDatFin.setText("");
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
			
			btnPesq.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					
					tblMsg.removeAll();
					
					if(cmbMsg.getSelectionIndex()>=0){

						if(cmbMsg.getItem(cmbMsg.getSelectionIndex()).equals(MSG_USU)){
							try {
								showUserMessages();
							} catch (ParseException e1) {
								
								e1.printStackTrace();
								MessageBox dialog = 
										  new MessageBox(new Shell(SWT.DIALOG_TRIM), SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
										dialog.setText("Error at initial time.");
										dialog.setMessage(e1.getCause().toString() + " - " + e1.getMessage().toString());
										dialog.open();
							}
						}
						else{
							try {
								showSystemMessages();
							} catch (ParseException e1) {
								e1.printStackTrace();
								MessageBox dialog = 
										  new MessageBox(new Shell(SWT.DIALOG_TRIM), SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
										dialog.setText("Error at final time.");
										dialog.setMessage(e1.getCause().toString() + " - " + e1.getMessage().toString());
										dialog.open();
							}
						}
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
				}
			});

		} else {
			Label lblLog = new Label(parent, SWT.NONE);
			lblLog.setText("You must Perform Login");
		}
	}
	

	@Override
	protected void open() {
		// TODO Auto-generated method stub
		
	}
	
	public void showUserMessages() throws ParseException {
		
		MessageVO message = CollaborativeFilter.getMESSAGEVO_FILTER();
		
		Collection<MessageVO> filteredMessages = (new MessageFilter()).findUserMessages(message);

		for (MessageVO messageTemp : sort(filteredMessages)) {
				
				TableItem item = new TableItem(tblMsg, SWT.NONE);
				item.setText(new String[] { messageTemp.getDeveloperVO().getName(),
						messageTemp.getDate(), 
						messageTemp.getActivity(),
						messageTemp.getEntity(), 
						messageTemp.getText(),
						messageTemp.getParadigm() });
		}
	}
	
	public void showSystemMessages() throws ParseException{
		
		MessageVO message = CollaborativeFilter.getMESSAGEVO_FILTER();
		
		Collection<MessageVO> filteredMessages = (new MessageFilter()).findSystemMessages(message);
		
		for(MessageVO messageTemp : sort(filteredMessages)){
			if (!messageTemp.getInitialContent().equals("") && !messageTemp.getFinalContent().equals("")) {
				TableItem item = new TableItem(tblMsg, SWT.NONE);
				item.setText(new String[] { messageTemp.getDeveloperVO().getName(),
						messageTemp.getDate(), messageTemp.getActivity(),
						messageTemp.getEntity(), messageTemp.getText(),
						messageTemp.getParadigm()});
				Image imageContent = SWTResourceManager.getImage(ICON_COMPARE);
				item.setImage(6, imageContent);
				item.setText(7, messageTemp.getInitialContent());
				item.setText(8, messageTemp.getFinalContent());
			}else{
				TableItem item = new TableItem(tblMsg, SWT.NONE);
				item.setText(new String[] { messageTemp.getDeveloperVO().getName(),
						messageTemp.getDate(), messageTemp.getActivity(),
						messageTemp.getEntity(), messageTemp.getText(),
						messageTemp.getParadigm()});
			}
		}
	}

	public Collection<MessageVO> sort(Collection<MessageVO> messages){
		Collection<MessageVO> newMessages = new ArrayList<MessageVO>();
		MessageVO[] strMessages = new MessageVO[messages.size()];
		int i = 0;
		for(MessageVO messageTemp : messages){
			strMessages[i] = messageTemp;
			i++;
		}
		
		for(int j = strMessages.length-1 ; j>=0 ; j--){
			newMessages.add(strMessages[j]);
		}
		
		return newMessages;
	}
	
}
