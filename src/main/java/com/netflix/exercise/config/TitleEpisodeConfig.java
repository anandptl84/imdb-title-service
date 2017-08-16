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
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.netflix.exercise.batch.TitleEpisodeWriter;
import com.netflix.exercise.batch.mapper.TitleEpisodeFieldSetMapper;
import com.netflix.exercise.batch.model.TitleEpisodeRow;

import jersey.repackaged.com.google.common.collect.Lists;

//@Configuration
public class TitleEpisodeConfig {

	@Autowired
	public DataSource dataSource;

	@Autowired
	private TitleEpisodeFieldSetMapper titleEpisodeFieldSetMapper;

	@Value("${title.basic.file.path}")
	public String titleCrewFilePath;

	@Bean
	public ItemReader<TitleEpisodeRow> titleEpisodeReader() {
		final FlatFileItemReader<TitleEpisodeRow> csvFileReader = new FlatFileItemReader<>();
		csvFileReader.setResource(new FileSystemResource("/Users/anand/exercise/title.episode.tsv"));
		csvFileReader.setLinesToSkip(1);
		csvFileReader.setLineMapper(titleEpisodeRowMapper());
		return csvFileReader;

	}

	@Bean
	public LineMapper<TitleEpisodeRow> titleEpisodeRowMapper() {
		final DefaultLineMapper<TitleEpisodeRow> titleEpisodeRowMapper = new DefaultLineMapper<>();
		titleEpisodeRowMapper.setLineTokenizer(titleEpisodeLineTokenizer());
		titleEpisodeRowMapper.setFieldSetMapper(titleEpisodeFieldSetMapper);
		return titleEpisodeRowMapper;
	}

	@Bean
	public LineTokenizer titleEpisodeLineTokenizer() {
		final DelimitedLineTokenizer titleEpisodeTokenizer = new DelimitedLineTokenizer();
		titleEpisodeTokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
		titleEpisodeTokenizer.setNames(new String[] { "episodeId", "parentTitleId", "seasonNumber", "episodeNumber" });
		return titleEpisodeTokenizer;
	}

	@SuppressWarnings("unchecked")
	@Bean
	public ItemWriter<TitleEpisodeRow> compositeTitleEpisodeRowWriter() {
		final CompositeItemWriter<TitleEpisodeRow> compositeWriter = new CompositeItemWriter<>();
		compositeWriter.setDelegates(Lists.newArrayList(titleEpisodeWriter()));
		return compositeWriter;
	}

	@Bean
	public ItemWriter<TitleEpisodeRow> titleEpisodeWriter() {
		return new TitleEpisodeWriter(jdbcTemplate());
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

}
