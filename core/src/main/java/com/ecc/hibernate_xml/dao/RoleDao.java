package com.ecc.hibernate_xml.dao;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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

	public List<Role> listRolesExcluding(List<Role> rolesToExclude) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		String conditionStatement = rolesToExclude.isEmpty()? "": 
			String.format("WHERE id NOT IN (%s)", 
				rolesToExclude.stream()
					.map(role -> role.getId().toString())
					.collect(Collectors.joining(", ")));

		Query query = session.createQuery(
			String.format("FROM Role %s ORDER BY id", conditionStatement));
		List<Role> roles = query.list();
		session.close();
		return roles;
	}

	public Serializable createRole(Role role) throws DaoException {
		try {
			return TransactionScope.executeTransactionWithResult(session -> session.save(role));			
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public void updateRole(Role role) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.update(role));
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public void deleteRole(Role role) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.delete(role));
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