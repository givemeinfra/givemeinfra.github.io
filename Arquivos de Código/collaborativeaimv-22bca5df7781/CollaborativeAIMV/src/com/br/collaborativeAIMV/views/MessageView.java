package com.br.collaborativeAIMV.views;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import aimv.views.UtilityView;

import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.control.SystemMessagesControl;
import com.br.collaborativeAIMV.delegate.ActivityDelegate;
import com.br.collaborativeAIMV.delegate.MessageDelegate;
import com.br.collaborativeAIMV.log.Log;
import com.br.collaborativeAIMV.util.CollaborativeFilter;
import com.br.collaborativeAIMV.util.SWTResourceManager;
import com.br.service.valueObject.ActivityVO;
import com.br.service.valueObject.MessageVO;

public class MessageView extends UtilityView {
	Label lblMsg;
	Text txtMsg, txtShowMsg;
	Combo cmbAtiv;
	Button btnSen, btnRef;
	Collection<ActivityVO> activities;
	int activityIndex;
	ActivityDelegate activityDelegate;
	MessageDelegate messageDelegate;
	private String CHAT_VIEW = "CHAT_VIEW";

	public MessageView() {
		activityDelegate = new ActivityDelegate();
		messageDelegate = new MessageDelegate();
	}

	@Override
	protected void closed() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createLayout() {
		// TODO Auto-generated method stub
		Log.collaborativeViewOpen(this.getID());
		Composite parent = getComposite();

		if (LoginControl.GET_INSTANCE().logado) {

			parent.setLayout(new GridLayout(1, true));

			Composite compMsg = new Composite(parent, SWT.NONE);
			compMsg.setLayout(new GridLayout(5, false));
			GridData asd = new GridData();
			asd.grabExcessHorizontalSpace = true;
			asd.horizontalAlignment = SWT.FILL;
			asd.verticalAlignment = SWT.FILL;
			compMsg.setLayoutData(asd);

			lblMsg = new Label(compMsg, SWT.NONE);
			lblMsg.setText("Message: ");

			GridData txtGD = new GridData();
			txtGD.horizontalAlignment = GridData.FILL;
			txtGD.grabExcessHorizontalSpace = true;
			txtMsg = new Text(compMsg, SWT.BORDER);
			txtMsg.setLayoutData(txtGD);
			txtMsg.setToolTipText("Insert your message");

			cmbAtiv = new Combo(compMsg, SWT.DROP_DOWN | SWT.READ_ONLY);
			cmbAtiv.setToolTipText("Choose activity");
			cmbAtiv.add("", 0);
			cmbAtiv.select(0);

			btnSen = new Button(compMsg, SWT.NONE);
			btnSen.setImage(SWTResourceManager
					.getImage("resources/icons/Ok-icon.png"));
			btnSen.setToolTipText("Send message");

			btnRef = new Button(compMsg, SWT.NONE);
			btnRef.setImage(SWTResourceManager
					.getImage("resources/icons/bt_reload.png"));
			btnRef.setToolTipText("Refresh");

			GridData showMsgGD = new GridData();
			showMsgGD.horizontalAlignment = GridData.FILL;
			showMsgGD.verticalAlignment = GridData.FILL;
			showMsgGD.horizontalSpan = 5;
			showMsgGD.grabExcessVerticalSpace = true;
			showMsgGD.grabExcessHorizontalSpace = true;

			txtShowMsg = new Text(parent, SWT.WRAP | SWT.MULTI
					| SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
			txtShowMsg.setLayoutData(showMsgGD);

			try {
				activities = activityDelegate
						.findActivitiesOnProject(LoginControl.GET_INSTANCE().currentProject);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (activities != null) {
				for (ActivityVO activityTemp : activities) {
					if (activityTemp.isActive())
						cmbAtiv.add(activityTemp.getName());
				}
			}
			cmbAtiv.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					activityIndex = cmbAtiv.getSelectionIndex();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			btnSen.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					if (txtMsg.getText() != null
							&& !"".equals(txtMsg.getText())) {

						MessageVO message = new MessageVO();
						message.setText(txtMsg.getText());

						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat(SystemMessagesControl.DATE_FORMAT);
						message.setDate(sdf.format(date));

						int indexSel = cmbAtiv.getSelectionIndex();
						if (indexSel == -1 || indexSel == 0){
							message.setActivity("");
						}else{
							message.setActivity(cmbAtiv.getItem(indexSel));
						}
						message.setDeveloperVO(LoginControl.GET_INSTANCE().currentDeveloper);
						message.setParadigm(CHAT_VIEW);
						message.setEntity("");

						try {
							messageDelegate.sendMessageOnProject(message);
							txtMsg.setText("");
							cmbAtiv.select(0);
							updateMessages();

							String strMessage = "PROJECTNAME: "
									+ LoginControl.GET_INSTANCE().currentProject.getName() + " DEVELOPER: "
									+ message.getDeveloperVO().getName() + " TEXT: "
									+ message.getText();

							Log.print(strMessage);
						} catch (RemoteException e1) {
							e1.printStackTrace();
							Log.print("ERROR TO SEND USER MESSAGE");
						}
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			btnRef.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					try {
						updateMessages();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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

	public void updateMessages() throws RemoteException {
		
		txtShowMsg.setText("");
		Collection<MessageVO> messages = messageDelegate.findUserMessagesOnProject(CollaborativeFilter.getMESSAGEVO_FILTER());
		boolean hasMessages = false;
		if (messages != null && !messages.isEmpty()) {
			for (MessageVO message : sort(messages)) {
				if (message.getParadigm()!=null && message.getParadigm().equals(CHAT_VIEW)) {
					hasMessages = true;
					if (message.getActivity()==null || message.getActivity().equals("")) {

						txtShowMsg.append(message.getDate() + ", "
								+ (message.getDeveloperVO().getName().equals(
										LoginControl.GET_INSTANCE().currentDeveloper.getName())?
												"You":message.getDeveloperVO().getName()) + " said: "
								+ message.getText() + "\n\n");
					} else {

						txtShowMsg.append(message.getDate() + ", Activity: "
								+ message.getActivity() + ", "
								+ (message.getDeveloperVO().getName().equals(
										LoginControl.GET_INSTANCE().currentDeveloper.getName())?
												"You":message.getDeveloperVO().getName()) + " said: "
								+ message.getText() + "\n\n");
					}
				}
			}
			if (!hasMessages){
				txtShowMsg.setText("Messages could not be found");
			}
		} else {
			txtShowMsg.setText("Messages could not be found");
		}
		
	}

	public Collection<MessageVO> sort(Collection<MessageVO> messages) {
		Collection<MessageVO> newMessages = new ArrayList<MessageVO>();
		MessageVO[] strMessages = new MessageVO[messages.size()];
		int i = 0;
		for (MessageVO messageTemp : messages) {
			strMessages[i] = messageTemp;
			i++;
		}

		for (int j = strMessages.length - 1; j >= 0; j--) {
			newMessages.add(strMessages[j]);
		}

		return newMessages;
	}

}
