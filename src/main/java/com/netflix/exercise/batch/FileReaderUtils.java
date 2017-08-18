package com.netflix.exercise.batch;

import java.util.function.Function;

public class FileReaderUtils {

	public static String NULL_VALUE = "\\N";
	
	
	public static <T> T getNullableValue(String value,Function<String, T> converter) {
		return NULL_VALUE.equals(value) ? null : converter.apply(value);
	}
}
