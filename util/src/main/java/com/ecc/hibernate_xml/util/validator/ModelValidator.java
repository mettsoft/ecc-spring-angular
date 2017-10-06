package com.ecc.hibernate_xml.util.validator;

import java.util.Set;
import java.util.HashSet;

public class ModelValidator {

	private Object data;	
	private Set<ValidatorPolicy> policies;

	private ModelValidator() {}
	private ModelValidator(Object data) {
		policies = new HashSet<ValidatorPolicy>();
		this.data = data;
	}

	public static  ModelValidator create(Object data) {
		return new ModelValidator(data);
	}

	public ModelValidator maxLength(Integer maximumLength, String errorMessage) {
		policies.add(new MaxLengthPolicy(data, maximumLength, errorMessage));
		return this;
	}

	public ModelValidator equalLength(Integer matchingLength, String errorMessage) {
		policies.add(new EqualLengthPolicy(data, matchingLength, errorMessage));
		return this;
	}

	public ModelValidator notEmpty(String errorMessage) {
		policies.add(new NotEmptyPolicy(data, errorMessage));
		return this;
	}

	public ModelValidator notNull(String errorMessage) {
		policies.add(new NotNullPolicy(data, errorMessage));
		return this;
	}

	public ModelValidator validEmail(String errorMessage) {
		policies.add(new ValidEmailPolicy(data, errorMessage));
		return this;
	}

	public ModelValidator digits(String errorMessage) {
		policies.add(new DigitsPolicy(data, errorMessage));
		return this;
	}

	public void validate() throws ValidationException {
		for (ValidatorPolicy policy : policies) {
			policy.validate();
		}
	}
}