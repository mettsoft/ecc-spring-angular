package com.ecc.hibernate_xml.util.app;

import com.ecc.hibernate_xml.util.function.CheckedUnaryOperator;

public class UiRoute {
	private CheckedUnaryOperator<Object> callback;
	private Object argument;

	public UiRoute(CheckedUnaryOperator<Object> callback) {
		this.callback = callback;
	}

	public Object run(Object argument) throws Exception {
		return callback.apply(argument);
	}

	public void setArgument(Object argument) {
		this.argument = argument;
	}

	public Object getArgument() {
		return argument;
	}
}