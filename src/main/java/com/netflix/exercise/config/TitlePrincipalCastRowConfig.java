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
import com.netflix.exercise.batch.TitlePrincipalCastWriter;
import com.netflix.exercise.batch.mapper.TitleEpisodeFieldSetMapper;
import com.netflix.exercise.batch.mapper.TitlePrincipalCastRowFieldSetMapper;
import com.netflix.exercise.batch.model.TitleCrewRow;
import com.netflix.exercise.batch.model.TitleEpisodeRow;
import com.netflix.exercise.batch.model.TitlePrincipalCastRow;

import jersey.repackaged.com.google.common.collect.Lists;

@Configuration
public class TitlePrincipalCastRowConfig {

	@Autowired
	public DataSource dataSource;
	
	@Autowired
	public TitlePrincipalCastRowFieldSetMapper titlePrincipleCastRowFieldSetMapper;

	
	@Value("${title.basic.file.path}")
	public String titleCrewFilePath;


	@Bean
	public ItemReader<TitlePrincipalCastRow> titlePrincipleCastRowReader() {
		FlatFileItemReader<TitlePrincipalCastRow> csvFileReader = new FlatFileItemReader<>();
		csvFileReader.setResource(new FileSystemResource("/Users/anand/exercise/title.principals.tsv"));
		csvFileReader.setLinesToSkip(1);
		csvFileReader.setLineMapper(titlePrincipleCastMapper());
		return csvFileReader;

	}
	
	@Bean
	public LineMapper<TitlePrincipalCastRow> titlePrincipleCastMapper(){
		DefaultLineMapper<TitlePrincipalCastRow> titleCrewRowMapper = new DefaultLineMapper<>();
		titleCrewRowMapper.setLineTokenizer(titlePrincipalCastLineTokenizer());
		titleCrewRowMapper.setFieldSetMapper(titlePrincipleCastRowFieldSetMapper);
		return titleCrewRowMapper;
	}

	@Bean
	public LineTokenizer titlePrincipalCastLineTokenizer() {
		DelimitedLineTokenizer titlePrincipalCastLineTokenizer = new DelimitedLineTokenizer();
		titlePrincipalCastLineTokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
		titlePrincipalCastLineTokenizer.setNames(new String[] { "titleId", "principalCasts"});
		return titlePrincipalCastLineTokenizer;
	}
	

	
	@SuppressWarnings("unchecked")
	@Bean
	public ItemWriter<TitlePrincipalCastRow> compositeTitlePrincipalCastRowWriter() {
		CompositeItemWriter<TitlePrincipalCastRow> compositeWriter = new CompositeItemWriter<>();
		compositeWriter.setDelegates(Lists.newArrayList(titlePrincipalCastRowWriter()));
		return compositeWriter;
	}
	
	@Bean
	public  ItemWriter<TitlePrincipalCastRow> titlePrincipalCastRowWriter() {
		return new TitlePrincipalCastWriter(jdbcTemplate());
		
	}
	
	@Bean 
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

}
