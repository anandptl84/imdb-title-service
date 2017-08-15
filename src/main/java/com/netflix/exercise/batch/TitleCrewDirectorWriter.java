package com.netflix.exercise.batch;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import com.netflix.exercise.batch.model.TitleCrewRow;

public class TitleCrewDirectorWriter extends AbstractOneToManyEntityItemWriter<TitleCrewRow> {
	
	private final static String INSERT_TITLE_DIRECTORS = "insert into title_director (title_id, director_id) values (:titleId,:directorId)";
	
	public TitleCrewDirectorWriter(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, "titleId", "directorId", INSERT_TITLE_DIRECTORS);
	}

	@Override
	protected Optional<String> getAssociationValues(TitleCrewRow t) {
		return Optional.ofNullable(t.getDirectors());
	}

	@Override
	protected String getEntityValue(TitleCrewRow item) {
		return item.getTitleId();
	}

}
