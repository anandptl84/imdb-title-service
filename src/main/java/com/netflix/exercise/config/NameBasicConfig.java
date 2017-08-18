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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.netflix.exercise.batch.mapper.NameBasicRowFieldSetMapper;
import com.netflix.exercise.batch.model.NameBasicRow;
import com.netflix.exercise.batch.writer.NameBasicItemWriter;

import jersey.repackaged.com.google.common.collect.Lists;

@Configuration
public class NameBasicConfig {

	@Autowired
	public DataSource dataSource;

	@Autowired
	private NameBasicRowFieldSetMapper nameBasicRowFieldSetMapper;

	@Value("${name.basics.path}")
	public String nameBasicsPath;

	@Bean
	public ItemReader<NameBasicRow> nameBasicRowReader() {
		final FlatFileItemReader<NameBasicRow> csvFileReader = new FlatFileItemReader<>();
		csvFileReader.setResource(new FileSystemResource(nameBasicsPath));
		csvFileReader.setLinesToSkip(1);
		csvFileReader.setLineMapper(nameBasicRowMapper());
		return csvFileReader;

	}

	@Bean
	public LineMapper<NameBasicRow> nameBasicRowMapper() {
		final DefaultLineMapper<NameBasicRow> nameBasicRowMapper = new DefaultLineMapper<>();
		nameBasicRowMapper.setLineTokenizer(nameBasicRowLineTokenizer());
		nameBasicRowMapper.setFieldSetMapper(nameBasicRowFieldSetMapper);
		return nameBasicRowMapper;
	}

	@Bean
	public LineTokenizer nameBasicRowLineTokenizer() {
		final DelimitedLineTokenizer nameBasicRowTokenizer = new DelimitedLineTokenizer();
		nameBasicRowTokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
		nameBasicRowTokenizer.setNames(new String[] { "nameId", "primaryName", "birthYear", "deathYear",
				"primaryProfession", "knownForTitles" });
		return nameBasicRowTokenizer;
	}

	@SuppressWarnings("unchecked")
	@Bean
	public ItemWriter<NameBasicRow> compositeNameBasicWriter() {
		final CompositeItemWriter<NameBasicRow> compositeWriter = new CompositeItemWriter<>();
		compositeWriter.setDelegates(Lists.newArrayList(nameBasicRowWriter()));
		return compositeWriter;
	}

	@Bean
	public ItemWriter<NameBasicRow> nameBasicRowWriter() {
		return new NameBasicItemWriter(jdbcTemplate());
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public NamedParameterJdbcTemplate namedJdbcTemplate() {
		return new NamedParameterJdbcTemplate(jdbcTemplate());
	}
}