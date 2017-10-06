package com.ecc.hibernate_xml.model;

import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class Address {
	public static class Factory {
		private Address address;

		public Factory() {
			address = new Address();
		}

		public Factory setStreetNumber(String streetNumber) throws ValidationException {
			address.setStreetNumber(streetNumber);
			return this;
		}

		public Factory setBarangay(Integer barangay) throws ValidationException {
			address.setBarangay(barangay);
			return this;
		}

		public Factory setMunicipality(String municipality) throws ValidationException {
			address.setMunicipality(municipality);
			return this;
		}

		public Factory setZipCode(Integer zipCode) throws ValidationException {
			address.setZipCode(zipCode);
			return this;
		}

		public Address build() throws ValidationException {
			address.setStreetNumber(address.streetNumber);
			address.setBarangay(address.barangay);
			address.setMunicipality(address.municipality);
			address.setZipCode(address.zipCode);
			return address;
		}
	}

	private static final Integer MAX_STREET_NUMBER_CHARACTERS = 20;
	private static final Integer MAX_MUNICIPALITY_CHARACTERS = 50;

	private static final String MAX_LENGTH_ERROR_MESSAGE_TEMPLATE = "%s must not exceed %d characters.";
	private static final String NOT_EMPTY_ERROR_MESSAGE_TEMPLATE = "%s cannot be empty.";
	private static final String NOT_NULL_ERROR_MESSAGE_TEMPLATE = "%s cannot be null.";

	private Integer id;
	private String streetNumber;
	private Integer barangay;
	private String municipality;
	private Integer zipCode;

	private Address() {}

	public Address(String streetNumber, Integer barangay, String municipality, Integer zipCode) throws ValidationException {
		setStreetNumber(streetNumber);
		setBarangay(barangay);
		setMunicipality(municipality);
		setZipCode(zipCode);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setStreetNumber(String streetNumber) throws ValidationException {
		ModelValidator
			.create(streetNumber)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Street number"))
			.maxLength(MAX_STREET_NUMBER_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Street number", MAX_STREET_NUMBER_CHARACTERS))
			.validate();

		this.streetNumber = streetNumber;
	}

	public void setBarangay(Integer barangay) throws ValidationException {
		ModelValidator
			.create(streetNumber)
			.notNull(String.format(NOT_NULL_ERROR_MESSAGE_TEMPLATE, "Barangay"))
			.validate();

		this.barangay = barangay;
	}

	public void setMunicipality(String municipality) throws ValidationException {
		ModelValidator
			.create(municipality)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Municipality"))
			.maxLength(MAX_MUNICIPALITY_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Municipality", MAX_MUNICIPALITY_CHARACTERS))
			.validate();

		this.municipality = municipality;
	}

	public void setZipCode(Integer zipCode) throws ValidationException {
		ModelValidator
			.create(zipCode)
			.notNull(String.format(NOT_NULL_ERROR_MESSAGE_TEMPLATE, "Zip code"))
			.validate();

		this.zipCode = zipCode;
	}

	public Integer getId() {
		return id;
	}
	
	public String getStreetNumber() {
		return streetNumber;
	}

	public Integer getBarangay() {
		return barangay;
	}

	public String getMunicipality() {
		return municipality;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	@Override
	public String toString() {
		return String.format("%s, Barangay %d, %s, %d", streetNumber, barangay, municipality, zipCode);
	}
}