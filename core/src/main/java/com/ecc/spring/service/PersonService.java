package com.ecc.spring.service;

import java.text.ParseException;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;

import com.ecc.spring.dto.AddressDTO;
import com.ecc.spring.dto.PersonDTO;
import com.ecc.spring.dto.NameDTO;
import com.ecc.spring.dto.RoleDTO;
import com.ecc.spring.dto.ContactDTO;
import com.ecc.spring.model.Address;
import com.ecc.spring.model.Person;
import com.ecc.spring.model.Name;
import com.ecc.spring.model.Role;
import com.ecc.spring.dao.PersonDao;
import com.ecc.spring.dao.RoleDao;
import com.ecc.spring.util.AssemblerUtils;
import com.ecc.spring.util.DateUtils;
import com.ecc.spring.util.ValidationUtils;
import com.ecc.spring.util.ValidationException;

@Service
public class PersonService extends AbstractService<Person, PersonDTO> implements Validator {
	private static final Integer DEFAULT_MAX_CHARACTERS = 20;
	private static final Integer LONG_MAX_CHARACTERS = 50;
	private static final Integer LANDLINE_DIGITS = 7;
	private static final Integer MOBILE_NUMBER_DIGITS = 11;
 	private static final Pattern PATTERN = Pattern.compile("(.*)=(.*)");
 	private static final String CONTACT_TYPE_LANDLINE = "Landline";
 	private static final String CONTACT_TYPE_MOBILE = "Mobile";
 	private static final String CONTACT_TYPE_EMAIL = "Email";

	private final PersonDao personDao;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private RoleDao roleDao;

	public PersonService(PersonDao personDao) {
		super(personDao);
		this.personDao = personDao;
	}

	@Override
	public boolean supports(Class clazz) {
    return clazz.isAssignableFrom(PersonDTO.class);
  }
  
	@Transactional
  public void validate(PersonDTO person, String objectName) {
  	if (person == null) {
  		throw new ValidationException("validation.message.notEmpty", new PersonDTO(), "localize:person.form.label");
  	}

  	Errors errors = new BindException(person, objectName);
  	validate(person, errors);
		validateRoles(person.getRoles(), errors);
  	if (errors.hasErrors()) {
  		throw new ValidationException(errors.getFieldErrors(), person);
  	}
  }

  @Override
  public void validate(Object command, Errors errors) {
  	PersonDTO person = (PersonDTO) command;

		validateName(person.getName().getTitle(), "name.title", errors, "localize:person.form.label.name.title");
		validateName(person.getName().getLastName(), "name.lastName", errors, "localize:person.form.label.name.lastName");
		validateName(person.getName().getFirstName(), "name.firstName", errors, "localize:person.form.label.name.firstName");
		validateName(person.getName().getMiddleName(), "name.middleName", errors, "localize:person.form.label.name.middleName");
		validateName(person.getName().getSuffix(), "name.suffix", errors, "localize:person.form.label.name.suffix");

		validateAddress(person.getAddress().getStreetNumber(), "address.streetNumber", errors, "localize:person.form.label.address.streetNumber");
		validateAddress(person.getAddress().getBarangay(), "address.barangay", errors, "localize:person.form.label.address.barangay");
		validateAddress(person.getAddress().getMunicipality(), "address.municipality", errors, "localize:person.form.label.address.municipality");
		validateAddress(person.getAddress().getZipCode(), "address.zipCode", errors, "localize:person.form.label.address.zipCode");

		ValidationUtils.testNotEmpty(person.getBirthday(), "birthday", errors, "localize:person.form.label.otherInformation.birthday");

		validateGWA(person.getGWA(), "GWA", errors, "localize:person.form.label.otherInformation.GWA");

		if (!person.getCurrentlyEmployed() && person.getDateHired() != null) {
			errors.rejectValue("dateHired", "person.validation.message.unemployed");
		}
		else if (person.getCurrentlyEmployed() && person.getDateHired() == null) {
			errors.rejectValue("dateHired", "person.validation.message.employed");
		}

		validateContacts(person.getContacts(), errors);
  }

  @Transactional
	public List<PersonDTO> list(String lastName, Integer roleId, Date birthday, String orderBy, String order) {
		return AssemblerUtils.asList(personDao.list(lastName, roleId, birthday, orderBy, order), this::createDTO);
	}

