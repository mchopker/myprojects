/**
 * AlarmingServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.test.alarming;

public class AlarmingServiceLocator extends org.apache.axis.client.Service implements com.test.alarming.AlarmingService {

    public AlarmingServiceLocator() {
    }


    public AlarmingServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AlarmingServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AlarmPort
    private java.lang.String AlarmPort_address = "http://localhost:9001/alarmingService";

    public java.lang.String getAlarmPortAddress() {
        return AlarmPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AlarmPortWSDDServiceName = "AlarmPort";

    public java.lang.String getAlarmPortWSDDServiceName() {
        return AlarmPortWSDDServiceName;
    }

    public void setAlarmPortWSDDServiceName(java.lang.String name) {
        AlarmPortWSDDServiceName = name;
    }

    public com.test.alarming.Alarm getAlarmPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AlarmPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAlarmPort(endpoint);
    }

    public com.test.alarming.Alarm getAlarmPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.test.alarming.AlarmPortBindingStub _stub = new com.test.alarming.AlarmPortBindingStub(portAddress, this);
            _stub.setPortName(getAlarmPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAlarmPortEndpointAddress(java.lang.String address) {
        AlarmPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.test.alarming.Alarm.class.isAssignableFrom(serviceEndpointInterface)) {
                com.test.alarming.AlarmPortBindingStub _stub = new com.test.alarming.AlarmPortBindingStub(new java.net.URL(AlarmPort_address), this);
                _stub.setPortName(getAlarmPortWSDDServiceName());
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
        if ("AlarmPort".equals(inputPortName)) {
            return getAlarmPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://alarming.impl.serviceagent.mahesh.com/", "AlarmingService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://alarming.impl.serviceagent.mahesh.com/", "AlarmPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AlarmPort".equals(portName)) {
            setAlarmPortEndpointAddress(address);
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
