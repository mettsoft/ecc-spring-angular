package com.ecc.hibernate_xml.util.validator;

class EqualLengthPolicy extends AbstractValidatorPolicy {

	String data;
	Integer matchingLength;
	String errorMessage;

	EqualLengthPolicy(Object data, Integer matchingLength, String errorMessage) {
		this.data = data == null? null: (String) data;
		this.matchingLength = matchingLength;
		this.errorMessage = errorMessage;
	}

	@Override
	public void validate() throws ValidationException {
		if (data != null && data.length() != matchingLength) {
			throw new ValidationException(errorMessage);
		}
	}
}