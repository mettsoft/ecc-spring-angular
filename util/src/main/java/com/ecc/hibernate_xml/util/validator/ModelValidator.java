package com.ecc.hibernate_xml.util.validator;

import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

public class ModelValidator {
	private static final String MAX_LENGTH_ERROR_MESSAGE_TEMPLATE = "%2$s must not exceed %1$d characters.";
	private static final String EQUALS_ERROR_MESSAGE = "%2$s must contain %1$d digits.";
	private static final String NOT_EMPTY_ERROR_MESSAGE_TEMPLATE = "%s cannot be empty.";
	private static final String NOT_NULL_ERROR_MESSAGE_TEMPLATE = "%s cannot be null.";
	private static final String EMAIL_IS_INVALID = "Email is invalid.";
	private static final String NUMERICAL_DIGITS_ERROR_MESSAGE = "%s must only contain numerical digits.";
	private static final String MINIMUM_ERROR_MESSAGE_TEMPLATE = "%2$s cannot be less than %1$d.";
	private static final String MAXIMUM_ERROR_MESSAGE_TEMPLATE = "%2$s cannot be greater than %1$d.";

	private Object data;	
	private Map<String, ValidatorPolicy> policies;

	private ModelValidator() {
		policies = new HashMap<>();

		policies.put("MaxLength", new ValidatorPolicy() {
			@Override
			public Boolean onValidate(Object data, Object... arguments) {
				Integer maximumLength = (Integer) arguments[0];
				String subject = data == null? null: (String) data;
				return subject == null || subject.length() <= maximumLength;
			}
		});

		policies.get("MaxLength").setMessageTemplate(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE);

		policies.put("EqualLength", new ValidatorPolicy() {
			@Override
			public Boolean onValidate(Object data, Object... arguments) {
				Integer matchingLength = (Integer) arguments[0];
				String subject = data == null? null: (String) data;
				return subject != null && subject.length() == matchingLength;
			}
		});
		policies.get("EqualLength").setMessageTemplate(EQUALS_ERROR_MESSAGE);

		policies.put("NotEmpty", new ValidatorPolicy() {
			@Override
			public Boolean onValidate(Object data, Object... arguments) {
				if (data == null) {
					return false;
				}
				else if (data instanceof String && ((String) data).trim().isEmpty()) {
					return false;				
				}
				return true;
			}
		});

		policies.get("NotEmpty").setMessageTemplate(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE);

		policies.put("ValidEmail", new ValidatorPolicy() {
			@Override
			public Boolean onValidate(Object data, Object... arguments) {
				String subject = data == null? null: (String) data;
				return EmailValidator.getInstance().isValid(subject);
			}
		});

		policies.get("ValidEmail").setMessageTemplate(EMAIL_IS_INVALID);

		policies.put("Digits", new ValidatorPolicy() {
			final Pattern PATTERN = Pattern.compile("^[0-9]+$");
			@Override
			public Boolean onValidate(Object data, Object... arguments) {
				String subject = data == null? null: (String) data;
				return subject != null && PATTERN.matcher(subject).find();
			}
		});

		policies.get("Digits").setMessageTemplate(NUMERICAL_DIGITS_ERROR_MESSAGE);

		policies.put("Minimum", new ValidatorPolicy() {
			@Override
			public Boolean onValidate(Object data, Object... arguments) {
				BigDecimal subject = data == null? null: (BigDecimal) data;
				BigDecimal threshold = new BigDecimal(arguments[0].toString());
				return subject != null && subject.compareTo(threshold) >= 0;
			}
		});

		policies.get("Minimum").setMessageTemplate(MINIMUM_ERROR_MESSAGE_TEMPLATE);

		policies.put("Maximum", new ValidatorPolicy() {
			@Override
			public Boolean onValidate(Object data, Object... arguments) {
				BigDecimal subject = data == null? null: (BigDecimal) data;
				BigDecimal threshold = new BigDecimal(arguments[0].toString());
				return subject != null && subject.compareTo(threshold) <= 0;
			}
		});

		policies.get("Maximum").setMessageTemplate(MAXIMUM_ERROR_MESSAGE_TEMPLATE);
	}

	public static ModelValidator create() {
		return new ModelValidator();
	}

	public void validate(String key, Object data, Object... arguments) throws ValidationException {
		policies.get(key).validate(data, arguments);
	}

	public ModelValidator setMessageTemplate(String key, String messageTemplate) {
		policies.get(key).setMessageTemplate(messageTemplate);
		return this;
	}
}