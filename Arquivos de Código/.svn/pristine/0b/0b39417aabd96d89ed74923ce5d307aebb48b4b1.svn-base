package com.br.collaborativeAIMV.delegate;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import com.br.service.facade.FacadeFactory;
import com.br.service.valueObject.MessageVO;

public class MessageDelegate {
	
	//User Messages
	Collection<MessageVO> messagesAll = new ArrayList<MessageVO>();
	DeveloperDelegate developerDelegate = new DeveloperDelegate();
	
	public boolean sendMessageOnProject(MessageVO message) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().sendMessageOnProject(message);
	}
	
	public Collection<MessageVO> findUserMessagesOnProject(MessageVO messageVO) throws RemoteException{
		
		MessageVO[] strMessages = FacadeFactory.GET_INSTANCE().getMessageService().findUserMessagesOnProject(messageVO);
		Collection<MessageVO> messages = new ArrayList<MessageVO>();
		
		if(strMessages!=null){
			for(int i=0;i<strMessages.length;i++){
				MessageVO message = new MessageVO();
				message.setActivity(strMessages[i].getActivity()!=null?strMessages[i].getActivity():"");
				message.setDate(strMessages[i].getDate());
				message.setDeveloperVO(strMessages[i].getDeveloperVO());
				message.setEntity(strMessages[i].getEntity()!=null?strMessages[i].getEntity():"");
				message.setParadigm(strMessages[i].getParadigm());
				message.setText(strMessages[i].getText());
				
				messages.add(message);
			}
			return messages;
		}else{
			return null;
		}
	}
	
	
	
	
	//System Messages
	public boolean sendSystemMessageOnProject(MessageVO message) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().sendSystemMessageOnProject(message);
	}
	
	public Collection<MessageVO> findSystemMessagesOnProject(MessageVO messageVO) throws RemoteException{
		
		MessageVO[] strMessages = FacadeFactory.GET_INSTANCE().getMessageService().findSystemMessagesOnProject(messageVO);
		Collection<MessageVO> messages = new ArrayList<MessageVO>();
		
		if(strMessages!=null){
			for(int i=0;i<strMessages.length;i++){
				MessageVO message = new MessageVO();
				message.setActivity(strMessages[i].getActivity());
				message.setDate(strMessages[i].getDate());
				message.setDeveloperVO(strMessages[i].getDeveloperVO());
				message.setEntity(strMessages[i].getEntity());
				message.setParadigm(strMessages[i].getParadigm());
				message.setText(strMessages[i].getText());
				if( (strMessages[i].getInitialContent()!=null && !strMessages[i].getInitialContent().equals("")) && 
						(strMessages[i].getFinalContent()!=null && !strMessages[i].getFinalContent().equals("")) ){
					message.setInitialContent(strMessages[i].getInitialContent());
					message.setFinalContent(strMessages[i].getFinalContent());
				}else{
					message.setInitialContent("");
					message.setFinalContent("");
				}
				
				messages.add(message);
			}
			return messages;
		}else{
			return null;
		}
	}
	
}
