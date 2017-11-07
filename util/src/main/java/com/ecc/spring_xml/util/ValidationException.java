package com.ecc.spring_xml.util;

import java.util.List;
import java.util.ArrayList;
import org.springframework.validation.ObjectError;

public class ValidationException extends RuntimeException {
	private List<ObjectError> errors;

	public ValidationException(String messageCode, Object...arguments) {
		errors = new ArrayList<>(1);
		errors.add(new ObjectError("command", new String[] { messageCode }, arguments, null));
	}

	public ValidationException(List<ObjectError> errors) {
		this.errors = errors;
	}

	public List<ObjectError> getAllErrors() {
		return errors;
	}
}