package com.netflix.exercise.service;

import java.util.Optional;
import java.util.Set;

import com.netflix.exercise.query.model.Title;

public interface TitleService {

	Set<Title> getTitles(int year, String type, Optional<String> genre);

	Title getTitle(String titleId);

	Set<Title> getTitlesForCast(String castId);
}
