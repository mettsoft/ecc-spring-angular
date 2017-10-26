package com.ecc.servlets.service;

import java.util.List;

import com.ecc.servlets.dto.RoleDTO;
import com.ecc.servlets.dto.PersonDTO;
import com.ecc.servlets.dao.RoleDao;
import com.ecc.servlets.model.Role;
import com.ecc.servlets.model.Person;
import com.ecc.servlets.assembler.RoleAssembler;
import com.ecc.servlets.assembler.PersonAssembler;
import com.ecc.servlets.util.app.AssemblerUtils;
import com.ecc.servlets.util.validator.ValidationException;
import com.ecc.servlets.util.validator.ModelValidator;

public class RoleService extends AbstractService<Role, RoleDTO> {
	private static final Integer MAX_CHARACTERS = 20;

	private final RoleDao roleDao;
	private final ModelValidator validator;
	private final PersonAssembler personAssembler;

	public RoleService() {
		super(new RoleDao(), new RoleAssembler());
		roleDao = (RoleDao) dao;
		validator = ModelValidator.create();
		personAssembler = new PersonAssembler();
	}

	public void validate(RoleDTO role) throws ValidationException {
		validator.validate("NotEmpty", role.getName(), "Role name");
		validator.validate("MaxLength", role.getName(), MAX_CHARACTERS, "Role name");
	}

	public List<RoleDTO> list() {
		return AssemblerUtils.asList(roleDao.list(), assembler::createDTO);
	}
}