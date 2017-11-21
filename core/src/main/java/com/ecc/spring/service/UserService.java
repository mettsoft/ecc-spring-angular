package com.ecc.spring.service;

import java.io.Serializable;
import java.util.function.Function;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

import com.ecc.spring.dao.UserDao;
import com.ecc.spring.dao.PermissionDao;
import com.ecc.spring.dto.UserDTO;
import com.ecc.spring.model.Permission;
import com.ecc.spring.model.User;
import com.ecc.spring.util.AssemblerUtils;
import com.ecc.spring.util.ValidationUtils;

@Service
public class UserService extends AbstractService<User, UserDTO> implements Validator, UserDetailsService {
	private static final Integer MAX_CHARACTERS = 255;

	private final UserDao userDao;
	private List<Permission> PERMISSIONS;
	private Map<String, Permission> PERMISSIONS_HASH;

	@Autowired
	private PermissionDao permissionDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserService(UserDao userDao) {
		super(userDao);
		this.userDao = userDao;
	}

	@Override
	public boolean supports(Class clazz) {
    return clazz.isAssignableFrom(UserDTO.class);
  }

  @Override
  public void validate(Object command, Errors errors) {
  	UserDTO user = (UserDTO) command;
		ValidationUtils.testNotEmpty(user.getUsername(), "username", errors, "localize:user.data.column.username");
		ValidationUtils.testMaxLength(user.getUsername(), "username", errors, MAX_CHARACTERS, "localize:user.data.column.username");
  }

	@Transactional
	@Override
	public Serializable create(UserDTO user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return super.create(user);
	}

	@Transactional
	@Override
	public void update(UserDTO user) {
		UserDTO originalUser = get(user.getId());
		if (!StringUtils.isEmpty(user.getPassword())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		else {
			user.setPassword(originalUser.getPassword());
		}
		super.update(user);
	}

	@Transactional
	public List<UserDTO> list() {
		return AssemblerUtils.asList(userDao.list(), this::createDTO);
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
			.map(t -> getPermissionHash().get(t))
			.collect(Collectors.toSet()));
		return model;
	}

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userDao.get(username);

		return user == null? null: 
			new org.springframework.security.core.userdetails.User(username, user.getPassword(), getAuthorities(user.getPermissions()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Permission> permissions) {
		if (permissions.contains(new Permission("ROLE_ADMIN"))) {
			permissions = getPermissions();
		}

		return permissions.stream()
			.map(t -> new SimpleGrantedAuthority(t.getName()))
			.collect(Collectors.toList());
	}

	@Transactional
	private List<Permission> getPermissions() {
		if (PERMISSIONS == null) {
			PERMISSIONS = permissionDao.list();
		}
		return PERMISSIONS;
	}

	@Transactional
	private Map<String, Permission> getPermissionHash() {
		if (PERMISSIONS_HASH == null) {
			PERMISSIONS_HASH = permissionDao.list().stream().collect(Collectors.toMap(t -> t.getName(), Function.identity()));
		}
		return PERMISSIONS_HASH;
	}
}