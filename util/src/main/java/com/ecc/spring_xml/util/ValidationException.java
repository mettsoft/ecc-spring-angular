package com.ecc.spring_xml.util;

import java.util.Locale;

public class ValidationException extends RuntimeException {
	private String messageCode;
	private Object[] arguments;

	public ValidationException(String messageCode, Object...arguments) {
		this.messageCode = messageCode;
		this.arguments = arguments;
	}

	public String getCode() {
		return messageCode;
	}

	public Object[] getArguments() {
		return arguments;
	}
}