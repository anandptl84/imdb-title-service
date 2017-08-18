package com.netflix.exercise.batch.mapper;
import static com.netflix.exercise.batch.FileReaderUtils.getNullableValue;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.netflix.exercise.batch.model.TitleRatingRow;

@Component
public class TitleRatingRowFieldSetMapper  implements FieldSetMapper<TitleRatingRow> {

	@Override
	public TitleRatingRow mapFieldSet(FieldSet fieldSet) throws BindException {
		TitleRatingRow result = new TitleRatingRow();
		result.setTitleId(fieldSet.readString(0));
		result.setAverageRating(getNullableValue(fieldSet.readString(1), Double::valueOf));
		result.setNumberOfVotes(getNullableValue(fieldSet.readString(2), Integer::valueOf));
		return result;
	}

}