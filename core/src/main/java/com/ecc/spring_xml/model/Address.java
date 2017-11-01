package com.ecc.spring_xml.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	private String streetNumber;
	private String barangay;
	private String municipality;
	private Integer zipCode;
		
	@Column(name="street_number", nullable=false, length=20)
	public String getStreetNumber() {
		return streetNumber;
	}

	@Column(nullable=false)
	public String getBarangay() {
		return barangay;
	}

	@Column(nullable=false, length=50)
	public String getMunicipality() {
		return municipality;
	}

	@Column(name="zip_code", nullable=false)
	public Integer getZipCode() {
		return zipCode;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public void setBarangay(String barangay) {
		this.barangay = barangay;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
}