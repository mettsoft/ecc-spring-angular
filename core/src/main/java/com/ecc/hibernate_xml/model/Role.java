package com.ecc.hibernate_xml.model;

public class Role {
	private static final Integer MAX_CHARACTERS = 20;

	private Integer id;
	private String name;

	private Role() {

	}

	public Role(String name) throws ModelException {
		setName(name);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) throws ModelException {
		if (name == null || name.trim().isEmpty()) {
			throw new ModelException("Role name cannot be empty.");
		}
		else if (name.length() > MAX_CHARACTERS) {
			throw new ModelException(String.format("Role name must not exceed %d characters.", 
				MAX_CHARACTERS));
		}
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return id.hashCode() << 4 + name.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Role) {
			Role otherRole = (Role) object;
			return otherRole.id.equals(id) && otherRole.name.equals(name);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("[ID=%d] %s", id, name);
	}
}