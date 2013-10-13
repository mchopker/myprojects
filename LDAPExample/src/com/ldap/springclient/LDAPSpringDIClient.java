package com.ldap.springclient;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

public class LDAPSpringDIClient {

	private LdapTemplate ldapTemplate;

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"context.xml");
			LDAPSpringDIClient ldapClientInstance = (LDAPSpringDIClient) context
					.getBean("ldapClient");

			if (args != null && args.length > 0) {
				String input = args[0];
				switch (input) {
				case "1":
					ldapClientInstance.createPerson("Mahesh K Chopker",
							"Mahesh", "mchopker", "Sales");
					break;
				case "2":
					ldapClientInstance.modifyPerson("Mahesh K Chopker",
							"Chopker", "mchopker2", "Sales");
					break;
				case "3":
					ldapClientInstance.removePerson("Mahesh K Chopker");
					break;
				default:
					ldapClientInstance.searchPerson();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createPerson(String name, String sn, String uid, String ou)
			throws NamingException {
		Attributes attrs = new BasicAttributes();
		String dn = "cn=" + name + ",ou=people";
		Attribute attr = new BasicAttribute("objectClass");
		attr.add("inetOrgPerson");
		attrs.put(attr);
		attrs.put("sn", sn);
		attrs.put("uid", uid);
		attrs.put("ou", ou);
		ldapTemplate.bind(dn, null, attrs);
	}

	private void modifyPerson(String name, String sn, String uid, String ou)
			throws NamingException {
		Attributes attrs = new BasicAttributes();
		String dn = "cn=" + name + ",ou=people";
		Attribute attr = new BasicAttribute("objectClass");
		attr.add("inetOrgPerson");
		attrs.put(attr);
		attrs.put("sn", sn);
		attrs.put("uid", uid);
		attrs.put("ou", ou);
		ldapTemplate.rebind(dn, null, attrs);
	}

	private void removePerson(String name) throws NamingException {
		ldapTemplate.unbind(name);
	}

	private void searchPerson() {
		System.out.println(ldapTemplate.search("cn=Mahesh K Chopker,ou=People",
				"(objectclass=*)", new AttributesMapper() {
					public Object mapFromAttributes(Attributes attrs)
							throws NamingException {
						Attributes attributes = new BasicAttributes();
						attributes.put(attrs.get("cn"));
						attributes.put(attrs.get("uid"));
						return attributes;
					}
				}));
	}
}
