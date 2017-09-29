package com.ecc.hibernate_xml.ui_handler;

import java.util.List;
import java.util.ArrayList;

import com.ecc.hibernate_xml.util.InputHandler;

public abstract class UiHandler {

	private static String MAIN_PROMPT = "Please choose what operation you want to perform:\n%sOperation: ";
	private static String BACK_OPTION = "0. Back\n";

	private Boolean isBackOptionEnabled;
	private String operationName;
	private List<UiHandler> uiHandlers;

	public UiHandler() {
		this.isBackOptionEnabled = false;		
		this.uiHandlers = new ArrayList<>();
	}

	public UiHandler(String operationName) {
		this();
		this.operationName = operationName;
	}

	public UiHandler(String operationName, Boolean allowBackOption) {
		this();
		this.isBackOptionEnabled = allowBackOption;
		this.operationName = operationName;
	}

	public void add(UiHandler uiHandler) {
		uiHandlers.add(uiHandler);
	}

	public String getOperationName() {
		return operationName;
	}

	public void handle() throws Exception {
		String userPrompt = String.format(MAIN_PROMPT, buildOperationPrompt());
		Integer optionIndex = InputHandler.getNextLine(userPrompt, 
			input -> Integer.valueOf(input));

		if (optionIndex > 0 || !isBackOptionEnabled) {
			uiHandlers.get(optionIndex - 1).handle();
			handle();
		}
	}

	private String buildOperationPrompt() {		
		StringBuilder builder = new StringBuilder();

		if (isBackOptionEnabled) {
			builder.append(BACK_OPTION);
		}

		for (int i = 0; i < uiHandlers.size(); i++) {
			builder.append(String.format("%d. %s\n", i + 1, uiHandlers.get(i).getOperationName()));
		}

		return builder.toString();
	}
}