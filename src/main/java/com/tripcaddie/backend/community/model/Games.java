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
@Table(name = "Games")
public class Games {

	@Id
	@GeneratedValue
	@Column(name = "game_id")
	private int gameId;
	@Column(name = "game_name")
	private String gameName;

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

	@OneToMany(mappedBy = "games")
	@Cascade(value = CascadeType.ALL)
	private Collection<GameComment> gameComments = new ArrayList<GameComment>();

	@OneToMany(mappedBy = "gameRatingPK.game")
	@Cascade(value = CascadeType.ALL)
	private Collection<GameRating> gameRatings = new ArrayList<GameRating>();
	
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

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Collection<GameComment> getGameComments() {
		return gameComments;
	}

	public void setGameComments(Collection<GameComment> gameComments) {
		this.gameComments = gameComments;
	}

	

	public Collection<GameRating> getGameRatings() {
		return gameRatings;
	}

	public void setGameRatings(Collection<GameRating> gameRatings) {
		this.gameRatings = gameRatings;
	}

	public Integer getAbuse() {
		return abuse;
	}

	public void setAbuse(Integer abuse) {
		this.abuse = abuse;
	}

}
