package com.ecc.hibernate_xml.util.app;

import com.ecc.hibernate_xml.util.function.CheckedUnaryOperator;
import java.util.function.Consumer;

public class UiRoute {
	private CheckedUnaryOperator<Object> downwardCallback;
	private Consumer<Object> upwardCallback;
	private Object argument;

	public UiRoute(CheckedUnaryOperator<Object> downwardCallback) {
		this.downwardCallback = downwardCallback;
	}

	public UiRoute(CheckedUnaryOperator<Object> downwardCallback, Consumer<Object> upwardCallback) {
		this(downwardCallback);
		this.upwardCallback = upwardCallback;
	}

	public Object runDownward(Object argument) throws Exception {
		return downwardCallback.apply(argument);
	}

	public void runUpward(Object argument) {
		if (upwardCallback != null) {
			upwardCallback.accept(argument);		
		}
	}

	public void setArgument(Object argument) {
		this.argument = argument;
	}

	public Object getArgument() {
		return argument;
	}
}