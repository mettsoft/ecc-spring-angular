package com.ecc.spring_xml.model;

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