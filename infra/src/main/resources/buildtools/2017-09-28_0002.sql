ALTER TABLE persons 
	DROP COLUMN street_number,
	DROP COLUMN barangay,
	DROP COLUMN municipality,
	DROP COLUMN zip_code,
	ADD COLUMN address_id INT NOT NULL,
	ADD CONSTRAINT address_foreign_key FOREIGN KEY (address_id) REFERENCES addresses (id);

CREATE TABLE addresses (
	id SERIAL NOT NULL,
	street_number VARCHAR(20) NOT NULL,
	barangay INT NOT NULL,
	municipality VARCHAR(50) NOT NULL,
	zip_code INT NOT NULL,
	PRIMARY KEY (id)
);