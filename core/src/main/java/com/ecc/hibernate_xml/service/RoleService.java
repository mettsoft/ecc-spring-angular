package com.ecc.hibernate_xml.service;

import java.io.Serializable;
import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.RoleDao;
import com.ecc.hibernate_xml.model.ModelException;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;

public class RoleService {
	private RoleDao roleDao;

	public RoleService() {
		roleDao = new RoleDao();
	}

	public List<Role> listRoles() {
		return roleDao.list();
	}

	public List<Role> listRoles(Person person) {
		return roleDao.list(person);
	}

	public List<Role> listRolesUnassignedTo(Person person) {
		return roleDao.listExcluding(person);
	}

	public Serializable createRole(Role role) throws DaoException {
		return roleDao.create(role);		
	}

	public void updateRole(Role role) throws DaoException {
		roleDao.update(role);
	}

	public void deleteRole(Integer roleId) throws DaoException {
		roleDao.delete(roleDao.get(roleId));
	}

	public Role getRole(Integer roleId) throws DaoException {
		return roleDao.get(roleId);
	}
}