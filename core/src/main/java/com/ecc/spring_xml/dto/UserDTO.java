package com.ecc.spring_xml.dto;

import java.util.List;

public class UserDTO {
	private Integer id;
	private String username;	
	private String password;
	private List<Integer> permissions;
	
	public Integer getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<Integer> getPermissions() {
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

	public void setPermissions(List<Integer> permissions) {
		this.permissions = permissions;
	}
}