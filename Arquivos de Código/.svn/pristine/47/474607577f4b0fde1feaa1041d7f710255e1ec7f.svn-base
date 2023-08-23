package collaborative.delegate;

import collaborative.service.facade.FacadeFactory;
import collaborative.valueObject.ActivityVO;
import collaborative.valueObject.ProjectVO;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

public class ActivityDelegate {
	
	
	public Boolean addActivityOnProject(ProjectVO project, ActivityVO activity) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().addActivityOnProject(project, activity);
	}
	
	public Boolean removeActivityOnProject(ProjectVO project, ActivityVO activity) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().removeActivityOnProject(project, activity);
	}
	
	public Collection<ActivityVO> findActivitiesOnProject(ProjectVO project) throws RemoteException{
		
		ActivityVO[] strActivities = FacadeFactory.GET_INSTANCE().getMessageService().findActivitiesOnProject(project);
		Collection<ActivityVO> activities = new ArrayList<ActivityVO>();
		
		if(strActivities!=null){
			for(int i=0;i<strActivities.length;i++){
				ActivityVO activity = new ActivityVO();
				activity.setName(strActivities[i].getName());
				activity.setActive(strActivities[i].isActive());
				
				activities.add(activity);
			}
			
			return activities;
		}else{
			return null;
		}
	}
	
	public Boolean updateActivityOnProject(ProjectVO project, ActivityVO activity, ActivityVO update) throws RemoteException{
		return FacadeFactory.GET_INSTANCE().getMessageService().updateActivityOnProject(project, activity, update);
	}
	
}
