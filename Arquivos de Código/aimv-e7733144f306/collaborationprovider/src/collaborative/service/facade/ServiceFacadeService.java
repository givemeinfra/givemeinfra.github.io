/**
 * ServiceFacadeService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package collaborative.service.facade;

public interface ServiceFacadeService extends javax.xml.rpc.Service {
    public java.lang.String getServiceFacadeAddress();

    public collaborative.service.facade.ServiceFacade getServiceFacade() throws javax.xml.rpc.ServiceException;

    public collaborative.service.facade.ServiceFacade getServiceFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
