package com.tripcaddie.backend.trip.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.itinerary.model.Activity;

@Entity
@Table(name="golf_tee_time")
public class RoundTeeTime {
	
	@Id @GeneratedValue
	@Column(name="tee_time_id")
	private int teeTimeId;
	@ManyToOne
	@JoinColumn(name="activity_id",nullable=false)
	private Activity activity;
	@ManyToOne
	@JoinColumn(name="player1",nullable=true)
	private TripMember player1;
	@ManyToOne
	@JoinColumn(name="player2",nullable=true)
	private TripMember player2;
	@ManyToOne
	@JoinColumn(name="player3",nullable=true)
	private TripMember player3;
	@ManyToOne
	@JoinColumn(name="player4",nullable=true)
	private TripMember player4;
	@ManyToOne
	@JoinColumn(name="player5",nullable=true)	
	private TripMember player5;
	@Column(name="timings")
	@Temporal(TemporalType.TIME)
	private Date Timings;
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
	
	public int getTeeTimeId() {
		return teeTimeId;
	}
	public void setTeeTimeId(int teeTimeId) {
		this.teeTimeId = teeTimeId;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public TripMember getPlayer1() {
		return player1;
	}
	public void setPlayer1(TripMember player1) {
		this.player1 = player1;
	}
	public TripMember getPlayer2() {
		return player2;
	}
	public void setPlayer2(TripMember player2) {
		this.player2 = player2;
	}
	public TripMember getPlayer3() {
		return player3;
	}
	public void setPlayer3(TripMember player3) {
		this.player3 = player3;
	}
	public TripMember getPlayer4() {
		return player4;
	}
	public void setPlayer4(TripMember player4) {
		this.player4 = player4;
	}
	public TripMember getPlayer5() {
		return player5;
	}
	public void setPlayer5(TripMember player5) {
		this.player5 = player5;
	}
	public Date getTimings() {
		return Timings;
	}
	public void setTimings(Date timings) {
		Timings = timings;
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
