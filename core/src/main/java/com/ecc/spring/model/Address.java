package com.ecc.spring.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	@Column(name="street_number", nullable=false, length=20)
	private String streetNumber;

	@Column(nullable=false)
	private String barangay;

	@Column(nullable=false, length=50)
	private String municipality;
	
	@Column(name="zip_code", nullable=false)
	private Integer zipCode;
		
	public String getStreetNumber() {
		return streetNumber;
	}

	public String getBarangay() {
		return barangay;
	}

	public String getMunicipality() {
		return municipality;
	}

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