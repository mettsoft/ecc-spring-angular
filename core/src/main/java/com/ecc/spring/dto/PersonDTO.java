package com.ecc.spring.dto;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.ecc.spring.util.DateUtils;
public class PersonDTO {
	private Integer id;
	private NameDTO name;
	private AddressDTO address;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date birthday;
	private BigDecimal GWA;
	private Boolean currentlyEmployed;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date dateHired;
	private List<ContactDTO> contacts;
	private List<RoleDTO> roles;
	
	public PersonDTO() {
		name = new NameDTO();
		address = new AddressDTO();
		currentlyEmployed = false;
		contacts = new ArrayList<>();
		roles = new ArrayList<>();
	}

	public PersonDTO(Integer id, NameDTO name) {
		this();
		setId(id);
		setName(name);
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
}