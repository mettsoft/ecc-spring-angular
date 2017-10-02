package com.ecc.hibernate_xml.dao;

public class DaoException extends Exception {
	private static final String MESSAGE = "Database operation failed!";
	public DaoException(Throwable cause) {
		super(MESSAGE, cause);
	}
}