package com.ecc.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

import com.ecc.spring.dto.RoleDTO;
import com.ecc.spring.model.Role;
import com.ecc.spring.dao.RoleDao;
import com.ecc.spring.util.AssemblerUtils;
import com.ecc.spring.util.ValidationUtils;

@Service
public class RoleService extends AbstractService<Role, RoleDTO> implements Validator {
	private static final Integer MAX_CHARACTERS = 20;

	private final RoleDao roleDao;

	@Autowired 
	private PersonService personService;

	public RoleService(RoleDao roleDao) {
		super(roleDao);
		this.roleDao = roleDao;
	}

	@Override
	public boolean supports(Class clazz) {
    return clazz.isAssignableFrom(RoleDTO.class);
  }

  @Override
  public void validate(Object command, Errors errors) {
  	RoleDTO role = (RoleDTO) command;
		ValidationUtils.testNotEmpty(role.getName(), "name", errors, "localize:role.form.label.name");
		ValidationUtils.testMaxLength(role.getName(), "name", errors, MAX_CHARACTERS, "localize:role.form.label.name");
  }

	@Transactional
	public List<RoleDTO> list() {
		return AssemblerUtils.asList(roleDao.list(), this::createDTO);
	}

	@Override
	public RoleDTO createDTO(Role model) {
		if (model == null) {
			return null;
		}
		RoleDTO dto = new RoleDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setPersons(AssemblerUtils.asList(model.getPersons(), personService::createBasicDTO));
		return dto;
	}

	@Override 
	public Role createModel(RoleDTO dto) {
		if (dto == null) {
			return null;
		}
		Role model = new Role();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setPersons(AssemblerUtils.asSet(dto.getPersons(), personService::createBasicModel));
		return model;
	}
}