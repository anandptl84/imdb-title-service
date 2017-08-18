package com.netflix.exercise.config;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.netflix.exercise.batch.mapper.TitleBasicRowFieldSetMapper;
import com.netflix.exercise.batch.model.TitleBasicRow;
import com.netflix.exercise.batch.writer.TitleBasicItemWriter;

@Configuration
public class TitleBasicRowConfig {

	@Autowired
	public DataSource dataSource;

	@Autowired
	private TitleBasicRowFieldSetMapper titleBasicRowFieldSetMapper;

	@Value("${title.basics.path}")
	public String titleBasicsPath;

	@Bean(name = "titleBasicReader")
	public ItemReader<TitleBasicRow> titleBasicReader() {
		final FlatFileItemReader<TitleBasicRow> csvFileReader = new FlatFileItemReader<>();
		csvFileReader.setResource(new FileSystemResource(titleBasicsPath));
		csvFileReader.setLinesToSkip(741466);
		csvFileReader.setLineMapper(titleBasicRowMapper());
		return csvFileReader;
	}

	@Bean
	public LineMapper<TitleBasicRow> titleBasicRowMapper() {
		final DefaultLineMapper<TitleBasicRow> titleBasicRowMapper = new DefaultLineMapper<>();
		titleBasicRowMapper.setLineTokenizer(titleBasicRowTokenizer());
		titleBasicRowMapper.setFieldSetMapper(titleBasicRowFieldSetMapper);
		return titleBasicRowMapper;
	}

	@Bean
	public LineTokenizer titleBasicRowTokenizer() {
		final DelimitedLineTokenizer titleBasicRowTokenizer = new DelimitedLineTokenizer();
		titleBasicRowTokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
		titleBasicRowTokenizer.setNames(new String[] { "titleId", "titleType", "primaryTitle", "originalTitle",
				"isAdult", "startYear", "endYear", "runTimeMinutes", "genres" });
		return titleBasicRowTokenizer;
	}

	@Bean
	public TitleBasicItemWriter basicItemWriter() {
		return new TitleBasicItemWriter(jdbcTemplate());
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}
}
