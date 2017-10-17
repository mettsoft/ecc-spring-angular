package com.ecc.hibernate_xml.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Temporal;
import javax.persistence.OrderBy;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.TemporalType;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;

@Entity
@Table(name="persons")
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

	public Person() {
		setCurrentlyEmployed(false);
		setContacts(new HashSet<>());
		setRoles(new HashSet<>());
	}

	public Person(Name name) {
		this();
		setName(name);
	}
	
	@Id @GeneratedValue(generator="PersonIdGenerator")
	@SequenceGenerator(name="PersonIdGenerator", sequenceName="persons_id_seq")
	@Column(nullable=false)
	public Integer getId() {
		return id;
	}

	@Embedded
	public Name getName() {
		return name;
	}

	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	public Address getAddress() {
		return address;
	}

	@Temporal(TemporalType.DATE)
	public Date getBirthday() {
		return birthday;
	}

	@Column(precision=4, scale=3)
	public BigDecimal getGWA() {
		return GWA;
	}

	@Column(name="currently_employed", nullable=false)
	public Boolean getCurrentlyEmployed() {
		return currentlyEmployed;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="date_hired")
	public Date getDateHired() {
		return dateHired;
	}

	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy="person", fetch=FetchType.EAGER)
	@OrderBy
	public Set<Contact> getContacts() {
		return contacts;
	}

	@Fetch(FetchMode.SELECT)
	@ManyToMany(mappedBy="persons", fetch=FetchType.EAGER)
	@OrderBy
	public Set<Role> getRoles() {
		return roles;
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

	public void setCurrentlyEmployed(boolean currentlyEmployed) {
		this.currentlyEmployed = currentlyEmployed;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;		
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Person) {
			Person otherPerson = (Person) object; 
			return name.equals(otherPerson.name);
		}
		return false;
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