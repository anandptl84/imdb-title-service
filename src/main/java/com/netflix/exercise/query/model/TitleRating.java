package com.netflix.exercise.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="title_ratings")
public class TitleRating {

	@Column(name="title_id")
	@Id
	private String titleId;
	
	@Column(name="rating")
	private Double averageRating;
	
	@Column(name="number_votes")
	private Integer numOfVotes;
	
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
	public Integer getNumOfVotes() {
		return numOfVotes;
	}
	public void setNumOfVotes(Integer numOfVotes) {
		this.numOfVotes = numOfVotes;
	}
	
	
}
