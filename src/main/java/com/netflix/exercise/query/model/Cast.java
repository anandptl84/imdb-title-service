package com.netflix.exercise.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="name_basic")
public class Cast {

	@Column(name="name_id")
	@Id
	private String name_id;
	
	@Column(name="primary_name")
	private String primary_name;
	
	@Column(name="birth_year")
	private  Integer birthYear;
	

	@Column(name="death_year")
	private Integer deathYear;
	
	public String getName_id() {
		return name_id;
	}

	public void setName_id(String name_id) {
		this.name_id = name_id;
	}

	public String getPrimary_name() {
		return primary_name;
	}

	public void setPrimary_name(String primary_name) {
		this.primary_name = primary_name;
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

}
