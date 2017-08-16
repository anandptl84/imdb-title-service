package com.netflix.exercise.batch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.netflix.exercise.batch.model.NameBasicRow;

@Component
public class NameBasicItemWriter implements ItemWriter<NameBasicRow> {
	private static final String INSERT_NAME_BASIC = "insert into name_basic (name_id, primary_name, birth_year, death_year) values (?,?,?,?)";
	private final JdbcTemplate jdbcTemplate;

	public NameBasicItemWriter(@Autowired JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void write(List<? extends NameBasicRow> items) throws Exception {
		jdbcTemplate.batchUpdate(INSERT_NAME_BASIC, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				final NameBasicRow nameBasic = items.get(i);
				ps.setString(1, nameBasic.getNameId());
				ps.setString(2, nameBasic.getPrimaryName());

				if (nameBasic.getBirthYear() == null) {
					ps.setNull(3, java.sql.Types.INTEGER);
				} else {
					ps.setInt(3, nameBasic.getBirthYear());
				}
				if (nameBasic.getDeathYear() == null) {
					ps.setNull(4, java.sql.Types.INTEGER);
				} else {
					ps.setInt(4, nameBasic.getDeathYear());
				}
			}

			@Override
			public int getBatchSize() {
				return items.size();
			}

		});

	}
}
