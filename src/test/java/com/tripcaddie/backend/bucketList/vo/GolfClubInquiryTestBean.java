package com.tripcaddie.backend.bucketList.vo;

import java.util.Calendar;

import com.tripcaddie.backend.courses.vo.GolfCourseTestBean;
import com.tripcaddie.backend.user.vo.UserTestBean;

public class GolfClubInquiryTestBean {
	
	private int golfClubInquiryId;
	private String question;
	private Calendar lastUpdatedDate;
	private Calendar createdDate;
	private String createdBy;
	private String lastUpdatedBy;
	private UserTestBean user;
	private GolfCourseTestBean golfCourse;
	public int getGolfClubInquiryId() {
		return golfClubInquiryId;
	}
	public void setGolfClubInquiryId(int golfClubInquiryId) {
		this.golfClubInquiryId = golfClubInquiryId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public UserTestBean getUser() {
		return user;
	}
	public void setUser(UserTestBean user) {
		this.user = user;
	}
	public GolfCourseTestBean getGolfCourse() {
		return golfCourse;
	}
	public void setGolfCourse(GolfCourseTestBean golfCourse) {
		this.golfCourse = golfCourse;
	}
	
	

}
