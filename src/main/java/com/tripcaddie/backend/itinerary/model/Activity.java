package com.tripcaddie.backend.itinerary.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.trip.model.RoundTeeTime;
import com.tripcaddie.backend.trip.model.Trip;

@Entity
@Table(name="activity")
public class Activity {
	
	@Id @GeneratedValue
	@Column(name="activity_id")
	private int activityId;
	@ManyToOne
	@JoinColumn(name="trip_id",nullable=false)
	private Trip trip;
	@ManyToOne
	@JoinColumn(name="activity_type_id")
	private ActivityType activityType;
	@Column(name="activity_name")
	private String activityName;
	@Column(name="activity_date")
	@Temporal(TemporalType.DATE)
	private Calendar activityDate;
	@Column(name="activity_time")
	@Temporal(TemporalType.TIME)
	private Date activityTime;
	@Column(name="time_pending",length=1,nullable=false)
	private Integer timePending;
	@Column(name="url")
	private String url;
	@Lob
	@Column(name="map_info")
	private String mapInfo;
	@Lob
	@Column(name="comment")
	private String comment;
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
	
	@OneToMany(mappedBy="activity",cascade=CascadeType.ALL)
	private Collection<RoundTeeTime> roundTeeTimes=new ArrayList<RoundTeeTime>();
	
	
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	public ActivityType getActivityType() {
		return activityType;
	}
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Calendar getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Calendar activityDate) {
		this.activityDate = activityDate;
	}
	public Date getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}
	public Integer getTimePending() {
		return timePending;
	}
	public void setTimePending(Integer timePending) {
		this.timePending = timePending;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMapInfo() {
		return mapInfo;
	}
	public void setMapInfo(String mapInfo) {
		this.mapInfo = mapInfo;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public Collection<RoundTeeTime> getRoundTeeTimes() {
		return roundTeeTimes;
	}
	public void setRoundTeeTimes(Collection<RoundTeeTime> roundTeeTimes) {
		this.roundTeeTimes = roundTeeTimes;
	}
	

}
