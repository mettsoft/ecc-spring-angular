package com.ecc.hibernate_xml.util;

@FunctionalInterface
public interface CheckedSupplier<T> {
    T get() throws Exception;
}
