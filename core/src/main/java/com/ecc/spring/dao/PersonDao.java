package com.ecc.spring.dao;

import java.util.List;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.ecc.spring.model.Person;

@Repository
public class PersonDao extends AbstractDao<Person> {
	public PersonDao(SessionFactory sessionFactory) {
		super(Person.class, sessionFactory);
	}

	public List<Person> list(String lastName, Integer roleId, Date birthday, String orderBy, String order) {
		String orderColumn = StringUtils.isEmpty(orderBy)? "id": orderBy;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class)
			.addOrder("DESC".equals(order)? Order.desc(orderColumn): Order.asc(orderColumn));

		if (!StringUtils.isEmpty(lastName)) {
			criteria.add(Restrictions.ilike("name.lastName", "%" + lastName + "%"));
		}

		if (roleId != null) {
			criteria.createAlias("roles", "R").add(Restrictions.eq("R.id", roleId));
		}

		if (birthday != null) {
			criteria.add(Restrictions.between("birthday", birthday, new Date(birthday.getTime() + 24*60*60*1000)));
		}

		return criteria.list();	
	}
}