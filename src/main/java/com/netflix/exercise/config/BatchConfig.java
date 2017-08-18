package com.netflix.exercise.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
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

	@Value("${batch.size}")
	private int batchSize;

	@Autowired
	@Qualifier("titleBasicReader")
	public ItemReader<TitleBasicRow> titleBasicReader;

	@Autowired
	@Qualifier("basicItemWriter")
	public ItemWriter<TitleBasicRow> compositeTitleBasicRowWriter;

	@Autowired
	@Qualifier("titlePrincipleCastRowReader")
	public ItemReader<TitlePrincipalCastRow> titlePrincipalCastRowReader;

	@Autowired
	@Qualifier("compositeTitlePrincipalCastRowWriter")
	public ItemWriter<TitlePrincipalCastRow> compositeTitlePrincipalCastRowWriter;


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
		final Flow nameBasicFlow = new FlowBuilder<Flow>("nameBasicFlow").from(nameBasicInsert()).end();
		final Flow titleBasicFlow = new FlowBuilder<Flow>("titleBasicFlow").from(titleBasicInsert()).end();
		final Flow principalCastFlow = new FlowBuilder<Flow>("principalCastFlow").from(titlePrincipleCastInsert()).end();
		final Flow ratingFlow = new FlowBuilder<Flow>("titleRatingFlow").from(titleRatingInsert()).end();

		return jobBuilderFactory.get("titleBasicInsertJob")
				.incrementer(new RunIdIncrementer())
				.start(titleBasicFlow)
				.split(asyncTaskExecutor()).add(nameBasicFlow)
				.split(asyncTaskExecutor()).add(principalCastFlow)
				.split(asyncTaskExecutor()).add(ratingFlow)
				.end().build();

	}

	@Bean
	public Step titleBasicInsert() {
		return stepBuilderFactory.get("titleBasicInsert").<TitleBasicRow, TitleBasicRow>chunk(batchSize)
				.reader(titleBasicReader).writer(compositeTitleBasicRowWriter).faultTolerant().skip(Exception.class)
				.skipLimit(Integer.MAX_VALUE).build();

	}

	@Bean
	public Step titlePrincipleCastInsert() {

		return stepBuilderFactory.get("titlePrincipleCastInsert").<TitlePrincipalCastRow, TitlePrincipalCastRow>chunk(batchSize)
				.reader(titlePrincipalCastRowReader).writer(compositeTitlePrincipalCastRowWriter).faultTolerant()
				.skip(Exception.class).skipLimit(Integer.MAX_VALUE).build();
	}

	@Bean
	public Step titleRatingInsert() {

		return stepBuilderFactory.get("titleRatingInsert").<TitleRatingRow, TitleRatingRow>chunk(batchSize)
				.reader(titleRatingRowReader).writer(compositeTitleRatingWriter).faultTolerant().skip(Exception.class)
				.skipLimit(Integer.MAX_VALUE).build();
	}

	@Bean
	public Step nameBasicInsert() {

		return stepBuilderFactory.get("nameBasicInsert").<NameBasicRow, NameBasicRow>chunk(batchSize)
				.reader(nameBasicRowReader).writer(compositeNameBasicWriter).faultTolerant().skip(Exception.class)
				.skipLimit(Integer.MAX_VALUE).build();
	}

	@Bean
	public SimpleAsyncTaskExecutor asyncTaskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory mf) {
		final HibernateJpaSessionFactoryBean sessionFactory = new HibernateJpaSessionFactoryBean();
		sessionFactory.setEntityManagerFactory(mf);
		return sessionFactory;
	}
}
