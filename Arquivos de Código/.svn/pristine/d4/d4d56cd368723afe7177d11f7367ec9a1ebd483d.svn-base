package collaborative.filters;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import collaborative.delegate.MessageDelegate;
import collaborative.valueObject.MessageVO;

public class MessageFilter {

	MessageDelegate messageDelegate;

	public MessageFilter() {
		messageDelegate = new MessageDelegate();
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
	
	public Collection<MessageVO> findUserMessages(MessageVO message) {

		Collection<MessageVO> filteredMessages = null;
		Collection<MessageVO> messages = null;

		try {
			messages = messageDelegate.findUserMessagesOnProject(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		filteredMessages = new ArrayList<MessageVO>();
		if (messages != null) {
			for (MessageVO messageTemp : messages) {
				filteredMessages.add(messageTemp);
			}
		}
		return filteredMessages;
	}

	public Collection<MessageVO> findSystemMessages(MessageVO message) {

		Collection<MessageVO> filteredMessages = null;
		Collection<MessageVO> messages = null;

		try {
			messages = messageDelegate.findSystemMessagesOnProject(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		filteredMessages = new ArrayList<MessageVO>();
		if (messages != null) {
			for (MessageVO messageTemp : messages) {
				filteredMessages.add(messageTemp);
			}
		}
		return filteredMessages;
	}

}
