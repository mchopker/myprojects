package com.mahesh;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: HelloMDB
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/helloMDB") })
public class HelloMDB implements MessageListener {

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			System.out.println("Inside HelloMDB");
			System.out.println("Message: " + ((TextMessage) message).getText()
					+ " with ID: " + message.getJMSMessageID());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
