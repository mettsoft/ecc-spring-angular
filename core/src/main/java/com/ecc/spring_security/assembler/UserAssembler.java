package com.ecc.spring_security.assembler;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecc.spring_security.dao.PermissionDao;
import com.ecc.spring_security.dto.UserDTO;
import com.ecc.spring_security.model.User;
import com.ecc.spring_security.model.Permission;

@Component
public class UserAssembler implements Assembler<User, UserDTO> {
	@Autowired
	private PermissionDao permissionDao;
	private Map<String, Permission> PERMISSIONS;

	@PostConstruct
	public void init() {
		PERMISSIONS = permissionDao.list().stream().collect(Collectors.toMap(t -> t.getName(), Function.identity()));
	}

	@Override
	public UserDTO createDTO(User model) {
		if (model == null) {
			return null;
		}
		UserDTO dto = new UserDTO();
		dto.setId(model.getId());
		dto.setUsername(model.getUsername());
		dto.setPassword(model.getPassword());
		dto.setPermissions(model.getPermissions().stream()
			.map(t -> t.getName())
			.collect(Collectors.toList()));
		return dto;
	}

	@Override 
	public User createModel(UserDTO dto) {
		if (dto == null) {
			return null;
		}
		User model = new User();
		model.setId(dto.getId());
		model.setUsername(dto.getUsername());
		model.setPassword(dto.getPassword());
		model.setPermissions(dto.getPermissions().stream()
			.map(t -> PERMISSIONS.get(t))
			.collect(Collectors.toSet()));
		return model;
	}
}