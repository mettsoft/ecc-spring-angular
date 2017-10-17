package com.ecc.hibernate_xml.model;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("MOBILE_NUMBER")
public class MobileNumber extends Contact {
	@Transient
	@Override
	public String getContactType() {
		return "Mobile Number";
	}
}