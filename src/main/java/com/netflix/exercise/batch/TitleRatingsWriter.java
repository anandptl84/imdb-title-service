package com.netflix.exercise.batch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.netflix.exercise.batch.model.TitleBasicRow;
import com.netflix.exercise.batch.model.TitleRatingRow;

@Component
public class TitleRatingsWriter implements ItemWriter<TitleRatingRow> {
	
	private static final String INSERT_TITLE_RATINGS = "insert into title_ratings (title_id, rating, number_votes) values (?,?,?)";
	private final JdbcTemplate jdbcTemplate; 
	
	
	public TitleRatingsWriter(@Autowired JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void write(List<? extends TitleRatingRow> items) throws Exception {
		jdbcTemplate.batchUpdate(INSERT_TITLE_RATINGS, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TitleRatingRow titleRating = items.get(i);
				ps.setString(1, titleRating.getTitleId());
				if (titleRating.getAverageRating() != null) {
					ps.setDouble(2,titleRating.getAverageRating());
				} else {
					ps.setNull(2, java.sql.Types.DOUBLE);
				}
				if (titleRating.getNumberOfVotes() != null) {
					ps.setDouble(3,titleRating.getNumberOfVotes());
				} else {
					ps.setNull(3, java.sql.Types.DOUBLE);
				}
			}

			@Override
			public int getBatchSize() {
				return items.size();
			}

		  });
		
	}

}