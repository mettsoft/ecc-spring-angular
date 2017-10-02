package com.ecc.hibernate_xml.ui_handler;

public abstract class UiHandler {

	private String operationName;

	public UiHandler(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void handle() {
		try {
			onHandle();
		}
		catch (Exception exception) {
			System.out.println("Error: " + exception.getMessage());
		}
		finally {
			if (!relinquishControl()) {
				handle();		
			}
		}
	}

	public abstract void onHandle() throws Exception;
	protected abstract Boolean relinquishControl();
}