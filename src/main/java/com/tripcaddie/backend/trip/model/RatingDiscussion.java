package com.tripcaddie.backend.trip.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="rating_discussion")
public class RatingDiscussion {
	
	@EmbeddedId
	private RatingDiscussionPK ratingDiscussionPK;
	@Column(name="rating")
	private Double rating;
	
	public RatingDiscussionPK getRatingDiscussionPK() {
		return ratingDiscussionPK;
	}
	public void setRatingDiscussionPK(RatingDiscussionPK ratingDiscussionPK) {
		this.ratingDiscussionPK = ratingDiscussionPK;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}


}
