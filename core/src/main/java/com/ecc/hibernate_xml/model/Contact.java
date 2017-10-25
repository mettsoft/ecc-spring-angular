package com.ecc.hibernate_xml.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	name="contacts",
	uniqueConstraints=
		@UniqueConstraint(columnNames={"contact_type", "data", "person_id"})
)
public class Contact {
	private Integer id;
	private String data;
	private String contactType;
	private Person person;

	@Id @GeneratedValue(generator="ContactIdGenerator")
	@SequenceGenerator(name="ContactIdGenerator", sequenceName="contacts_id_seq")
	@Column(nullable=false)
	public Integer getId() {
		return id;
	}

	@Column(nullable=false, length=50)
	public String getData() {
		return data;
	}

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	public Person getPerson() {
		return person;
	}
	
	@Column(name="contact_type", length=20, nullable=false)
	public String getContactType() {
		return contactType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	@Override
	public int hashCode() {
		if (data == null || contactType == null || person == null) {
			return super.hashCode();
		}
		return data.hashCode() << 4 + contactType.hashCode() << 2 + person.getName().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Contact) {
			Contact otherContact = (Contact) object; 
			return data.equals(otherContact.data) && contactType.equals(otherContact.contactType) && 
				person.getName().equals(otherContact.person.getName());
		}
		return false;
	}
}