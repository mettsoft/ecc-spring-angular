package com.ecc.hibernate_xml.util.validator;

class MaxLengthPolicy extends AbstractValidatorPolicy {

	String data;
	Integer maximumLength;
	String errorMessage;

	MaxLengthPolicy(Object data, Integer maximumLength, String errorMessage) {
		this.data = data == null? null: (String) data;
		this.maximumLength = maximumLength;
		this.errorMessage = errorMessage;
	}

	@Override
	public void validate() throws ValidationException {
		if (data != null && data.length() > maximumLength) {
			throw new ValidationException(errorMessage);
		}
	}
}