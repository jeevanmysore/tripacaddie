package com.tripcaddie.frontend.bucketList.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.tripcaddie.backend.bucketList.model.GolfCourseReview;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.courses.dto.GolfCourseDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public class GolfCourseReviewDto implements Serializable {

	private static final long serialVersionUID = 1201945787251870546L;
	private int reviewId;
	private String review;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private String source;
	private Double rating;
	private GolfCourseDto golfCourseDto;
	private TripcaddieUserDto userDto;
	private Collection<TripcaddieUserDto> usersDtos=new ArrayList<TripcaddieUserDto>();
	private Integer likeCount;
	private String currentUser;
	
	public Integer getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	public Collection<TripcaddieUserDto> getUsersDtos() {
		return usersDtos;
	}
	public void setUsersDtos(Collection<TripcaddieUserDto> usersDtos) {
		this.usersDtos = usersDtos;
	}
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
	public GolfCourseDto getGolfCourseDto() {
		return golfCourseDto;
	}
	public void setGolfCourseDto(GolfCourseDto golfCourseDto) {
		this.golfCourseDto = golfCourseDto;
	}
	public TripcaddieUserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(TripcaddieUserDto userDto) {
		this.userDto = userDto;
	}
	
	public static void populate(GolfCourseReviewDto courseReviewDto,GolfCourseReview courseReview) {
		
		courseReviewDto.setCreatedBy(courseReview.getCreatedBy());
		courseReviewDto.setCreatedDate(courseReview.getCreatedDate());
		courseReviewDto.setGolfCourseDto(GolfCourseDto.instantiate(courseReview.getGolfCourse()));
		courseReviewDto.setLastUpdatedBy(courseReview.getLastUpdatedBy());
		courseReviewDto.setLastUpdatedDate(courseReview.getLastUpdatedDate());
		courseReviewDto.setRating(courseReview.getRating());
		courseReviewDto.setReview(courseReview.getReview());
		courseReviewDto.setReviewId(courseReview.getReviewId());
		courseReviewDto.setSource(courseReview.getSource());
		courseReviewDto.setUserDto(TripcaddieUserDto.instantiate(courseReview.getUser()));
		for(TripCaddieUser user:courseReview.getUsers()){
			courseReviewDto.getUsersDtos().add(TripcaddieUserDto.instantiate(user));
		}
	}
	
	public static GolfCourseReviewDto instantiate(GolfCourseReview courseReview) {
		
		GolfCourseReviewDto courseReviewDto=new GolfCourseReviewDto();
		populate(courseReviewDto, courseReview);
		return courseReviewDto;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

}
