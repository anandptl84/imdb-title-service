package com.netflix.exercise.batch;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;

import com.netflix.exercise.batch.model.TitleCrewRow;

public class TitleCrewWritersWriter  extends AbstractOneToManyEntityItemWriter<TitleCrewRow> {
	
	private final static String INSERT_TITLE_WRITER = "insert into title_writer (title_id, writer_id) values (:titleId,:writerId)";
	
	public TitleCrewWritersWriter(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, "titleId", "writerId", INSERT_TITLE_WRITER);
	}

	@Override
	protected Optional<String> getAssociationValues(TitleCrewRow t) {
		return Optional.ofNullable(t.getWriter());
	}

	@Override
	protected String getEntityValue(TitleCrewRow item) {
		return item.getTitleId();
	}

}
