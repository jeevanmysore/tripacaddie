package com.tripcaddie.backend.bucketList.vo;

import com.tripcaddie.backend.courses.vo.GolfCourseTestBean;
import com.tripcaddie.backend.user.vo.UserTestBean;

public class BucketListTestBean {
	
	private Integer priority;
	private GolfCourseTestBean golfCourse;
	private UserTestBean tripCaddieUser;
	private VisitedPlaceTestBean placesPlayed;
	
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public GolfCourseTestBean getGolfCourse() {
		return golfCourse;
	}
	public void setGolfCourse(GolfCourseTestBean golfCourse) {
		this.golfCourse = golfCourse;
	}
	public UserTestBean getTripCaddieUser() {
		return tripCaddieUser;
	}
	public void setTripCaddieUser(UserTestBean tripCaddieUser) {
		this.tripCaddieUser = tripCaddieUser;
	}
	public VisitedPlaceTestBean getPlacesPlayed() {
		return placesPlayed;
	}
	public void setPlacesPlayed(VisitedPlaceTestBean placesPlayed) {
		this.placesPlayed = placesPlayed;
	}
}
