package com.ecc.hibernate_xml.app;

import java.util.logging.Logger;
import java.util.logging.Level;

import com.ecc.hibernate_xml.util.CheckedUnaryOperator;
import com.ecc.hibernate_xml.util.HibernateUtility;
import com.ecc.hibernate_xml.util.UiRouter;
import com.ecc.hibernate_xml.util.Menu;

public class Application {
	private static String MAIN_HEADER = "--------------------------\nExercise 7 - Hibernate XML\nPerson Registration System\nBy Emmett Young\n---------------------------";
	private static UiRouter uiRouter;

	static {
		Menu menu = new Menu()
			.add(new Menu("Go to Person Registry.")
				.add(new Menu("List Person records.")
					.add(new Menu("Sort by GWA."))
					.add(new Menu("Sort by date hired."))
					.add(new Menu("Sort by last name.")))
				.add(new Menu("Create a new Person.")
					.add(new Menu("Change name."))
					.add(new Menu("Change address."))
					.add(new Menu("Change birthday."))
					.add(new Menu("Change GWA."))
					.add(new Menu("Change employment status."))
					.add(new Menu("Manage contact information.")
						.add(new Menu("List contact information."))
						.add(new Menu("Add contact information.")
							.add(new Menu("Add landline."))
							.add(new Menu("Add email."))
							.add(new Menu("Add mobile number.")))
						.add(new Menu("Update contact information."))
						.add(new Menu("Delete contact information.")))
					.add(new Menu("Manage roles.")
						.add(new Menu("List roles."))
						.add(new Menu("Add role."))
						.add(new Menu("Remove role."))))
				.add(new Menu("Update an existing Person record.")
					.add(new Menu("Change name."))
					.add(new Menu("Change address."))
					.add(new Menu("Change birthday."))
					.add(new Menu("Change GWA."))
					.add(new Menu("Change employment status."))
					.add(new Menu("Manage contact information.")
						.add(new Menu("List contact information."))
						.add(new Menu("Add contact information.")
							.add(new Menu("Add landline."))
							.add(new Menu("Add email."))
							.add(new Menu("Add mobile number.")))
						.add(new Menu("Update contact information."))
						.add(new Menu("Delete contact information.")))
					.add(new Menu("Manage roles.")
						.add(new Menu("List roles."))
						.add(new Menu("Add role."))
						.add(new Menu("Remove role."))))
				.add(new Menu("Delete an existing Person record.")))
			.add(new Menu("Go to Role Registry.")
				.add(new Menu("List Role records."))
				.add(new Menu("Create a new Role record."))
				.add(new Menu("Update an existing Role record."))
				.add(new Menu("Delete an existing Role record.")));
		uiRouter = new UiRouter(menu);
	}

	public static void main(String[] args) {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		System.out.println(MAIN_HEADER);

		HibernateUtility.initializeSessionFactory();
		registerRoutes();
		uiRouter.run();
		HibernateUtility.closeSessionFactory();
	}

	public static void registerRoutes() {
		// No passing of parameters.
		uiRouter.register("Sort by GWA.", PersonUiHandler::listByGWA);
		uiRouter.register("Sort by date hired.", PersonUiHandler::listByDateHired);
		uiRouter.register("Sort by last name.", PersonUiHandler::listByLastName);
		uiRouter.register("Delete an existing Person record.", PersonUiHandler::delete);

		uiRouter.register("List Role records.", RoleUiHandler::list);
		uiRouter.register("Create a new Role record.", RoleUiHandler::create);
		uiRouter.register("Update an existing Role record.", RoleUiHandler::update);
		uiRouter.register("Delete an existing Role record.", RoleUiHandler::delete);

		// Passes the Person detached instance to the next level.
		uiRouter.register("Create a new Person.", PersonUiHandler::create);
		uiRouter.register("Update an existing Person record.", PersonUiHandler::update);

		// Receives the Person detached instance from the previous level.
		uiRouter.register("Change name.", PersonUiHandler::changeName);
		uiRouter.register("Change address.", PersonUiHandler::changeAddress);
		uiRouter.register("Change birthday.", PersonUiHandler::changeBirthday);
		uiRouter.register("Change GWA.", PersonUiHandler::changeGWA);
		uiRouter.register("Change employment status.", PersonUiHandler::changeEmploymentStatus);

		// Receives the Person detached instance from the previous level and passes it to the next level.
		uiRouter.register("Manage contact information.", CheckedUnaryOperator.identity());
		uiRouter.register("Manage roles.", CheckedUnaryOperator.identity());

		// Receives the Person detached instance from the previous level.
		uiRouter.register("List contact information.", PersonContactUiHandler::list);
		uiRouter.register("Update contact information.", PersonContactUiHandler::update);
		uiRouter.register("Delete contact information.", PersonContactUiHandler::delete);
		uiRouter.register("List roles.", PersonRoleUiHandler::list);
		uiRouter.register("Add role.", PersonRoleUiHandler::add);
		uiRouter.register("Remove role.", PersonRoleUiHandler::remove);

		// Receives the Person detached instance from the previous level and passes it to the next level.
		uiRouter.register("Add contact information.", CheckedUnaryOperator.identity());

		// Receives the Person detached instance from the previous level.
		uiRouter.register("Add landline.", arg -> PersonContactUiHandler.addContact(arg, "Landline"));
		uiRouter.register("Add email.", arg -> PersonContactUiHandler.addContact(arg, "Email"));
		uiRouter.register("Add mobile number.", arg -> PersonContactUiHandler.addContact(arg, "Mobile Number"));
	}
}