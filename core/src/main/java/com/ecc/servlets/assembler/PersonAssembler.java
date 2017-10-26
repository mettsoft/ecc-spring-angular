package com.ecc.servlets.assembler;

import com.ecc.servlets.model.Person;
import com.ecc.servlets.model.Address;
import com.ecc.servlets.dto.PersonDTO;
import com.ecc.servlets.dto.AddressDTO;
import com.ecc.servlets.util.app.AssemblerUtils;

public class PersonAssembler implements Assembler<Person, PersonDTO> {
	private NameAssembler nameAssembler = new NameAssembler();
	private RoleAssembler roleAssembler = new RoleAssembler();
	private ContactAssembler contactAssembler = new ContactAssembler();

	@Override
	public PersonDTO createDTO(Person model) {
		if (model == null) {
			return null;
		}
		PersonDTO dto = new PersonDTO(model.getId(), nameAssembler.createDTO(model.getName()));
		dto.setAddress(createAddressDTO(model.getAddress()));
		dto.setBirthday(model.getBirthday());
		dto.setGWA(model.getGWA());
		dto.setCurrentlyEmployed(model.getCurrentlyEmployed());
		dto.setDateHired(model.getDateHired());
		dto.setGWA(model.getGWA());
		dto.setContacts(AssemblerUtils.asList(model.getContacts(), contactAssembler::createDTO));
		dto.setRoles(AssemblerUtils.asList(model.getRoles(), roleAssembler::createDTO));
		return dto;
	}

	@Override 
	public Person createModel(PersonDTO dto) {
		if (dto == null) {
			return null;
		}
		Person model = new Person(dto.getId(), nameAssembler.createModel(dto.getName()));
		model.setAddress(createAddressModel(dto.getAddress()));
		model.setBirthday(dto.getBirthday());
		model.setGWA(dto.getGWA());
		model.setCurrentlyEmployed(dto.getCurrentlyEmployed());
		model.setDateHired(dto.getDateHired());
		model.setGWA(dto.getGWA());
		model.setContacts(AssemblerUtils.asSet(dto.getContacts(), contactAssembler::createModel));
		model.setRoles(AssemblerUtils.asSet(dto.getRoles(), roleAssembler::createModel));
		return model;
	}

	private AddressDTO createAddressDTO(Address model) {
		if (model == null) {
			return null;
		}
		AddressDTO dto = new AddressDTO();
		dto.setStreetNumber(model.getStreetNumber());
		dto.setBarangay(model.getBarangay());
		dto.setMunicipality(model.getMunicipality());
		dto.setZipCode(model.getZipCode());
		return dto;	
	}

	private Address createAddressModel(AddressDTO dto) {
		if (dto == null) {
			return null;
		}
		Address model = new Address();
		model.setStreetNumber(dto.getStreetNumber());
		model.setBarangay(dto.getBarangay());
		model.setMunicipality(dto.getMunicipality());
		model.setZipCode(dto.getZipCode());
		return model;	
	}
}