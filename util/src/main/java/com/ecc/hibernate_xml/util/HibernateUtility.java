package com.ecc.hibernate_xml.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {

	private static final String CONFIGURATION_PATH = "persistence/hibernate.cfg.xml";

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() throws HibernateException {
		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure(CONFIGURATION_PATH)
				.buildSessionFactory();			
		}
		return sessionFactory;
	}
}