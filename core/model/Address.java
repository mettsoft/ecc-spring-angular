package com.ecc.hibernate-xml.model;

public class Address {
	private String streetNumber;
	private Integer barangay;
	private String municipality;
	private Integer zipCode;

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public void setBarangay(Integer barangay) {
		this.barangay = barangay;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
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
}