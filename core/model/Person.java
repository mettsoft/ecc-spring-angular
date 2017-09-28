package com.ecc.hibernate-xml.model;

import java.math.BigDecimal;
import java.util.Date;

public class Person {
	private Integer id;
	private Name name;
	private Address address;
	private Date birthday;
	private BigDecimal GWA;
	private Boolean currentlyEmployed;
	private Date dateHired;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setGWA(BigDecimal GWA) {
		this.GWA = GWA;
	}

	public void setCurrentEmployed(Boolean currentEmployed) {
		this.currentEmployed = currentEmployed;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	public Integer getId() {
		return id;
	}

	public Name getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}

	public Date getDate() {
		return date;
	}

	public BigDecimal getGWA() {
		return GWA;
	}

	public Boolean getCurrentEmployed() {
		return currentEmployed;
	}

	public Date getDateHired() {
		return dateHired;
	}
}