package com.netflix.exercise.batch.writer;

import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.netflix.exercise.batch.model.TitlePrincipalCastRow;

@Component
public class TitlePrincipalCastWriter extends AbstractOneToManyEntityItemWriter<TitlePrincipalCastRow> {

	private final static String INSERT_TITLE_PRINCIPAL_CAST = "insert into title_principal_cast (title_id, cast_id) values (:titleId,:castId)";

	public TitlePrincipalCastWriter(NamedParameterJdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, "titleId", "castId", INSERT_TITLE_PRINCIPAL_CAST);
	}

	@Override
	protected Optional<String> getAssociationValues(TitlePrincipalCastRow t) {
		return Optional.ofNullable(t.getPrincipalCasts());
	}

	@Override
	protected String getEntityValue(TitlePrincipalCastRow item) {
		return item.getTitleId();
	}
}
