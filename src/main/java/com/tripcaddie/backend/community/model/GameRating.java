package com.tripcaddie.backend.community.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "game_rating")
public class GameRating {

	@EmbeddedId
	private GameRatingPK gameRatingPK;

	@Column(name = "rating")
	private Double rating;

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public GameRatingPK getGameRatingPK() {
		return gameRatingPK;
	}

	public void setGameRatingPK(GameRatingPK gameRatingPK) {
		this.gameRatingPK = gameRatingPK;
	}

}
