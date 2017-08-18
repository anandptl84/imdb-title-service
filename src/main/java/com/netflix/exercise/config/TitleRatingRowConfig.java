package com.netflix.exercise.config;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.netflix.exercise.batch.mapper.TitleRatingRowFieldSetMapper;
import com.netflix.exercise.batch.model.TitleRatingRow;
import com.netflix.exercise.batch.writer.TitleRatingsWriter;

import jersey.repackaged.com.google.common.collect.Lists;

@Configuration
public class TitleRatingRowConfig {

	@Autowired
	public DataSource dataSource;

	@Autowired
	private TitleRatingRowFieldSetMapper titleRatingRowFieldSetMapper;

	@Value("${title.ratings.path}")
	public String titleRatingsPath;

	@Bean
	public ItemReader<TitleRatingRow> titleRatingRowReader() {
		final FlatFileItemReader<TitleRatingRow> csvFileReader = new FlatFileItemReader<>();
		csvFileReader.setResource(new FileSystemResource(titleRatingsPath));
		csvFileReader.setLinesToSkip(1);
		csvFileReader.setLineMapper(titleRatingRowMapper());
		return csvFileReader;

	}

	@Bean
	public LineMapper<TitleRatingRow> titleRatingRowMapper() {
		final DefaultLineMapper<TitleRatingRow> titleRatingRowMapper = new DefaultLineMapper<>();
		titleRatingRowMapper.setLineTokenizer(titleRatingLineTokenizer());
		titleRatingRowMapper.setFieldSetMapper(titleRatingRowFieldSetMapper);
		return titleRatingRowMapper;
	}

	@Bean
	public LineTokenizer titleRatingLineTokenizer() {
		final DelimitedLineTokenizer titleRatingLineTokenizer = new DelimitedLineTokenizer();
		titleRatingLineTokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
		titleRatingLineTokenizer.setNames(new String[] { "titleId", "avgRaging", "numOfVotes" });
		return titleRatingLineTokenizer;
	}

	@SuppressWarnings("unchecked")
	@Bean
	public ItemWriter<TitleRatingRow> compositeTitleRatingWriter() {
		final CompositeItemWriter<TitleRatingRow> compositeWriter = new CompositeItemWriter<>();
		compositeWriter.setDelegates(Lists.newArrayList(titleRatingsWriter()));
		return compositeWriter;
	}

	@Bean
	public TitleRatingsWriter titleRatingsWriter() {
		return new TitleRatingsWriter(jdbcTemplate());
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}
}
