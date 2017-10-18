-- DDL Commands
CREATE TABLE addresses (
	id SERIAL NOT NULL,
	street_number VARCHAR(20) NOT NULL,
	barangay INT NOT NULL,
	municipality VARCHAR(50) NOT NULL,
	zip_code INT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE persons (
	id SERIAL NOT NULL,
	title VARCHAR(20),
	last_name VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	middle_name VARCHAR(20) NOT NULL,
	suffix VARCHAR(20),
	address_id INT REFERENCES addresses(id),
	birthday DATE,
	GWA DECIMAL(4, 3),
	currently_employed BOOLEAN NOT NULL,
	date_hired DATE,
	PRIMARY KEY(id)
);

CREATE TABLE roles (
	id SERIAL NOT NULL,
	name VARCHAR(20) NOT NULL UNIQUE,
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
	person_id INT NOT NULL REFERENCES persons(id),
	PRIMARY KEY (id),
	UNIQUE(contact_type, data, person_id)
);

CREATE INDEX contacts_contact_type ON contacts (contact_type);

-- DML Commands
START TRANSACTION;

INSERT INTO 
	roles (name) 
VALUES 
	('Software Engineer'),
	('QA');

INSERT INTO 
	addresses (street_number, barangay, municipality, zip_code)
VALUES 
	('1410', 317, 'Manila', 1003),
	('1408', 317, 'Manila', 1003);

INSERT INTO 
	persons (last_name, first_name, middle_name, address_id, currently_employed, date_hired, GWA)
VALUES 
	('Young', 'Emmett', 'Ngan', 1, true, '2017-07-31', 5),
	('Dela Cruz', 'Juan', 'Tamad', 2, true, '2017-08-24', 1);

INSERT INTO 
	persons_roles (person_id, role_id)
VALUES 
	(1, 1),
	(2, 2);

INSERT INTO 
	contacts (contact_type, data, person_id)
VALUES 
	('Mobile', '09955886334', 1),
	('Email', 'emmettyoung92@gmail.com', 1),
	('Landline', '5015036', 1),
	('Email', 'juandelacruz@yahoo.com', 2);

COMMIT;