package com.ecc.hibernate_xml.service;

import java.util.List;

import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.dto.PersonDTO;
import com.ecc.hibernate_xml.dao.RoleDao;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.assembler.RoleAssembler;
import com.ecc.hibernate_xml.assembler.PersonAssembler;
import com.ecc.hibernate_xml.util.app.AssemblerUtils;
import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

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

	public List<RoleDTO> list(PersonDTO personDTO) {
		Person person = personAssembler.createModel(personDTO);
		return AssemblerUtils.asList(roleDao.list(person), assembler::createDTO);
	}
}