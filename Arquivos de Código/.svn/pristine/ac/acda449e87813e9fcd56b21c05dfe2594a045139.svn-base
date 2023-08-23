package com.br.collaborativeAIMV.control;

import collaborative.persistence.MemoryCollaborativeSourceminer;

import java.rmi.RemoteException;
import java.util.Collection;

import aimv.controllers.Nodes;
import aimv.modeling.Node;

import com.br.collaborativeAIMV.delegate.DeveloperDelegate;
import com.br.service.facade.ServiceFacade;
import com.br.service.valueObject.DeveloperVO;
import com.br.service.valueObject.ProjectVO;

public class LoginControl {
	public static LoginControl instance;
	public ProjectVO currentProject;
	public ServiceFacade serviceFacade;
	public DeveloperVO currentDeveloper;
	public boolean logado;
	public static DeveloperDelegate developerDelegate;
	
	public static LoginControl GET_INSTANCE(){
		if(LoginControl.instance==null){
			LoginControl.instance = new LoginControl();
			LoginControl.instance.logado = false;
			developerDelegate = new DeveloperDelegate();
		}
		return LoginControl.instance;
	}
	
	//Método para tentativa de login a partir dos campos preenchidos na tela de login
	public boolean logging(String developer, String password, String logaProjeto) throws RemoteException{
		boolean flag = false;
		
		ProjectVO project = new ProjectVO();
		project.setName(logaProjeto);
		
		Collection<DeveloperVO> developers = developerDelegate.findDevelopersOnProject(project);
		
		if (developers != null) {
			for (DeveloperVO developerTemp : developers) {
				if (developerTemp.getUserName().equals(developer)&& developerTemp.getPassWord().equals(password) && developerTemp.isActive()) {
					this.currentDeveloper = developerTemp;
					this.currentDeveloper.setProjectVO(project);
					this.currentProject = project;

					this.logado = true;
					flag = true;
					
					// persist to GiveMe Views
					MemoryCollaborativeSourceminer.setLogged(logado);
					MemoryCollaborativeSourceminer.setCurrentProject(currentProject.isActive(), currentProject.getName());
					MemoryCollaborativeSourceminer.setCurrentDevelop(currentDeveloper.isActive(), currentDeveloper.getCoordination(), currentDeveloper.getName(), currentDeveloper.getPassWord(), currentDeveloper.getProjectVO().isActive(), currentDeveloper.getProjectVO().getName(), currentDeveloper.getUserName());
				}
			}
		}
		return flag;
	}
	
	
	public void disconect(){
		SystemMessagesControl.sendSystemMessage(LoginControl.GET_INSTANCE().currentProject.getName(),
				"", "", "Disconnected.");
		this.logado = false;
		LoginControl.instance = null;
		
		MemoryCollaborativeSourceminer.setLogged(false);
	}
	
	public boolean isRightProjectAnalysed(){
		if(Nodes.getGroup("project")!=null && LoginControl.GET_INSTANCE().currentProject!=null){
			Collection<Node> nodes = Nodes.getGroup("project").getNodes();
			for(Node nodeTemp : nodes){
				if(nodeTemp.getID().equals("Project " + LoginControl.GET_INSTANCE().currentProject.getName())){
					return true;
				}
			}
			return false;
		}
		else{
			return false;
		}
	}
	
	
	/*Metodo para verificar se o projeto analisado pelo Sourceminer é o mesmo no qual o uauário está logado
	com o Collaborative. A verificação é chamada quando: 
	-o método STOP() do ControllerListener é chamado, ou seja
		quando a análise de um projeto começa e termina.
	-quando o usuário logar
	*/
	public void verifyAnalysedProject(){
		if(this.isRightProjectAnalysed()){
			ConcernsControl.GET_INSTANCE().addMessagesConcernOnController();
			ConcernsControl.GET_INSTANCE().addMessageConcernFromXml();
		}
	}
	
}


