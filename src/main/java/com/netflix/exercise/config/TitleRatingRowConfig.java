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

import com.netflix.exercise.batch.TitleCrewDirectorWriter;
import com.netflix.exercise.batch.TitleCrewWritersWriter;
import com.netflix.exercise.batch.TitleRatingsWriter;
import com.netflix.exercise.batch.mapper.TitlePrincipalCastRowFieldSetMapper;
import com.netflix.exercise.batch.mapper.TitleRatingRowFieldSetMapper;
import com.netflix.exercise.batch.model.TitleCrewRow;
import com.netflix.exercise.batch.model.TitleEpisodeRow;
import com.netflix.exercise.batch.model.TitlePrincipalCastRow;
import com.netflix.exercise.batch.model.TitleRatingRow;

import jersey.repackaged.com.google.common.collect.Lists;

@Configuration
public class TitleRatingRowConfig {
	
	@Autowired
	public DataSource dataSource;
	
	@Autowired
	private TitleRatingRowFieldSetMapper titleRatingRowFieldSetMapper;

	
	@Value("${title.basic.file.path}")
	public String titleCrewFilePath;



	@Bean
	public ItemReader<TitleRatingRow> titleRatingRowReader() {
		FlatFileItemReader<TitleRatingRow> csvFileReader = new FlatFileItemReader<>();
		csvFileReader.setResource(new FileSystemResource("/Users/anand/exercise/title.ratings.tsv"));
		csvFileReader.setLinesToSkip(1);
		csvFileReader.setLineMapper(titleRatingRowMapper());
		return csvFileReader;

	}
	
	@Bean
	public LineMapper<TitleRatingRow> titleRatingRowMapper(){
		DefaultLineMapper<TitleRatingRow> titleRatingRowMapper = new DefaultLineMapper<>();
		titleRatingRowMapper.setLineTokenizer(titleRatingLineTokenizer());
		titleRatingRowMapper.setFieldSetMapper(titleRatingRowFieldSetMapper);
		return titleRatingRowMapper;
	}

	@Bean
	public LineTokenizer titleRatingLineTokenizer() {
		DelimitedLineTokenizer titleRatingLineTokenizer = new DelimitedLineTokenizer();
		titleRatingLineTokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
		titleRatingLineTokenizer.setNames(new String[] { "titleId", "avgRaging", "numOfVotes"});
		return titleRatingLineTokenizer;
	}
	

	
	@SuppressWarnings("unchecked")
	@Bean
	public ItemWriter<TitleRatingRow> compositeTitleRatingWriter() {
		CompositeItemWriter<TitleRatingRow> compositeWriter = new CompositeItemWriter<>();
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
