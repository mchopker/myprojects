package test;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService(name = "Alarm", serviceName = "AlarmingService", portName = "AlarmPort")
public class AlarmingListenerImpl {

	@WebMethod
	public String sendAlarm(@WebParam(name = "alarm") String alarm) {
		if (alarm != null) {
			System.out.println("Message received:" + alarm);
		}
		return "success";
	}

	public static void main(String[] args) {
		// Start Alarm Listener
		Endpoint endPoint = Endpoint.create(new AlarmingListenerImpl());
		endPoint.publish("http://localhost:9001/alarmingService");
	}
}