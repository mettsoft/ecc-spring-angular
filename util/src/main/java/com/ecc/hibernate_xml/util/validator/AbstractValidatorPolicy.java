package com.ecc.hibernate_xml.util.validator;

abstract class AbstractValidatorPolicy implements ValidatorPolicy {

	@Override 
	public int hashCode() {
		return getClass().getSimpleName().hashCode();
	}

	@Override 
	public boolean equals(Object object) {
		if (object != null && object instanceof AbstractValidatorPolicy) {
			return object.getClass().getSimpleName().equals(
				getClass().getSimpleName()); 
		}
		return false;
	}
}