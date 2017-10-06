package com.ecc.hibernate_xml.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class Person {
	private Integer id;
	private Name name;
	private Address address;
	private Date birthday;
	private BigDecimal GWA;
	private Boolean currentlyEmployed;
	private Date dateHired;
	private List<Contact> contacts;
	private List<Role> roles;

	private Person() {
		setCurrentlyEmployed(false);
		setContacts(new ArrayList<>());
		setRoles(new ArrayList<>());
	}
	
	public Person(Name name) throws ValidationException {
		this();
		setName(name);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(Name name) throws ValidationException {
		ModelValidator
			.create(name)
			.notNull("Name cannot be null.")
			.validate();

		this.name = name;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setBirthday(Date birthday) {
 		this.birthday = birthday;
	}

	public void setGWA(BigDecimal GWA) throws ValidationException {
		ModelValidator
			.create(GWA)
			.minimum(new BigDecimal(1), "GWA cannot be less than 1.")
			.maximum(new BigDecimal(5), "GWA cannot be greater than 5.")
			.validate();

		this.GWA = GWA;
	}

	public void setCurrentlyEmployed(boolean currentlyEmployed) {
		this.currentlyEmployed = currentlyEmployed;
	}

	public void setDateHired(Date dateHired) throws ValidationException {
		if (!getCurrentlyEmployed() && dateHired != null) {
			throw new ValidationException("Date hired cannot be assigned if person is unemployed.");
		}
		else if (getCurrentlyEmployed() && dateHired == null) {
			throw new ValidationException("Date hired cannot be null if person is employed.");
		}
		this.dateHired = dateHired;		
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public void setRoles(List<Role> roles) {
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

	public List<Contact> getContacts() {
		return contacts;
	}

	public List<Role> getRoles() {
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

		if (contacts != null && contacts.size() > 0) {
			tokens.add("Contacts: " + contacts.stream()
				.map(contact -> String.format("[%s] %s", contact.getContactType(), contact.getData()))
				.collect(Collectors.joining(", ")));		
		}

		if (roles != null && roles.size() > 0) {
			tokens.add("Roles: " + roles.stream()
				.map(role -> role.getName())
				.collect(Collectors.joining(", ")));			
		}

		return String.format("------ ID: %d ------\n%s", id, 
			tokens.stream().collect(Collectors.joining("\n")));
	}
}