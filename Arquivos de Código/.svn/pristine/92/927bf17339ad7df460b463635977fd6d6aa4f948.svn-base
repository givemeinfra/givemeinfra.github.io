/**
 * ServiceFacadeServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package collaborative.service.facade;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ServiceFacadeServiceLocator extends org.apache.axis.client.Service implements collaborative.service.facade.ServiceFacadeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3615588918140483656L;
	
	// Use to get a proxy class for MessageFacade
    private String ServiceFacade_address;
	
    public ServiceFacadeServiceLocator() {
    	
    	String ipAddress = null;
    	FileReader in = null;
    	BufferedReader buff = null;
		try {
			in = new FileReader("service_address.txt");
			buff = new BufferedReader(in);
			
			try {
				ipAddress = buff.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	if (ipAddress == null || "".equals(ipAddress)){
	    		ipAddress = "csmservice.kinghost.net";
	    	}
		} catch (FileNotFoundException e){
			FileWriter fw = null;
			PrintWriter pr = null;
			try {
				fw = new FileWriter("service_address.txt",true);
				pr = new PrintWriter(fw,true);
				pr.print("csmservice.kinghost.net");
				ipAddress = "csmservice.kinghost.net";
			} catch (IOException e1) {
				e.printStackTrace();
			}finally{
				try {
					pr.close();
					fw.close();
				} catch (IOException e1) {
					System.out.println("None action is necessary.");
				}
			}
		}finally{
			try {
				buff.close();
				in.close();
			} catch (IOException e) {
				System.out.println("None action is necessary.");
			}
		}
		ServiceFacade_address = "http://" + ipAddress + "/CollaborativeAIMVService/services/ServiceFacade";
    }

    public ServiceFacadeServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceFacadeServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    public java.lang.String getServiceFacadeAddress() {
        return ServiceFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceFacadeWSDDServiceName = "ServiceFacade";

    public java.lang.String getServiceFacadeWSDDServiceName() {
        return ServiceFacadeWSDDServiceName;
    }

    public void setServiceFacadeWSDDServiceName(java.lang.String name) {
        ServiceFacadeWSDDServiceName = name;
    }

    public collaborative.service.facade.ServiceFacade getServiceFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiceFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiceFacade(endpoint);
    }

    public collaborative.service.facade.ServiceFacade getServiceFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	collaborative.service.facade.ServiceFacadeSoapBindingStub _stub = new collaborative.service.facade.ServiceFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getServiceFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceFacadeEndpointAddress(java.lang.String address) {
        ServiceFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (collaborative.service.facade.ServiceFacade.class.isAssignableFrom(serviceEndpointInterface)) {
            	collaborative.service.facade.ServiceFacadeSoapBindingStub _stub = new collaborative.service.facade.ServiceFacadeSoapBindingStub(new java.net.URL(ServiceFacade_address), this);
                _stub.setPortName(getServiceFacadeWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ServiceFacade".equals(inputPortName)) {
            return getServiceFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://facade.service.br.com", "ServiceFacadeService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://facade.service.br.com", "ServiceFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServiceFacade".equals(portName)) {
            setServiceFacadeEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
