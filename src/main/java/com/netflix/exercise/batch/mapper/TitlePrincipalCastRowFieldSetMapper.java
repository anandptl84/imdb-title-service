package com.netflix.exercise.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.netflix.exercise.batch.model.TitlePrincipalCastRow;

@Component
public class TitlePrincipalCastRowFieldSetMapper implements FieldSetMapper<TitlePrincipalCastRow> {

	@Override
	public TitlePrincipalCastRow mapFieldSet(FieldSet fieldSet) throws BindException {
		TitlePrincipalCastRow result = new TitlePrincipalCastRow();
		result.setTitleId(fieldSet.readString(0));
		result.setPrincipalCasts(fieldSet.readString(1));
		return result;
	}

}