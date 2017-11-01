package com.ecc.spring_xml.dao;

public class DaoException extends Exception {
	private static final String MESSAGE = "Database operation failed!";

	public DaoException(Throwable cause) {
		super(MESSAGE + String.format(" %s", cause.getMessage()), cause);
	}

	public DaoException(String message) {
		super(message);
	}
}