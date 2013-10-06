/**
 * AlarmingService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.test.alarming;

public interface AlarmingService extends javax.xml.rpc.Service {
    public java.lang.String getAlarmPortAddress();

    public com.test.alarming.Alarm getAlarmPort() throws javax.xml.rpc.ServiceException;

    public com.test.alarming.Alarm getAlarmPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
