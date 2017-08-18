package com.netflix.exercise.batch.mapper;


import static com.netflix.exercise.batch.FileReaderUtils.getNullableValue;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.netflix.exercise.batch.model.NameBasicRow;

@Component
public class NameBasicRowFieldSetMapper implements FieldSetMapper<NameBasicRow> {

	@Override
	public NameBasicRow mapFieldSet(FieldSet fieldSet) throws BindException {
		NameBasicRow result = new NameBasicRow();
		result.setNameId(fieldSet.readString(0));
		result.setPrimaryName(fieldSet.readString(1));
		result.setBirthYear(getNullableValue(fieldSet.readString(2), Integer::valueOf));
		result.setDeathYear(getNullableValue(fieldSet.readString(3), Integer::valueOf));
		result.setPrimaryProfession(fieldSet.readString(4));
		result.setKnownTitles(fieldSet.readString(5));
		return result;
	}

}
