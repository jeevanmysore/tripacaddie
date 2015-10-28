package com.tripcaddie.backend.community.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "joke_rating")
public class JokeRating {

	@EmbeddedId
	private JokeRatingPK jokeRatingPK;

	@Column(name = "rating")
	private Double rating;

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public JokeRatingPK getJokeRatingPK() {
		return jokeRatingPK;
	}

	public void setJokeRatingPK(JokeRatingPK jokeRatingPK) {
		this.jokeRatingPK = jokeRatingPK;
	}

	

}
