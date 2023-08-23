package collaborative.service.facade;

public class ServiceFacadeProxy implements collaborative.service.facade.ServiceFacade {
  private String _endpoint = null;
  private collaborative.service.facade.ServiceFacade serviceFacade = null;
  
  public ServiceFacadeProxy() {
    _initServiceFacadeProxy();
  }
  
  public ServiceFacadeProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiceFacadeProxy();
  }
  
  private void _initServiceFacadeProxy() {
    try {
      serviceFacade = (new collaborative.service.facade.ServiceFacadeServiceLocator()).getServiceFacade();
      if (serviceFacade != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)serviceFacade)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)serviceFacade)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (serviceFacade != null)
      ((javax.xml.rpc.Stub)serviceFacade)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public collaborative.service.facade.ServiceFacade getServiceFacade() {
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade;
  }
  
  public collaborative.valueObject.ProjectVO[] findProjects() throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findProjects();
  }
  
  public boolean removeProject(collaborative.valueObject.ProjectVO project) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.removeProject(project);
  }
  
  public boolean updateProject(collaborative.valueObject.ProjectVO project, collaborative.valueObject.ProjectVO update) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.updateProject(project, update);
  }
  
  public boolean sendMessageOnProject(collaborative.valueObject.MessageVO message) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.sendMessageOnProject(message);
  }
  
  public collaborative.valueObject.MessageVO[] findSystemMessagesOnProject(collaborative.valueObject.MessageVO messageVO) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findSystemMessagesOnProject(messageVO);
  }
  
  public boolean updateDeveloperOnProject(collaborative.valueObject.ProjectVO project, collaborative.valueObject.DeveloperVO developer, collaborative.valueObject.DeveloperVO update) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.updateDeveloperOnProject(project, developer, update);
  }
  
  public boolean updateActivityOnProject(collaborative.valueObject.ProjectVO project, collaborative.valueObject.ActivityVO activity, collaborative.valueObject.ActivityVO update) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.updateActivityOnProject(project, activity, update);
  }
  
  public boolean sendSystemMessageOnProject(collaborative.valueObject.MessageVO message) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.sendSystemMessageOnProject(message);
  }
  
  public collaborative.valueObject.DeveloperVO findDeveloperOnProject(collaborative.valueObject.DeveloperVO developerVO) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findDeveloperOnProject(developerVO);
  }
  
  public boolean addDeveloperOnProject(collaborative.valueObject.ProjectVO project, collaborative.valueObject.DeveloperVO developer) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.addDeveloperOnProject(project, developer);
  }
  
  public boolean removeDeveloperOnProject(collaborative.valueObject.ProjectVO project, collaborative.valueObject.DeveloperVO developer) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.removeDeveloperOnProject(project, developer);
  }
  
  public collaborative.valueObject.ActivityVO[] findActivitiesOnProject(collaborative.valueObject.ProjectVO project) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findActivitiesOnProject(project);
  }
  
  public boolean removeActivityOnProject(collaborative.valueObject.ProjectVO project, collaborative.valueObject.ActivityVO activity) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.removeActivityOnProject(project, activity);
  }
  
  public collaborative.valueObject.MessageVO[] findUserMessagesOnProject(collaborative.valueObject.MessageVO messageVO) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findUserMessagesOnProject(messageVO);
  }
  
  public boolean addActivityOnProject(collaborative.valueObject.ProjectVO project, collaborative.valueObject.ActivityVO activity) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.addActivityOnProject(project, activity);
  }
  
  public collaborative.valueObject.DeveloperVO[] findDevelopersOnProject(collaborative.valueObject.ProjectVO project) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findDevelopersOnProject(project);
  }
  
  public boolean addProject(collaborative.valueObject.ProjectVO project) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.addProject(project);
  }
  
  
}