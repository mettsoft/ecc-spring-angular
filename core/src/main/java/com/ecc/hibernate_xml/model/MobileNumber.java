package com.ecc.hibernate_xml.model;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Transient;

import javax.persistence.Cacheable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@DiscriminatorValue("MOBILE_NUMBER")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class MobileNumber extends Contact {
	@Transient
	@Override
	public String getContactType() {
		return "Mobile Number";
	}
}