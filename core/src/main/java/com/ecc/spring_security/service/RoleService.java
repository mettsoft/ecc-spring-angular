package com.ecc.spring_security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

import com.ecc.spring_security.dto.RoleDTO;
import com.ecc.spring_security.model.Role;
import com.ecc.spring_security.dao.RoleDao;
import com.ecc.spring_security.assembler.RoleAssembler;
import com.ecc.spring_security.util.AssemblerUtils;
import com.ecc.spring_security.util.ValidationUtils;
import com.ecc.spring_security.util.ValidationException;

@Component("roleService")
public class RoleService extends AbstractService<Role, RoleDTO> implements Validator {
	private static final Integer MAX_CHARACTERS = 20;

	private final RoleDao roleDao;
	private final RoleAssembler roleAssembler;

	public RoleService(RoleDao roleDao, RoleAssembler roleAssembler) {
		super(roleDao, roleAssembler);
		this.roleDao = roleDao;
		this.roleAssembler = roleAssembler;
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
		return AssemblerUtils.asList(roleDao.list(), roleAssembler::createDTO);
	}

	@Override
	protected RuntimeException onCreateFailure(Role role, RuntimeException cause) {
		role.setId(null);
		return onUpdateFailure(role, cause);
	}

	@Override
	protected RuntimeException onUpdateFailure(Role role, RuntimeException cause) {
		if (cause instanceof DataIntegrityViolationException) {
			return new ValidationException("role.validation.message.duplicateEntry", roleAssembler.createDTO(role), role.getName());
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

			return new ValidationException("role.validation.message.inUsed", roleAssembler.createDTO(role), personNames);
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