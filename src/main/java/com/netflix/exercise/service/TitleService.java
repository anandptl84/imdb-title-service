package com.netflix.exercise.service;

import java.util.Optional;
import java.util.Set;

import com.netflix.exercise.query.model.Title;

/**
 * Service to mainly provides accesses to {@link Title} resource
 *
 * @author anand
 *
 */
public interface TitleService {

	/**
	 * Returns set of Titles matching given input year, type and genre if present.
	 *
	 * @param year
	 * @param type
	 * @param genre
	 * @return
	 */
	Set<Title> getTitles(int year, String type, Optional<String> genre);

	/**
	 * Return {@link Title} for a given id
	 * @param titleId
	 * @return
	 */
	Title getTitle(String titleId);

	/**
	 * Return {@link Title} for a given cast id
	 *
	 * @param castId
	 * @return
	 */
	Set<Title> getTitlesForCast(String castId);
}
