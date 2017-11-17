package com.ecc.spring.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;

@Entity
@Table(name="permissions")
public class Permission {
	@Id @GeneratedValue(generator="PermissionIdGenerator")
	@SequenceGenerator(name="PermissionIdGenerator", sequenceName="permissions_id_seq")
	@Column(nullable=false)
	private Integer id;

	@Column(unique=true, nullable=false)
	private String name;

	public Permission() {}

	public Permission(String name) {
		setName(name);
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Permission) {
			Permission other = (Permission) object; 
			return name.equals(other.name);
		}
		return false;
	}
}