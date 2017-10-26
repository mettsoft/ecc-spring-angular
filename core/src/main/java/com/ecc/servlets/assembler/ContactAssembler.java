package com.ecc.servlets.assembler;

import com.ecc.servlets.model.Contact;
import com.ecc.servlets.model.Person;
import com.ecc.servlets.dto.ContactDTO;
import com.ecc.servlets.dto.PersonDTO;

public class ContactAssembler implements Assembler<Contact, ContactDTO> {
	@Override
	public ContactDTO createDTO(Contact model) {
		if (model == null) {
			return null;
		}
		ContactDTO dto = new ContactDTO();
		dto.setId(model.getId());
		dto.setData(model.getData());
		dto.setContactType(model.getContactType());
		return dto;
	}

	@Override 
	public Contact createModel(ContactDTO dto) {
		if (dto == null) {
			return null;
		}
		Contact model = new Contact();
		model.setId(dto.getId());
		model.setData(dto.getData());
		model.setContactType(dto.getContactType());
		return model;
	}
}