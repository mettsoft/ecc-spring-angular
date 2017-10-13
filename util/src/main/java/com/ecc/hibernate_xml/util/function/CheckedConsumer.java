package com.ecc.hibernate_xml.util.function;

@FunctionalInterface
public interface CheckedConsumer<T> {
    void accept(T t) throws Exception;
}
