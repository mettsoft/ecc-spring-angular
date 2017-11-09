package com.ecc.spring_security.util;

import java.util.List;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.lang3.StringUtils;

public class ValidationUtils {
	private static final Pattern PATTERN = Pattern.compile("^[0-9]+$");
	private static final String MAX_LENGTH_ERROR_MESSAGE_TEMPLATE = "{1} must not exceed {0} characters.";
	private static final String EQUALS_ERROR_MESSAGE = "{1} must contain {0} digits.";
	private static final String NOT_EMPTY_ERROR_MESSAGE_TEMPLATE = "{0} cannot be empty.";
	private static final String EMAIL_IS_INVALID = "Email is invalid.";
	private static final String NUMERICAL_DIGITS_ERROR_MESSAGE = "{0} must only contain numerical digits.";
	private static final String MINIMUM_ERROR_MESSAGE_TEMPLATE = "{1} cannot be less than {0}.";
	private static final String MAXIMUM_ERROR_MESSAGE_TEMPLATE = "{1} cannot be greater than {0}.";

	public static List<String> localize(List<ObjectError> errors, MessageSource messageSource, Locale locale) {
		return errors.stream()
				.map(t -> messageSource.getMessage(t.getCode(), 
					localizeArguments(t.getArguments(), messageSource, locale), locale))
				.collect(Collectors.toList());
	}

	private static Object[] localizeArguments(Object[] arguments, MessageSource messageSource, Locale locale) {
		return arguments == null? null: Arrays.stream(arguments).map(arg -> arg == null? "null": arg).map(arg -> 
			arg.toString().startsWith("localize:")? 
				messageSource.getMessage
				(
					StringUtils.substringAfter(arg.toString(), "localize:"), 
					null, 
					locale
				): arg).toArray();
	}

	public static void testMaxLength(String data, String field, Errors errors, Object... arguments) {
		Integer maximumLength = (Integer) arguments[0];
		if (data != null && data.length() > maximumLength) {
			errors.rejectValue(field, "validation.message.maxLength", arguments, MAX_LENGTH_ERROR_MESSAGE_TEMPLATE);
		}
	}

	public static void testEqualLength(String data, String field, Errors errors, Object... arguments) {
		Integer matchingLength = (Integer) arguments[0];
		if (data != null && data.length() != matchingLength) {
			errors.rejectValue(field, "validation.message.equalLength", arguments, EQUALS_ERROR_MESSAGE);		
		}
	}

	public static void testNotEmpty(Object data, String field, Errors errors, Object... arguments) {
		if (data == null || data instanceof String && StringUtils.isEmpty((String) data)) {
			errors.rejectValue(field, "validation.message.notEmpty", arguments, NOT_EMPTY_ERROR_MESSAGE_TEMPLATE);
		}
	}

	public static void testValidEmail(String data, String field, Errors errors, Object... arguments) {
		if (!EmailValidator.getInstance().isValid(data)) {
			errors.rejectValue(field, "validation.message.email", arguments, EMAIL_IS_INVALID);
		}
	}

	public static void testDigits(String data, String field, Errors errors, Object... arguments) {
		if (data != null && !PATTERN.matcher(data).find()) {
			errors.rejectValue(field, "validation.message.digits", arguments, NUMERICAL_DIGITS_ERROR_MESSAGE);
		}
	}

	public static void testMinimumValue(BigDecimal data, String field, Errors errors, Object... arguments) {	
		BigDecimal threshold = new BigDecimal(arguments[0].toString());
		if (data != null && data.compareTo(threshold) < 0) {
			errors.rejectValue(field, "validation.message.minimumValue", arguments, MINIMUM_ERROR_MESSAGE_TEMPLATE);
		}
	}

	public static void testMaximumValue(BigDecimal data, String field, Errors errors, Object... arguments) {
		BigDecimal threshold = new BigDecimal(arguments[0].toString());
		if (data != null && data.compareTo(threshold) > 0) {
			errors.rejectValue(field, "validation.message.maximumValue", arguments, MAXIMUM_ERROR_MESSAGE_TEMPLATE);
		}
	}
}