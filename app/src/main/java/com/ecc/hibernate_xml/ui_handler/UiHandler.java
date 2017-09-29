package com.ecc.hibernate_xml.ui_handler;

public abstract class UiHandler {

	private String operationName;

	public UiHandler(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationName() {
		return operationName;
	}
	
	public abstract void handle() throws Exception;
}