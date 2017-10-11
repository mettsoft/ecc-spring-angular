package com.ecc.hibernate_xml.service;

import java.io.Serializable;
import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.RoleDao;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;

public class RoleService extends AbstractService<Role> {
	private RoleDao roleDao;

	public RoleService() {
		super(new RoleDao());
		roleDao = (RoleDao) dao;
	}

	public List<Role> list(Person person) {
		return roleDao.list(person);
	}

	public List<Role> listRolesNotBelongingTo(Person person) {
		return roleDao.listRolesNotBelongingTo(person);
	}

	public void addRoleToPerson(Integer roleId, Person person) throws DaoException {
		Role role = roleDao.get(roleId);
		role.getPersons().add(person);
		person.getRoles().add(role);
		roleDao.update(role);
	}

	public void removeRoleFromPerson(Integer roleId, Person person) throws DaoException {
		Role role = roleDao.get(roleId);
		role.getPersons().remove(person);
		person.getRoles().remove(role);
		roleDao.update(role);
	}
}