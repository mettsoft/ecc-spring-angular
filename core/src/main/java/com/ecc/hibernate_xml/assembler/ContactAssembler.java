package com.ecc.hibernate_xml.assembler;

import com.ecc.hibernate_xml.dto.ContactDTO;
import com.ecc.hibernate_xml.dto.PersonDTO;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Contact;

public class ContactAssembler extends AbstractAssembler<Contact, ContactDTO> {	
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
		if (model == null) {
			return null;
		}		
		PersonDTO dto = new PersonDTO();
		dto.setId(model.getId());
		dto.setName(nameAssembler.createDTO(model.getName()));
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
		model.setPerson(createProxyPersonModel(dto.getPerson()));
		return model;
	}

	private Person createProxyPersonModel(PersonDTO dto) {
		if (dto == null) {
			return null;
		}
		Person model = new Person();
		model.setId(dto.getId());
		model.setName(nameAssembler.createModel(dto.getName()));
		return model;
	}
}