	public PersonDTO createPersonDTO(MultipartFile file) {
		try {
			String dataString = IOUtils.toString(file.getBytes(), "utf-8");
			Matcher matcher = PATTERN.matcher(dataString);

			PersonDTO person = new PersonDTO();
			while (matcher.find()) {
				String property = StringUtils.trim(matcher.group(1)).toLowerCase();
				String value = StringUtils.trim(matcher.group(2));

				try {				
					switch(property) {
						case "name:title": 
							person.getName().setTitle(value);
							break;
						case "name:lastname": 
							person.getName().setLastName(value);
							break;
						case "name:firstname": 
							person.getName().setFirstName(value);
							break;
						case "name:middlename": 
							person.getName().setMiddleName(value);
							break;
						case "name:suffix": 
							person.getName().setSuffix(value);
							break;
						case "address:streetnumber": 
							person.getAddress().setStreetNumber(value);
							break;
						case "address:barangay": 
							person.getAddress().setBarangay(value);
							break;
						case "address:municipality": 
							person.getAddress().setMunicipality(value);
							break;
						case "address:zipcode": 
							person.getAddress().setZipCode(Integer.valueOf(value));	
							break;
						case "birthday": 
							person.setBirthday(DateUtils.DATE_FORMAT.parse(value));	
							break;
						case "gwa": 
							person.setGWA(new BigDecimal(value));
							break;
						case "currentlyemployed": 
							person.setCurrentlyEmployed(Boolean.valueOf(value));
							break;
						case "datehired": 
							person.setDateHired(DateUtils.DATE_FORMAT.parse(value));
							break;
						case "contacts":
							String[] contactTokens = StringUtils.split(value, ",");
							for (String contactToken: contactTokens) {
								ContactDTO contact = new ContactDTO();
								contactToken = StringUtils.trim(contactToken);
								if (StringUtils.startsWithIgnoreCase(contactToken, "landline:")) {
									contact.setContactType(CONTACT_TYPE_LANDLINE);
									contact.setData(StringUtils.removeStartIgnoreCase(contactToken, "landline:"));
								}
								else if (StringUtils.startsWithIgnoreCase(contactToken, "mobile:")) {
									contact.setContactType(CONTACT_TYPE_MOBILE);
									contact.setData(StringUtils.removeStartIgnoreCase(contactToken, "mobile:"));
								}
								else if (StringUtils.startsWithIgnoreCase(contactToken, "email:")) {
									contact.setContactType(CONTACT_TYPE_EMAIL);
									contact.setData(StringUtils.removeStartIgnoreCase(contactToken, "email:"));
								}
								else {
									throw new RuntimeException();
								}
								person.getContacts().add(contact);
							}
							break;
						case "roles": 
							String[] roleTokens = StringUtils.split(value, ",");
							for (String roleToken: roleTokens) {
								RoleDTO role = new RoleDTO();
								role.setName(StringUtils.trim(roleToken));
								person.getRoles().add(role);
							}
							break;
					}
				}
				catch (Exception exception) {
					throw new ParseException(matcher.group(0), 0);
				}
			}

			return person;
		}
		catch (ParseException exception) {
			throw new ValidationException("person.validation.message.parseError", new PersonDTO(), exception.getMessage());
		}
		catch (Exception exception) {
			if (exception instanceof ValidationException) {
				throw (ValidationException) exception;
			}
			throw new ValidationException("person.validation.message.invalidFormat", new PersonDTO());
		}
	}

	@Override
	public PersonDTO createDTO(Person model) {
		PersonDTO dto = createBasicDTO(model);
		if (dto == null) {
			return null;
		}
		dto.setAddress(createAddressDTO(model.getAddress()));
		dto.setBirthday(model.getBirthday());
		dto.setGWA(model.getGWA());
		dto.setCurrentlyEmployed(model.getCurrentlyEmployed());
		dto.setDateHired(model.getDateHired());
		dto.setGWA(model.getGWA());
		dto.setContacts(AssemblerUtils.asList(model.getContacts(), contactService::createDTO));
		dto.setRoles(AssemblerUtils.asList(model.getRoles(), roleService::createDTO));
		return dto;
	}

	@Override 
	public Person createModel(PersonDTO dto) {
		Person model = createBasicModel(dto);
		if (model == null) {
			return null;
		}
		model.setAddress(createAddressModel(dto.getAddress()));
		model.setBirthday(dto.getBirthday());
		model.setGWA(dto.getGWA());
		model.setCurrentlyEmployed(dto.getCurrentlyEmployed());
		model.setDateHired(dto.getDateHired());
		model.setGWA(dto.getGWA());
		model.setContacts(AssemblerUtils.asSet(dto.getContacts(), contactService::createModel));
		model.setRoles(AssemblerUtils.asSet(dto.getRoles(), roleService::createModel));
		return model;
	}

