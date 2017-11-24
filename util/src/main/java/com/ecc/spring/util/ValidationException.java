package com.ecc.spring.util;

import java.util.List;
import java.util.ArrayList;
import org.springframework.validation.FieldError;

public class ValidationException extends RuntimeException {
	private List<FieldError> errors;
	private Object target;

	public ValidationException(String messageCode, Object target, Object...arguments) {
		this.errors = new ArrayList<>(1);
		this.errors.add(new FieldError("command", "default", null, false, new String[] { messageCode }, arguments, null));
		this.target = target;
	}

	public ValidationException(List<FieldError> errors, Object target) {
		this.errors = errors;
		this.target = target;
	}

	public List<FieldError> getFieldErrors() {
		return errors;
	}

	public Object getTarget() {
		return target;
	}
}