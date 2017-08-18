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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping("/titles/refresh")
	public String titleBasicInsertJob() throws Exception {

		final JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.toJobParameters();
		final JobExecution jobExecution = jobLauncher.run(titleBasicInsertJob, jobParameters);

		return String.format("Job finished with status: %s", jobExecution.getExitStatus().getExitCode());
	}

	@RequestMapping("/titles/id/{id}")
	public Title getTitle(@PathVariable("id") String id) {
		final Title result = titleService.getTitle(id);
		return result;
	}

	@RequestMapping("/titles")
	public Set<Title> getTitles(@QueryParam("year") Integer year, @QueryParam("type") String type,
			@QueryParam("genre") String genre) {
		Assert.notNull(year, "Must provie year query paramers");
		Assert.notNull(type, "Must provide type query parameter");
		return titleService.getTitles(year, type, Optional.ofNullable(genre));
	}

	@RequestMapping("/titles/cast/{id}")
	public Set<Title> getTitlesByCast(@PathVariable("id") String castId) {
		return titleService.getTitlesForCast(castId);
	}

}
