package com.ecc.spring_xml.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Temporal;
import javax.persistence.OrderBy;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.TemporalType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;

@Entity
@Table(name="persons")
public class Person {
	@Id @GeneratedValue(generator="PersonIdGenerator")
	@SequenceGenerator(name="PersonIdGenerator", sequenceName="persons_id_seq")
	@Column(nullable=false)
	private Integer id;

	@Embedded
	private Name name;

	@Embedded
	private Address address;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(precision=4, scale=3)
	private BigDecimal GWA;

	@Column(name="currently_employed", nullable=false)
	private Boolean currentlyEmployed;

	@Temporal(TemporalType.DATE)
	@Column(name="date_hired")
	private Date dateHired;

	@Fetch(FetchMode.SELECT)
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name="person_id")
	@OrderBy
	private Set<Contact> contacts;

	@Fetch(FetchMode.SELECT)
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		joinColumns=@JoinColumn(name="person_id"),
		inverseJoinColumns=@JoinColumn(name="role_id"))
	@OrderBy
	private Set<Role> roles;

	public Person() {
		setCurrentlyEmployed(false);
		setContacts(new HashSet<>());
		setRoles(new HashSet<>());
	}
	
	public Person(Integer id, Name name) {
		this();
		setId(id);
		setName(name);		
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

	public Set<Contact> getContacts() {
		return contacts;
	}

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
}