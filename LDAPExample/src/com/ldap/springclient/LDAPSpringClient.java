package com.ldap.springclient;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

public class LDAPSpringClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			LdapContextSource ldapContextSource = new LdapContextSource();
			ldapContextSource.setUrl("ldap://localhost");
			ldapContextSource.setBase("dc=maxcrc,dc=com");
			ldapContextSource.setUserDn("cn=Manager,dc=maxcrc,dc=com");
			ldapContextSource.setPassword("secret");

			// this line needed since we are not using DI and manually
			// initizlizing context
			ldapContextSource.afterPropertiesSet();

			LdapTemplate ldapTemplate = new LdapTemplate();
			ldapTemplate.setContextSource(ldapContextSource);

			searchPerson(ldapTemplate);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void searchPerson(LdapTemplate ldapTemplate) {
		System.out.println(ldapTemplate.search("cn=Mahesh Chopker,ou=People",
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
