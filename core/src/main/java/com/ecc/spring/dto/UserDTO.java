package com.ecc.spring.dto;

import java.util.List;
import java.util.ArrayList;

public class UserDTO {
	private Integer id;
	private String username;	
	private String password;
	private Boolean allowEmptyPassword;
	private List<String> permissions;
	
	public UserDTO() {
		permissions = new ArrayList<>();
		allowEmptyPassword = false;
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

	public Boolean getAllowEmptyPassword() {
		return allowEmptyPassword;
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

	public void setAllowEmptyPassword(Boolean allowEmptyPassword) {
		this.allowEmptyPassword = allowEmptyPassword;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
}