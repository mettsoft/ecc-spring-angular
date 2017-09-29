CREATE TABLE persons_roles (
	person_id INT NOT NULL REFERENCES persons (id),
	role_id INT NOT NULL REFERENCES roles (id),
	PRIMARY KEY (person_id, role_id)
);