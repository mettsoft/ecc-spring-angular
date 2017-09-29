ALTER TABLE contacts 
	ADD COLUMN person_id INT NOT NULL REFERENCES persons (id);