	public PersonDTO createBasicDTO(Person model) {
		return model == null? null: new PersonDTO(model.getId(), createNameDTO(model.getName()));
	}

	public Person createBasicModel(PersonDTO dto) {
		return dto == null? null: new Person(dto.getId(), createNameModel(dto.getName()));
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

	private NameDTO createNameDTO(Name model) {
		if (model == null) {
			return null;
		}
		NameDTO dto = new NameDTO();
		dto.setTitle(model.getTitle());
		dto.setLastName(model.getLastName());
		dto.setFirstName(model.getFirstName());
		dto.setMiddleName(model.getMiddleName());
		dto.setSuffix(model.getSuffix());
		return dto;
	}
 
	private Name createNameModel(NameDTO dto) {
		if (dto == null) {
			return null;
		}
		Name model = new Name();
		model.setTitle(dto.getTitle());
		model.setLastName(dto.getLastName());
		model.setFirstName(dto.getFirstName());
		model.setMiddleName(dto.getMiddleName());
		model.setSuffix(dto.getSuffix());
		return model;
	}

	private void validateName(String data, String field, Errors errors, String argument) {
		if (!StringUtils.containsIgnoreCase(field, "title") && !StringUtils.containsIgnoreCase(field, "suffix")) {
			ValidationUtils.testNotEmpty(data, field, errors, argument);
		}
		ValidationUtils.testMaxLength(data, field, errors, DEFAULT_MAX_CHARACTERS, argument);
	}

	private void validateAddress(Object data, String field, Errors errors, String argument) {
		ValidationUtils.testNotEmpty(data, field, errors, argument);

		if (data instanceof String && StringUtils.containsIgnoreCase(field, "street")) {
			ValidationUtils.testMaxLength((String) data, field, errors, DEFAULT_MAX_CHARACTERS, argument);
		}
		else if (data instanceof String && (StringUtils.containsIgnoreCase(field, "municipality") || StringUtils.containsIgnoreCase(field, "barangay"))) {
			ValidationUtils.testMaxLength((String) data, field, errors, LONG_MAX_CHARACTERS, argument);
		}
	}

	private void validateGWA(BigDecimal GWA, String field, Errors errors, String argument) {
		ValidationUtils.testNotEmpty(GWA, field, errors, argument);
		ValidationUtils.testMinimumValue(GWA, field, errors, 1, argument);
		ValidationUtils.testMaximumValue(GWA, field, errors, 5, argument);
	}

	private void validateRoles(List<RoleDTO> roles, Errors errors) {
		for (RoleDTO roleDTO: roles) { 
			Role role = roleDao.get(roleDTO.getName());
			if (role == null) {
				errors.rejectValue("roles", "role.validation.message.nameNotFound", new Object[] {roleDTO.getName()}, null);
			}
			else {
				roleDTO.setId(role.getId());
			}
		}
	}

	private void validateContacts(List<ContactDTO> contacts, Errors errors) {
		for (int i = 0; i < contacts.size(); i++) {
			ContactDTO contact = contacts.get(i);
			ValidationUtils.testNotEmpty(contact.getContactType(), null, errors, "localize:person.contactType");

			if (contact.getContactType() != null) {
				switch(contact.getContactType()) {
					case "Landline":
						validateNumericalContact(contact.getData(), errors, LANDLINE_DIGITS, "localize:person.contactType.landline"); 
						break;
					case "Mobile":
						validateNumericalContact(contact.getData(), errors, MOBILE_NUMBER_DIGITS, "localize:person.contactType.mobile"); 
						break;
					case "Email":
						validateEmail(contact.getData(), errors);
						break;
					default: 
						errors.reject("person.validation.contactType.invalid", new Object[] {contact.getContactType()}, null);
				}
			}
		}
	}

	private void validateNumericalContact(String data, Errors errors, Integer matchingDigits, String argument) {
		ValidationUtils.testNotEmpty(data, "contacts", errors, argument);
		ValidationUtils.testDigits(data, "contacts", errors, argument);
		ValidationUtils.testEqualLength(data, "contacts", errors, matchingDigits, argument);
	}

	private void validateEmail(String email, Errors errors) {
		ValidationUtils.testNotEmpty(email, "contacts", errors, "localize:person.contactType.email");
		ValidationUtils.testValidEmail(email, "contacts", errors);
		ValidationUtils.testMaxLength(email, "contacts", errors, LONG_MAX_CHARACTERS, "localize:person.contactType.email");
	}
}