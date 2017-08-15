package com.netflix.exercise.config;



import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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

import com.netflix.exercise.batch.TitleBasicItemWriter;
import com.netflix.exercise.batch.TitleGenreWriter;
import com.netflix.exercise.batch.mapper.TitleBasicRowFieldSetMapper;
import com.netflix.exercise.batch.model.TitleBasicRow;

import jersey.repackaged.com.google.common.collect.Lists;

@Configuration
public class TitleBasicRowConfig {
	
	@Autowired
	public DataSource dataSource;
	
	@Autowired
	private TitleBasicRowFieldSetMapper titleBasicRowFieldSetMapper;

	
	@Value("${title.basic.file.path}")
	public String titleBasicFilePath;

	@Bean(name="titleBasicReader")
	public ItemReader<TitleBasicRow> titleBasicReader() {
		FlatFileItemReader<TitleBasicRow> csvFileReader = new FlatFileItemReader<>();
		csvFileReader.setResource(new FileSystemResource("/Users/anand/exercise/title.basics.tsv"));
		csvFileReader.setLinesToSkip(1);
		csvFileReader.setLineMapper(titleBasicRowMapper());
		return csvFileReader;
	}
	
	@Bean
	public LineMapper<TitleBasicRow> titleBasicRowMapper(){
		DefaultLineMapper<TitleBasicRow> titleBasicRowMapper = new DefaultLineMapper<>();
		titleBasicRowMapper.setLineTokenizer(titleBasicRowTokenizer());
		titleBasicRowMapper.setFieldSetMapper(titleBasicRowFieldSetMapper);
		return titleBasicRowMapper;
	}

	@Bean
	public LineTokenizer titleBasicRowTokenizer() {
		DelimitedLineTokenizer titleBasicRowTokenizer = new DelimitedLineTokenizer();
		titleBasicRowTokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
		titleBasicRowTokenizer.setNames(new String[] { "titleId", "titleType" , "primaryTitle", "originalTitle", "isAdult", "startYear", "endYear", "runTimeMinutes", "genres"});
		return titleBasicRowTokenizer;
	}
	

	
	@SuppressWarnings("unchecked")
	@Bean
	public ItemWriter<TitleBasicRow> compositeTitleBasicRowWriter() {
		CompositeItemWriter<TitleBasicRow> compositeWriter = new CompositeItemWriter<>();
		compositeWriter.setDelegates(Lists.newArrayList(basicItemWriter(), genreWriter()));
		return compositeWriter;
	}
	
	@Bean
	public TitleBasicItemWriter basicItemWriter() {
		return new TitleBasicItemWriter(jdbcTemplate());
	}
	
	@Bean
	public TitleGenreWriter genreWriter() {
		return new TitleGenreWriter(jdbcTemplate());
	}
	
	@Bean 
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	

}
