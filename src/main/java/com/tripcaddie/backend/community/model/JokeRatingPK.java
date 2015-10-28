package com.tripcaddie.backend.community.model;


import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.backend.user.model.TripCaddieUser;

@Embeddable
public class JokeRatingPK implements Serializable {

	private static final long serialVersionUID = -1786935826896927065L;
	@ManyToOne
	@JoinColumn(name = "joke_id", nullable = false)
	private Jokes jokes;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private TripCaddieUser user;

	
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

	
}
