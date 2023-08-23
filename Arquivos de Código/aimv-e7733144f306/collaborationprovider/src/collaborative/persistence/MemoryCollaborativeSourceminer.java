package collaborative.persistence;

import collaborative.valueObject.ActivityVO;
import collaborative.valueObject.DeveloperVO;
import collaborative.valueObject.ProjectVO;

public class MemoryCollaborativeSourceminer 
{
	private static Boolean logged;
	private static ProjectVO currentProject;
	private static DeveloperVO currentDevelop;
	private static ActivityVO currentActivity;
	private static String comparisonViewEntityPatternName;
	
	public static MemoryCollaborativeSourceminer instancia = null;
	
	private MemoryCollaborativeSourceminer()
	{
		logged = false;
		currentProject = new ProjectVO();
		currentDevelop = new DeveloperVO();
		currentActivity = new ActivityVO();
		comparisonViewEntityPatternName = "19CB9C36887CD88C8CF37FCA68F5AF783C8ACE6C"; // That is sha1 id and means: "givemeviews.comparisonview.sha1.new"
	}
	
	public static MemoryCollaborativeSourceminer getMemory()
	{
		if(instancia == null)
			instancia = new MemoryCollaborativeSourceminer();
		return instancia;
	}

	public static Boolean getLogged() {
		return logged;
	}

	public static void setLogged(Boolean logged) {
		getMemory();
		MemoryCollaborativeSourceminer.logged = logged;
	}

	public static ProjectVO getCurrentProject() {
		if(logged == false)
			return null;
		return currentProject;
	}

	public static void setCurrentProject(Boolean active, String name) {
		getMemory();		
		MemoryCollaborativeSourceminer.currentProject.setActive(active);
		MemoryCollaborativeSourceminer.currentProject.setName(name);
	}

	public static DeveloperVO getCurrentDevelop() {
		if(logged == false)
			return null;
		return currentDevelop;
	}

	public static void setCurrentDevelop(Boolean active, Boolean coordination, String name,  String passWord, Boolean projectActive, String projectName, String userName)
	{
		getMemory();
		MemoryCollaborativeSourceminer.currentDevelop.setActive(active);
		MemoryCollaborativeSourceminer.currentDevelop.setCoordination(coordination);
		MemoryCollaborativeSourceminer.currentDevelop.setName(name);
		MemoryCollaborativeSourceminer.currentDevelop.setPassWord(passWord);
		MemoryCollaborativeSourceminer.currentDevelop.setProjectVO(new ProjectVO(projectActive, projectName));
		MemoryCollaborativeSourceminer.currentDevelop.setUserName(userName);	
		
	}

	public static ActivityVO getCurrentActivity() {
		if(logged == false)
			return null;
		return currentActivity;
	}

	public static void setCurrentActivity(Boolean active, String activityName) {
		getMemory();
		MemoryCollaborativeSourceminer.currentActivity.setActive(active);
		MemoryCollaborativeSourceminer.currentActivity.setName(activityName);
	}

	public static String getComparisonViewEntityPatternName() {
		return comparisonViewEntityPatternName;
	}

	public static void setComparisonViewEntityPatternName(String comparisonViewEntityPatternName) {
		getMemory();
		MemoryCollaborativeSourceminer.comparisonViewEntityPatternName = comparisonViewEntityPatternName;
	}
	
}
