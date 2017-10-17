package com.ecc.hibernate_xml.model;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("LANDLINE")
public class Landline extends Contact {}