package collaborative.delegate;

import collaborative.service.facade.FacadeFactory;
import collaborative.valueObject.DeveloperVO;
import collaborative.valueObject.ProjectVO;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;


public class DeveloperDelegate {
	
	Collection<DeveloperVO> developers = new ArrayList<DeveloperVO>();
	

	public Collection<DeveloperVO> findDevelopersOnProject(ProjectVO project) throws RemoteException{
		DeveloperVO[] strDevelopers = FacadeFactory.GET_INSTANCE().getMessageService().findDevelopersOnProject(project);
		Collection<DeveloperVO> developers = new ArrayList<DeveloperVO>();
		
		if(strDevelopers!=null){
			for(int i=0;i<strDevelopers.length;i++){
				DeveloperVO developer = new DeveloperVO();
				developer.setName(strDevelopers[i].getName());
				developer.setCoordination(strDevelopers[i].getCoordination());
				if (developer.getCoordination() == null){
					developer.setCoordination(false);
				}
				developer.setPassWord(strDevelopers[i].getPassWord());
				developer.setProjectVO(strDevelopers[i].getProjectVO());
				developer.setUserName(strDevelopers[i].getUserName());
				developer.setActive(strDevelopers[i].isActive());
				developer.setProjectVO(strDevelopers[i].getProjectVO());
				
				developers.add(developer);
				}
			
			return developers;
		}else{
			return null;
		}
		
	}
	
	
	public Boolean addDeveloperOnProject(ProjectVO project, DeveloperVO developer) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().addDeveloperOnProject(project, developer);
	}
	
	public Boolean removeDeveloperOnProject(ProjectVO project, DeveloperVO developer) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().removeDeveloperOnProject(project, developer);
	}
	
	public Boolean updateDeveloperOnProject(ProjectVO project, DeveloperVO developer, DeveloperVO update) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().updateDeveloperOnProject(project, developer, update);
	}
}
