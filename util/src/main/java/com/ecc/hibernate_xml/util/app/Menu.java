package com.ecc.hibernate_xml.util.app;

import java.util.List;
import java.util.ArrayList;

public class Menu {
	private static String MENU_PROMPT = "Please choose what operation you want to perform:\n0. Close Menu\n%sOperation: ";

	private Menu parent;
	private List<Menu> children;
	private String description;

	public Menu() {
		this(null);
	}

	public Menu(String description) {
		this.parent = null;
		this.children = new ArrayList<>();
		this.description = description;
	}

	public Menu getParent() {
		return parent;
	}
	
	public Boolean hasChildren() {
		return !children.isEmpty();
	}

	public String getDescription() {
		return description;
	}

	public Menu add(Menu child) {
		child.parent = this;
		children.add(child);
		return this;
	}

	public static Menu chooseMenu(Menu menu) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < menu.children.size(); i++) {
			builder.append(String.format("%d. %s\n", i + 1, menu.children.get(i).description));
		}

		String menuPrompt = String.format(MENU_PROMPT, builder.toString());

		try {
			Integer optionIndex = InputHandler.getNextLine(menuPrompt, Integer::valueOf);
			if (optionIndex == 0) {
				return menu.parent;
			}
			else {
				return menu.children.get(optionIndex-1);			
			}
		}
		catch (Exception cause) {
			ExceptionHandler.printException(new InputException(cause));
			return chooseMenu(menu);
		}
	}
}