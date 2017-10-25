package com.ecc.hibernate_xml.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ecc.hibernate_xml.model.Person;

@Entity
@Table(name="roles")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Role {
	private Integer id;
	private String name;
	private Set<Person> persons;

	public Role() {}

	public Role(Integer id, String name) {
		setId(id);
		setName(name);
	}

	@Id @GeneratedValue(generator="RoleIdGenerator")
	@SequenceGenerator(name="RoleIdGenerator", sequenceName="roles_id_seq")
	@Column(nullable=false)
	public Integer getId() {
		return id;
	}
	
	@Column(unique=true, nullable=false, length=20)
	public String getName() {
		return name;
	}

	@Fetch(FetchMode.SELECT)
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="roles")
	@OrderBy
	public Set<Person> getPersons() {
		return persons;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Role) {
			Role otherRole = (Role) object; 
			return name.equals(otherRole.name);
		}
		return false;
	}
}