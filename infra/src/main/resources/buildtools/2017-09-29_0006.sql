CREATE TABLE contacts (
	id SERIAL NOT NULL,
	contact_type VARCHAR(20) NOT NULL,
	data VARCHAR(50) NOT NULL,
	PRIMARY KEY (id)
);

CREATE INDEX contacts_contact_type ON contacts (contact_type);