package com.ecc.hibernate_xml.util;

@FunctionalInterface
public interface CheckedRunnable<T> {
    void run() throws Exception;
}
