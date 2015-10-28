package com.tripcaddie.backend.bucketList.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.backend.user.model.TripCaddieUser;

@Embeddable
public class BucketListPK implements Serializable{

	private static final long serialVersionUID = -5536870296919852996L;
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
