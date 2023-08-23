package com.br.collaborativeAIMV.delegate;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import com.br.service.facade.FacadeFactory;
import com.br.service.valueObject.ActivityVO;
import com.br.service.valueObject.ProjectVO;

public class ProjectDelegate {
	Collection<ProjectVO> projects = new ArrayList<ProjectVO>();
	
	public Collection<ProjectVO> findProjects() throws RemoteException{
		ProjectVO[] strProjects = FacadeFactory.GET_INSTANCE().getMessageService().findProjects();
		Collection<ProjectVO> projects = new ArrayList<ProjectVO>();
		
		if(strProjects!=null){
			for(int i=0;i<strProjects.length;i++){
				ProjectVO project = new ProjectVO();
				project.setName(strProjects[i].getName());
				project.setActive(strProjects[i].isActive());
			
				projects.add(project);
			}
			return projects;
		}else{
			return null;
		}
	}
	
	public Boolean addProject(ProjectVO project) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().addProject(project);
	}
	
	public Boolean removeProject(ProjectVO project) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().removeProject(project);
	}
	
	public Boolean updateProject(ProjectVO project, ProjectVO update) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().updateProject(project, update);
	}
}
