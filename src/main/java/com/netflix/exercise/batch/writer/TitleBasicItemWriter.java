package com.netflix.exercise.batch.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.netflix.exercise.batch.model.TitleBasicRow;

@Component
public class TitleBasicItemWriter implements ItemWriter<TitleBasicRow> {
	
	private static final String INSERT_TITLE_BASIC = "insert into title_basic (id, type, primary_title, original_title, is_adult, start_year, end_year, run_time_minutes, genres) values (?,?,?,?,?,?,?,?,?)";
	private final JdbcTemplate jdbcTemplate; 
	
	
	public TitleBasicItemWriter(@Autowired JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void write(List<? extends TitleBasicRow> items) throws Exception {
		jdbcTemplate.batchUpdate(INSERT_TITLE_BASIC, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TitleBasicRow titleBasic = items.get(i);
				ps.setString(1, titleBasic.getTitleId());
				ps.setString(2, titleBasic.getTitleType());
				ps.setString(3, titleBasic.getPrimaryTitle());
				ps.setString(4,  titleBasic.getOriginalTitle());
				ps.setInt(5, titleBasic.isAdult()? 1: 0);
				ps.setInt(6, titleBasic.getStartYear());
				if (titleBasic.getEndYear() == null) {
					ps.setNull(7,java.sql.Types.INTEGER);
				} else {
					ps.setInt(7, Integer.valueOf(titleBasic.getEndYear()));
				}
				if (titleBasic.getRunTimeMinutes() == null) {
					ps.setNull(8, java.sql.Types.INTEGER);
				} else {
					ps.setInt(8, titleBasic.getRunTimeMinutes());
				}
				ps.setString(9, titleBasic.getGenres());
			}

			@Override
			public int getBatchSize() {
				return items.size();
			}

		  });
		
	}

}
