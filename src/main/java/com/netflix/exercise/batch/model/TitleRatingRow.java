package com.netflix.exercise.batch.model;

public class TitleRatingRow {

	private String titleId;
	private Double averageRating;
	private Integer numberOfVotes;
	
	public String getTitleId() {
		return titleId;
	}
	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}
	public Double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}
	public Integer getNumberOfVotes() {
		return numberOfVotes;
	}
	public void setNumberOfVotes(Integer numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	
}
