package com.ecc.spring_xml.dao;

import java.util.List;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.apache.commons.lang3.StringUtils;

import com.ecc.spring_xml.model.Person;
import com.ecc.spring_xml.util.dao.TransactionScope;

public class PersonDao extends AbstractDao<Person> {
	public PersonDao() {
		super(Person.class);
	}

	public List<Person> list(String lastName, Integer roleId, Date birthday, String orderBy, String order) {
		return TransactionScope.executeTransactionWithResult(session -> {
			String orderColumn = StringUtils.isEmpty(orderBy)? "id": orderBy;
			Criteria criteria = session.createCriteria(Person.class)
				.addOrder("DESC".equals(order)? Order.desc(orderColumn): Order.asc(orderColumn));

			if (!StringUtils.isEmpty(lastName)) {
				criteria.add(Restrictions.ilike("name.lastName", "%" + lastName + "%"));
			}

			if (roleId != null) {
				criteria.createAlias("roles", "R").add(Restrictions.eq("R.id", roleId));
			}

			if (birthday != null) {
				criteria.add(Restrictions.eq("birthday", birthday));
			}

			return criteria.list();
		});
	}

	@Override
	public void update(Person person) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> {
				Person persistentPerson = (Person) session.get(Person.class, person.getId());
				persistentPerson.getContacts().clear();
				session.flush();
				session.evict(persistentPerson);
				session.update(person);
			});			
		}
		catch (Exception cause) {
			throw new DaoException(onUpdateFailure(person, cause));
		}
	}
}