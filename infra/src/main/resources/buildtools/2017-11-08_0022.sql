-- DDL Commands
CREATE TABLE persons (
	id SERIAL NOT NULL,
	title VARCHAR(20),
	last_name VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	middle_name VARCHAR(20) NOT NULL,
	suffix VARCHAR(20),
	street_number VARCHAR(20) NOT NULL,
	barangay VARCHAR(50) NOT NULL,
	municipality VARCHAR(50) NOT NULL,
	zip_code INT NOT NULL,
	birthday DATE,
	GWA DECIMAL(4, 3),
	currently_employed BOOLEAN NOT NULL,
	date_hired DATE,
	PRIMARY KEY(id)
);

CREATE TABLE roles (
	id SERIAL NOT NULL,
	name VARCHAR(20) NOT NULL,
	PRIMARY KEY (id)
);

CREATE UNIQUE INDEX role_name_unique_idx on roles (LOWER(name)); 

CREATE TABLE persons_roles (
	person_id INT NOT NULL REFERENCES persons (id),
	role_id INT NOT NULL REFERENCES roles (id),
	PRIMARY KEY (person_id, role_id)
);

CREATE TABLE contacts (
	id SERIAL NOT NULL,
	contact_type VARCHAR(20) NOT NULL,
	data VARCHAR(50) NOT NULL,
	person_id INT REFERENCES persons(id),
	PRIMARY KEY (id),
	UNIQUE(contact_type, data, person_id)
);

CREATE TABLE users (
	id SERIAL NOT NULL,
	username VARCHAR(255) NOT NULL,
	password CHAR(64) NOT NULL,
	permissions INT  DEFAULT(0),
	PRIMARY KEY(id),
	UNIQUE(username)
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
	persons (last_name, first_name, middle_name, street_number, barangay, municipality, zip_code, currently_employed, date_hired, GWA, birthday)
VALUES 
	('Dela Cruz', 'Juan', 'Tamad', '1408', '317', 'Manila', 1003, true, '2017-08-24', 1, '1993-07-07'),
	('Young', 'Emmett', 'Ngan', '1410', '317', 'Manila', 1003, true, '2017-07-31', 5, '1992-08-04');

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

INSERT INTO 
	users (username, password, permissions)
VALUES 
	('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', null),
	('user', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 0);

COMMIT;