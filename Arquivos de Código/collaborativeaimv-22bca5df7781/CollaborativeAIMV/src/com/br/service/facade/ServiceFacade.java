/**
 * ServiceFacade.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.br.service.facade;

public interface ServiceFacade extends java.rmi.Remote {
    public com.br.service.valueObject.ProjectVO[] findProjects() throws java.rmi.RemoteException;
    public boolean removeProject(com.br.service.valueObject.ProjectVO project) throws java.rmi.RemoteException;
    public boolean updateProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.ProjectVO update) throws java.rmi.RemoteException;
    public boolean sendMessageOnProject(com.br.service.valueObject.MessageVO message) throws java.rmi.RemoteException;
    public com.br.service.valueObject.MessageVO[] findSystemMessagesOnProject(com.br.service.valueObject.MessageVO messageVO) throws java.rmi.RemoteException;
    public boolean updateDeveloperOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.DeveloperVO developer, com.br.service.valueObject.DeveloperVO update) throws java.rmi.RemoteException;
    public boolean updateActivityOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.ActivityVO activity, com.br.service.valueObject.ActivityVO update) throws java.rmi.RemoteException;
    public boolean sendSystemMessageOnProject(com.br.service.valueObject.MessageVO message) throws java.rmi.RemoteException;
    public com.br.service.valueObject.DeveloperVO findDeveloperOnProject(com.br.service.valueObject.DeveloperVO developerVO) throws java.rmi.RemoteException;
    public boolean addDeveloperOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.DeveloperVO developer) throws java.rmi.RemoteException;
    public boolean removeDeveloperOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.DeveloperVO developer) throws java.rmi.RemoteException;
    public com.br.service.valueObject.ActivityVO[] findActivitiesOnProject(com.br.service.valueObject.ProjectVO project) throws java.rmi.RemoteException;
    public boolean removeActivityOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.ActivityVO activity) throws java.rmi.RemoteException;
    public com.br.service.valueObject.MessageVO[] findUserMessagesOnProject(com.br.service.valueObject.MessageVO messageVO) throws java.rmi.RemoteException;
    public boolean addActivityOnProject(com.br.service.valueObject.ProjectVO project, com.br.service.valueObject.ActivityVO activity) throws java.rmi.RemoteException;
    public com.br.service.valueObject.DeveloperVO[] findDevelopersOnProject(com.br.service.valueObject.ProjectVO project) throws java.rmi.RemoteException;
    public boolean addProject(com.br.service.valueObject.ProjectVO project) throws java.rmi.RemoteException;
}
