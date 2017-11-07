package com.ecc.spring_xml.util;

import java.util.List;
import java.util.ArrayList;
import org.springframework.validation.ObjectError;

public class ValidationException extends RuntimeException {
	private List<ObjectError> errors;
	private Object target;

	public ValidationException(String messageCode, Object target, Object...arguments) {
		this.errors = new ArrayList<>(1);
		this.errors.add(new ObjectError("command", new String[] { messageCode }, arguments, null));
		this.target = target;
	}

	public ValidationException(List<ObjectError> errors, Object target) {
		this.errors = errors;
		this.target = target;
	}

	public List<ObjectError> getAllErrors() {
		return errors;
	}

	public Object getTarget() {
		return target;
	}
}