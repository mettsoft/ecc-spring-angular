package com.ecc.hibernate_xml.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;

import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.TransactionScope;
import com.ecc.hibernate_xml.util.HibernateUtility;

public class RoleDao {

	public List<Role> listRoles() {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<Role> roles = session.createQuery("FROM Role ORDER BY id").list();
		session.close();
		return roles;
	}

	public List<Role> listRoles(Person person) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		Query query = session.createQuery("SELECT R FROM Person P JOIN P.roles R WHERE P.id = :id ORDER BY R.id");
		query.setParameter("id", person.getId());
		List<Role> roles = query.list();
		session.close();
		return roles;
	}

	public void createRole(Role newRole) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.save(newRole));			
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public void updateRole(Integer roleId, Role newRole) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> {
				newRole.setId(roleId);
				session.update(newRole);
			});
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public void deleteRole(Integer roleId) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> 
				session.delete(session.get(Role.class, roleId)));
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public Role getRole(Integer roleId) throws DaoException {
		Session session = HibernateUtility.getSessionFactory().openSession();
		Role role = (Role) session.get(Role.class, roleId);
		session.close();
		if (role == null) {
			throw new DaoException("Role not found!");
		}
		return role;
	}
}