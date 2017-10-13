package com.ecc.hibernate_xml.util.function;

@FunctionalInterface
public interface CheckedRunnable<T> {
    void run() throws Exception;
}
