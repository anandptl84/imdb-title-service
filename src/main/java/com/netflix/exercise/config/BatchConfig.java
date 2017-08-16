package com.netflix.exercise.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import com.netflix.exercise.batch.model.NameBasicRow;
import com.netflix.exercise.batch.model.TitleBasicRow;
import com.netflix.exercise.batch.model.TitlePrincipalCastRow;
import com.netflix.exercise.batch.model.TitleRatingRow;

@Configuration
@Import({ TitleBasicRowConfig.class, TitlePrincipalCastRowConfig.class, TitleRatingRowConfig.class,
		NameBasicConfig.class })
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("titleBasicReader")
	public ItemReader<TitleBasicRow> titleBasicReader;

	@Autowired
	@Qualifier("basicItemWriter")
	public ItemWriter<TitleBasicRow> compositeTitleBasicRowWriter;

	// @Autowired
	// @Qualifier("titleCrewReader")
	// public ItemReader<TitleCrewRow> titleCrewReader;
	//
	// @Autowired
	// @Qualifier("compositeTitleCrewRowWriter")
	// public ItemWriter<TitleCrewRow> compositeTitleCrewRowWriter;

	@Autowired
	@Qualifier("titlePrincipleCastRowReader")
	public ItemReader<TitlePrincipalCastRow> titlePrincipalCastRowReader;

	@Autowired
	@Qualifier("compositeTitlePrincipalCastRowWriter")
	public ItemWriter<TitlePrincipalCastRow> compositeTitlePrincipalCastRowWriter;
	//
	// @Autowired
	// @Qualifier("titleEpisodeReader")
	// public ItemReader<TitleEpisodeRow> titleEpisodeReader;
	//
	// @Autowired
	// @Qualifier("compositeTitleEpisodeRowWriter")
	// public ItemWriter<TitleEpisodeRow> compositeTitleEpisodeRowWriter;

	@Autowired
	@Qualifier("titleRatingRowReader")
	public ItemReader<TitleRatingRow> titleRatingRowReader;

	@Autowired
	@Qualifier("compositeTitleRatingWriter")
	public ItemWriter<TitleRatingRow> compositeTitleRatingWriter;

	@Autowired
	@Qualifier("nameBasicRowReader")
	public ItemReader<NameBasicRow> nameBasicRowReader;

	@Autowired
	@Qualifier("compositeNameBasicWriter")
	public ItemWriter<NameBasicRow> compositeNameBasicWriter;

	@Bean
	public Job titleBasicInsertJob() {
		return jobBuilderFactory.get("titleBasicInsertJob").incrementer(new RunIdIncrementer())
				// .start(titleBasicInsert()).next(titlePrincipleCastInsert()).next(titleRatingInsert())
				.start(nameBasicInsert()).build();
	}

	@Bean
	public Step titleBasicInsert() {
		return stepBuilderFactory.get("titleBasicInsert").<TitleBasicRow, TitleBasicRow>chunk(1)
				.reader(titleBasicReader).writer(compositeTitleBasicRowWriter).faultTolerant().skip(Exception.class)
				.skipLimit(Integer.MAX_VALUE).build();

	}
	//
	// @Bean
	// public Step titleCrewInsert() {
	// return stepBuilderFactory.get("titleCrewInsert").<TitleCrewRow,
	// TitleCrewRow>chunk(1).reader(titleCrewReader)
	// .writer(compositeTitleCrewRowWriter).faultTolerant().skip(Exception.class).skipLimit(Integer.MAX_VALUE)
	// .build();
	// }
	//
	// @Bean
	// public Step titleEpisodeInsert() {
	// return stepBuilderFactory.get("titleEpisodeInsert").<TitleEpisodeRow,
	// TitleEpisodeRow>chunk(1)
	// .reader(titleEpisodeReader).writer(compositeTitleEpisodeRowWriter).faultTolerant().skip(Exception.class)
	// .skipLimit(Integer.MAX_VALUE).build();
	// }

	@Bean
	public Step titlePrincipleCastInsert() {

		return stepBuilderFactory.get("titlePrincipleCastInsert").<TitlePrincipalCastRow, TitlePrincipalCastRow>chunk(1)
				.reader(titlePrincipalCastRowReader).writer(compositeTitlePrincipalCastRowWriter).faultTolerant()
				.skip(Exception.class).skipLimit(Integer.MAX_VALUE).build();
	}

	@Bean
	public Step titleRatingInsert() {

		return stepBuilderFactory.get("titleRatingInsert").<TitleRatingRow, TitleRatingRow>chunk(1)
				.reader(titleRatingRowReader).writer(compositeTitleRatingWriter).faultTolerant().skip(Exception.class)
				.skipLimit(Integer.MAX_VALUE).build();
	}

	@Bean
	public Step nameBasicInsert() {

		return stepBuilderFactory.get("nameBasicInsert").<NameBasicRow, NameBasicRow>chunk(10)
				.reader(nameBasicRowReader).writer(compositeNameBasicWriter).faultTolerant().skip(Exception.class)
				.skipLimit(Integer.MAX_VALUE).build();
	}

	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory mf) {
		final HibernateJpaSessionFactoryBean sessionFactory = new HibernateJpaSessionFactoryBean();
		sessionFactory.setEntityManagerFactory(mf);
		return sessionFactory;
	}
}
