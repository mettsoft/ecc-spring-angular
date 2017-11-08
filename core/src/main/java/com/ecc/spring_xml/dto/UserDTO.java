package com.ecc.spring_xml.dto;

public class UserDTO {
	private Integer id;
	private String username;	
	private String password;
	private Integer permissions;

	public UserDTO() {
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