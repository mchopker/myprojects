package com.ldap.purjavaclient;

import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPPurJavaClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			InitialDirContext context = getDirCtx("ldap://localhost",
					"cn=Manager,dc=maxcrc,dc=com", "secret");
			try {
				if (args != null && args.length > 0) {
					String input = args[0];
					switch (input) {
					case "1":
						createPerson(context, "Mahesh Chopker", "Mahesh",
								"mchopker", "Sales");
						break;
					case "2":
						modifyPerson(context, "Mahesh Chopker", "Chopker",
								"mchopker", "Sales");
						break;
					case "3":
						removePerson(context, "Mahesh Chopker");
						break;
					default:
						searchPerson(context);
					}
				}
			} finally {
				context.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void searchPerson(InitialDirContext context) {
		Attributes attrs = new BasicAttributes();
		Attribute attr = new BasicAttribute("objectClass");
		attr.add("inetOrgPerson");
		attrs.put(attr);
		SearchControls ctls = new SearchControls();
		String[] ret = { "sn", "uid" };
		String filter = "(objectclass=*)";
		ctls.setReturningAttributes(ret);
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> enu = null;
		try {
			enu = context.search("cn=" + "Mahesh Chopker"
					+ ",ou=people,dc=maxcrc,dc=com", filter, ctls);
			while (enu != null && enu.hasMore()) {
				SearchResult sr = (SearchResult) enu.next();
				System.out.println(sr.getNameInNamespace());
				Attributes rattr = sr.getAttributes();
				System.out.println(rattr.get("sn"));
				System.out.println(rattr.get("uid"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (enu != null) {
				try {
					enu.close();
				} catch (Exception e1) {
					// igonore this
				}
			}
		}
	}

	private static void createPerson(InitialDirContext context, String name,
			String sn, String uid, String ou) throws NamingException {
		Attributes attrs = new BasicAttributes();
		String dn = "cn=" + name + ",ou=people,dc=maxcrc,dc=com";
		Attribute attr = new BasicAttribute("objectClass");
		attr.add("inetOrgPerson");
		attrs.put(attr);
		attrs.put("sn", sn);
		attrs.put("uid", uid);
		attrs.put("ou", ou);
		SearchResult sr = new SearchResult(dn, null, attrs);
		context.createSubcontext(sr.getName(), attrs);
	}

	private static void modifyPerson(InitialDirContext context, String name,
			String sn, String uid, String ou) throws NamingException {
		Attributes attrs = new BasicAttributes();
		String dn = "cn=" + name + ",ou=people,dc=maxcrc,dc=com";
		Attribute attr = new BasicAttribute("objectClass");
		attr.add("inetOrgPerson");
		attrs.put(attr);
		attrs.put("sn", sn);
		attrs.put("uid", uid);
		attrs.put("ou", ou);
		SearchResult sr = new SearchResult(dn, null, attrs);
		context.modifyAttributes(sr.getName(), DirContext.REPLACE_ATTRIBUTE,
				attrs);
	}

	private static void removePerson(InitialDirContext context, String name)
			throws NamingException {
		context.destroySubcontext(name);
	}

	private static InitialDirContext getDirCtx(String url, String userBindDn,
			String userBindPw) throws Exception {
		InitialDirContext ctx = null;
		Properties env = new Properties();
		env.setProperty(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.setProperty(javax.naming.Context.PROVIDER_URL, url);
		env.setProperty(javax.naming.Context.SECURITY_PRINCIPAL, userBindDn);
		env.setProperty(javax.naming.Context.SECURITY_CREDENTIALS, userBindPw);
		try {
			ctx = new InitialDirContext(env);
		} catch (NamingException ex) {
			throw new Exception("LdapManagerBean::createConnection failed", ex);
		}
		return ctx;
	}
}
