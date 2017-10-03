package com.ecc.hibernate_xml.model;

import java.math.BigDecimal;
import java.util.Set;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Person {
	private Integer id;
	private Name name;
	private Address address;
	private Date birthday;
	private BigDecimal GWA;
	private Boolean currentlyEmployed;
	private Date dateHired;
	private Set<Contact> contacts;
	private Set<Role> roles;

	private Person() {
		setCurrentlyEmployed(false);
	}
	
	public Person(Name name) {
		this();
		setName(name);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setGWA(BigDecimal GWA) {
		this.GWA = GWA;
	}

	public void setCurrentlyEmployed(Boolean currentlyEmployed) {
		this.currentlyEmployed = currentlyEmployed;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	public void setContacts(Set contacts) {
		this.contacts = contacts;
	}

	public void setRoles(Set roles) {
		this.roles = roles;
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

	public Date getBirthday() {
		return birthday;
	}

	public BigDecimal getGWA() {
		return GWA;
	}

	public Boolean getCurrentlyEmployed() {
		return currentlyEmployed;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public Set getContacts() {
		return contacts;
	}

	public Set getRoles() {
		return roles;
	}

	@Override
	public String toString() {
		List<String> tokens = new ArrayList<>(5);

		tokens.add("Name: " + name);
		if (address != null) {
			tokens.add("Address: " + address);
		}

		if (birthday != null) {
			tokens.add("Birthday: " + birthday);
		}

		if (GWA != null) {
			tokens.add("GWA: " + GWA);
		}

		tokens.add("Currently Employed: " + (currentlyEmployed? dateHired: "No"));		

		// TODO: Contacts
		// TODO: Roles
		return String.format("------ ID: %d ------\n%s", id, 
			tokens.stream().collect(Collectors.joining("\n")));
	}
}