package com.ecc.hibernate_xml.model;

public class Address {
	private static final Integer MAX_STREET_NUMBER_CHARACTERS = 20;
	private static final Integer MAX_MUNICIPALITY_CHARACTERS = 50;

	private Integer id;
	private String streetNumber;
	private Integer barangay;
	private String municipality;
	private Integer zipCode;

	private Address() {

	}

	public Address(String streetNumber, Integer barangay, String municipality, Integer zipCode) {
		setStreetNumber(streetNumber);
		setBarangay(barangay);
		setMunicipality(municipality);
		setZipCode(zipCode);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setStreetNumber(String streetNumber) {
		if (streetNumber == null || streetNumber.trim().isEmpty()) {
			throw new ModelException("Street number cannot be empty.");
		}
		else if (streetNumber.length() > MAX_STREET_NUMBER_CHARACTERS) {
			throw new ModelException(String.format("Street number must not exceed %d characters.", 
				MAX_STREET_NUMBER_CHARACTERS));
		}
		this.streetNumber = streetNumber;
	}

	public void setBarangay(Integer barangay) {
		if (barangay == null) {
			throw new ModelException("Barangay cannot be empty.");
		}
		this.barangay = barangay;
	}

	public void setMunicipality(String municipality) {
		if (municipality == null || municipality.trim().isEmpty()) {
			throw new ModelException("Municipality cannot be empty.");
		}
		else if (municipality.length() > MAX_MUNICIPALITY_CHARACTERS) {
			throw new ModelException(String.format("Municipality must not exceed %d characters.", 
				MAX_MUNICIPALITY_CHARACTERS));
		}
		this.municipality = municipality;
	}

	public void setZipCode(Integer zipCode) {
		if (zipCode == null) {
			throw new ModelException("Zip code cannot be empty.");
		}
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