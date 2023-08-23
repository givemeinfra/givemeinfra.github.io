package collaborative.controller;

import collaborative.delegate.MessageDelegate;
import collaborative.valueObject.DeveloperVO;
import collaborative.valueObject.MessageVO;
import collaborative.valueObject.ProjectVO;
import collaborative.persistence.MemoryCollaborativeSourceminer;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;

public class MessageController 
{
	public static final String DATE_FORMAT = "E dd/MM/yyyy HH:mm:ss";
	private String CHAT_VIEW = "CHAT_VIEW";

	public void sendComunicationMessage(String comunicationMessage)
	{
		if(MemoryCollaborativeSourceminer.getLogged())
		{
			
			MessageVO message = new MessageVO();
			//message.setText("Mensagem Enviada pelo GiveMe Views via Webservice");
			message.setText(comunicationMessage);

			Date date = new Date();						
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			message.setDate(sdf.format(date));

			message.setActivity(""); // recupera mensagens de todas as atividades
			
			//DeveloperVO currentDeveloper = new DeveloperVO(true, true, "Carlos Fábio", "12345", project, "carlosfabio");
			DeveloperVO currentDeveloper = MemoryCollaborativeSourceminer.getCurrentDevelop();
			message.setDeveloperVO(currentDeveloper);
			message.setParadigm(CHAT_VIEW);
			message.setEntity("");
			MessageDelegate messageDelegate = new MessageDelegate();

			try {
				messageDelegate.sendMessageOnProject(message);
				JOptionPane.showMessageDialog(null, "Registered Successfully");
			}
			catch (RemoteException d) {
				d.printStackTrace();
				JOptionPane.showMessageDialog(null, "Impossible register message");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Impossible register message");
		}
	}
	
	public void sendSystemMessage(String systemMessage, String resourceName, String packageName)
	{
		if(MemoryCollaborativeSourceminer.getLogged())
		{
		
			MessageDelegate messageDelegate = new MessageDelegate();
			
			MessageVO messageSys = new MessageVO();
	
			Date date2 = new Date();
			SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT);
			messageSys.setDate(sdf2.format(date2));
	
			messageSys.setActivity(MemoryCollaborativeSourceminer.getCurrentActivity().getName());
			DeveloperVO currentDevelop = MemoryCollaborativeSourceminer.getCurrentDevelop();
			
			messageSys.setDeveloperVO(currentDevelop);
			
			messageSys.setEntity(packageName + "." + resourceName);

			if(systemMessage.equals("Was changed."))
				messageSys.setParadigm("EDITOR");
			else
				messageSys.setParadigm("PROJECT EXPLORER");
			messageSys.setText(systemMessage);		
			
			messageSys.setInitialContent("");
			messageSys.setFinalContent("");
			try 
			{
				Boolean status = messageDelegate.sendSystemMessageOnProject(messageSys);
				if(status)
				{
					//System.out.println("Cadastrada com sucesso");
					JOptionPane.showMessageDialog(null, "Registered Successfully");
				}				
				else
				{
					//System.out.println("Impossível cadastrar");
					JOptionPane.showMessageDialog(null, "Impossible register message");
				}				
					
			} 
			catch (RemoteException e4) {
				e4.printStackTrace();
				JOptionPane.showMessageDialog(null, "Impossible register message");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Impossible register message");
		}
	}
	
	public boolean sendCollaborationMsgByEntity(String message, String clickedEntity, String selectedActivity)
	{
		if(MemoryCollaborativeSourceminer.getLogged())
		{
			if (!message.equals("")) 
			{
	
				MessageVO messageEntity = new MessageVO();
				messageEntity.setText(message);
	
				Date date2 = new Date();
				SimpleDateFormat sdfs = new SimpleDateFormat(DATE_FORMAT);
				messageEntity.setDate(sdfs.format(date2));
				
				if(selectedActivity.equals(""))
					messageEntity.setActivity(MemoryCollaborativeSourceminer.getCurrentActivity().getName());
				else
					messageEntity.setActivity(selectedActivity);
				
				DeveloperVO currentDevelop = MemoryCollaborativeSourceminer.getCurrentDevelop();
				messageEntity.setDeveloperVO(currentDevelop);
	
				messageEntity.setParadigm(null);
				messageEntity.setEntity(clickedEntity);
	
				MessageDelegate messageDelegate = new MessageDelegate();
				
				try 
				{
					boolean flag = messageDelegate.sendMessageOnProject(messageEntity);
					if(flag == false)
					{
						JOptionPane.showMessageDialog(null, "Impossible register message");
						return false;
					}
				}
				catch (RemoteException r) {
					r.printStackTrace();
					JOptionPane.showMessageDialog(null, "Impossible register message");
					return false;
				}
			}			
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Impossible register message");
			return false;
		}
	}
	
	public ArrayList getCollaborationSystemAndUserMsgsByEntity(String clickedEntity)
	{	
		ProjectVO project = MemoryCollaborativeSourceminer.getCurrentProject();
		
		MessageDelegate messageDelegate = new MessageDelegate();
		//String clickedEntityNameAux = "MobileMedia/ws.properties";
		
		Collection<MessageVO> messages3 = null;
		Collection<MessageVO> sysMessages3 = null;

		MessageVO messageVO3 = new MessageVO();
		
		DeveloperVO developerVO3 = new DeveloperVO();
		developerVO3.setName(null);
		messageVO3.setDeveloperVO(developerVO3);
		messageVO3.getDeveloperVO().setProjectVO(project);
		
		messageVO3.setActivity(null);
		messageVO3.setInitialTime(null);
		messageVO3.setFinalTime(null);	
		
		ArrayList listMsgs = new ArrayList<>();
		
		try 
		{		
			messages3 = messageDelegate.findUserMessagesOnProject(messageVO3);
			sysMessages3 = messageDelegate.findSystemMessagesOnProject(messageVO3);
			
			listMsgs.add(messages3);
			listMsgs.add(sysMessages3);
			
			return listMsgs;
			
		} 
		catch (RemoteException e1) {			
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Impossible get messages");
			return listMsgs;
		}
	}
	
	/*void getComunicationMessage()
	{
		ProjectDelegate auxProject = new ProjectDelegate();
		ProjectVO project = auxProject.createObjectProject(true, "MobileMedia");
		
		MessageVO message = new MessageVO();
		message.setText("Mensagem Enviada pelo GiveMe Views via Webservice");

		Date date = new Date();						
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		message.setDate(sdf.format(date));

		//message.setActivity("Atividade1");
		message.setActivity(""); // recupera mensagens de todas as atividades
		
		DeveloperVO developerActual = new DeveloperVO(true, true, "Carlos Fábio", "12345", project, "carlosfabio");
		message.setDeveloperVO(developerActual);
		message.setParadigm(CHAT_VIEW);
		message.setEntity("");
		MessageDelegate messageDelegate = new MessageDelegate();
		
		System.out.println("Imprimindo as mensagens de usuário (Comunicação)");						
		//MessageVO auxMessage = messageDelegate.messageBasis("MobileMedia", "PackagejacimarTeste", "", "sysMsgGiveMeViews");
		//MessageVO amsg = new MessageVO();
		Collection<MessageVO> messages = null;
		try {
			messages = messageDelegate.findUserMessagesOnProject(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(messages != null)
		{
			for(MessageVO messageVO : messages)
			{
				System.out.println("Mensagem: " + messageVO.getActivity() + " | " + messageVO.getText());
			}
		}
		System.out.println();
	}
	
	void getCollaborationAndComunicationMsgs()
	{
		ProjectDelegate auxProject = new ProjectDelegate();
		ProjectVO project = auxProject.createObjectProject(true, "MobileMedia");
		
		System.out.println("Imprimindo todas as mensagens de usuário (Colaboração e Comunicação) ");	
		MessageVO messageVO = new MessageVO();
		
		DeveloperVO devTeste = new DeveloperVO(false, null, null, null, project, null);
		
		DeveloperVO developerVO = new DeveloperVO();
		developerVO.setName(null);
		//messageVO.setDeveloperVO(developerVO);
		messageVO.setDeveloperVO(devTeste);
		messageVO.getDeveloperVO().setProjectVO(project);
		
		messageVO.setActivity(null);
		messageVO.setInitialTime(null);
		messageVO.setFinalTime(null);
		
		Collection<MessageVO> filteredMessages = (new MessageFilter()).findUserMessages(messageVO);

		MessageFilter filter = new MessageFilter();
		for (MessageVO messageTemp : filter.sort(filteredMessages)) {
			System.out.println(messageTemp.getDeveloperVO().getName() + " | " +
						messageTemp.getDate()+ " | " + 
						messageTemp.getActivity()+ " | " +
						messageTemp.getEntity()+ " | " + 
						messageTemp.getText()+ " | " +
						messageTemp.getParadigm());	
		}
	}
	*/

}
