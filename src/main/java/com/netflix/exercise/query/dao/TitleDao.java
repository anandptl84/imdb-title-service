package com.netflix.exercise.query.dao;

import java.util.Set;

import com.netflix.exercise.query.model.Title;

public interface TitleDao {

	/**
	 * Return title for given id
	 *
	 * @param titleId
	 * @return
	 */
	Title getTitle(String titleId);

	/**
	 * Returns all title for given year and title type
	 *
	 * @param year
	 * @param type
	 * @return all matching titles
	 */
	Set<Title> getTitles(int year, String type);

	/**
	 * Returns all titles for given cast id
	 * 
	 * @param castId
	 * @return
	 */
	Set<Title> getTitlesForCast(String castId);
}
