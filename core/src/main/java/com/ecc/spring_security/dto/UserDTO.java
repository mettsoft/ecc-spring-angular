package com.ecc.spring.dto;

import java.util.List;
import java.util.ArrayList;

public class UserDTO {
	private Integer id;
	private String username;	
	private String password;
	private List<String> permissions;
	
	public UserDTO() {
		permissions = new ArrayList<>();
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

	public List<String> getPermissions() {
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

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
}