package com.tripcaddie.backend.community.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "Jokes")
public class Jokes {

	@Id
	@GeneratedValue
	@Column(name = "joke_id")
	private int jokeId;
	@Column(name = "joke_name")
	private String jokeName;

	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "avg_rating")
	private Double avgRating;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdDate;

	@Column(name = "last_updated_by")
	private String lastUpdatedBy;

	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdatedDate;

	@OneToMany(mappedBy = "jokes")
	@Cascade(value = CascadeType.ALL)
	private Collection<JokeComment> jokeComments = new ArrayList<JokeComment>();
	
	@OneToMany(mappedBy = "jokeRatingPK.jokes")
	@Cascade(value = CascadeType.ALL)
	private Collection<JokeRating> jokeRatings = new ArrayList<JokeRating>();
	
	@Column(name="abuse")
	private Integer abuse;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public int getJokeId() {
		return jokeId;
	}

	public void setJokeId(int jokeId) {
		this.jokeId = jokeId;
	}

	public String getJokeName() {
		return jokeName;
	}

	public void setJokeName(String jokeName) {
		this.jokeName = jokeName;
	}

	public Collection<JokeComment> getJokeComments() {
		return jokeComments;
	}

	public void setJokeComments(Collection<JokeComment> jokeComments) {
		this.jokeComments = jokeComments;
	}

	public Collection<JokeRating> getJokeRatings() {
		return jokeRatings;
	}

	public void setJokeRatings(Collection<JokeRating> jokeRatings) {
		this.jokeRatings = jokeRatings;
	}

	public Integer getAbuse() {
		return abuse;
	}

	public void setAbuse(Integer abuse) {
		this.abuse = abuse;
	}

	

}
