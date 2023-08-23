package com.br.service.facade;

public class ServiceFacadeProxy implements com.br.service.facade.ServiceFacade {
  private String _endpoint = null;
  private com.br.service.facade.ServiceFacade serviceFacade = null;
  
  public ServiceFacadeProxy() {
    _initServiceFacadeProxy();
  }
  
  public ServiceFacadeProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiceFacadeProxy();
  }
  
  private void _initServiceFacadeProxy() {
    try {
      serviceFacade = (new com.br.service.facade.ServiceFacadeServiceLocator()).getServiceFacade();
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
  
  public com.br.service.facade.ServiceFacade getServiceFacade() {
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade;
  }
  
  public com.br.service.valueObject.ProjectVO[] findProjects() throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findProjects();
  }
  
  public boolean removeProject(com.br.service.valueObject.ProjectVO project) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.removeProject(project);
  }
  
  public boolean updateProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.ProjectVO update) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.updateProject(project, update);
  }
  
  public boolean sendMessageOnProject(com.br.service.valueObject.MessageVO message) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.sendMessageOnProject(message);
  }
  
  public com.br.service.valueObject.MessageVO[] findSystemMessagesOnProject(com.br.service.valueObject.MessageVO messageVO) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findSystemMessagesOnProject(messageVO);
  }
  
  public boolean updateDeveloperOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.DeveloperVO developer, com.br.service.valueObject.DeveloperVO update) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.updateDeveloperOnProject(project, developer, update);
  }
  
  public boolean updateActivityOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.ActivityVO activity, com.br.service.valueObject.ActivityVO update) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.updateActivityOnProject(project, activity, update);
  }
  
  public boolean sendSystemMessageOnProject(com.br.service.valueObject.MessageVO message) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.sendSystemMessageOnProject(message);
  }
  
  public com.br.service.valueObject.DeveloperVO findDeveloperOnProject(com.br.service.valueObject.DeveloperVO developerVO) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findDeveloperOnProject(developerVO);
  }
  
  public boolean addDeveloperOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.DeveloperVO developer) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.addDeveloperOnProject(project, developer);
  }
  
  public boolean removeDeveloperOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.DeveloperVO developer) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.removeDeveloperOnProject(project, developer);
  }
  
  public com.br.service.valueObject.ActivityVO[] findActivitiesOnProject(com.br.service.valueObject.ProjectVO project) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findActivitiesOnProject(project);
  }
  
  public boolean removeActivityOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.ActivityVO activity) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.removeActivityOnProject(project, activity);
  }
  
  public com.br.service.valueObject.MessageVO[] findUserMessagesOnProject(com.br.service.valueObject.MessageVO messageVO) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findUserMessagesOnProject(messageVO);
  }
  
  public boolean addActivityOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.ActivityVO activity) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.addActivityOnProject(project, activity);
  }
  
  public com.br.service.valueObject.DeveloperVO[] findDevelopersOnProject(com.br.service.valueObject.ProjectVO project) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.findDevelopersOnProject(project);
  }
  
  public boolean addProject(com.br.service.valueObject.ProjectVO project) throws java.rmi.RemoteException{
    if (serviceFacade == null)
      _initServiceFacadeProxy();
    return serviceFacade.addProject(project);
  }
  
  
}