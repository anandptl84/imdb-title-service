package com.netflix.exercise.batch.mapper;

import static com.netflix.exercise.batch.mapper.FileConstants.getNullableValue;

import java.util.function.Function;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import com.netflix.exercise.batch.model.TitleCrewRow;

@Component
public class TitleCrewRowFieldSetMapper implements FieldSetMapper<TitleCrewRow> {

	@Override
	public TitleCrewRow mapFieldSet(FieldSet fieldSet) throws BindException {
		TitleCrewRow result = new TitleCrewRow();
		result.setTitleId(fieldSet.readString(0));
		result.setDirectors(getNullableValue(fieldSet.readString(1), Function.identity()));
		result.setWriter(getNullableValue(fieldSet.readString(1), Function.identity()));
		return result;
	}

}
