package com.ecc.hibernate_xml.dto;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PersonDTO {
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private Integer id;
	private NameDTO name;
	private AddressDTO address;
	private Date birthday;
	private BigDecimal GWA;
	private Boolean currentlyEmployed;
	private Date dateHired;
	private List<ContactDTO> contacts;
	private List<RoleDTO> roles;
	
	public PersonDTO() {
		currentlyEmployed = false;
		contacts = new ArrayList<>();
		roles = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public NameDTO getName() {
		return name;
	}

	public AddressDTO getAddress() {
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

	public List<ContactDTO> getContacts() {
		return contacts;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(NameDTO name) {
		this.name = name;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public void setBirthday(Date birthday) {
 		this.birthday = birthday;
	}

	public void setGWA(BigDecimal GWA) {
		this.GWA = GWA;
	}

	public void setCurrentlyEmployed(boolean currentlyEmployed) {
		this.currentlyEmployed = currentlyEmployed;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;		
	}

	public void setContacts(List<ContactDTO> contacts) {
		this.contacts = contacts;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toSimplifiedForm() {
		return String.format("[ID=%d] %s", id, name);
	}

	public String toString(String propertyToEmphasize) {
		List<String> tokens = new ArrayList<>(7);

		String nameToken = "Name: " + name;
		String GWAToken = "GWA: " + GWA;
		String currentlyEmployedToken = "Currently Employed: " + getEmploymentStatus();

		tokens.add(propertyToEmphasize.equals("name")? ">> " + nameToken: nameToken);
		if (address != null) {
			tokens.add("Address: " + address);
		}

		if (birthday != null) {
			tokens.add("Birthday: " + birthday);
		}

		if (GWA != null) {
			tokens.add(propertyToEmphasize.equals("GWA")? ">> " + GWAToken: GWAToken);
		}

		tokens.add(propertyToEmphasize.equals("currentlyEmployed")? ">> " + currentlyEmployedToken: currentlyEmployedToken);		

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

	public String getEmploymentStatus() {
		return currentlyEmployed? dateFormat.format(dateHired): "No";
	}
}