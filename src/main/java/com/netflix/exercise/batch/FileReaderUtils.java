package com.netflix.exercise.batch;

import java.util.function.Function;

public class FileReaderUtils {

	public static String NULL_VALUE = "\\N";


	/**
	 * Interprets \\N as null value and if it is returns java null
	 * if value not a \\N it will apply the converter function to return the converted value type.
	 *
	 * @param value
	 * @param converter
	 * @return
	 */
	public static <T> T getNullableValue(String value,Function<String, T> converter) {
		return NULL_VALUE.equals(value) ? null : converter.apply(value);
	}
}
