package com.ecc.hibernate_xml.ui_handler;

import java.util.List;
import java.util.ArrayList;

import com.ecc.hibernate_xml.util.InputHandler;

public class CompositeUiHandler extends UiHandler {

	private static String MAIN_PROMPT = "Please choose what operation you want to perform:\n%sOperation: ";
	private static String BACK_OPTION = "0. Close Menu\n";

	private List<UiHandler> uiHandlers;

	public CompositeUiHandler() {
		this(null);
	}

	public CompositeUiHandler(String operationName) {
		super(operationName);	
		this.uiHandlers = new ArrayList<>();
	}

	public CompositeUiHandler add(UiHandler uiHandler) {
		uiHandlers.add(uiHandler);
		return this;
	}

	@Override
	public void onHandle() throws Exception {
		try {
			String userPrompt = String.format(MAIN_PROMPT, buildOperationPrompt());
			Integer optionIndex = InputHandler.getNextLine(userPrompt, Integer::valueOf);
			if (optionIndex > 0) {
				uiHandlers.get(optionIndex - 1).handle();
			}
		}
		catch (Exception exception) {
			throw new InputException(exception);
		}
	}

	private String buildOperationPrompt() {		
		StringBuilder builder = new StringBuilder(BACK_OPTION);

		for (int i = 0; i < uiHandlers.size(); i++) {
			builder.append(String.format("%d. %s\n", i + 1, uiHandlers.get(i).getOperationName()));
		}

		return builder.toString();
	}

	@Override 
	protected Boolean relinquishControl() {
		return false;
	}
}