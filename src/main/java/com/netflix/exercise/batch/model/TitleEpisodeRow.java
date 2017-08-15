package com.netflix.exercise.batch.model;

public class TitleEpisodeRow {

	private String episodeId;
	private String parentTitleId;
	private Integer seasonNumber;
	private Integer episodeNumber;
	
	public String getEpisodeId() {
		return episodeId;
	}
	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}
	public String getParentTitleId() {
		return parentTitleId;
	}
	public void setParentTitleId(String parentTitleId) {
		this.parentTitleId = parentTitleId;
	}
	public Integer getSeasonNumber() {
		return seasonNumber;
	}
	public void setSeasonNumber(Integer seasonNumber) {
		this.seasonNumber = seasonNumber;
	}
	public Integer getEpisodeNumber() {
		return episodeNumber;
	}
	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}
	
	
}
