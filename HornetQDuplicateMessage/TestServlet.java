package com.mahesh;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory qconFactory;

	@Resource(mappedName = "java:/queue/helloMDB")
	private Queue queue;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestServlet() {
		super();
	}

	private static final long serialVersionUID = -8314035702649252239L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		try {
			out.write("<p>Sending messages to <em>helloMDB</em></p>");
			sendMessage("A Sample Message", "A Unique ID");
			out.write("<p>Message sent: A Sample Message</p>");
			out.write("</br>");
			out.write("<p><i>Go to your JBoss Application Server console or Server log to see the result of messages processing</i></p>");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("<h2>A problem occurred during the delivery of this message</h2>");
			out.write("</br>");
			out.write("<p><i>Go your the JBoss Application Server console or Server log to see the error stack trace</i></p>");
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private void sendMessage(final String message, final String uniqueMessageID)
			throws Exception {
		Connection qcon = null;
		try {
			qcon = qconFactory.createConnection();
			Session qsession = qcon.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer qsender = qsession.createProducer(queue);
			TextMessage txtMsg = qsession.createTextMessage(message);
			txtMsg.setStringProperty("_HQ_DUPL_ID", uniqueMessageID);
			qsender.send(txtMsg);
			System.out.println("Message sent: " + txtMsg);
			qcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (qcon != null) {
				qcon.close();
			}
		}
	}

}
