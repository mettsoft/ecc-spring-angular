package com.ecc.hibernate_xml.service;

import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.RoleDao;
import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.dto.RoleAssembler;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class RoleService extends AbstractService<Role, RoleDTO> {
	private static final Integer MAX_CHARACTERS = 20;

	private final RoleDao roleDao;
	private final PersonDao personDao;
	private final ModelValidator validator;

	public RoleService() {
		super(new RoleDao(), new RoleAssembler());
		roleDao = (RoleDao) dao;
		personDao = new PersonDao();
		validator = ModelValidator.create();
	}

	public String validateName(String roleName) throws ValidationException {
		validator.validate("NotEmpty", roleName, "Role name");
		validator.validate("MaxLength", roleName, MAX_CHARACTERS, "Role name");
		return roleName;
	}

	public List<RoleDTO> list(Person person) {
		return assembler.createDTO(roleDao.list(person));
	}

	public List<RoleDTO> listRolesNotBelongingTo(Person person) {
		return assembler.createDTO(roleDao.listRolesNotBelongingTo(person));
	}

	public void addRoleToPerson(Integer roleId, Person person) throws DaoException {
		Role role = roleDao.get(roleId);
		role.getPersons().add(person);
		if (person.getRoles().add(role)) {
			personDao.update(person);
		}
		else {
			throw new DaoException("Role not found!");
		}
	}

	public void removeRoleFromPerson(Integer roleId, Person person) throws DaoException {
		Role role = roleDao.get(roleId);
		role.getPersons().remove(person);
		if (person.getRoles().remove(role)) {
			personDao.update(person);
		}
		else {
			throw new DaoException("Role not found!");
		}
	}
}