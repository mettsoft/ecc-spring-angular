package com.ecc.spring_security.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	name="contacts",
	uniqueConstraints=
		@UniqueConstraint(columnNames={"contact_type", "data", "person_id"})
)
public class Contact {
	@Id @GeneratedValue(generator="ContactIdGenerator")
	@SequenceGenerator(name="ContactIdGenerator", sequenceName="contacts_id_seq")
	@Column(nullable=false)
	private Integer id;

	@Column(nullable=false, length=50)
	private String data;

	@Column(name="contact_type", length=20, nullable=false)
	private String contactType;

	public Integer getId() {
		return id;
	}

	public String getData() {
		return data;
	}
	
	public String getContactType() {
		return contactType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	@Override
	public int hashCode() {
		if (data == null || contactType == null) {
			return super.hashCode();
		}
		return data.hashCode() << 4 + contactType.hashCode() << 2;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Contact) {
			Contact otherContact = (Contact) object; 
			return data.equals(otherContact.data) && contactType.equals(otherContact.contactType);
		}
		return false;
	}
}