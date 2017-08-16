package com.netflix.exercise.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.exercise.query.dao.TitleDao;
import com.netflix.exercise.query.model.Title;

@Component
public class TitleServiceImpl implements TitleService {

	@Autowired
	private TitleDao titleDao;

	@Override
	public Set<Title> getTitles(int year, String type, Optional<String> genre) {
		final Set<Title> titleByYearAndType = titleDao.getTitles(year, type);
		return genre.isPresent() ? filterByGenre(titleByYearAndType, genre.get()) : titleByYearAndType;
	}

	private Set<Title> filterByGenre(Set<Title> titles, String genre) {
		return titles.stream().filter(t -> t.getGenres().contains(genre)).collect(Collectors.toSet());
	}

	@Override
	public Title getTitle(String titleId) {
		return titleDao.getTitle(titleId);
	}

	@Override
	public Set<Title> getTitlesForCast(String castId) {
		return titleDao.getTitlesForCast(castId);
	}
}
