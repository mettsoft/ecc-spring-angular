package com.ecc.spring_xml.factory;

import java.text.ParseException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ecc.spring_xml.dto.PersonDTO;
import com.ecc.spring_xml.dto.ContactDTO;
import com.ecc.spring_xml.dto.RoleDTO;
import com.ecc.spring_xml.util.ValidationException;
import com.ecc.spring_xml.util.DateUtils;

@Component
public class PersonFactory {
   	private static final Pattern PATTERN = Pattern.compile("(.*)=(.*)");
   	private static final String CONTACT_TYPE_LANDLINE = "Landline";
   	private static final String CONTACT_TYPE_MOBILE = "Mobile";
   	private static final String CONTACT_TYPE_EMAIL = "Email";

	public PersonDTO createPersonDTO(byte[] data) {
		try {
			String dataString = IOUtils.toString(data, "utf-8");
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
							person.getName().setMiddleName(value);
							break;
						case "name:middlename": 
							person.getName().setFirstName(value);
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
}