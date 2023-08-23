package com.br.collaborativeAIMV.messageFilter;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import com.br.collaborativeAIMV.delegate.MessageDelegate;
import com.br.service.valueObject.MessageVO;

public class MessageFilter {

	MessageDelegate messageDelegate;

	public MessageFilter() {
		messageDelegate = new MessageDelegate();
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
