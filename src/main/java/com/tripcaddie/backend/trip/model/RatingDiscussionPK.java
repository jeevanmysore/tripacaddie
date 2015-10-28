package com.tripcaddie.backend.trip.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class RatingDiscussionPK implements Serializable {

	private static final long serialVersionUID = -4026554606052889253L;
	@ManyToOne
	@JoinColumn(name="discussion_id")
	private Discussion discussion;
	@ManyToOne
	@JoinColumn(name="trip_member_id")
	private TripMember tripMember;
	
	public Discussion getDiscussion() {
		return discussion;
	}
	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}
	public TripMember getTripMember() {
		return tripMember;
	}
	public void setTripMember(TripMember tripMember) {
		this.tripMember = tripMember;
	}
	
}
