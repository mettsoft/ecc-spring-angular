package com.ecc.spring.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.FieldError;

import com.ecc.spring.util.ValidationException;
import com.ecc.spring.util.ValidationUtils;

@ControllerAdvice("com.ecc.spring.web")
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler({ ValidationException.class })
	protected ResponseEntity<Object> handleValidationException(ValidationException cause, WebRequest request, Locale locale) {
		try {
			List<FieldError> errors = cause.getFieldErrors();
			Map<String, List<String>> errorMessages = ValidationUtils.localize(errors, messageSource, locale);			
			for (Map.Entry entry : errorMessages.entrySet()) {
				logger.warn(entry.toString());
			}
			logger.warn(null, cause);
			return handleExceptionInternal(cause, errorMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);			
		}
		catch(Exception exception) {
			logger.error("Fatal error!", exception);
			throw exception;
		}
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("Bad request:", ex);
		return super.handleHttpMessageNotReadable(ex, headers, status, request);
	}
}