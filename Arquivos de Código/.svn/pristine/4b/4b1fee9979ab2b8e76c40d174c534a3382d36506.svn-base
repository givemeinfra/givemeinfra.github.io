package collaborative.controller;

import java.rmi.RemoteException;
import java.util.Collection;

import collaborative.delegate.ActivityDelegate;
import collaborative.valueObject.ActivityVO;
import collaborative.persistence.MemoryCollaborativeSourceminer;

public class ActivityController 
{
	
	public Collection<ActivityVO> getActivitysByProject()
	{
		try 
		{
			ActivityDelegate activityDelegate = new ActivityDelegate();
			Collection<ActivityVO> listActivitys = activityDelegate.findActivitiesOnProject(MemoryCollaborativeSourceminer.getCurrentProject());
			return listActivitys;
		} 
		catch (RemoteException b) 
		{			
			b.printStackTrace();
			return null;
		}
	}
	
	/*
	 *  ProjectDelegate auxProject = new ProjectDelegate();
		ProjectVO project = auxProject.createObjectProject(true, "MobileMedia");						
							
		/*ActivityDelegate activityDelegate = new ActivityDelegate();
		//ActivityVO auxActivity = activityDelegate.createObjectActivity(true, "Atividade 2 de Manutenção");
		
		try {
			 System.out.println("Inserindo atividade para um projeto");
			 System.out.println(activityDelegate.addActivityOnProject(project, auxActivity));
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		System.out.println("Imprimindo as atividades cadastradas no web service:");
		try {
			for(ActivityVO activityTemp : activityDelegate.findActivitiesOnProject(project)){
				System.out.println(activityTemp.getName());
			}
		} catch (RemoteException b) {
			b.printStackTrace();
		}
		
		
	 */

}
