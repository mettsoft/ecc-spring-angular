package com.ecc.hibernate_xml.assembler;

import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.dto.ContactDTO;
import com.ecc.hibernate_xml.dto.PersonDTO;

public class ContactAssembler implements Assembler<Contact, ContactDTO> {	
	private NameAssembler nameAssembler = new NameAssembler();

	@Override
	public ContactDTO createDTO(Contact model) {
		if (model == null) {
			return null;
		}
		ContactDTO dto = new ContactDTO();
		dto.setId(model.getId());
		dto.setData(model.getData());
		dto.setContactType(model.getContactType());
		dto.setPerson(createProxyPersonDTO(model.getPerson()));
		return dto;
	}

	private PersonDTO createProxyPersonDTO(Person model) {
		return model == null? null: new PersonDTO(model.getId(), nameAssembler.createDTO(model.getName()));
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
		model.setPerson(createProxyPersonModel(dto.getPerson()));
		return model;
	}

	private Person createProxyPersonModel(PersonDTO dto) {
		return dto == null? null: new Person(dto.getId(), nameAssembler.createModel(dto.getName()));
	}
}