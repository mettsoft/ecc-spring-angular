package com.ecc.hibernate_xml.util.app;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

import com.ecc.hibernate_xml.util.function.CheckedUnaryOperator;
import com.ecc.hibernate_xml.util.function.CheckedConsumer;
import com.ecc.hibernate_xml.util.function.CheckedRunnable;
import com.ecc.hibernate_xml.util.function.CheckedSupplier;

public class UiRouter {
	private Menu currentMenu;
	private Map<String, UiRoute> routes;

	public UiRouter(Menu currentMenu) {
		this.currentMenu = currentMenu;
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

	public void register(String route, CheckedSupplier downwardCallback, Consumer upwardCallback) {
		routes.put(route, new UiRoute(t -> downwardCallback.get(), upwardCallback));
	}

	public void run() {
		while (true) {
			Menu parentMenu = currentMenu.getParent();
			currentMenu = Menu.chooseMenu(currentMenu);
			if (currentMenu == null) {
				break;
			}

			UiRoute currentRoute = getRoute(currentMenu);

			InputHandler.clearScreen();
			if (currentMenu.getDescription() != null) {
				System.out.println("--- " + currentMenu.getDescription() + " ---");			
			}

			if (currentRoute != null) {
				Object argument = getArgument(currentMenu.getParent());

				// Only execute the callback if navigating downwards.
				if (parentMenu != currentMenu) {
					try {
						Object result = currentRoute.runDownward(argument);	
						currentRoute.setArgument(result);
					}
					catch (Exception cause) {
						ExceptionHandler.printException(cause);
						currentMenu = navigateToParent(currentMenu);
					}					
				}
				else {
					currentRoute.runUpward(currentRoute.getArgument());	
				}

				if (!currentMenu.hasChildren()) {
					currentMenu = navigateToParent(currentMenu);
				}
			}
		}
	}

	private Menu navigateToParent(Menu menu) {
		menu = menu.getParent();
		UiRoute route = getRoute(menu);
		if (route != null) {
			route.runUpward(route.getArgument());			
		}
		return menu;
	}

	private Object getArgument(Menu menu) {		
		UiRoute route = getRoute(menu);
		if (route != null) {
			return route.getArgument();
		} 
		return null;
	}

	private UiRoute getRoute(Menu menu) {
		if (menu != null) {
			String key = menu.getDescription();
			return routes.get(key);			
		}
		return null;
	}
}