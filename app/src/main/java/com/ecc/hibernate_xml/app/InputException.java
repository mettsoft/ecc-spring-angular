package com.ecc.hibernate_xml.app;

public class InputException extends Exception {
	private static final String MESSAGE = "Invalid input!";

	public InputException(Throwable cause) {
		super(MESSAGE, cause);
	}
}