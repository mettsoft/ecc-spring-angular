package com.ecc.hibernate_xml.util.validator;

import java.math.BigDecimal;

class MaximumPolicy extends AbstractValidatorPolicy {
	BigDecimal data;
	BigDecimal maximumThreshold;
	String errorMessage;

	MaximumPolicy(Object data, BigDecimal maximumThreshold, String errorMessage) {
		this.data = data == null? null: (BigDecimal) data;
		this.maximumThreshold = maximumThreshold;
		this.errorMessage = errorMessage;
	}

	@Override
	public void validate() throws ValidationException {
		if (data != null && data.compareTo(maximumThreshold) > 0) {
			throw new ValidationException(errorMessage);
		}
	}
}