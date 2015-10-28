package com.tripcaddie.backend.galleries.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="rating_video_files")
public class RatingVideoFile {
	
	@EmbeddedId
	private RatingVideoFilePK ratingVideoFilePK;
	@Column(name="rating")
	private Double rating;
	
	public RatingVideoFilePK getRatingVideoFilePK() {
		return ratingVideoFilePK;
	}
	public void setRatingVideoFilePK(RatingVideoFilePK ratingVideoFilePK) {
		this.ratingVideoFilePK = ratingVideoFilePK;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
}
