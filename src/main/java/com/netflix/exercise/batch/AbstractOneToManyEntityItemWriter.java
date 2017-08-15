package com.netflix.exercise.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class AbstractOneToManyEntityItemWriter<T> implements ItemWriter<T> {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String entityKey;
	private final String associationKey;
	private final String sql;

	protected AbstractOneToManyEntityItemWriter(@Autowired JdbcTemplate jdbcTemplate, String entityKey,
			String associationKey, String sql) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		this.entityKey = entityKey;
		this.associationKey = associationKey;
		this.sql = sql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends T> items) throws Exception {
		List<Map<String, String>> params = new ArrayList<>();
		for (T item : items) {
			Optional<String> associationValues = getAssociationValues(item);
			if (associationValues.isPresent()) {
				for (String genre : associationValues.get().split(",")) {
					Map<String, String> param_map = new HashMap<>();
					param_map.put(entityKey, getEntityValue(item));
					param_map.put(associationKey, genre);
					params.add(param_map);
				}
			}
		}
		this.jdbcTemplate.batchUpdate(sql, params.toArray(new HashMap[params.size()]));
	}

	protected abstract Optional<String> getAssociationValues(T t);

	protected abstract String getEntityValue(T item);

}
