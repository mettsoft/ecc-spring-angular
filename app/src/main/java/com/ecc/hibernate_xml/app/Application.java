package com.ecc.hibernate_xml.app;

import java.util.logging.Logger;
import java.util.logging.Level;

import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.list.ListPersonsUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.list.ListPersonsByGwaUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.list.ListPersonsByDateHiredUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.list.ListPersonsByLastNameUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.CreatePersonUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.UpdatePersonUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.DeletePersonUiHandler;
import com.ecc.hibernate_xml.ui_handler.role.ListRolesUiHandler;
import com.ecc.hibernate_xml.ui_handler.role.CreateRoleUiHandler;
import com.ecc.hibernate_xml.ui_handler.role.UpdateRoleUiHandler;
import com.ecc.hibernate_xml.ui_handler.role.DeleteRoleUiHandler;
import com.ecc.hibernate_xml.util.HibernateUtility;

public class Application {

	private static String MAIN_HEADER = "--------------------------\nExercise 7 - Hibernate XML\nPerson Registration System\nBy Emmett Young\n---------------------------";

	public static void main(String[] args) {

		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		System.out.println(MAIN_HEADER);

		HibernateUtility.initializeSessionFactory();

		new CompositeUiHandler()
			.add(new CompositeUiHandler("Go to Person Registry.")
				.add(new ListPersonsUiHandler("List Person Records.")
					.add(new ListPersonsByGwaUiHandler("Sort by GWA."))
					.add(new ListPersonsByDateHiredUiHandler("Sort by date hired."))
					.add(new ListPersonsByLastNameUiHandler("Sort by last name.")))
				.add(new CreatePersonUiHandler("Create a new Person record."))
				.add(new UpdatePersonUiHandler("Update an existing Person record."))
				.add(new DeletePersonUiHandler("Delete an existing Person record.")))
			.add(new CompositeUiHandler("Go to Role Registry.")
				.add(new ListRolesUiHandler("List Role Records."))
				.add(new CreateRoleUiHandler("Create a new Role record."))
				.add(new UpdateRoleUiHandler("Update an existing Role record."))
				.add(new DeleteRoleUiHandler("Delete an existing Role record.")))
			.handle();

		HibernateUtility.closeSessionFactory();
	}
}