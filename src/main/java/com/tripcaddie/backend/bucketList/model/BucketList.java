package com.tripcaddie.backend.bucketList.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="bucket_list")
public class BucketList {
	
	@Column(name="priority")
	private Integer priority;
	@EmbeddedId
	private BucketListPK bucketListPK;
	/*@ManyToOne
	@JoinColumn(name="golf_coures_id")
	private GolfCourse golfCourse;
	@ManyToOne
	@JoinColumn(name="user_id")
	private TripCaddieUser tripCaddieUser;*/
	@ManyToOne
	@JoinColumn(name="places_played_id")
	private VisitedPlace placesPlayed;        
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
	public BucketListPK getBucketListPK() {
		return bucketListPK;
	}
	public void setBucketListPK(BucketListPK bucketListPK) {
		this.bucketListPK = bucketListPK;
	}
	
	/*public GolfCourse getGolfCourse() {
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
	}*/
	public VisitedPlace getPlacesPlayed() {
		return placesPlayed;
	}
	public void setPlacesPlayed(VisitedPlace placesPlayed) {
		this.placesPlayed = placesPlayed;
	}
	
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}
