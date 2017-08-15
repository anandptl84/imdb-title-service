package com.netflix.exercise;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;

@SpringBootApplication
@EnableBatchProcessing
@EnableAutoConfiguration(exclude= {BatchAutoConfiguration.class})
public class ImdbMovieServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImdbMovieServerApplication.class, args);
	}
}
