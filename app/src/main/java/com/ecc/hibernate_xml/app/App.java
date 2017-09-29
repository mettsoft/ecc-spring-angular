package com.ecc.hibernate_xml.app;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Name;
import com.ecc.hibernate_xml.model.Address;

public class App {

	private static SessionFactory factory;

	public static void main(String[] args) {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		try {
			factory = new Configuration().configure("persistence/hibernate.cfg.xml").buildSessionFactory();		
			Session session = factory.openSession();
			try {
				Person person = new Person();
				person.setName(new Name("Young", "Emmett", "Ngan"));
				person.setAddress(new Address("1410", 317, "Manila", 1003));
				person.setBirthday(new Date());
				person.setGWA(new BigDecimal(3.202));
				person.setCurrentlyEmployed(true);
				person.setDateHired(new Date());

				Transaction tx = session.beginTransaction();
				session.save(person);
				tx.commit();
			}
			catch (HibernateException exception) {
				printException(exception);
			}
			finally {
				session.close();
			}
		}
		catch (HibernateException exception) {
			printException(exception);
		}
		finally {
			factory.close();
		}
	}

	private static void printException(Exception exception) {
		exception.printStackTrace();
		if (exception.getCause() != null) {
			System.err.print("Caused by: ");
			exception.getCause().printStackTrace();
		}	
	}
}