CREATE TABLE persons (
	id SERIAL NOT NULL,
	title VARCHAR(20),
	last_name VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	middle_name VARCHAR(20) NOT NULL,
	suffix VARCHAR(20),
	street_number VARCHAR(20) NOT NULL,
	barangay INT NOT NULL,
	municipality VARCHAR(50) NOT NULL,
	zip_code INT NOT NULL,
	birthday DATE NOT NULL,
	GWA DECIMAL(3) NOT NULL,
	currently_employed BOOLEAN NOT NULL,
	date_hired DATE NULL,
	PRIMARY KEY(id)
);

CREATE TABLE addresses (
	id SERIAL NOT NULL,
	street_number VARCHAR(20) NOT NULL,
	barangay INT NOT NULL,
	municipality VARCHAR(50) NOT NULL,
	zip_code INT NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE persons 
	DROP COLUMN street_number,
	DROP COLUMN barangay,
	DROP COLUMN municipality,
	DROP COLUMN zip_code,
	ADD COLUMN address_id INT NOT NULL,
	ADD CONSTRAINT address_foreign_key FOREIGN KEY (address_id) REFERENCES addresses (id);

ALTER TABLE persons 
	ALTER COLUMN GWA TYPE DECIMAL(4, 3);

	CREATE TABLE roles (
	id SERIAL NOT NULL,
	name VARCHAR(20) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE persons_roles (
	person_id INT NOT NULL REFERENCES persons (id),
	role_id INT NOT NULL REFERENCES roles (id),
	PRIMARY KEY (person_id, role_id)
);

CREATE TABLE contacts (
	id SERIAL NOT NULL,
	contact_type VARCHAR(20) NOT NULL,
	data VARCHAR(50) NOT NULL,
	PRIMARY KEY (id)
);

CREATE INDEX contacts_contact_type ON contacts (contact_type);

ALTER TABLE contacts 
	ADD COLUMN person_id INT NOT NULL REFERENCES persons (id);

ALTER TABLE persons 
	ALTER COLUMN address_id DROP NOT NULL,
	ALTER COLUMN birthday DROP NOT NULL,
	ALTER COLUMN GWA DROP NOT NULL,
	ALTER COLUMN currently_employed DROP NOT NULL;

ALTER TABLE persons 
	ALTER COLUMN currently_employed SET NOT NULL;

ALTER TABLE roles ADD CONSTRAINT role_unique_key UNIQUE (name);