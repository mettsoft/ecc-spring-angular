package com.ecc.spring_xml.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;

@Entity
@Table(name="users")
public class User {
	@Id @GeneratedValue(generator="UserIdGenerator")
	@SequenceGenerator(name="UserIdGenerator", sequenceName="users_id_seq")
	@Column(nullable=false)
	private Integer id;

	@Column(unique=true, nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column
	private Integer permissions;

	public User() {
		permissions = 0;
	}

	public Integer getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Integer getPermissions() {
		return permissions;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPermissions(Integer permissions) {
		this.permissions = permissions;
	}
}