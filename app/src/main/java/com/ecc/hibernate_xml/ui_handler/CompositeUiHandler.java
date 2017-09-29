package com.ecc.hibernate_xml.ui_handler;

import java.util.List;
import java.util.ArrayList;

import com.ecc.hibernate_xml.util.InputHandler;

public class CompositeUiHandler extends UiHandler {

	private static String MAIN_PROMPT = "Please choose what operation you want to perform:\n%sOperation: ";
	private static String BACK_OPTION = "0. Back\n";

	private Boolean isBackOptionEnabled;
	private List<UiHandler> uiHandlers;

	public CompositeUiHandler() {
		super(null);
		this.isBackOptionEnabled = false;		
		this.uiHandlers = new ArrayList<>();
	}

	public CompositeUiHandler(String operationName) {
		super(operationName);
		this.isBackOptionEnabled = true;		
		this.uiHandlers = new ArrayList<>();
	}

	public CompositeUiHandler add(UiHandler uiHandler) {
		uiHandlers.add(uiHandler);
		return this;
	}

	@Override
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