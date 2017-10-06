package com.ecc.hibernate_xml.util.validator;

import java.math.BigDecimal;

class MinimumPolicy extends AbstractValidatorPolicy {
	BigDecimal data;
	BigDecimal minimumThreshold;
	String errorMessage;

	MinimumPolicy(Object data, BigDecimal minimumThreshold, String errorMessage) {
		this.data = data == null? null: (BigDecimal) data;
		this.minimumThreshold = minimumThreshold;
		this.errorMessage = errorMessage;
	}

	@Override
	public void validate() throws ValidationException {
		if (data != null && data.compareTo(minimumThreshold) < 0) {
			throw new ValidationException(errorMessage);
		}
	}
}