package com.tripcaddie.backend.community.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.user.model.TripCaddieUser;

@Entity
@Table(name = "Joke_comment")
public class JokeComment {

	@Id
	@GeneratedValue
	@Column(name = "joke_comment_id")
	private int jokeCommentId;
	@ManyToOne
	@JoinColumn(name = "joke_id", nullable = false)
	private Jokes jokes;

	@Column(name = "comment")
	private String comments;
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

	@ManyToOne
	@JoinColumn(name="user_id")
	private TripCaddieUser user;
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Jokes getJokes() {
		return jokes;
	}

	public void setJokes(Jokes jokes) {
		this.jokes = jokes;
	}

	public int getJokeCommentId() {
		return jokeCommentId;
	}

	public void setJokeCommentId(int jokeCommentId) {
		this.jokeCommentId = jokeCommentId;
	}

	public TripCaddieUser getUser() {
		return user;
	}

	public void setUser(TripCaddieUser user) {
		this.user = user;
	}

}
