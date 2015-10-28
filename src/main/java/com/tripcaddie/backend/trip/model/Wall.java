package com.tripcaddie.backend.trip.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="wall")
public class Wall {
	
	@Id @GeneratedValue
	@Column(name="wall_id")
	private int wallId;
	@Column(name="message")
	private String message;
	@ManyToOne
	@JoinColumn(name="trip_id",nullable=false)
	private Trip trip;
	@ManyToOne
	@JoinColumn(name="trip_member_id",nullable=false)
	private TripMember tripMember;
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
	@OneToMany(mappedBy="wall",cascade=CascadeType.ALL)
	private Collection<WallComment> comments=new ArrayList<WallComment>();
	
	public Collection<WallComment> getComments() {
		return comments;
	}
	public void setComments(Collection<WallComment> comments) {
		this.comments = comments;
	}
	public int getWallId() {
		return wallId;
	}
	public void setWallId(int wallId) {
		this.wallId = wallId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	public TripMember getTripMember() {
		return tripMember;
	}
	public void setTripMember(TripMember tripMember) {
		this.tripMember = tripMember;
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

}
