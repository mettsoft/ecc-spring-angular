package com.ecc.hibernate_xml.util.app;

import java.util.Map;
import java.util.HashMap;

import com.ecc.hibernate_xml.util.function.CheckedUnaryOperator;
import com.ecc.hibernate_xml.util.function.CheckedConsumer;
import com.ecc.hibernate_xml.util.function.CheckedRunnable;
import com.ecc.hibernate_xml.util.function.CheckedSupplier;

public class UiRouter {
	private Menu menu;
	private Map<String, UiRoute> routes;

	public UiRouter(Menu menu) {
		this.menu = menu;
		this.routes = new HashMap<>();
	}

	public void register(String route, CheckedUnaryOperator<Object> callback) {
		routes.put(route, new UiRoute(callback));
	}

	public void register(String route, CheckedConsumer<Object> callback) {
		routes.put(route, new UiRoute(t -> {
			callback.accept(t);
			return 0;
		}));
	}

	public void register(String route, CheckedRunnable callback) {
		routes.put(route, new UiRoute(t -> {
			callback.run();
			return 0;
		}));
	}

	public void register(String route, CheckedSupplier callback) {
		routes.put(route, new UiRoute(t -> callback.get()));
	}

	public void run() {
		while (true) {
			Menu parentMenu = menu.getParent();
			menu = Menu.chooseMenu(menu);
			if (menu == null) {
				break;
			}

			String currentKey = menu.getDescription();
			UiRoute currentRoute = routes.get(currentKey);

			InputHandler.clearScreen();
			if (menu.getDescription() != null) {
				System.out.println("--- " + menu.getDescription() + " ---");			
			}

			if (currentRoute != null) {
				Object argument = null;
				if (menu.getParent() != null) {
					String parentKey = menu.getParent().getDescription();
					UiRoute parentRoute = routes.get(parentKey);
					if (parentRoute != null) {
						argument = parentRoute.getArgument();					
					}
				}

				// Only execute the callback if navigating downwards.
				if (parentMenu != menu) {
					try {
						Object result = currentRoute.run(argument);	
						currentRoute.setArgument(result);
					}
					catch (Exception cause) {
						ExceptionHandler.printException(cause);
						menu = menu.getParent();
					}					
				}

				if (!menu.hasChildren()) {
					menu = menu.getParent();
				}
			}
		}
	}
}