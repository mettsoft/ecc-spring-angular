package com.ecc.hibernate_xml.model;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("EMAIL")
public class Email extends Contact {}