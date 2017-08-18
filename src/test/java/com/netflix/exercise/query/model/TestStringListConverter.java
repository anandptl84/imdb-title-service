package com.netflix.exercise.query.model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import jersey.repackaged.com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class TestStringListConverter {

	private static final String VALUE_1 = "value1";
	private static final String VALUE_2 = "vaule2";
	private static final String VALUE_3 = "value3";

	private static final String databaseValue = String.format("%s,%s,%s", VALUE_1, VALUE_2, VALUE_3);
	private static final List<String> entityValue = Lists.newArrayList(VALUE_1, VALUE_2, VALUE_3);

	private final StringListConverter cut = new StringListConverter();

	@Test
	public void testConvertToDatabaseColumn() {
		final String actualValue = cut.convertToDatabaseColumn(entityValue);
		assertEquals(databaseValue, actualValue);
	}

	@Test
	public void testConvertToEntityAttribute() {
		final List<String> actualValue = cut.convertToEntityAttribute(databaseValue);
		assertEquals(entityValue, actualValue);
	}

	@Test
	public void testNullDatabaseValue() {
		final List<String> actualValue = cut.convertToEntityAttribute(null);
		assertEquals(Lists.newArrayList(), actualValue);
	}
}
