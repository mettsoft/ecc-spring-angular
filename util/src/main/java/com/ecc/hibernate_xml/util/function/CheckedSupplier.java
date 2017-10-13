package com.ecc.hibernate_xml.util.function;

@FunctionalInterface
public interface CheckedSupplier<T> {
    T get() throws Exception;
}
