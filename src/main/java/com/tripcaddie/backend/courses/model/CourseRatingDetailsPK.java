package com.tripcaddie.backend.courses.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tripcaddie.backend.user.model.TripCaddieUser;

@Embeddable
public class CourseRatingDetailsPK implements Serializable{
	
	private static final long serialVersionUID = -3861007786819914718L;
	@ManyToOne
	@JoinColumn(name="golf_coures_id")
	private GolfCourse golfCourse;
	@ManyToOne
	@JoinColumn(name="user_id")
	private TripCaddieUser tripCaddieUser;
	
	public GolfCourse getGolfCourse() {
		return golfCourse;
	}
	public void setGolfCourse(GolfCourse golfCourse) {
		this.golfCourse = golfCourse;
	}
	public TripCaddieUser getTripCaddieUser() {
		return tripCaddieUser;
	}
	public void setTripCaddieUser(TripCaddieUser tripCaddieUser) {
		this.tripCaddieUser = tripCaddieUser;
	}

	
}
