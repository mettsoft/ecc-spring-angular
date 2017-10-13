package com.ecc.hibernate_xml.util.function;

@FunctionalInterface
public interface CheckedUnaryOperator<T> extends CheckedFunction<T, T> {
    static <T> CheckedUnaryOperator<T> identity() {
        return t -> t;
    }	
}
