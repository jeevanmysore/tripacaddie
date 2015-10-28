package com.tripcaddie.backend.bucketList.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.backend.user.model.TripCaddieUser;

@Entity
@Table(name="golf_course_review")
public class GolfCourseReview {

	@Id @GeneratedValue
	@Column(name="review_id")
	private int reviewId;
	@Lob
	@Column(name="review")
	private String review;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdDate;
	@Column(name="last_updated_by")
	private String lastUpdatedBy;
	@Column(name="last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdatedDate;
	@Column(name="source")
	private String source;
	@Column(name="rating")
	private Double rating;
	@ManyToOne
	@JoinColumn(name="course_id")
	private GolfCourse golfCourse;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private TripCaddieUser user;
	
	@ManyToMany
	@JoinTable(name="review_like",
			joinColumns=@JoinColumn(name="review_id",nullable=true),
			inverseJoinColumns=@JoinColumn(name="user_id",nullable=true))	
	private Collection<TripCaddieUser> users=new ArrayList<TripCaddieUser>();
	
	public TripCaddieUser getUser() {
		return user;
	}
	public void setUser(TripCaddieUser user) {
		this.user = user;
	}
	public GolfCourse getGolfCourse() {
		return golfCourse;
	}
	public void setGolfCourse(GolfCourse golfCourse) {
		this.golfCourse = golfCourse;
	}
		
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public Collection<TripCaddieUser> getUsers() {
		return users;
	}
	public void setUsers(Collection<TripCaddieUser> users) {
		this.users = users;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
		
}
