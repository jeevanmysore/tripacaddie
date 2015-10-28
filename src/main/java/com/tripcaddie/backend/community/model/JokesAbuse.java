package com.tripcaddie.backend.community.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.user.model.TripCaddieUser;

@Entity
@Table(name = "jokes_abuse")
public class JokesAbuse {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "abuse_reason")
	private AbuseReasons reasons;

	@ManyToOne
	@JoinColumn(name = "joke_id")
	private Jokes jokes;

	@ManyToOne
	@JoinColumn(name = "from_user")
	private TripCaddieUser user;

	@Lob
	@Column(name = "message")
	private String message;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AbuseReasons getReasons() {
		return reasons;
	}

	public void setReasons(AbuseReasons reasons) {
		this.reasons = reasons;
	}

	public Jokes getJokes() {
		return jokes;
	}

	public void setJokes(Jokes jokes) {
		this.jokes = jokes;
	}

	public TripCaddieUser getUser() {
		return user;
	}

	public void setUser(TripCaddieUser user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

}
