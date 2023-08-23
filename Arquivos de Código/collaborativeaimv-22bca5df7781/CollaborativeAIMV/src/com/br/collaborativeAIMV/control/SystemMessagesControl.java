package com.br.collaborativeAIMV.control;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.br.collaborativeAIMV.delegate.MessageDelegate;
import com.br.service.valueObject.MessageVO;

public class SystemMessagesControl {
	public static String activitySelected = "";
	public static final String DATE_FORMAT = "E dd/MM/yyyy HH:mm:ss";
	private static MessageDelegate messageDelegate;
	private static MessageVO message;
	public static String old_content = "";
	public static String new_content = "";
	
	
	public static void sendSystemMessage(String projectName, String packageName, String resourceName,String action,String initialContent,
		String finalContent){
		messageDelegate = new MessageDelegate();
		messageBasis(projectName, packageName, resourceName, action);
		message.setInitialContent(initialContent);
		message.setFinalContent(finalContent);
		try {
			messageDelegate.sendSystemMessageOnProject(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendSystemMessage(String projectName, String packageName, String resourceName,String action){
		messageDelegate = new MessageDelegate();
		messageBasis(projectName,packageName, resourceName, action);
		message.setInitialContent("");
		message.setFinalContent("");
		try {
			messageDelegate.sendSystemMessageOnProject(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private static void messageBasis(String projectName, String packageName, String resourceName,String action) {

		message = new MessageVO();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		message.setDate(sdf.format(date));

		message.setActivity(activitySelected);
		message.setDeveloperVO(LoginControl.GET_INSTANCE().currentDeveloper);
		
		message.setEntity(packageName + "." + resourceName);
		if(action.equals("Was changed."))
			message.setParadigm("EDITOR");
		else
			message.setParadigm("PROJECT EXPLORER");
		message.setText(action);

	}
}
