package com.ecc.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

import com.ecc.spring.dto.RoleDTO;
import com.ecc.spring.model.Role;
import com.ecc.spring.dao.RoleDao;
import com.ecc.spring.util.AssemblerUtils;
import com.ecc.spring.util.ValidationUtils;
import com.ecc.spring.util.ValidationException;

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

	@Override
	protected RuntimeException onCreateFailure(Role role, RuntimeException cause) {
		role.setId(null);
		return onUpdateFailure(role, cause);
	}

	@Override
	protected RuntimeException onUpdateFailure(Role role, RuntimeException cause) {
		if (cause instanceof DataIntegrityViolationException) {
			return new ValidationException("role.validation.message.duplicateEntry", createDTO(role), role.getName());
		}
		return super.onUpdateFailure(role, cause);
	}

	@Override
	protected RuntimeException onDeleteFailure(Role role, RuntimeException cause) {
		if (cause instanceof DataIntegrityViolationException && role.getPersons().size() > 0) {
			String personNames = role.getPersons()
				.stream()
				.map(person -> person.getName().toString())
				.collect(Collectors.joining("; "));

			return new ValidationException("role.validation.message.inUsed", createDTO(role), personNames);
		}
		return super.onDeleteFailure(role, cause);
	}

	@Override
	protected RuntimeException onGetFailure(Integer id, RuntimeException cause) {
		if (cause instanceof DataRetrievalFailureException) {
			return new ValidationException("role.validation.message.notFound", new RoleDTO(), id);		
		}
		return super.onGetFailure(id, cause);
	}
}