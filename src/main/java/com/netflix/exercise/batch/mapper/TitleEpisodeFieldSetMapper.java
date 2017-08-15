package com.netflix.exercise.batch.mapper;

import static com.netflix.exercise.batch.mapper.FileConstants.getNullableValue;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.netflix.exercise.batch.model.TitleEpisodeRow;

@Component
public class TitleEpisodeFieldSetMapper implements FieldSetMapper<TitleEpisodeRow> {

	@Override
	public TitleEpisodeRow mapFieldSet(FieldSet fieldSet) throws BindException {
		TitleEpisodeRow result = new TitleEpisodeRow();
		result.setEpisodeId(fieldSet.readString(0));
		result.setParentTitleId(fieldSet.readString(1));
		result.setSeasonNumber(getNullableValue(fieldSet.readString(2), Integer::valueOf));
		result.setEpisodeNumber(getNullableValue(fieldSet.readString(2), Integer::valueOf));
		return result;
	}

}
