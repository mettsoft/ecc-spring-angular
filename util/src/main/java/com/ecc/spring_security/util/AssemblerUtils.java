package com.ecc.spring_security.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.function.Function;

public class AssemblerUtils {
	public static <T, R> List<R> asList(Collection<T> data, Function<T, R> converter) {
		return data == null? null: data.stream().map(converter::apply).collect(Collectors.toList());
	}

	public static <T, R> Set<R> asSet(Collection<T> data, Function<T, R> converter) {
		return data == null? null: data.stream().map(converter::apply).collect(Collectors.toSet());
	}
}