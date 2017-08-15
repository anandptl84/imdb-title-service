package com.netflix.exercise.batch;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.netflix.exercise.batch.model.NameBasicRow;

@Component
public class NameKnownTitleWriter extends AbstractOneToManyEntityItemWriter<NameBasicRow> {

	private static final String INSERT_NAME_KNOWN_TITLE = "insert into name_known_title (name_id,  title_id) values (:nameId,:titleId)";
	
	
	public NameKnownTitleWriter(@Autowired JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, "nameId", "titleId", INSERT_NAME_KNOWN_TITLE);
	}

	@Override
	protected Optional<String> getAssociationValues(NameBasicRow t) {
		return Optional.ofNullable(t.getKnownTitles());
	}

	@Override
	protected String getEntityValue(NameBasicRow item) {
		return item.getNameId();
	}
}