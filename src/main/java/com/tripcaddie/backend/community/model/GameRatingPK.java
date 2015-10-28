package com.tripcaddie.backend.community.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;

import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.backend.user.model.TripCaddieUser;

@Embeddable
public class GameRatingPK implements Serializable {

	private static final long serialVersionUID = -1786935826896927065L;
	@ManyToOne
	@JoinColumn(name = "game_id", nullable = false)
	private Games game;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private TripCaddieUser user;

	

	

	public TripCaddieUser getUser() {
		return user;
	}

	public void setUser(TripCaddieUser user) {
		this.user = user;
	}

	public Games getGame() {
		return game;
	}

	public void setGame(Games game) {
		this.game = game;
	}

}
