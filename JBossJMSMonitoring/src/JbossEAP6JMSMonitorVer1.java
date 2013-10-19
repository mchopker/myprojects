import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.jboss.as.cli.scriptsupport.CLI;
import org.jboss.as.cli.scriptsupport.CLI.Result;
import org.jboss.dmr.ModelNode;

/**
 * The JBoss client program to fetch JBoss EAP 6 SERVER JMS queue statistics,
 * which includes queue-name,current message count and total message count.
 * 
 * The necessary input parameters are taken from the jmsmonitoring.properties
 * file, where the parameters like hostname (ex. host122),jmsnodename (ex.
 * salcore-jms-node-122),and queuename(ex. alarms) will have to match according
 * to the enviornment under which it is being used.
 * 
 * @author mchopker
 * 
 */
public class JbossEAP6JMSMonitorVer1 {

	public static void main(String[] args) {

		String host;
		int port;
		String username;
		String password;
		String[] jmsHostNodes;
		String[] operations;
		String[] queues;
		CLI cli = null;

		try {

			File f = new File("jmsmonitoring.properties");
			if (f.exists()) {
				Properties jmsMonProperties = new Properties();
				FileInputStream in = new FileInputStream(f);
				jmsMonProperties.load(in);

				// fetch properties
				host = jmsMonProperties.getProperty("DomainHost");
				port = Integer.parseInt(jmsMonProperties
						.getProperty("DomainPort"));
				username = jmsMonProperties.getProperty("Username");
				password = jmsMonProperties.getProperty("Password");
				jmsHostNodes = jmsMonProperties.getProperty("JmsHostNodes")
						.split(",");
				operations = jmsMonProperties.getProperty("Operations").split(
						",");
				queues = jmsMonProperties.getProperty("Queues").split(",");
			} else {
				System.out.println("Input Proerty file missing");
				return;
			}

			// Get CLI instance
			cli = CLI.newInstance();
			// connect to domain controller
			cli.connect(host, port, username, password.toCharArray());

			// iterate through JMS Nodes and Queues to fetch the needed data
			for (String jmsNode : jmsHostNodes) {
				StringBuffer cmdString = new StringBuffer("/");
				cmdString.append(jmsNode);
				System.out.println("########### JMS Node: " + jmsNode
						+ " #########");
				for (String queue : queues) {
					System.out.println("Queue: " + queue);
					StringBuffer cmdString2 = new StringBuffer(
							cmdString.toString())
							.append("/subsystem=messaging/hornetq-server=default/jms-queue=")
							.append(queue).append("/:read-attribute(name=");
					for (String operation : operations) {
						// execute the command
						System.out.println("command: " + cmdString2.toString()
								+ operation + ")");
						Result result = cli.cmd(cmdString2.toString()
								+ operation + ")");
						ModelNode response = result.getResponse();
						String returnValue = response.get("result").asString();
						System.out.println(operation + " : " + returnValue);
					}
				}
			}
		} catch (Exception e) {
			// print exception trace
			e.printStackTrace();
		} finally {
			// disconnect the CLI instance
			if (cli != null)
				cli.disconnect();
		}
	}
}
