package com.ecc.spring_xml.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import com.ecc.spring_xml.model.Role;

public class RoleDao extends AbstractDao<Role> {
	public RoleDao(SessionFactory sessionFactory) {
		super(Role.class, sessionFactory);
	}

	public List<Role> list() {
		return sessionFactory.getCurrentSession()
			.createCriteria(Role.class)
			.setCacheable(true)
			.addOrder(Order.asc("id"))
			.list();
	}

	@Override
	protected RuntimeException onCreateFailure(Role role, Exception cause) {
		return onUpdateFailure(role, cause);
	}

	@Override
	protected RuntimeException onUpdateFailure(Role role, Exception cause) {
		if (cause instanceof ConstraintViolationException) {
			return new RuntimeException(String.format(
				"Role name \"%s\" is already existing.", role.getName()));
		}
		return super.onUpdateFailure(role, cause);
	}

	@Override
	protected RuntimeException onDeleteFailure(Role role, Exception cause) {
		if (cause instanceof ConstraintViolationException && role.getPersons().size() > 0) {
			String personNames = role.getPersons()
				.stream()
				.map(person -> person.getName().toString())
				.collect(Collectors.joining("; "));

			return new RuntimeException(
				String.format("Role is in used by persons [%s].", personNames));
		}
		return super.onDeleteFailure(role, cause);
	}
}