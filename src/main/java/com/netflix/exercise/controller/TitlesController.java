package com.netflix.exercise.controller;

import java.util.Optional;
import java.util.Set;

import javax.ws.rs.QueryParam;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.exercise.query.model.Title;
import com.netflix.exercise.service.TitleService;

@RestController
public class TitlesController {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("titleBasicInsertJob")
	private Job titleBasicInsertJob;

	@Autowired
	private TitleService titleService;

	@RequestMapping("/titleBasicInsertJob")
	public String titleBasicInsertJob() throws Exception {

		final JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.toJobParameters();
		final JobExecution jobExecution = jobLauncher.run(titleBasicInsertJob, jobParameters);

		return String.format("Job finished with status: %s", jobExecution.getExitStatus().getExitCode());
	}

	@RequestMapping("{id}")
	public Title getMovie(@QueryParam("id") String id) {
		return titleService.getTitle(id);
	}

	@RequestMapping("/titles")
	public Set<Title> getTitles(@QueryParam("year") int year, @QueryParam("type") String type,
			@QueryParam("genre") String genre) {
		return titleService.getTitles(year, type, Optional.ofNullable(genre));
	}

}
