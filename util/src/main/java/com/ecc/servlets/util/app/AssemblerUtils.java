package com.ecc.servlets.util.app;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AssemblerUtils {
	@FunctionalInterface
	public interface Converter<T, R> {
		R convert(T input);
	}

	public static <T, R> List<R> asList(Collection<T> data, Converter<T, R> converter) {
		return data == null? null: data.stream().map(converter::convert).collect(Collectors.toList());
	}

	public static <T, R> Set<R> asSet(Collection<T> data, Converter<T, R> converter) {
		return data == null? null: data.stream().map(converter::convert).collect(Collectors.toSet());
	}
}