package com.netflix.exercise.query.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "title_basic")
public class Title {

	@Column(name = "primary_title")
	private String primaryTitle;

	@Column(name = "id")
	@Id
	private String titleId;

	@Column(name = "original_title")
	private String originalTitle;

	@Convert(converter = StringListConverter.class)
	@Column(name = "genres")
	private List<String> genres;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	private TitleRating titleRating;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "title_principal_cast", joinColumns = @JoinColumn(name = "title_id"), inverseJoinColumns = @JoinColumn(name = "cast_id"))
	private List<Cast> primaryCast;

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public List<Cast> getPrimaryCast() {
		return primaryCast;
	}

	public void setPrimaryCast(List<Cast> primaryCast) {
		this.primaryCast = primaryCast;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public TitleRating getTitleRating() {
		return titleRating;
	}

	public void setTitleRating(TitleRating titleRating) {
		this.titleRating = titleRating;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	@Column(name = "start_year")
	private int startYear;

	@Column(name = "type")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrimaryTitle() {
		return primaryTitle;
	}

	public void setPrimaryTitle(String primaryTitle) {
		this.primaryTitle = primaryTitle;
	}

	public String getTitle_id() {
		return titleId;
	}

	public void setTitle_id(String title_id) {
		this.titleId = title_id;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

}
