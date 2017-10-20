package com.ecc.hibernate_xml.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="addresses")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Address implements com.ecc.hibernate_xml.model.Entity {
	private Integer id;
	private String streetNumber;
	private Integer barangay;
	private String municipality;
	private Integer zipCode;
	
	@Id @GeneratedValue(generator="AddressIdGenerator")
	@SequenceGenerator(name="AddressIdGenerator", sequenceName="addresses_id_seq")
	@Column(nullable=false)
	public Integer getId() {
		return id;
	}
	
	@Column(name="street_number", nullable=false, length=20)
	public String getStreetNumber() {
		return streetNumber;
	}

	@Column(nullable=false)
	public Integer getBarangay() {
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
}