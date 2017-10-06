package com.ecc.hibernate_xml.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Query;

import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.HibernateUtility;

public class RoleDao extends AbstractDao<Role> {
	
	public RoleDao() {
		super(Role.class);
	} 

	@Override
	protected void onBeforeSave(Session session, Role role) {
		if (get(session, role.getName()) != null) {
			throw new RuntimeException(String.format(
				"Role name \"%s\" is already existing.", role.getName()));
		}
	}

	private Role get(Session session, String name) {
		Query query = session.createQuery("FROM Role WHERE name = :name");
		query.setParameter("name", name);
		Role role = (Role) query.uniqueResult();
		return role;
	}

	public List<Role> list(Person person) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		Query query = session.createQuery("SELECT R FROM Person P JOIN P.roles R WHERE P.id = :id ORDER BY R.id");
		query.setParameter("id", person.getId());
		List<Role> roles = query.list();
		session.close();
		return roles;
	}

	public List<Role> listExcluding(List<Role> rolesToExclude) {
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
}