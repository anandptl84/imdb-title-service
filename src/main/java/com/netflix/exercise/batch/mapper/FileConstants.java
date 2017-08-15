package com.netflix.exercise.batch.mapper;

import java.util.function.Function;

public class FileConstants {

	public static String NULL_VALUE = "\\N";
	
	
	public static <T> T getNullableValue(String value,Function<String, T> converter) {
		return NULL_VALUE.equals(value) ? null : converter.apply(value);
	}
}
