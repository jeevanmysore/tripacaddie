package com.tripcaddie.backend.bucketList.vo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.tripcaddie.backend.courses.vo.GolfCourseTestBean;
import com.tripcaddie.backend.user.vo.UserTestBean;

public class GolfCourseReviewTestBean {
	
	private int reviewId;
	private String review;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private String source;
	private Double rating;
	private GolfCourseTestBean golfCourse;
	private UserTestBean user;
	private Collection<UserTestBean> users=new ArrayList<UserTestBean>();
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
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
	public GolfCourseTestBean getGolfCourse() {
		return golfCourse;
	}
	public void setGolfCourse(GolfCourseTestBean golfCourse) {
		this.golfCourse = golfCourse;
	}
	public UserTestBean getUser() {
		return user;
	}
	public void setUser(UserTestBean user) {
		this.user = user;
	}
	public Collection<UserTestBean> getUsers() {
		return users;
	}
	public void setUsers(Collection<UserTestBean> users) {
		this.users = users;
	}
}
