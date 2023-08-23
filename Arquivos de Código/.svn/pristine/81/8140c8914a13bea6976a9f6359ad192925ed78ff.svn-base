/**
 * 
 */
package com.br.service.DAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.br.messageService.util.CFUtil;
import com.br.service.valueObject.DeveloperVO;
import com.br.service.valueObject.MessageVO;
import com.br.service.valueObject.ProjectVO;

/**
 * @author Carlos Fábio
 * 
 * Classe responsável por lidar com a lógica de 
 * persistência e recuperação das mensagens.
 *
 */
public final class XmlDAOAccess extends XmlAbstractDAO{

	private static XmlDAOAccess instance;
	
	private XmlDAOAccess(){
	}
	
	public static XmlDAOAccess getInstance(){
		if (CFUtil.isEmpty(instance)){
			instance = new XmlDAOAccess();
		}
		instance.initFields();
		return instance;
	}
	
	public void insertProject(String projectName)
	{
		Node nodeProject = this.getNodeProject(projectName);
		
		if (!CFUtil.isEmpty(nodeProject))
		{
//			throw new CFException("O projeto já cadastrado!");
		}
		
		Element elementProject = getDocument().createElement(XmlPersistenceConstants.NODE_PROJECT);
		elementProject.setAttribute(XmlPersistenceConstants.ATTR_NAME, projectName);
		getNodeSemiSynchronous().appendChild(elementProject);
		
		super.performPersistence(getDocument());
	}
	
	public void insertUserOnProject(DeveloperVO developerVO)
	{
		Node nodeProject = this.getNodeProject(developerVO.getProjectVO());
		
		if (CFUtil.isEmpty(nodeProject))
		{
//			throw new CFException("Projeto não encontrado!");
		}
		
		Node nodeTeam = null;
		NodeList projectChildsList = nodeProject.getChildNodes();
		
		loopFor1 : for (int i = 0; i < projectChildsList.getLength(); i++)
		{	
			Node projectChild = projectChildsList.item(i);
			
			if (XmlPersistenceConstants.NODE_TEAM.equals(projectChild.getNodeName()))
			{
				nodeTeam = projectChild;
				break loopFor1;
			}
		}
		
		if (CFUtil.isEmpty(nodeTeam)){
			
			Element elementTeam = getDocument().createElement(XmlPersistenceConstants.NODE_TEAM);
			nodeTeam = nodeProject.appendChild(elementTeam);
		}

		Element elementUser = getDocument().createElement(XmlPersistenceConstants.NODE_USER);
		elementUser.setAttribute(XmlPersistenceConstants.ATTR_USER_KEY, developerVO.getPassWord());
		elementUser.setAttribute(XmlPersistenceConstants.ATTR_USER_LOGIN, developerVO.getUserName());
		elementUser.setAttribute(XmlPersistenceConstants.ATTR_NAME, developerVO.getName());
		elementUser.setAttribute(XmlPersistenceConstants.ATTR_USER_COORDINATOR, developerVO.getCoordination().toString());
		nodeTeam.appendChild(elementUser);
		
		this.performPersistence(getDocument());
	}
	
	public void insertActivityOnProject(String projectName, String activityName)
	{
		Node nodeProject = this.getNodeProject(projectName);
		
		if (CFUtil.isEmpty(nodeProject))
		{
//			throw new CFException("Projeto não encontrado!");
		}
		
		Node nodeActivities = null;
		NodeList projectChildsList = nodeProject.getChildNodes();
		
		loopFor1 : for (int i = 0; i < projectChildsList.getLength(); i++)
		{	
			Node projectChild = projectChildsList.item(i);
			
			if (XmlPersistenceConstants.NODE_ACTIVITIES.equals(projectChild.getNodeName()))
			{
				nodeActivities = projectChild;
				break loopFor1;
			}
		}
		
		if (CFUtil.isEmpty(nodeActivities)){
			
			Element elementActivities = getDocument().createElement(XmlPersistenceConstants.NODE_ACTIVITIES);
			nodeActivities = nodeProject.appendChild(elementActivities);
		}
		
		Element elementActivity = getDocument().createElement(XmlPersistenceConstants.NODE_ACTIVITY);
		elementActivity.setAttribute(XmlPersistenceConstants.ATTR_NAME, activityName);
		
		nodeActivities.appendChild(elementActivity);
		
		this.performPersistence(getDocument());
	}
	
	private Node getNodeProject(ProjectVO project)
	{
		NodeList nodeProjectList = getNodeSemiSynchronous().getChildNodes();
		
		for (int i = 0; i < nodeProjectList.getLength(); i++)
		{
			Node nodeProject = nodeProjectList.item(i);
			Attr attrProjectName = (Attr)nodeProject.getAttributes().item(0);
			
			if (project.getName().equals(attrProjectName.getValue()))
			{
				return nodeProject;
			}
		}
		return null;
	}
	
	private Node getNodeProject(String projectName)
	{
		NodeList nodeProjectList = getNodeSemiSynchronous().getChildNodes();
		
		for (int i = 0; i < nodeProjectList.getLength(); i++)
		{
			Node nodeProject = nodeProjectList.item(i);
			Attr attrProjectName = (Attr)nodeProject.getAttributes().item(0);
			
			if (projectName.equals(attrProjectName.getValue()))
			{
				return nodeProject;
			}
		}
		return null;
	}
	
