package com.tripcaddie.backend.galleries.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="rating_picture_files")
public class RatingPictureFile {
	
	@EmbeddedId
	private RatingPictureFilePK ratingPictureFilePK;
	@Column(name="rating")
	private Double rating;
	
	public RatingPictureFilePK getRatingPictureFilePK() {
		return ratingPictureFilePK;
	}
	public void setRatingPictureFilePK(RatingPictureFilePK ratingPictureFilePK) {
		this.ratingPictureFilePK = ratingPictureFilePK;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
}
