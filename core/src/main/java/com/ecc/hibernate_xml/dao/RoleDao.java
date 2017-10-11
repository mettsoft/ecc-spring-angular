package com.ecc.hibernate_xml.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.HibernateUtility;

public class RoleDao extends AbstractDao<Role> {
	
	public RoleDao() {
		super(Role.class);
	} 

	@Override
	protected void onBeforeSave(Session session, Role role) {
		onBeforeUpdate(session, role);
	}

	@Override
	protected void onBeforeUpdate(Session session, Role role) {
		Role existingRole = get(role.getName());
		if (existingRole != null && existingRole.getId() != role.getId()) {
			throw new RuntimeException(String.format(
				"Role name \"%s\" is already existing.", role.getName()));
		}
	}

	@Override
	protected void onBeforeDelete(Session session, Role role) {
		if (role.getPersons().size() > 0) {
			String personIds = role.getPersons()
				.stream()
				.map(person -> person.getId().toString())
				.collect(Collectors.joining(", "));

			throw new RuntimeException(
				String.format("Role is in used by person IDs [%s].", personIds));
		}
	}

	private Role get(String name) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		Role role = (Role) session.createCriteria(Role.class)
			.add(Restrictions.eq("name", name))
			.uniqueResult();
		session.close();
		return role;
	}

	public List<Role> list(Person person) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<Role> roles = session.createCriteria(Role.class)
			.createAlias("persons", "P")
			.add(Restrictions.eq("P.id", person.getId()))
			.addOrder(Order.asc("id"))
			.list();
		session.close();
		return roles;
	}

	public List<Role> listExcluding(Person person) {
		List roleIds = list(person).stream()
			.map(role -> role.getId())
			.collect(Collectors.toList());

		Session session = HibernateUtility.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Role.class)
			.addOrder(Order.asc("id"));

		if (!roleIds.isEmpty()) {
			criteria.add(Restrictions.not(Restrictions.in("id", roleIds)));
		}

		List<Role> roles = criteria.list();
		session.close();
		return roles;
	}
}