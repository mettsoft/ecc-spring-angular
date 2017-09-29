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