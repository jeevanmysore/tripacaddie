package com.tripcaddie.backend.courses.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="course_rating_details")
public class CourseRatingDetails {

	@EmbeddedId
	private CourseRatingDetailsPK courseRatingDetailsPK;
	@Column(name="rating")
	private int rating;
	
	public CourseRatingDetailsPK getCourseRatingDetailsPK() {
		return courseRatingDetailsPK;
	}
	public void setCourseRatingDetailsPK(CourseRatingDetailsPK courseRatingDetailsPK) {
		this.courseRatingDetailsPK = courseRatingDetailsPK;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
}
