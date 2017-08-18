package com.netflix.exercise.batch.writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * Absctact entity to provide templated code to create one to many relationship instructions for given type.
 *
 * E.g. object with parent id =1 , and child values as = 1,2,3 will create three inserts for
 * (1,1), (1,2), (1,3)
 *
 * @author anand
 *
 * @param <T>
 */
public abstract class AbstractOneToManyEntityItemWriter<T> implements ItemWriter<T> {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String entityKey;
	private final String associationKey;
	private final String sql;

	protected AbstractOneToManyEntityItemWriter(NamedParameterJdbcTemplate jdbcTemplate, String entityKey,
			String associationKey, String sql) {
		this.jdbcTemplate = jdbcTemplate;
		this.entityKey = entityKey;
		this.associationKey = associationKey;
		this.sql = sql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends T> items) throws Exception {
		final List<Map<String, String>> params = new ArrayList<>();
		for (final T item : items) {
			final Optional<String> associationValues = getAssociationValues(item);
			if (associationValues.isPresent()) {
				for (final String genre : associationValues.get().split(",")) {
					final Map<String, String> param_map = new HashMap<>();
					param_map.put(entityKey, getEntityValue(item));
					param_map.put(associationKey, genre);
					params.add(param_map);
				}
				this.jdbcTemplate.batchUpdate(sql, params.toArray(new HashMap[params.size()]));
			}
		}

	}

	protected abstract Optional<String> getAssociationValues(T t);

	protected abstract String getEntityValue(T item);

}
