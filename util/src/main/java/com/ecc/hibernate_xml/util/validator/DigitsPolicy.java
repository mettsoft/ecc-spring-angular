package com.ecc.hibernate_xml.util.validator;

import java.util.regex.Pattern;

class DigitsPolicy extends AbstractValidatorPolicy {

	final Pattern PATTERN = Pattern.compile("^[0-9]+$");

	String data;
	String errorMessage;

	DigitsPolicy(Object data, String errorMessage) {
		this.data = data == null? null: (String) data;
		this.errorMessage = errorMessage;
	}

	@Override
	public void validate() throws ValidationException {
		if (data != null && !PATTERN.matcher(data).find()) {
			throw new ValidationException(errorMessage);
		}
	}
}