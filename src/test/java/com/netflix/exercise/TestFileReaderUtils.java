package com.netflix.exercise;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.netflix.exercise.batch.FileReaderUtils;

public class TestFileReaderUtils {

	@Test
	public void testNonNullIntegerValue() {
		final Integer actualValue = FileReaderUtils.getNullableValue("123", Integer::valueOf);
		assertTrue("expected values are not same", 123 == actualValue);
	}

	@Test
	public void testNullValue() {
		final String actualValue = FileReaderUtils.getNullableValue(FileReaderUtils.NULL_VALUE, String::valueOf);
		assertTrue("expected null value but actual is differnt", null == actualValue);
	}
}
