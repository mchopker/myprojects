package com.test.alarming;


public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AlarmingServiceLocator asLocator = new AlarmingServiceLocator();
			asLocator.setEndpointAddress("AlarmPort",
					"http://localhost:9001/alarmingService");
			Alarm service = asLocator.getAlarmPort();
			service.sendAlarm("test mahesh alarm");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
