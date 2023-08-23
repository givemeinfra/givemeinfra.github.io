package com.br.collaborativeAIMV.listeners;

import givemeviews.model.BucketItem;
import givemeviews.model.Item;
import givemeviews.views.provider.MasterProvider;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import aimv.controllers.Nodes;
import aimv.controllers.SessionProgram;
import aimv.modeling.Node;

import com.br.collaborativeAIMV.control.ConcernsControl;
import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.control.ViewControl;
import com.br.collaborativeAIMV.control.NodesControl.AuxiliarNode;
import com.br.collaborativeAIMV.delegate.ActivityDelegate;
import com.br.collaborativeAIMV.delegate.MessageDelegate;
import com.br.collaborativeAIMV.log.Log;
import com.br.collaborativeAIMV.util.CollaborativeFilter;
import com.br.service.valueObject.ActivityVO;
import com.br.service.valueObject.MessageVO;


public class NodesListener implements MouseListener{

	private ActivityDelegate activityDelegate;
	private MessageDelegate messageDelegate;
	private final String dateFormat = "E dd/MM/yyyy HH:mm:ss";
	private Object clickedEntity = null;
	private String clickedEntityString;
	private String clickedEntityName;
	
	public NodesListener(){
		activityDelegate = new ActivityDelegate();
		messageDelegate = new MessageDelegate();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if (LoginControl.GET_INSTANCE().isRightProjectAnalysed()) {
			if (e.button == 3) {
				clickedEntity = e.getSource();
				clickedEntityString = splitString(e.getSource().toString());
				this.clickedEntityName = this.identifyEntityName();
				
				Runnable run = new Runnable(){
					@Override
					public void run() {
						createMessageWindow();
					}
				};
				PlatformUI.getWorkbench().getDisplay().syncExec(run);
			}
			else if (e.button == 2){
				
				clickedEntity = e.getSource();
				clickedEntityString = splitString(e.getSource().toString());
				this.clickedEntityName = this.identifyEntityName();
				
				if(LoginControl.GET_INSTANCE().isRightProjectAnalysed()){
					
					Runnable run = new Runnable(){
						@Override
						public void run() {
							showWindowMessages();
						}
					};
					PlatformUI.getWorkbench().getDisplay().syncExec(run);
					
				}
			}
		}
	}
	
