package com.ecc.spring_xml.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;

import com.ecc.spring_xml.dto.RoleDTO;
import com.ecc.spring_xml.model.Role;
import com.ecc.spring_xml.dao.RoleDao;
import com.ecc.spring_xml.assembler.RoleAssembler;
import com.ecc.spring_xml.util.app.AssemblerUtils;
import com.ecc.spring_xml.util.validator.ValidationException;
import com.ecc.spring_xml.util.validator.ModelValidator;

public class RoleService extends AbstractService<Role, RoleDTO> {
	private static final Integer MAX_CHARACTERS = 20;

	private final RoleDao roleDao;
	private final RoleAssembler roleAssembler;
	private final ModelValidator validator;

	public RoleService(RoleDao roleDao, RoleAssembler roleAssembler, ModelValidator validator) {
		super(roleDao, roleAssembler);
		this.roleDao = roleDao;
		this.roleAssembler = roleAssembler;
		this.validator = validator;
	}

	public void validate(RoleDTO role) throws ValidationException {
		validator.validate("NotEmpty", role.getName(), "Role name");
		validator.validate("MaxLength", role.getName(), MAX_CHARACTERS, "Role name");
	}

	public List<RoleDTO> list() {
		return AssemblerUtils.asList(roleDao.list(), roleAssembler::createDTO);
	}

	@Override
	protected RuntimeException onCreateFailure(Role role, RuntimeException cause) {
		return onUpdateFailure(role, cause);
	}

	@Override
	protected RuntimeException onUpdateFailure(Role role, RuntimeException cause) {
		if (cause instanceof DataIntegrityViolationException) {
			return new RuntimeException(String.format(
				"Role name \"%s\" is already existing.", role.getName()));
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

			return new RuntimeException(
				String.format("Role is in used by persons [%s].", personNames));
		}
		return super.onDeleteFailure(role, cause);
	}
}