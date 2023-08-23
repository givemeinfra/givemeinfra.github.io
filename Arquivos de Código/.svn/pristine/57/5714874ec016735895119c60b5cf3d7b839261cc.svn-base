package com.br.service.facade;

import java.util.List;

import com.br.messageService.util.CFUtil;
import com.br.service.DAO.XmlDAOAccess;
import com.br.service.valueObject.ActivityVO;
import com.br.service.valueObject.DeveloperVO;
import com.br.service.valueObject.MessageVO;
import com.br.service.valueObject.ProjectVO;

public class ServiceFacade {
	
	public ServiceFacade(){
//		activityDAO = new ActivityDAO();
//		developerDAO = new DeveloperDAO();
//		projectDAO = new ProjectDAO();	
//		messageDAO = new MessageDAO();
	}
	
	//Eventos relacionados a ATIVIDADES
	
	public Boolean addActivityOnProject(ProjectVO project, ActivityVO activity){
		XmlDAOAccess.getInstance().insertActivityOnProject(project.getName(), activity.getName());
		return true;
	}
	
	public Boolean removeActivityOnProject(ProjectVO project, ActivityVO activity){
//		return activityDAO.removeActivityOnProject(project, activity);
		return true;
	}
	
	public ActivityVO[] findActivitiesOnProject(ProjectVO project){
		
		List<String> activitiesList = XmlDAOAccess.getInstance().findActivitiesOnProject(project);
		
		if (!CFUtil.isEmpty(activitiesList))
		{
			ActivityVO[] activitiesArray = new ActivityVO[activitiesList.size()];
			int i = 0;
			
			for (String activity : activitiesList)
			{
				ActivityVO activityTemp = new ActivityVO();
				activityTemp.setName(activity);
				//TODO
				activityTemp.setActive(true);
				activitiesArray[i++] = activityTemp;
			}
			
			return activitiesArray;
		}
		else
		{
			return null;
		}
	}
	
	public Boolean updateActivityOnProject(ProjectVO project, ActivityVO activity, ActivityVO update){
		//return activityDAO.updateActivityOnProject(project, activity, update);
		//TODO
		return true;
	}
	
	
	
	//Eventos relacionados a PROJETOS

	public ProjectVO[] findProjects(){
	
		List<ProjectVO> projectsList = XmlDAOAccess.getInstance().findAllProjects();
		
		if (!CFUtil.isEmpty(projectsList))
		{
			ProjectVO[] projectsArray = new ProjectVO[projectsList.size()];
			int i = 0;
			
			for (ProjectVO project : projectsList){
				projectsArray[i++] = project;
			}
			return projectsArray;
		}
		else{
			return null;
		}
	}
	
	public Boolean addProject(ProjectVO project){
		XmlDAOAccess.getInstance().insertProject(project.getName());
		return true;
	}
	
	public Boolean removeProject(ProjectVO project){
//		return projectDAO.removeProject(project);
		//TODO
		return true;
	}
	
	public Boolean updateProject(ProjectVO project, ProjectVO update){
//		return projectDAO.updateProject(project, update);
		//TODO
		return true;
	}
	
	
	//Eventos relacionados a DESENVOLVEDORES
	
	public DeveloperVO findDeveloperOnProject(DeveloperVO developerVO)
	{	
		return XmlDAOAccess.getInstance().findDeveloperOnProject(developerVO);
	}
	
	public DeveloperVO[] findDevelopersOnProject(ProjectVO project){
		
		List<DeveloperVO> usersList = XmlDAOAccess.getInstance().findDevelopersOnProject(project);
		
		if (!CFUtil.isEmpty(usersList))
		{
			DeveloperVO[] usersArray = new DeveloperVO[usersList.size()];
			int i = 0;
			
			for (DeveloperVO user : usersList)
			{
				usersArray[i++] = user;
			}
			
			return usersArray;
		}
		else
		{
			return null;
		}
	}
	
	
	public Boolean addDeveloperOnProject(ProjectVO project, DeveloperVO developer){
		developer.setProjectVO(project);
		XmlDAOAccess.getInstance().insertUserOnProject(developer);
		return true;
	}
	
	public Boolean removeDeveloperOnProject(ProjectVO project, DeveloperVO developer){
//		return developerDAO.removeDeveloperOnProject(project, developer);
		//TODO
		return true;
	}
	
	public Boolean updateDeveloperOnProject(ProjectVO project, DeveloperVO developer, DeveloperVO update){
//		return developerDAO.updateDeveloperOnProject(project, developer, update);
		//TODO
		return true;
	}
	
	//Eventos relacionados a mensagens de usuário
	
	public boolean sendMessageOnProject(String project, MessageVO message){
//		return messageDAO.sendMessageOnProject(project, message);
		message.setProjectVO(new ProjectVO(project));
		XmlDAOAccess.getInstance().insertUserMessage(message);
		return true;
	}
	
	public MessageVO[] findUserMessagesOnProject(MessageVO messageVO){
		
		List<MessageVO> mensagensList = XmlDAOAccess.getInstance().findUserMessages(messageVO);
		
		if (!CFUtil.isEmpty(mensagensList))
		{
			MessageVO[] messagesArray = new MessageVO[mensagensList.size()];
			int i = 0;
			
			for (MessageVO mensagem : mensagensList)
			{
				messagesArray[i++] = mensagem;
			}
			
			return messagesArray;
		}
		else
		{
			return null;
		}
	}
	
//	public MessageVO[] getMessagesByDeveloperOnProject(String project, String developer){
//		int i=0;
//		int j;
//		
//		Collection<MessageVO> messages = messageDAO.getMessagesByDeveloperOnProject(project, developer);
//		MessageVO strMessages[] = new MessageVO[messages.size()];
//		
//		if(strMessages!=null){
//			for(MessageVO messageTemp : messages){
//				strMessages[i] = messageTemp;
//				i++;
//			}
//			return strMessages;
//		}else{
//			return null;
//		}
//	}
	
	//Eventos relacionados a mensagens de sistema
	
	public boolean sendSystemMessageOnProject(String project, MessageVO message){
		message.setProjectVO(new ProjectVO(project));
		XmlDAOAccess.getInstance().insertSystemMessage(message);
		return true;
	}
	
	public MessageVO[] findSystemMessagesOnProject(MessageVO messageVO){
		
		List<MessageVO> mensagensList = XmlDAOAccess.getInstance().findSystemMessages(messageVO);
		
		if (!CFUtil.isEmpty(mensagensList)){
			MessageVO[] messagesArray = new MessageVO[mensagensList.size()];
			int i = 0;
			
			for (MessageVO mensagem : mensagensList){
				messagesArray[i++] = mensagem;
			}
			return messagesArray;
		}
		else{
			return null;
		}
	}
}