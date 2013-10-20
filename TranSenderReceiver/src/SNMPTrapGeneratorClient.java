package com.mahesh;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * @author mchopker
 *
 */
public class SNMPTrapGeneratorClient {

	private static final String community = "public";
	private static final String trapOid = ".1.3.6.1.2.1.1.6";
	private static final String ipAddress = "127.0.0.1";
	private static final int port = 162;

	public static void main(String args[]) {
		sendSnmpV1Trap();
		sendSnmpV2Trap();
		sendSnmpV3Trap();
	}

	/**
	 * This methods sends the V1 trap
	 */
	private static void sendSnmpV1Trap() {
		try {
			// Create Transport Mapping
			TransportMapping<?> transport = new DefaultUdpTransportMapping();
			transport.listen();

			// Create Target
			CommunityTarget comtarget = new CommunityTarget();
			comtarget.setCommunity(new OctetString(community));
			comtarget.setVersion(SnmpConstants.version1);
			comtarget.setAddress(new UdpAddress(ipAddress + "/" + port));
			comtarget.setRetries(2);
			comtarget.setTimeout(5000);

			// Create PDU for V1
			PDUv1 pdu = new PDUv1();
			pdu.setType(PDU.V1TRAP);
			pdu.setEnterprise(new OID(trapOid));
			pdu.setGenericTrap(PDUv1.ENTERPRISE_SPECIFIC);
			pdu.setSpecificTrap(1);
			pdu.setAgentAddress(new IpAddress(ipAddress));
			long sysUpTime = 111111;
			pdu.setTimestamp(sysUpTime);

			// Send the PDU
			Snmp snmp = new Snmp(transport);
			System.out.println("Sending V1 Trap to " + ipAddress + " on Port "
					+ port);
			snmp.send(pdu, comtarget);
			snmp.close();
		} catch (Exception e) {
			System.err.println("Error in Sending V1 Trap to " + ipAddress
					+ " on Port " + port);
			System.err.println("Exception Message = " + e.getMessage());
		}
	}

	/**
	 * This methods sends the V2 trap
	 */
	private static void sendSnmpV2Trap() {
		try {
			// Create Transport Mapping
			TransportMapping<?> transport = new DefaultUdpTransportMapping();
			transport.listen();

			// Create Target
			CommunityTarget comtarget = new CommunityTarget();
			comtarget.setCommunity(new OctetString(community));
			comtarget.setVersion(SnmpConstants.version2c);
			comtarget.setAddress(new UdpAddress(ipAddress + "/" + port));
			comtarget.setRetries(2);
			comtarget.setTimeout(5000);

			// Create PDU for V2
			PDU pdu = new PDU();

			// need to specify the system up time
			long sysUpTime = 111111;
			pdu.add(new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(
					sysUpTime)));
			pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID(
					trapOid)));
			pdu.add(new VariableBinding(SnmpConstants.snmpTrapAddress,
					new IpAddress(ipAddress)));

			// variable binding for Enterprise Specific objects
			pdu.add(new VariableBinding(new OID(trapOid), new OctetString(
					"Major")));
			pdu.setType(PDU.NOTIFICATION);

			// Send the PDU
			Snmp snmp = new Snmp(transport);
			System.out.println("Sending V2 Trap to " + ipAddress + " on Port "
					+ port);
			snmp.send(pdu, comtarget);
			snmp.close();
		} catch (Exception e) {
			System.err.println("Error in Sending V2 Trap to " + ipAddress
					+ " on Port " + port);
			System.err.println("Exception Message = " + e.getMessage());
		}
	}

	/**
	 * Sends the v3 trap
	 */
	private static void sendSnmpV3Trap() {
		try {
			long start = System.currentTimeMillis();
			Address targetAddress = GenericAddress.parse("udp:" + ipAddress
					+ "/" + port);

			// Create Transport Mapping
			TransportMapping<?> transport = new DefaultUdpTransportMapping();
			Snmp snmp = new Snmp(transport);
			USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
					MPv3.createLocalEngineID()), 0);
			SecurityModels.getInstance().addSecurityModel(usm);
			transport.listen();

			snmp.getUSM().addUser(
					new OctetString("MD5DES"),
					new UsmUser(new OctetString("MD5DES"), null, null, null,
							null));

			// Create Target
			UserTarget target = new UserTarget();
			target.setAddress(targetAddress);
			target.setRetries(1);

			// set timeout
			target.setTimeout(11500);
			target.setVersion(SnmpConstants.version3);
			target.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
			target.setSecurityName(new OctetString("MD5DES"));

			// Create PDU for V3
			ScopedPDU pdu = new ScopedPDU();
			pdu.setType(ScopedPDU.NOTIFICATION);

			// need to specify the system up time
			long sysUpTime = (System.currentTimeMillis() - start) / 10;
			pdu.add(new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(
					sysUpTime)));
			pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID,
					SnmpConstants.linkDown));
			pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.1.1"),
					new Integer32(1)));

			// Send the PDU
			System.out.println("Sending V3 Trap to " + ipAddress + " on Port "
					+ port);
			snmp.send(pdu, target);
			snmp.addCommandResponder(new CommandResponder() {
				@Override
				public void processPdu(CommandResponderEvent arg0) {
					System.out.println(arg0);
				}
			});
			snmp.close();
		} catch (Exception e) {
			System.err.println("Error in Sending V2 Trap to " + ipAddress
					+ " on Port " + port);
			System.err.println("Exception Message = " + e.getMessage());
		}
	}
}
