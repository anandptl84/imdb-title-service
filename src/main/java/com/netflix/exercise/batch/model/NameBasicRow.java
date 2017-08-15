package com.netflix.exercise.batch.model;

public class NameBasicRow {

	private String nameId;
	private String primaryName;
	private Integer birthYear;
	private Integer deathYear;
	private String primaryProfession;
	private String knownTitles;
	
	public String getNameId() {
		return nameId;
	}
	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	public String getPrimaryName() {
		return primaryName;
	}
	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}
	public Integer getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
	public Integer getDeathYear() {
		return deathYear;
	}
	public void setDeathYear(Integer deathYear) {
		this.deathYear = deathYear;
	}
	public String getPrimaryProfession() {
		return primaryProfession;
	}
	public void setPrimaryProfession(String primaryProfession) {
		this.primaryProfession = primaryProfession;
	}
	public String getKnownTitles() {
		return knownTitles;
	}
	public void setKnownTitles(String knownTitles) {
		this.knownTitles = knownTitles;
	} 
	
}
