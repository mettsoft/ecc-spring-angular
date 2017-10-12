package com.ecc.hibernate_xml.model;

public class Address {
	private Integer id;
	private String streetNumber;
	private Integer barangay;
	private String municipality;
	private Integer zipCode;
	
	public void setId(Integer id) {
		this.id = id;
	}

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