package com.netflix.exercise.batch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.netflix.exercise.batch.model.TitleBasicRow;
import com.netflix.exercise.batch.model.TitleEpisodeRow;

public class TitleEpisodeWriter implements ItemWriter<TitleEpisodeRow> {
	
	private static final String INSERT_TITLE_BASIC = "insert into title_episode (episode_id, title_id, season_number, episode_number) values (?,?,?,?)";
	private final JdbcTemplate jdbcTemplate; 
	
	
	public TitleEpisodeWriter(@Autowired JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void write(List<? extends TitleEpisodeRow> items) throws Exception {
		jdbcTemplate.batchUpdate(INSERT_TITLE_BASIC, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TitleEpisodeRow titleBasic = items.get(i);
				ps.setString(1, titleBasic.getEpisodeId());
				ps.setString(2, titleBasic.getParentTitleId());
				
				if (titleBasic.getSeasonNumber() != null) {
					ps.setInt(3, titleBasic.getSeasonNumber());
				} else {
					ps.setNull(3, Types.INTEGER);
				}
				if (titleBasic.getEpisodeNumber() != null) {
					ps.setInt(4,  titleBasic.getEpisodeNumber());
				} else {
					ps.setNull(4, Types.INTEGER);
				}
				
			}

			@Override
			public int getBatchSize() {
				return items.size();
			}

		  });
		
	}
}