	private void createMessageWindow(){
		
        final Shell shell = new Shell();
        shell.setSize(650, 240);
        shell.setText("Send a Message about " + this.clickedEntityName);
        shell.setLayout(new org.eclipse.swt.layout.GridLayout(1, false));
        
        shell.addShellListener(new ShellListener() {
			
			@Override
			public void shellIconified(ShellEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeiconified(ShellEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeactivated(ShellEvent e) {
				// TODO Auto-generated method stub
				shell.dispose();
			}
			
			@Override
			public void shellClosed(ShellEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void shellActivated(ShellEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        
        GridData gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        
        final Text txtMsg = new Text(shell, SWT.WRAP | SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        txtMsg.setLayoutData(gd);
        
        Composite compBtns = new Composite(shell, SWT.NONE);
        compBtns.setLayout(new org.eclipse.swt.layout.GridLayout(4, true));
        
        GridData gd2 = new GridData();
        gd2.horizontalAlignment = GridData.FILL;
        gd2.verticalAlignment = GridData.FILL;
        
        Button btnSend = new Button(compBtns, SWT.PUSH);
        btnSend.setText("  Send  ");
        btnSend.setLayoutData(gd2);
        
        Button btnCancel = new Button(compBtns, SWT.PUSH);
        btnCancel.setText(" Cancel ");
        btnCancel.setLayoutData(gd2);
        
        GridData gdCmb = new GridData();
        gdCmb.horizontalSpan = 2;
        
        final Combo cmbAtiv = new Combo(compBtns, SWT.DROP_DOWN | SWT.READ_ONLY);
        cmbAtiv.add("", 0);
        cmbAtiv.setToolTipText("Choose activity");
        cmbAtiv.setLayoutData(gdCmb);

        
        Collection<ActivityVO> activities = null;
        
        try {
        	if(LoginControl.GET_INSTANCE().currentProject!=null){
				activities = activityDelegate.findActivitiesOnProject(LoginControl.GET_INSTANCE().currentProject);
        	}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		if(activities!=null){
			for(ActivityVO activityTemp : activities){
				if(activityTemp.isActive())
					cmbAtiv.add(activityTemp.getName());
			}
		}
		
        btnCancel.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
        
        btnSend.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!txtMsg.getText().equals("")) {

					MessageVO message = new MessageVO();
					message.setText(txtMsg.getText());

					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
					message.setDate(sdf.format(date));

					int sel = cmbAtiv.getSelectionIndex();
					if (sel <= 0)
						message.setActivity("");
					else
						message.setActivity(cmbAtiv.getItem(cmbAtiv.getSelectionIndex()));
					message.setDeveloperVO(LoginControl.GET_INSTANCE().currentDeveloper);

					// CAPTURAR ID DA VIEW E O ID DA ENTIDADE

					message.setParadigm(ViewControl.GET_INSTANCE().getFocusedViewString());

					message.setEntity(clickedEntityName);

					try {
						boolean flag = messageDelegate.sendMessageOnProject(message);
						if(flag){
							
							txtMsg.setText("");
							shell.setText("Message sent successfully :: "+clickedEntityString);
							
							SessionProgram session = SessionProgram.getInstance();
							MasterProvider provider = new MasterProvider();
							if(session.getUsuario() == 1 && provider.providerProgramDataSource() == true)
							{								
								ConcernsControl.GET_INSTANCE().addUserMessageConcernOnNode(provider.getNode(message.getEntity()));						
							}
							else
							{
								ConcernsControl.GET_INSTANCE().addUserMessageConcernOnNode(Nodes.getGroup("all").getNode(message.getEntity()));
							}
							
							
							
							String strMessage = "PROJECTNAME: " + LoginControl.GET_INSTANCE().currentProject.getName() + 
									" RESOURCENAME: " + message.getEntity() +
									" TEXT: " + message.getText();
							Log.print(strMessage);
							
							shell.dispose();
							
						}else{
							shell.setText("Error to send message :: "+clickedEntityString);
							Log.print("ERRO TO SEND USER MESSAGE");
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						Log.print("ERRO TO SEND USER MESSAGE");
					}
				}else{
					shell.setText("Please, write a message :: "+ clickedEntityString);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        shell.open();
	}
	
	private void showWindowMessages() {
		final Shell shell = new Shell();
		shell.setSize(650, 440);
		shell.setText("Messages about " + this.clickedEntityName);
		shell.setLayout(new org.eclipse.swt.layout.GridLayout(1, false));
		
		shell.addShellListener(new ShellListener() {
			
			@Override
			public void shellIconified(ShellEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeiconified(ShellEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeactivated(ShellEvent e) {
				// TODO Auto-generated method stub
				shell.dispose();
			}
			
			@Override
			public void shellClosed(ShellEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void shellActivated(ShellEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		
		
		final Text txtMsg = new Text(shell, SWT.WRAP | SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY);
		txtMsg.setLayoutData(gd);
		
		Button btnCancel = new Button(shell, SWT.PUSH);
		btnCancel.setText(" Cancel ");
		
		btnCancel.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				shell.dispose();
				//display.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		Collection<MessageVO> messages = null;
		Collection<MessageVO> sysMessages = null;
		try {
			messages = messageDelegate.findUserMessagesOnProject(CollaborativeFilter.getMESSAGEVO_FILTER());
			sysMessages = messageDelegate.findSystemMessagesOnProject(CollaborativeFilter.getMESSAGEVO_FILTER());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		if ( ((messages==null || messages.isEmpty()) && 
				(sysMessages==null || sysMessages.isEmpty())) || 
				
				( !this.hasMessageAboutEntity(messages) &&
						!this.hasMessageAboutEntity(sysMessages)	)
				
				) {
			txtMsg.append("NO MESSAGES.");
		}
		else{
			if (messages != null){
				boolean showTitleUserMessage = true;
				for(MessageVO message : messages){
					if (message.getEntity()!=null && message.getEntity().contains(this.clickedEntityName)) 
					{
						if (showTitleUserMessage){
							txtMsg.append("USER MESSAGES:\n");
							showTitleUserMessage = false;
						}
						if (message.getActivity().equals("")) {
							txtMsg.append(message.getDate() + ", "
									+ (message.getDeveloperVO().getName().equals(
											LoginControl.GET_INSTANCE().currentDeveloper.getName())?
													"You":message.getDeveloperVO().getName()) + " said: "
									+ message.getText() + "\n\n");
						} else {
							txtMsg.append(message.getDate() + ", Activity: "
									+ message.getActivity() + ", "
									+ (message.getDeveloperVO().getName().equals(
											LoginControl.GET_INSTANCE().currentDeveloper.getName())?
													"You":message.getDeveloperVO().getName()) + " said: "
									+ message.getText() + "\n\n");
						}
					}
				}
				if (!showTitleUserMessage){
					txtMsg.append("\n\n");
				}
			}
			
			if(sysMessages!=null){
				boolean showTitleSystemMessage = true;
				for(MessageVO message : sysMessages){
					if (message.getEntity()!=null && message.getEntity().contains(this.clickedEntityName)) {
						if (showTitleSystemMessage){
							txtMsg.append("SYSTEM MESSAGES:\n");
							showTitleSystemMessage = false;
						}
						if (message.getActivity().equals("")) {
	
							txtMsg.append(message.getDate() + ", "
									+ (message.getDeveloperVO().getName().equals(
											LoginControl.GET_INSTANCE().currentDeveloper.getName())?
													"You":message.getDeveloperVO().getName()) + " acted on " 
									+ (message.getEntity()!=null?message.getEntity():"") + ": " 
									+ message.getText() + "\n\n");
						} else {
	
							txtMsg.append(message.getDate() + ", Activity: "
									+ message.getActivity() + ", "
									+ (message.getDeveloperVO().getName().equals(
											LoginControl.GET_INSTANCE().currentDeveloper.getName())?
													"You":message.getDeveloperVO().getName()) + " acted on "
									+ (message.getEntity()!=null?message.getEntity():"") + ": "
									+ message.getText() + "\n\n");
						}
					}
				}
			}
		}
		shell.open();
	}
	
	private Boolean hasMessageAboutEntity(Collection<MessageVO> messages){
		if (messages!=null && !messages.isEmpty()){
			for(MessageVO message : messages){
				if (message.getEntity()!=null && message.getEntity().contains(this.clickedEntityName)){
					return true;
				}
			}
		}
		return false;
	}
	
	public String identifyEntityName(){
		
		SessionProgram session = SessionProgram.getInstance();
		MasterProvider provider = new MasterProvider();
		
		
		
		if(session.getUsuario() == 1 && provider.providerProgramDataSource() == true) // Condição para obter nodes gerados pelo GiveMe Views
		{
			ArrayList<Node> nodes = new ArrayList<Node>();
			
			BucketItem bucket = provider.providerNodes();
			ArrayList<Item> listItems = bucket.getListBucketItems();
			
			for(int i = 0; i < listItems.size(); i++)
			{
				Item itemAux = listItems.get(i);
				Collection<Node> nodesAux = itemAux.getListNodes();
				for (Node nodeTemp : nodesAux) {
					nodes.add(nodeTemp);
				}
			}
			
			// Continua processo normalmente
			for(Node nodeTemp : nodes){
				if (nodeTemp.getProperty("treemap_item") != null) {
					if (nodeTemp.getProperty("treemap_item").equals(clickedEntity))
						return nodeTemp.getID();
				}
				
				if(nodeTemp.getProperty("grah_item") != null){
					if (nodeTemp.getProperty("grah_item").equals(clickedEntity))
						return nodeTemp.getID();
				}
				
				if(nodeTemp.getProperty("grid_item") != null){
					if (nodeTemp.getProperty("grid_item").equals(clickedEntity))
						return nodeTemp.getID();
				}
				
				if(nodeTemp.getProperty("polimetric_item") != null){
					if (nodeTemp.getProperty("polimetric_item").equals(clickedEntity))
						return nodeTemp.getID();
				}
				
				if(nodeTemp.getProperty("matrix_topitem") != null){
					if (nodeTemp.getProperty("matrix_topitem").equals(clickedEntity))
						return nodeTemp.getID();
				}
				
				if(nodeTemp.getProperty("matrix_sideitem") != null){
					if (nodeTemp.getProperty("matrix_sideitem").equals(clickedEntity))
						return nodeTemp.getID();
				}
			}
			return "";
		}
		else // Obtem nodes gerados pelo Sourceminer, que estão no AIMV
		{		
			Collection<Node> nodes = Nodes.getGroup("all").getNodes();
			// Continua processo normalmente
			for(Node nodeTemp : nodes){
				if (nodeTemp.getProperty("treemap_item") != null) {
					if (nodeTemp.getProperty("treemap_item").equals(clickedEntity))
						return nodeTemp.getID();
				}
				
				if(nodeTemp.getProperty("grah_item") != null){
					if (nodeTemp.getProperty("grah_item").equals(clickedEntity))
						return nodeTemp.getID();
				}
				
				if(nodeTemp.getProperty("grid_item") != null){
					if (nodeTemp.getProperty("grid_item").equals(clickedEntity))
						return nodeTemp.getID();
				}
				
				if(nodeTemp.getProperty("polimetric_item") != null){
					if (nodeTemp.getProperty("polimetric_item").equals(clickedEntity))
						return nodeTemp.getID();
				}
				
				if(nodeTemp.getProperty("matrix_topitem") != null){
					if (nodeTemp.getProperty("matrix_topitem").equals(clickedEntity))
						return nodeTemp.getID();
				}
				
				if(nodeTemp.getProperty("matrix_sideitem") != null){
					if (nodeTemp.getProperty("matrix_sideitem").equals(clickedEntity))
						return nodeTemp.getID();
				}
			}
			return "";
		}
		
		
	}
	
	public String splitString(String s){
		String strVet[] = s.split("@");
		return strVet[0];
	}
	
	//@Override
	public void mouseDoubleClicked(MouseEvent e) {
		clickedEntity = e.getSource();
		clickedEntityString = splitString(e.getSource().toString());
		this.clickedEntityName = this.identifyEntityName();
		
		if(LoginControl.GET_INSTANCE().isRightProjectAnalysed()){
			
			Runnable run = new Runnable(){
				@Override
				public void run() {
					showWindowMessages();
				}
			};
			PlatformUI.getWorkbench().getDisplay().syncExec(run);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
