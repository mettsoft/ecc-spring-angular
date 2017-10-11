package com.ecc.hibernate_xml.app;

import java.util.logging.Logger;
import java.util.logging.Level;

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
				.add(new Menu("Create a new Person."))
				.add(new Menu("Update an existing Person record."))
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
		uiRouter.register("Sort by GWA.", PersonUiHandler::listByGWA);
		uiRouter.register("Sort by date hired.", PersonUiHandler::listByDateHired);
		uiRouter.register("Sort by last name.", PersonUiHandler::listByLastName);
		uiRouter.register("Create a new Person.", PersonUiHandler::create);
		uiRouter.register("Update an existing Person record.", PersonUiHandler::update);
		uiRouter.register("Delete an existing Person record.", PersonUiHandler::delete);

		uiRouter.register("List Role records.", RoleUiHandler::list);
		uiRouter.register("Create a new Role record.", RoleUiHandler::create);
		uiRouter.register("Update an existing Role record.", RoleUiHandler::update);
		uiRouter.register("Delete an existing Role record.", RoleUiHandler::delete);
	}
}