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
	password CHAR(60) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(username)
);

CREATE TABLE permissions (
	id SERIAL NOT NULL,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(name)
);

CREATE TABLE users_permissions (
	user_id INT NOT NULL REFERENCES users (id),
	permission_id INT NOT NULL REFERENCES permissions(id),
	PRIMARY KEY (user_id, permission_id)
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
	users (id, username, password)
VALUES 
	(1, 'admin', '$2a$10$pC4T1OslbWX7aMJWBkbKBOSURRgD6pF0rt2hgbYBwTKDvn6TTIhYi'),
	(2, 'user', '$2a$10$yr4i3jT.vYP/nApp0OQVD.DCpZpivrVh5Wx9NKD1NwRjU.u37If8q');

INSERT INTO 
	permissions (id, name)
VALUES 
	(1, 'ROLE_ADMIN'),
	(2, 'ROLE_CREATE_PERSON'),
	(3, 'ROLE_UPDATE_PERSON'),
	(4, 'ROLE_DELETE_PERSON'),
	(5, 'ROLE_CREATE_ROLE'),
	(6, 'ROLE_UPDATE_ROLE'),
	(7, 'ROLE_DELETE_ROLE');

INSERT INTO 
	users_permissions (user_id, permission_id)
VALUES 	
	(1, 1);

COMMIT;