package com.netflix.exercise.query.dao;

import java.util.Set;

import com.netflix.exercise.query.model.Title;

public interface TitleDao {

	Title getTitle(String titleId);
	Set<Title> getTitles(int year,String type);
	
}
