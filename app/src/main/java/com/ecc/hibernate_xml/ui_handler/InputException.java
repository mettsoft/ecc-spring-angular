package com.ecc.hibernate_xml.ui_handler;

public class InputException extends Exception {
	private static final String MESSAGE = "Invalid input!";

	public InputException(Throwable cause) {
		super(MESSAGE, cause);
	}
}