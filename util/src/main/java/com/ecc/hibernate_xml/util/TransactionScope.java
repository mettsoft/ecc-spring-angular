package com.ecc.hibernate_xml.util;

import java.util.function.Consumer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecc.hibernate_xml.util.HibernateUtility;

public class TransactionScope {
    public static void executeTransaction(Consumer<Session> function) {
    	Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = null;
    	try {
			transaction = session.beginTransaction();
			function.accept(session);
			transaction.commit();
    	}
    	catch (Exception exception) {
            if (transaction != null) {
        		transaction.rollback();
            }
    		throw exception;
    	}
        finally {
            session.close();
        }
    }
}