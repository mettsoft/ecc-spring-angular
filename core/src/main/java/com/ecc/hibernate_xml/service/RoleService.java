package com.ecc.hibernate_xml.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.RoleDao;
import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.dto.PersonDTO;
import com.ecc.hibernate_xml.assembler.RoleAssembler;
import com.ecc.hibernate_xml.assembler.PersonAssembler;
import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class RoleService extends AbstractService<Role, RoleDTO> {
	private static final Integer MAX_CHARACTERS = 20;

	private final RoleDao roleDao;
	private final PersonDao personDao;
	private final ModelValidator validator;
	private final PersonAssembler personAssembler;

	public RoleService() {
		super(new RoleDao(), new RoleAssembler());
		roleDao = (RoleDao) dao;
		personDao = new PersonDao();
		validator = ModelValidator.create();
		personAssembler = new PersonAssembler();
	}

	public void validate(RoleDTO role) throws ValidationException {
		validator.validate("NotEmpty", role.getName(), "Role name");
		validator.validate("MaxLength", role.getName(), MAX_CHARACTERS, "Role name");
	}

	public List<RoleDTO> list(PersonDTO personDTO) {
		Person person = personAssembler.createModel(personDTO);
		return assembler.createDTO(roleDao.list(person));
	}

	public List<RoleDTO> listRolesNotBelongingTo(PersonDTO personDTO) {
		Person person = personAssembler.createModel(personDTO);
		return assembler.createDTO(roleDao.listRolesNotBelongingTo(person));
	}

	public void addRoleToPerson(Integer roleId, PersonDTO personDTO) throws DaoException {
		Person person = personAssembler.createModel(personDTO);
		Role role = roleDao.get(roleId);
		if (person.getRoles().add(role)) {
			personDao.update(person);
			personDTO.setRoles(person.getRoles().stream().map(assembler::createDTO).collect(Collectors.toList()));
		}
		else {
			throw new DaoException("Role not found!");
		}
	}

	public void removeRoleFromPerson(Integer roleId, PersonDTO personDTO) throws DaoException {
		Person person = personAssembler.createModel(personDTO);
		Role role = roleDao.get(roleId);
		if (person.getRoles().remove(role)) {
			personDao.update(person);
			personDTO.setRoles(person.getRoles().stream().map(assembler::createDTO).collect(Collectors.toList()));
		}
		else {
			throw new DaoException("Role not found!");
		}
	}
}