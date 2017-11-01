package com.ecc.spring_xml.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
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
	protected Throwable onCreateFailure(Role role, Throwable cause) {
		return onUpdateFailure(role, cause);
	}

	@Override
	protected Throwable onUpdateFailure(Role role, Throwable cause) {
		if (cause instanceof ConstraintViolationException) {
			return new DataAccessException(String.format(
				"Role name \"%s\" is already existing.", role.getName()));
		}
		return cause;
	}

	@Override
	protected Throwable onDeleteFailure(Role role, Throwable cause) {
		if (cause instanceof ConstraintViolationException && role.getPersons().size() > 0) {
			String personNames = role.getPersons()
				.stream()
				.map(person -> person.getName().toString())
				.collect(Collectors.joining("; "));

			return new DataAccessException(
				String.format("Role is in used by persons [%s].", personNames));
		}
		return cause;
	}
}