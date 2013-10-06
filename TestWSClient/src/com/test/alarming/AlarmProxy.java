package com.test.alarming;

public class AlarmProxy implements com.test.alarming.Alarm {
  private String _endpoint = null;
  private com.test.alarming.Alarm alarm = null;
  
  public AlarmProxy() {
    _initAlarmProxy();
  }
  
  public AlarmProxy(String endpoint) {
    _endpoint = endpoint;
    _initAlarmProxy();
  }
  
  private void _initAlarmProxy() {
    try {
      alarm = (new com.test.alarming.AlarmingServiceLocator()).getAlarmPort();
      if (alarm != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)alarm)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)alarm)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (alarm != null)
      ((javax.xml.rpc.Stub)alarm)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.test.alarming.Alarm getAlarm() {
    if (alarm == null)
      _initAlarmProxy();
    return alarm;
  }
  
  public java.lang.String sendAlarm(java.lang.String alarm0) throws java.rmi.RemoteException{
    if (alarm == null)
      _initAlarmProxy();
    return alarm.sendAlarm(alarm0);
  }
  
  
}