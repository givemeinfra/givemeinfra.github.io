package com.br.collaborativeAIMV.control;

import givemeviews.views.provider.MasterProvider;

import java.rmi.RemoteException;
import java.util.Collection;

import com.br.collaborativeAIMV.delegate.MessageDelegate;
import com.br.collaborativeAIMV.util.CollaborativeFilter;
import com.br.service.valueObject.MessageVO;

import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.controllers.SessionProgram;
import aimv.modeling.Node;

public class ConcernsControl {
	private static ConcernsControl instance;
	private static final String USER_MESSAGES = "User_Messages";
	private static final String SYSTEM_MESSAGES = "System_Messages";
	
	public static ConcernsControl GET_INSTANCE(){
		if(ConcernsControl.instance==null){
			ConcernsControl.instance = new ConcernsControl();
		}
		return ConcernsControl.instance;
	}
	
	public void addMessagesConcernOnController(){
		
		if (LoginControl.GET_INSTANCE().isRightProjectAnalysed()) {
			String[] concerns = (String[]) AIMV.getProperty("Concerns");

			String[] novoConcerns = new String[concerns.length + 2];
			for (int i = 0; i < concerns.length; i++) {
				novoConcerns[i] = concerns[i];
			}
			novoConcerns[concerns.length] = USER_MESSAGES;
			novoConcerns[concerns.length+1] = SYSTEM_MESSAGES;
			AIMV.setProperty("Concerns", novoConcerns);
		}
	}
	
	public void addUserMessageConcernOnNode(Node node){
		String[] concerns = (String[]) node.getProperty("Concerns");
		if(concerns != null){
			for(int i=0; i<concerns.length; i++){
				if(concerns[i].equals(USER_MESSAGES)){
					return;
				}
			}
			
			String[] novoConcerns = new String[concerns.length+1];
			for(int i=0;i<concerns.length;i++){
				novoConcerns[i] = concerns[i];
			}
			novoConcerns[concerns.length] = USER_MESSAGES;
			node.setProperty("Concerns", novoConcerns);
			node.setProperty("Number Concerns", novoConcerns.length);
		}
	}
	
	public void addSystemMessageConcernOnNode(Node node){
		String[] concerns = (String[]) node.getProperty("Concerns");
		if(concerns != null){
			for(int i=0; i<concerns.length; i++){
				if(concerns[i].equals(SYSTEM_MESSAGES)){
					return;
				}
			}
			
			String[] novoConcerns = new String[concerns.length+1];
			for(int i=0;i<concerns.length;i++){
				novoConcerns[i] = concerns[i];
			}
			novoConcerns[concerns.length] = SYSTEM_MESSAGES;
			node.setProperty("Concerns", novoConcerns);
			node.setProperty("Number Concerns", novoConcerns.length);
		}
	}
	
	public void addMessageConcernFromXml(){
		if (LoginControl.GET_INSTANCE().isRightProjectAnalysed() && Nodes.getGroup("all") != null) {
			MessageDelegate messageDelegate = new MessageDelegate();
			Collection<MessageVO> messages = null;
			Collection<MessageVO> sysMessages = null;
			try {
				messages = messageDelegate.findUserMessagesOnProject(CollaborativeFilter.getMESSAGEVO_FILTER());
				sysMessages = messageDelegate.findSystemMessagesOnProject(CollaborativeFilter.getMESSAGEVO_FILTER());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			SessionProgram session = SessionProgram.getInstance();
			MasterProvider provider = new MasterProvider();
			if(session.getUsuario() == 1 && provider.providerProgramDataSource() == true)
			{
				if (messages != null && messages.size() != 0) {
					for (MessageVO messageTemp : messages) {
						for (Node nodeTemp : provider.getCollectionNodes()) {
							if (nodeTemp.getID().equals(messageTemp.getEntity())) {
								addUserMessageConcernOnNode(nodeTemp);
							}
						}
					}
				}
				if (sysMessages != null && sysMessages.size() != 0) {
					for (MessageVO messageTemp : sysMessages) {
						for (Node nodeTemp : provider.getCollectionNodes()) {
							if ( 
									(messageTemp.getEntity()!=null && 
									messageTemp.getEntity().indexOf(nodeTemp.getID()) != -1) ) 
							{
								addSystemMessageConcernOnNode(nodeTemp);
							}
						}
					}
				}
			}
			else
			{
				if (messages != null && messages.size() != 0) {
					for (MessageVO messageTemp : messages) {
						for (Node nodeTemp : Nodes.getGroup("all").getNodes()) {
							if (nodeTemp.getID().equals(messageTemp.getEntity())) {
								addUserMessageConcernOnNode(nodeTemp);
							}
						}
					}
				}
				if (sysMessages != null && sysMessages.size() != 0) {
					for (MessageVO messageTemp : sysMessages) {
						for (Node nodeTemp : Nodes.getGroup("all").getNodes()) {
							if ( 
									(messageTemp.getEntity()!=null && 
									messageTemp.getEntity().indexOf(nodeTemp.getID()) != -1) ) 
							{
								addSystemMessageConcernOnNode(nodeTemp);
							}
						}
					}
				}
			}			
		}
	}
	
	public void removeAllMessageConcern(){
		removeMessageConcernFromController();
		removeMessageConcernFromNodes();
	}
	
	private void removeMessageConcernFromController(){
		String[] concerns = (String[]) AIMV.getProperty("Concerns");
		if (concerns!=null){
			String[] novoConcerns = new String[concerns.length-2];
			
			for(int i=0; i<novoConcerns.length; i++){
				if(concerns[i].equals(USER_MESSAGES) || concerns[i].equals(SYSTEM_MESSAGES))
					break;
				novoConcerns[i] = concerns[i];
			}
			
			AIMV.setProperty("Concerns", novoConcerns);
		}
	}
	
	public void removeMessageConcernFromNodes(){
		
		boolean hasConcern;
		
		if (Nodes.getGroup("all")!=null){
			Collection<Node> nodes = Nodes.getGroup("all").getNodes();
			for(Node nodeTemp : nodes){
				hasConcern = false;
				String[] concerns = (String[]) nodeTemp.getProperty("Concerns");
				if (concerns.length > 0) {
					for (int i = 0; i < concerns.length; i++) {
						if (concerns[i].equals(USER_MESSAGES))
							hasConcern = true;
					}
					if (hasConcern) {
						String[] novoConcerns = new String[concerns.length-1];
						for(int i=0;i<novoConcerns.length; i++){
							if(concerns[i].equals("Messages"))
								break;
							novoConcerns[i] = concerns[i];
						}
						nodeTemp.setProperty("Concerns", novoConcerns);
						nodeTemp.setProperty("Number Concerns", novoConcerns.length);
					}
				}
				
				hasConcern = false;
				concerns = (String[]) nodeTemp.getProperty("Concerns");
				if (concerns.length > 0) {
					for (int i = 0; i < concerns.length; i++) {
						if (concerns[i].equals(SYSTEM_MESSAGES))
							hasConcern = true;
					}
					if (hasConcern) {
						String[] novoConcerns = new String[concerns.length-1];
						for(int i=0;i<novoConcerns.length; i++){
							if(concerns[i].equals(SYSTEM_MESSAGES))
								break;
							novoConcerns[i] = concerns[i];
						}
						nodeTemp.setProperty("Concerns", novoConcerns);
						nodeTemp.setProperty("Number Concerns", novoConcerns.length);
					}
				}
			}
		}
	}
}
