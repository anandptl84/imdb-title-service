package com.netflix.exercise.batch;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.netflix.exercise.batch.model.TitleBasicRow;

@Component
public class TitleGenreWriter extends AbstractOneToManyEntityItemWriter<TitleBasicRow> {

	private static final String INSERT_TITLE_GENRE = "insert into title_genre (title_id,  genre) values (:titleId,:genre)";
	
	
	public TitleGenreWriter(@Autowired JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, "titleId", "genre", INSERT_TITLE_GENRE);
	}

	@Override
	protected Optional<String> getAssociationValues(TitleBasicRow t) {
		return Optional.ofNullable(t.getGenres());
	}

	@Override
	protected String getEntityValue(TitleBasicRow item) {
		return item.getTitleId();
	}

}
