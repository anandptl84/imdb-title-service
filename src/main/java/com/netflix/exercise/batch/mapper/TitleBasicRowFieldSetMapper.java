package com.netflix.exercise.batch.mapper;

import static com.netflix.exercise.batch.mapper.FileConstants.getNullableValue;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.netflix.exercise.batch.model.TitleBasicRow;

@Component
public class TitleBasicRowFieldSetMapper implements FieldSetMapper<TitleBasicRow> {

	@Override
	public TitleBasicRow mapFieldSet(FieldSet fieldSet) throws BindException {
		TitleBasicRow result = new TitleBasicRow();
		result.setTitleId(fieldSet.readString(0));
		result.setTitleType(fieldSet.readString(1));
		result.setPrimaryTitle(fieldSet.readString(2));
		result.setOriginalTitle(fieldSet.readString(3));
		result.setAdult(Boolean.valueOf(fieldSet.readBoolean(4)));
		result.setStartYear(Integer.valueOf(fieldSet.readString(5)));
		result.setEndYear(getNullableValue(fieldSet.readString(6), Integer::valueOf));
		result.setRunTimeMinutes(getNullableValue(fieldSet.readString(7), Integer::valueOf));
		result.setGenres(fieldSet.readString(8));
		return result;
	}

}