	public DeveloperVO findDeveloperOnProject(DeveloperVO developer)
	{
		try
		{
			Node nodeProject = this.getNodeProject(developer.getProjectVO());
			
			Node nodeTeam = null;
			
			NodeList projectChildsList = nodeProject.getChildNodes();
			
			loopFor1 : for (int i = 0; i < projectChildsList.getLength(); i++)
			{	
				Node projectChild = projectChildsList.item(i);
				
				if (XmlPersistenceConstants.NODE_TEAM.equals(projectChild.getNodeName()))
				{
					nodeTeam = projectChild;
					break loopFor1;
				}
			}
			
			if (CFUtil.isEmpty(nodeTeam)){
				return null;
			}
			
			NodeList nodeUserList = nodeTeam.getChildNodes();
			
			for (int y = 0; y < nodeUserList.getLength(); y++)
			{
				DeveloperVO userOnNode = new DeveloperVO();
				Node nodeUser = nodeUserList.item(y);
				
				for (int z = 0; z < nodeUser.getAttributes().getLength(); z++)
				{
					Attr attr = (Attr)nodeUser.getAttributes().item(z);
					
					if (attr.getName().equals(XmlPersistenceConstants.ATTR_USER_LOGIN))
					{
						userOnNode.setUserName(attr.getValue());
					}
					else if (attr.getName().equals(XmlPersistenceConstants.ATTR_USER_KEY))
					{
						userOnNode.setPassWord(attr.getValue());
					}
					else if (attr.getName().equals(XmlPersistenceConstants.ATTR_NAME))
					{
						userOnNode.setName(attr.getValue());
					}
					else if (attr.getName().equals(XmlPersistenceConstants.ATTR_USER_COORDINATOR))
					{
						userOnNode.setCoordination(Boolean.valueOf(attr.getValue()));
					}
				}
				if (developer.getUserName().equals(userOnNode.getUserName()) && 
						developer.getPassWord().equals(userOnNode.getPassWord()))
				{
					userOnNode.setProjectVO(developer.getProjectVO());
					return userOnNode;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> findActivitiesOnProject(ProjectVO projectVO)
	{
		List<String> activities = new ArrayList<String>();
		
			Node nodeProject = this.getNodeProject(projectVO);
			
			if (CFUtil.isEmpty(nodeProject))
			{
//				throw new CFException("Projeto não encontrado!");
			}
			
			Node nodeActivities = null;
			
			NodeList projectChildsList = nodeProject.getChildNodes();
			
			loopFor1 : for (int i = 0; i < projectChildsList.getLength(); i++)
			{	
				Node projectChild = projectChildsList.item(i);
				
				if (XmlPersistenceConstants.NODE_ACTIVITIES.equals(projectChild.getNodeName()))
				{
					nodeActivities = projectChild;
					break loopFor1;
				}
			}
			
			if (!CFUtil.isEmpty(nodeActivities))
			{
				NodeList nodeActivitiesList = nodeActivities.getChildNodes();
				
				for (int y = 0; y < nodeActivitiesList.getLength(); y++)
				{
					Node nodeActivity = nodeActivitiesList.item(y);
					
					activities.add(((Attr)nodeActivity.getAttributes().item(0)).getValue());
				}
			}
		return activities;
	}
	
	public MessageVO insertUserMessage(MessageVO messageModel)
	{
			Node nodeProject = this.getNodeProject(messageModel.getProjectVO());
			
			NodeList projectChildsList = nodeProject.getChildNodes();
			
			Node nodeMessages = null;
				
			searchNodeMessages : for (int i = 0; i < projectChildsList.getLength(); i++)
			{
				Node projectChild = projectChildsList.item(i);
				if (XmlPersistenceConstants.NODE_USER_MESSAGES.equals(projectChild.getNodeName()))
				{
					nodeMessages = projectChild;
					break searchNodeMessages;
				}
			}
			
			if (CFUtil.isEmpty(nodeMessages))
			{
				Element elementMessages = getDocument().createElement(XmlPersistenceConstants.NODE_USER_MESSAGES);
				nodeMessages = nodeProject.appendChild(elementMessages);
			}
			
			Element elementMessage = getDocument().createElement(XmlPersistenceConstants.NODE_MESSAGE);
			Node nodeMessage = nodeMessages.appendChild(elementMessage);
			
			Element elementUserSent = getDocument().createElement(XmlPersistenceConstants.NODE_USER_SENT);
			nodeMessage.appendChild(elementUserSent);
			elementUserSent.setAttribute(XmlPersistenceConstants.ATTR_USER_LOGIN, messageModel.getDeveloperVO().getUserName());
			elementUserSent.setTextContent(messageModel.getDeveloperVO().getName());
			
			Element elementTimeSent = getDocument().createElement(XmlPersistenceConstants.NODE_TIME_SENT);
			nodeMessage.appendChild(elementTimeSent);
			elementTimeSent.setTextContent(messageModel.getDate());
			
			Element elementMessageText = getDocument().createElement(XmlPersistenceConstants.NODE_MESSAGE_TEXT);
			nodeMessage.appendChild(elementMessageText);
			elementMessageText.setTextContent(messageModel.getText());
			
			if (!CFUtil.isEmpty(messageModel.getParadigm()))
			{
				Element elementViewReference = getDocument().createElement(XmlPersistenceConstants.NODE_VIEW_REFERENCE);
				nodeMessage.appendChild(elementViewReference);
				elementViewReference.setTextContent(messageModel.getParadigm());
			}
			
			if (!CFUtil.isEmpty(messageModel.getEntity()))
			{
				Element elementClassReference = getDocument().createElement(XmlPersistenceConstants.NODE_CLASS_REFERENCE);
				nodeMessage.appendChild(elementClassReference);
				elementClassReference.setTextContent(messageModel.getEntity());
			}
			
			if (!CFUtil.isEmpty(messageModel.getActivity()))
			{
				Element elementActivity = getDocument().createElement(XmlPersistenceConstants.NODE_ACTIVITY);
				nodeMessage.appendChild(elementActivity);
				elementActivity.setTextContent(messageModel.getActivity());
			}
			
			this.performPersistence(getDocument());
			
			return messageModel;
	}
	
	public List<MessageVO> findUserMessages(MessageVO messageVO)
	{
		List<MessageVO> messages = new ArrayList<MessageVO>();
		
		try
		{
			Node nodeProject = this.getNodeProject(messageVO.getProjectVO());
			
			NodeList projectChildsList = nodeProject.getChildNodes();
			
			for (int i = 0; i < projectChildsList.getLength(); i++)
			{
				Node projectChild = projectChildsList.item(i);
				if (XmlPersistenceConstants.NODE_USER_MESSAGES.equals(projectChild.getNodeName()))
				{
					NodeList nodeMessageList = projectChild.getChildNodes();
					for (int y = 0; y < nodeMessageList.getLength(); y++)
					{
						MessageVO message = new MessageVO();
						message.setDeveloperVO(new DeveloperVO());
						
						Node nodeMessage = nodeMessageList.item(y);
						NodeList nodeMessageChildsList = nodeMessage.getChildNodes();
						
						for (int z = 0; z < nodeMessageChildsList.getLength(); z++)
						{
							Node nodeMessageChild = nodeMessageChildsList.item(z);
							
							if (XmlPersistenceConstants.NODE_USER_SENT.equals(nodeMessageChild.getNodeName()))
							{
								Attr attr = (Attr)nodeMessageChild.getAttributes().item(0);
								message.getDeveloperVO().setUserName(attr.getValue());
								message.getDeveloperVO().setName(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_TIME_SENT.equals(nodeMessageChild.getNodeName()))
							{
								message.setDate(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_MESSAGE_TEXT.equals(nodeMessageChild.getNodeName()))
							{
								message.setText(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_ACTIVITY.equals(nodeMessageChild.getNodeName()))
							{
								message.setActivity(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_VIEW_REFERENCE.equals(nodeMessageChild.getNodeName()))
							{
								message.setParadigm(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_CLASS_REFERENCE.equals(nodeMessageChild.getNodeName()))
							{
								message.setEntity(nodeMessageChild.getTextContent());
							}
//							else if (XmlPersistenceConstants.NODE_METHOD_REFERENCE.equals(nodeMessageChild.getNodeName()))
//							{
//								message.setMethodReference(nodeMessageChild.getTextContent());
//							}
						}
						
						Date initialDate = null;
						Date finalDate = null;
						Date messageDate = null;
						
						SimpleDateFormat fmt = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss");
						
						if ( !CFUtil.isEmpty(messageVO.getInitialTime()) ){
							
							initialDate = fmt.parse(messageVO.getInitialTime());
						}
						
						if ( !CFUtil.isEmpty(messageVO.getFinalTime()) ){
							
							finalDate = fmt.parse(messageVO.getFinalTime());
						}
						
						messageDate = fmt.parse(message.getDate());
						
						if ( (CFUtil.isEmpty(messageVO.getActivity()) || 
								messageVO.getActivity().equals(message.getActivity())) && 
								
								(CFUtil.isEmpty(messageVO.getDeveloperVO()) || CFUtil.isEmpty(messageVO.getDeveloperVO().getName()) || 
										messageVO.getDeveloperVO().getName().equals(message.getDeveloperVO().getName())) &&
										
										(CFUtil.isEmpty(messageVO.getParadigm()) || 
												messageVO.getParadigm().equals(message.getParadigm())) &&
												
												(CFUtil.isEmpty(initialDate) || 
														initialDate.before(messageDate) ) && 
														
														(CFUtil.isEmpty(finalDate) || 
																finalDate.after(messageDate) ) )
						{
							messages.add(message);
						}
					}
				}
			}
			return messages;

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return messages;
	}
	
	public MessageVO insertSystemMessage(MessageVO messageModel)
	{
		try
		{
			Node nodeProject = this.getNodeProject(messageModel.getProjectVO());
			
			NodeList projectChildsList = nodeProject.getChildNodes();
			
			Node nodeSystemMessages = null;
			
			loopfor1 : for (int i = 0; i < projectChildsList.getLength(); i++)
			{
				Node projectChild = projectChildsList.item(i);
				if (XmlPersistenceConstants.NODE_SYSTEM_MESSAGES.equals(projectChild.getNodeName()))
				{
					nodeSystemMessages = projectChild;
					break loopfor1;
				}
			}
			if (!CFUtil.isEmpty(nodeSystemMessages))
			{
				NodeList usersList = nodeSystemMessages.getChildNodes();
				Node nodeUserMessage = null;
				
				loopfor2 : for (int y = 0; y < usersList.getLength(); y++)
				{
					Node nodeUser = usersList.item(y);
					
					String login = null;
					String name = null;
					
					for (int z = 0; z < nodeUser.getAttributes().getLength(); z++)
					{
						Attr attr = (Attr)nodeUser.getAttributes().item(z);
						
						if (attr.getName().equals(XmlPersistenceConstants.ATTR_USER_LOGIN))
						{
							login = attr.getValue();
						}
						else if (attr.getName().equals(XmlPersistenceConstants.ATTR_NAME))
						{
							name = attr.getValue();
						}
					}

					if (messageModel.getDeveloperVO().getPassWord().equals(login) && 
							messageModel.getDeveloperVO().getName().equals(name))
					{
						nodeUserMessage = nodeUser;
						break loopfor2;
					}
				}
				if (!CFUtil.isEmpty(nodeUserMessage))
				{
					//Lógica para inserir a mensagem no usuário já existente
					Element elementMessage = getDocument().createElement(XmlPersistenceConstants.NODE_MESSAGE);
					Node nodeMessage = nodeUserMessage.appendChild(elementMessage);
					
//					SimpleDateFormat fmt = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss");
					Element elementTimeSent = getDocument().createElement(XmlPersistenceConstants.NODE_TIME_SENT);
					nodeMessage.appendChild(elementTimeSent);
					elementTimeSent.setTextContent(messageModel.getDate());
					
					Element elementMessageText = getDocument().createElement(XmlPersistenceConstants.NODE_MESSAGE_TEXT);
					nodeMessage.appendChild(elementMessageText);
					elementMessageText.setTextContent(messageModel.getText());
					
					if (!CFUtil.isEmpty(messageModel.getInitialContent())){
						Element elementInitialContent = getDocument().createElement(XmlPersistenceConstants.NODE_INITIAL_CONTENT);
						nodeMessage.appendChild(elementInitialContent);
						elementInitialContent.setTextContent(messageModel.getInitialContent());
					}
					
					if (!CFUtil.isEmpty(messageModel.getFinalContent())){
						Element elementFinalContent = getDocument().createElement(XmlPersistenceConstants.NODE_FINAL_CONTENT);
						nodeMessage.appendChild(elementFinalContent);
						elementFinalContent.setTextContent(messageModel.getFinalContent());
					}
					
					if (!CFUtil.isEmpty(messageModel.getActivity())){
						Element elementActivity = getDocument().createElement(XmlPersistenceConstants.NODE_ACTIVITY);
						nodeMessage.appendChild(elementActivity);
						elementActivity.setTextContent(messageModel.getActivity());
					}
					
					if (!CFUtil.isEmpty(messageModel.getParadigm()))
					{
						Element elementViewReference = getDocument().createElement(XmlPersistenceConstants.NODE_VIEW_REFERENCE);
						nodeMessage.appendChild(elementViewReference);
						elementViewReference.setTextContent(messageModel.getParadigm());
					}
					
					if (!CFUtil.isEmpty(messageModel.getEntity()))
					{
						Element elementClassReference = getDocument().createElement(XmlPersistenceConstants.NODE_CLASS_REFERENCE);
						nodeMessage.appendChild(elementClassReference);
						elementClassReference.setTextContent(messageModel.getEntity());
					}
					
//					if (!CFUtil.isEmpty(messageModel.getMethodReference()))
//					{
//						Element elementMethodReference = getDocument().createElement(XmlPersistenceConstants.NODE_METHOD_REFERENCE);
//						nodeMessage.appendChild(elementMethodReference);
//						elementMethodReference.setTextContent(messageModel.getMethodReference());
//					}
					
					this.performPersistence(getDocument());
					
					return messageModel;
				}
				else
				{
					Element elementUser = getDocument().createElement(XmlPersistenceConstants.NODE_USER);
					Node nodeUser = nodeSystemMessages.appendChild(elementUser);
					elementUser.setAttribute(XmlPersistenceConstants.ATTR_USER_LOGIN, messageModel.getDeveloperVO().getUserName());
					elementUser.setAttribute(XmlPersistenceConstants.ATTR_NAME, messageModel.getDeveloperVO().getName());
					
					Element elementMessage = getDocument().createElement(XmlPersistenceConstants.NODE_MESSAGE);
					Node nodeMessage = nodeUser.appendChild(elementMessage);
					
					SimpleDateFormat fmt = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss");
					Element elementTimeSent = getDocument().createElement(XmlPersistenceConstants.NODE_TIME_SENT);
					nodeMessage.appendChild(elementTimeSent);
					messageModel.setDate(fmt.format(new Date()));
					elementTimeSent.setTextContent(messageModel.getDate());
					
					Element elementMessageText = getDocument().createElement(XmlPersistenceConstants.NODE_MESSAGE_TEXT);
					nodeMessage.appendChild(elementMessageText);
					elementMessageText.setTextContent(messageModel.getText());
					
					if (!CFUtil.isEmpty(messageModel.getInitialContent()))
					{
						Element elementInitialContent = getDocument().createElement(XmlPersistenceConstants.NODE_INITIAL_CONTENT);
						nodeMessage.appendChild(elementInitialContent);
						elementInitialContent.setTextContent(messageModel.getInitialContent());
					}
					
					if (!CFUtil.isEmpty(messageModel.getFinalContent()))
					{
						Element elementFinalContent = getDocument().createElement(XmlPersistenceConstants.NODE_FINAL_CONTENT);
						nodeMessage.appendChild(elementFinalContent);
						elementFinalContent.setTextContent(messageModel.getFinalContent());
					}
					
					if (!CFUtil.isEmpty(messageModel.getActivity()))
					{
						Element elementActivity = getDocument().createElement(XmlPersistenceConstants.NODE_ACTIVITY);
						nodeMessage.appendChild(elementActivity);
						elementActivity.setTextContent(messageModel.getActivity());
					}
					
					if (!CFUtil.isEmpty(messageModel.getParadigm()))
					{
						Element elementViewReference = getDocument().createElement(XmlPersistenceConstants.NODE_VIEW_REFERENCE);
						nodeMessage.appendChild(elementViewReference);
						elementViewReference.setTextContent(messageModel.getParadigm());
					}
					
					if (!CFUtil.isEmpty(messageModel.getEntity()))
					{
						Element elementClassReference = getDocument().createElement(XmlPersistenceConstants.NODE_CLASS_REFERENCE);
						nodeMessage.appendChild(elementClassReference);
						elementClassReference.setTextContent(messageModel.getEntity());
					}
					
//					if (!CFUtil.isEmpty(messageModel.getMethodReference()))
//					{
//						Element elementMethodReference = getDocument().createElement(XmlPersistenceConstants.NODE_METHOD_REFERENCE);
//						nodeMessage.appendChild(elementMethodReference);
//						elementMethodReference.setTextContent(messageModel.getMethodReference());
//					}
					
					this.performPersistence(getDocument());
					
					return messageModel;
				}
			}
			else
			{
				//Sem system-message, então insere o primeiro
				Element elementSystemMessages = getDocument().createElement(XmlPersistenceConstants.NODE_SYSTEM_MESSAGES);
				nodeSystemMessages = nodeProject.appendChild(elementSystemMessages);
				
				Element elementUser = getDocument().createElement(XmlPersistenceConstants.NODE_USER);
				Node nodeUser = nodeSystemMessages.appendChild(elementUser);
				elementUser.setAttribute(XmlPersistenceConstants.ATTR_USER_LOGIN, messageModel.getDeveloperVO().getUserName());
				elementUser.setAttribute(XmlPersistenceConstants.ATTR_NAME, messageModel.getDeveloperVO().getName());
				
				Element elementMessage = getDocument().createElement(XmlPersistenceConstants.NODE_MESSAGE);
				Node nodeMessage = nodeUser.appendChild(elementMessage);
				
				SimpleDateFormat fmt = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss");
				Element elementTimeSent = getDocument().createElement(XmlPersistenceConstants.NODE_TIME_SENT);
				nodeMessage.appendChild(elementTimeSent);
				messageModel.setDate(fmt.format(new Date()));
				elementTimeSent.setTextContent(messageModel.getDate());
				
				Element elementMessageText = getDocument().createElement(XmlPersistenceConstants.NODE_MESSAGE_TEXT);
				nodeMessage.appendChild(elementMessageText);
				elementMessageText.setTextContent(messageModel.getText());
				
				if (!CFUtil.isEmpty(messageModel.getInitialContent()))
				{
					Element elementInitialContent = getDocument().createElement(XmlPersistenceConstants.NODE_INITIAL_CONTENT);
					nodeMessage.appendChild(elementInitialContent);
					elementInitialContent.setTextContent(messageModel.getInitialContent());
				}
				
				if (!CFUtil.isEmpty(messageModel.getFinalContent()))
				{
					Element elementFinalContent = getDocument().createElement(XmlPersistenceConstants.NODE_FINAL_CONTENT);
					nodeMessage.appendChild(elementFinalContent);
					elementFinalContent.setTextContent(messageModel.getFinalContent());
				}
				
				if (!CFUtil.isEmpty(messageModel.getActivity()))
				{
					Element elementActivity = getDocument().createElement(XmlPersistenceConstants.NODE_ACTIVITY);
					nodeMessage.appendChild(elementActivity);
					elementActivity.setTextContent(messageModel.getActivity());
				}
				
				if (!CFUtil.isEmpty(messageModel.getParadigm()))
				{
					Element elementViewReference = getDocument().createElement(XmlPersistenceConstants.NODE_VIEW_REFERENCE);
					nodeMessage.appendChild(elementViewReference);
					elementViewReference.setTextContent(messageModel.getParadigm());
				}
				
				if (!CFUtil.isEmpty(messageModel.getEntity()))
				{
					Element elementClassReference = getDocument().createElement(XmlPersistenceConstants.NODE_CLASS_REFERENCE);
					nodeMessage.appendChild(elementClassReference);
					elementClassReference.setTextContent(messageModel.getEntity());
				}
				
//				if (!CFUtil.isEmpty(messageModel.getMethodReference()))
//				{
//					Element elementMethodReference = getDocument().createElement(XmlPersistenceConstants.NODE_METHOD_REFERENCE);
//					nodeMessage.appendChild(elementMethodReference);
//					elementMethodReference.setTextContent(messageModel.getMethodReference());
//				}
				
				this.performPersistence(getDocument());
				
				return messageModel;
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return messageModel;
	}
	
	public List<MessageVO> findSystemMessages(MessageVO messageModel)
	{
		List<MessageVO> messages = new ArrayList<MessageVO>();
		
		try
		{
			Node nodeProject = this.getNodeProject(messageModel.getProjectVO());
			
			NodeList projectChildsList = nodeProject.getChildNodes();
			
			Node nodeSystemMessages = null;
			
			loopfor1 : for (int i = 0; i < projectChildsList.getLength(); i++)
			{
				Node projectChild = projectChildsList.item(i);
				if (XmlPersistenceConstants.NODE_SYSTEM_MESSAGES.equals(projectChild.getNodeName()))
				{
					nodeSystemMessages = projectChild;
					break loopfor1;
				}
			}
			if (!CFUtil.isEmpty(nodeSystemMessages))
			{
				NodeList usersList = nodeSystemMessages.getChildNodes();
				Node nodeUserMessage = null;
				
				loopfor2 : for (int y = 0; y < usersList.getLength(); y++)
				{
					Node nodeUser = usersList.item(y);
					
//					String login = null;
					String name = null;

					for (int z = 0; z < nodeUser.getAttributes().getLength(); z++)
					{
						Attr attr = (Attr)nodeUser.getAttributes().item(z);
						
//						if (attr.getName().equals(XmlPersistenceConstants.ATTR_USER_LOGIN))
//						{
//							login = attr.getValue();
//						}
						if (attr.getName().equals(XmlPersistenceConstants.ATTR_NAME))
						{
							name = attr.getValue();
						}
					}
					
					System.out.println("1. " + messageModel);
					System.out.println("2. " + messageModel.getDeveloperVO());
					System.out.println("3. " + messageModel.getDeveloperVO().getName());
					
					if (messageModel.getDeveloperVO()!=null && 
							messageModel.getDeveloperVO().getName()!=null && 
							messageModel.getDeveloperVO().getName().equals(name))
					{
						nodeUserMessage = nodeUser;
						break loopfor2;
					}
				}
				if (!CFUtil.isEmpty(nodeUserMessage))
				{
					//CAPTURA AS MENSAGENS DE UM USUÁRIO ESPECÍFICO.
					NodeList nodeMessageList = nodeUserMessage.getChildNodes();
					
					for (int y = 0; y < nodeMessageList.getLength(); y++)
					{
						MessageVO message = new MessageVO();
						message.setDeveloperVO(messageModel.getDeveloperVO());
						
						Node nodeMessage = nodeMessageList.item(y);
						NodeList nodeMessageChildsList = nodeMessage.getChildNodes();
						
						for (int z = 0; z < nodeMessageChildsList.getLength(); z++)
						{
							Node nodeMessageChild = nodeMessageChildsList.item(z);
							
							if (XmlPersistenceConstants.NODE_TIME_SENT.equals(nodeMessageChild.getNodeName()))
							{
								message.setDate(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_MESSAGE_TEXT.equals(nodeMessageChild.getNodeName()))
							{
								message.setText(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_INITIAL_CONTENT.equals(nodeMessageChild.getNodeName()))
							{
								message.setInitialContent(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_FINAL_CONTENT.equals(nodeMessageChild.getNodeName()))
							{
								message.setFinalContent(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_ACTIVITY.equals(nodeMessageChild.getNodeName()))
							{
								message.setActivity(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_VIEW_REFERENCE.equals(nodeMessageChild.getNodeName()))
							{
								message.setParadigm(nodeMessageChild.getTextContent());
							}
							else if (XmlPersistenceConstants.NODE_CLASS_REFERENCE.equals(nodeMessageChild.getNodeName()))
							{
								message.setEntity(nodeMessageChild.getTextContent());
							}
//							else if (XmlPersistenceConstants.NODE_METHOD_REFERENCE.equals(nodeMessageChild.getNodeName()))
//							{
//								message.setMethodReference(nodeMessageChild.getTextContent());
//							}
						}
						
						Date initialDate = null;
						Date finalDate = null;
						Date messageDate = null;
						
						SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						
						if ( !CFUtil.isEmpty(messageModel.getInitialTime()) ){
							
							initialDate = fmt.parse(messageModel.getInitialTime());
						}
						
						if ( !CFUtil.isEmpty(messageModel.getFinalTime()) ){
							
							finalDate = fmt.parse(messageModel.getFinalTime());
						}
						
						messageDate = fmt.parse(message.getDate());
						
						if (CFUtil.isEmpty(messageModel.getActivity()) || 
								messageModel.getActivity().equals(message.getActivity())  &&
								
								(CFUtil.isEmpty(initialDate) || 
										initialDate.after(messageDate) ) && 
										
										(CFUtil.isEmpty(finalDate) || 
												finalDate.before(messageDate) ) ) 
						{
							messages.add(message);
						}
					}
					return messages;
				}
				else
				{
					//CAPTURA AS MENSAGENS DE TODOS OS USUARIOS
					for (int tmp = 0; tmp < usersList.getLength(); tmp++)
					{
						nodeUserMessage = usersList.item(tmp);
						NodeList nodeMessageList = nodeUserMessage.getChildNodes();
						
						for (int y = 0; y < nodeMessageList.getLength(); y++)
						{
							MessageVO message = new MessageVO();
							message.setDeveloperVO(messageModel.getDeveloperVO());
							
							Node nodeMessage = nodeMessageList.item(y);
							NodeList nodeMessageChildsList = nodeMessage.getChildNodes();
							
							for (int z = 0; z < nodeMessageChildsList.getLength(); z++)
							{
								Node nodeMessageChild = nodeMessageChildsList.item(z);
								
								if (XmlPersistenceConstants.NODE_TIME_SENT.equals(nodeMessageChild.getNodeName()))
								{
									message.setDate(nodeMessageChild.getTextContent());
								}
								else if (XmlPersistenceConstants.NODE_MESSAGE_TEXT.equals(nodeMessageChild.getNodeName()))
								{
									message.setText(nodeMessageChild.getTextContent());
								}
								else if (XmlPersistenceConstants.NODE_INITIAL_CONTENT.equals(nodeMessageChild.getNodeName()))
								{
									message.setInitialContent(nodeMessageChild.getTextContent());
								}
								else if (XmlPersistenceConstants.NODE_FINAL_CONTENT.equals(nodeMessageChild.getNodeName()))
								{
									message.setFinalContent(nodeMessageChild.getTextContent());
								}
								else if (XmlPersistenceConstants.NODE_ACTIVITY.equals(nodeMessageChild.getNodeName()))
								{
									message.setActivity(nodeMessageChild.getTextContent());
								}
								else if (XmlPersistenceConstants.NODE_VIEW_REFERENCE.equals(nodeMessageChild.getNodeName()))
								{
									message.setParadigm(nodeMessageChild.getTextContent());
								}
								else if (XmlPersistenceConstants.NODE_CLASS_REFERENCE.equals(nodeMessageChild.getNodeName()))
								{
									message.setEntity(nodeMessageChild.getTextContent());
								}
//								else if (XmlPersistenceConstants.NODE_METHOD_REFERENCE.equals(nodeMessageChild.getNodeName()))
//								{
//									message.setMethodReference(nodeMessageChild.getTextContent());
//								}
							}
							
							Date initialDate = null;
							Date finalDate = null;
							Date messageDate = null;
							
							SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							
							if ( !CFUtil.isEmpty(messageModel.getInitialTime()) ){
								
								initialDate = fmt.parse(messageModel.getInitialTime());
							}
							
							if ( !CFUtil.isEmpty(messageModel.getFinalTime()) ){
								
								finalDate = fmt.parse(messageModel.getFinalTime());
							}
							
							messageDate = fmt.parse(message.getDate());
							
							if (CFUtil.isEmpty(messageModel.getActivity()) || 
									messageModel.getActivity().equals(message.getActivity())  &&
									
									(CFUtil.isEmpty(initialDate) || 
											initialDate.after(messageDate) ) && 
											
											(CFUtil.isEmpty(finalDate) || 
													finalDate.before(messageDate) ) ) 
							{
								messages.add(message);
							}
						}
					}
				}
			}
			else
			{	
				return null;
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<DeveloperVO> findDevelopersOnProject(ProjectVO projectVO)
	{
		List<DeveloperVO> users = new ArrayList<DeveloperVO>();
		
			Node nodeProject = this.getNodeProject(projectVO);
			
			Node nodeTeam = null;
			
			NodeList projectChildsList = nodeProject.getChildNodes();
			
			loopFor1 : for (int i = 0; i < projectChildsList.getLength(); i++)
			{	
				Node projectChild = projectChildsList.item(i);
				
				if (XmlPersistenceConstants.NODE_TEAM.equals(projectChild.getNodeName()))
				{
					nodeTeam = projectChild;
					break loopFor1;
				}
			}
			
			if (CFUtil.isEmpty(nodeTeam)){
				return null;
			}
			
			NodeList nodeUserList = nodeTeam.getChildNodes();
			
			for (int y = 0; y < nodeUserList.getLength(); y++)
			{
				DeveloperVO userOnNode = new DeveloperVO();
				Node nodeUser = nodeUserList.item(y);
				
				for (int z = 0; z < nodeUser.getAttributes().getLength(); z++)
				{
					Attr attr = (Attr)nodeUser.getAttributes().item(z);
					
					if (attr.getName().equals(XmlPersistenceConstants.ATTR_USER_LOGIN))
					{
						userOnNode.setUserName(attr.getValue());
					}
					else if (attr.getName().equals(XmlPersistenceConstants.ATTR_USER_KEY))
					{
						userOnNode.setPassWord(attr.getValue());
					}
					else if (attr.getName().equals(XmlPersistenceConstants.ATTR_NAME))
					{
						userOnNode.setName(attr.getValue());
					}
					else if (attr.getName().equals(XmlPersistenceConstants.ATTR_USER_COORDINATOR))
					{
						userOnNode.setCoordination(Boolean.valueOf(attr.getValue()));
					}
				}
				userOnNode.setProjectVO(projectVO);
				users.add(userOnNode);
			}
		return users;
	}
	
//	public List<UserModel> findAllUsersWithSystemMessagesOnProject(ProjectModel projectModel) throws CFSystemException, CFException
//	{
//		List<UserModel> users = new ArrayList<UserModel>();
//		
//		try
//		{
//			Node nodeProject = this.getNodeProject(projectModel);
//			
//			if (CFUtil.isEmpty(nodeProject))
//			{
//				throw new CFException("Projeto não encontrado!");
//			}
//			
//			NodeList projectChildsList = nodeProject.getChildNodes();
//			
//			Node nodeSystemMessages = null;
//			
//			loopfor1 : for (int i = 0; i < projectChildsList.getLength(); i++)
//			{
//				Node projectChild = projectChildsList.item(i);
//				if (XmlPersistenceConstants.NODE_SYSTEM_MESSAGES.equals(projectChild.getNodeName()))
//				{
//					nodeSystemMessages = projectChild;
//					break loopfor1;
//				}
//			}
//			
//			if (!CFUtil.isEmpty(nodeSystemMessages))
//			{
//				NodeList usersList = nodeSystemMessages.getChildNodes();
//				
//				for (int y = 0; y < usersList.getLength(); y++)
//				{
//					UserModel userTemp = new UserModel();
//					
//					Node nodeUser = usersList.item(y);
//					
//					for (int z = 0; z < nodeUser.getAttributes().getLength(); z++)
//					{
//						Attr attr = (Attr)nodeUser.getAttributes().item(z);
//						
//						if (attr.getName().equals(XmlPersistenceConstants.ATTR_USER_LOGIN))
//						{
//							userTemp.setLogin(attr.getValue());
//						}
//						else if (attr.getName().equals(XmlPersistenceConstants.ATTR_NAME))
//						{
//							userTemp.setName(attr.getValue());
//						}
//					}
//
//					users.add(userTemp);
//				}
//			}
//			
//			return users;
//			
//		}catch(Exception e)
//		{
//			throw new CFSystemException(e);
//		}
//	}
	
	public List<ProjectVO> findAllProjects()
	{
		List<ProjectVO> projectList = new ArrayList<ProjectVO>();
		
		NodeList nodeProjectList = getNodeSemiSynchronous().getChildNodes();
		
		for (int i = 0; i < nodeProjectList.getLength(); i++)
		{	
			Node nodeProject = nodeProjectList.item(i);
			Attr attrProjectName = (Attr)nodeProject.getAttributes().item(0);
			
			projectList.add(new ProjectVO(attrProjectName.getValue()));
			
		}
		return projectList;
	}
	
	public static void main(String args[])
	{
		//TESTE DA INSERÇÃO DE MENSAGENS DE USUÁRIO E DE USUÁRIO 
//		UserModel user = new UserModel();
//		user.setLogin("josemaria");
//		user.setKey("12345");
//		user.setName("José Maria");
//		user.setProject(new ProjectModel("Db4o"));
//		
//		UserModel user1 = new UserModel();
//		user1.setLogin("carlosfabio");
//		user1.setKey("12345");
//		user1.setName("Carlos Fábio");
//		user1.setProject(new ProjectModel("Db4o"));
//		
//		UserModel userReturn = XmlDAOAccess.getInstance().findUserOnProject(user);
//		if (userReturn!=null)
//		{
//			MessageModel messageModel1 = new MessageModel();
//			messageModel1.setUserSent(user);
//			messageModel1.setMessage("Mensagem 1");
//			XmlDAOAccess.getInstance().insertMessage(messageModel1);
//			
//			MessageModel messageModel2 = new MessageModel();
//			messageModel2.setUserSent(user1);
//			messageModel2.setMessage("Mensagem 2");
//			XmlDAOAccess.getInstance().insertMessage(messageModel2);
//			
//			MessageModel messageModel3 = new MessageModel();
//			messageModel3.setUserSent(user);
//			messageModel3.setMessage("Mensagem 3");
//			XmlDAOAccess.getInstance().insertMessage(messageModel3);
//			
//			System.out.println("Mensagem inserida com sucesso!");
//		}

//		TESTE DA BUSCA DE MENSAGENS DE USUÁRIO
//		ProjectModel projectModel = new ProjectModel("ESTrabalho1");
//		MessageModel messageModel = new MessageModel();
//		messageModel.setProjectModel(projectModel);
//		messageModel.setUserSent(new UserModel());
//		messageModel.getUserSent().setName("");
//		messageModel.setActivity("");
//		List<MessageModel> messages = XmlDAOAccess.getInstance().findUserMessages(messageModel);
//		
//		for (MessageModel message : messages)
//		{
//			System.out.println(message.getActivity());
//			System.out.println(message.getUserSent().getName());
//			System.out.println(message.getMessage());
//			System.out.println(message.getTimeSent());
//			System.out.println("=====================");
//		}
		
		//TESTE DA INSERÇÃO DE MENSAGENS DE SISTEMA
//		UserModel user = new UserModel();
//		user.setLogin("carlosfabio");
//		user.setName("Carlos Fábio");
//		user.setProject(new ProjectModel("ESTrabalho1"));
//		
//		MessageModel messageModel3 = new MessageModel();
//		messageModel3.setUserSent(user);
//		messageModel3.setMessage("Mensagem carlos 2");
//		messageModel3.setActivity("Identificação do bad smell");
//		messageModel3.setInitialContent("public class rabo {" +
//				"\n sysout(rabo);" +
//				"}");
//		messageModel3.setFinalContent("public class rabo2 {" +
//				"\n sysout(rabo2);" +
//				"}");
//		XmlDAOAccess.getInstance().insertSystemMessage(messageModel3);
		
//		TESTE DA RECUPERAÇÃO DAS MENSAGENS DE SISTEMA DE UM DETERMINADO USUÁRIO
//		ProjectModel projectModel = new ProjectModel("ESTrabalho1");
//		MessageModel messageModel = new MessageModel();
//		messageModel.setProjectModel(projectModel);
//		messageModel.setUserSent(new UserModel());
//		messageModel.getUserSent().setName("Carlos Fábio");
//		messageModel.setActivity("Identificação do bad smell");
//		List<MessageModel> messages = XmlDAOAccess.getInstance().findSystemMessages(messageModel);
//		
//		for (MessageModel message : messages)
//		{
//			System.out.println(message.getUserSent().getName());
//			System.out.println(message.getMessage());
//			System.out.println(message.getTimeSent());
//		}
		
		//TESTE DA BUSCA DE ATIVIDADES
//		ProjectModel projectModel = new ProjectModel("Db4o");
//		List<String> activities = XmlDAOAccess.getInstance().findActivitiesOnProject(projectModel);
//		System.out.println(activities);
		
//		TESTE DA BUSCA USUÁRIOS POR PROJETO
//		ProjectModel projectModel = new ProjectModel("Db4o");
//		List<UserModel> users = XmlDAOAccess.getInstance().findUsersOnProject(projectModel);
//		for (UserModel user : users)
//		{
//			System.out.println(user.getName());
//			System.out.println(user.getLogin());
//		}
		
		//TESTE DE INSERCAO DE USUARIO
//		UserModel user = new UserModel();
//		user.setProject(new ProjectModel("Db4o"));
//		user.setDesenvolvedor();
//		user.setKey("12345");
//		user.setLogin("josemaria");
//		user.setName("José Maria");
//		
//		XmlDAOAccess.getInstance().insertUserOnProject(user);
		
		//TESTE DE INSERCAO DE ATIVIDADE
//		XmlDAOAccess.getInstance().insertActivityOnProject("Db4o", "Atividade 1");

		//TESTE DE INSERCAO DE PROJETO
//		XmlDAOAccess.getInstance().insertProject("Db4o");
		
//		System.out.println(XmlDAOAccess.class.getResource("").getPath().replace("/", "\\"));
//		System.out.println(( SystemEnvironment.getSystemEnvironment().getOsName().indexOf("Win") != -1 ));
	}
}