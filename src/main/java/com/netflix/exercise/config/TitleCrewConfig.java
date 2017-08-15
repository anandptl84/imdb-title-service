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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.netflix.exercise.batch.TitleCrewDirectorWriter;
import com.netflix.exercise.batch.TitleCrewWritersWriter;
import com.netflix.exercise.batch.mapper.TitleCrewRowFieldSetMapper;
import com.netflix.exercise.batch.model.TitleCrewRow;

import jersey.repackaged.com.google.common.collect.Lists;

@Configuration
public class TitleCrewConfig {
	
	@Autowired
	public DataSource dataSource;
	
	@Autowired
	private TitleCrewRowFieldSetMapper titleCrewRowFieldSetMapper;

	
	@Value("${title.basic.file.path}")
	public String titleCrewFilePath;



	@Bean
	ItemReader<TitleCrewRow> titleCrewReader() {
		FlatFileItemReader<TitleCrewRow> csvFileReader = new FlatFileItemReader<>();
		csvFileReader.setResource(new FileSystemResource("/Users/anand/exercise/title.crew.tsv"));
		csvFileReader.setLinesToSkip(1);
		csvFileReader.setLineMapper(titleCrewLineMapper());
		return csvFileReader;
	}
	
	@Bean
	public LineMapper<TitleCrewRow> titleCrewLineMapper(){
		DefaultLineMapper<TitleCrewRow> titleCrewRowMapper = new DefaultLineMapper<>();
		titleCrewRowMapper.setLineTokenizer(titleCrewLineTokenizer());
		titleCrewRowMapper.setFieldSetMapper(titleCrewRowFieldSetMapper);
		return titleCrewRowMapper;
	}

	@Bean
	public LineTokenizer titleCrewLineTokenizer() {
		DelimitedLineTokenizer titleCrewLineTokenizer = new DelimitedLineTokenizer();
		titleCrewLineTokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
		titleCrewLineTokenizer.setNames(new String[] { "titleId", "directors" , "writers"});
		return titleCrewLineTokenizer;
	}
	

	
	@SuppressWarnings("unchecked")
	@Bean
	public ItemWriter<TitleCrewRow> compositeTitleCrewRowWriter() {
		CompositeItemWriter<TitleCrewRow> compositeWriter = new CompositeItemWriter<>();
		compositeWriter.setDelegates(Lists.newArrayList(crewDirectorsWriter(), crewWritersWriter()));
		return compositeWriter;
	}
	
	@Bean
	public TitleCrewDirectorWriter crewDirectorsWriter() {
		return new TitleCrewDirectorWriter(jdbcTemplate());
	}
	
	public TitleCrewWritersWriter crewWritersWriter() {
		return new TitleCrewWritersWriter(jdbcTemplate());
	}
	
	@Bean 
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	


}
