package com.ecc.hibernate_xml.util.dao;

import java.util.function.Function;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionScope {
    public static void executeTransaction(Consumer<Session> function) {
        executeTransactionWithResult(session -> { 
            function.accept(session);
            return 0;
        });
    }

    public static <R> R executeTransactionWithResult(Function<Session, R> function) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            R returnValue = function.apply(session);
            transaction.commit();
            return returnValue;
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