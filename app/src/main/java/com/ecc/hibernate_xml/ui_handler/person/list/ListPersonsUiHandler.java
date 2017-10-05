package com.ecc.hibernate_xml.ui_handler.person.list;

import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;

public class ListPersonsUiHandler extends CompositeUiHandler {
	public ListPersonsUiHandler(String operationName) {
		super(operationName);	
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}