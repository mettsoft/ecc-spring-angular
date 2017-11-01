package com.ecc.spring_xml.util.dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
	private static final String CONFIGURATION_PATH = "persistence/hibernate.cfg.xml";

	private static SessionFactory sessionFactory;

	public static void initializeSessionFactory() throws HibernateException {
		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure(CONFIGURATION_PATH)
				.buildSessionFactory();			
		}
	}

	public static SessionFactory getSessionFactory() throws HibernateException {
		return sessionFactory;
	}

	public static void closeSessionFactory() {
		sessionFactory.close();
	}
}