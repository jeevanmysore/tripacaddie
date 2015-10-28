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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.backend.itinerary.model.Accommodation;
import com.tripcaddie.backend.itinerary.model.Activity;
import com.tripcaddie.backend.itinerary.model.Logistics;

/**
 * @author Praga
 *
 */
@Entity
@Table(name="trip")
public class Trip {
	
	@Id @GeneratedValue
	@Column(name="trip_id")
	private int tripId;
	@Column(name="trip_name")
	private String tripName;
	@Column(name="start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar startDate;
	@Column(name="end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar endDate;
	@Column(name="image_path")
	private String imagePath;
	@ManyToOne
	@JoinColumn(name="course_id")
	private GolfCourse golfCourse;
	@Lob
	@Column(name="welcome_message")
	private String welcomeMessage;
	@Column(name="annual_trip",nullable=false,length=1)
	private Integer annualTrip;
	@Column(name="promo_code")
	private String promoCode;
	@Column(name="wager_fee")
	private Double wagerFee;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdDate;
	@Column(name="last_updated_by")
	private String lastUpdatedBy;
	@Column(name="last_updated_date")
	private Calendar lastUpdatedDate;
	
	@OneToMany(mappedBy="trip",cascade=CascadeType.ALL)
	private Collection<TripMember> tripMember=new ArrayList<TripMember>();
	/*@OneToMany(mappedBy="trip",cascade=CascadeType.ALL)
	private Collection<Wall> walls=new ArrayList<Wall>();
	@OneToMany(mappedBy="trip",cascade=CascadeType.ALL)
	private Collection<Discussion> discussions=new ArrayList<Discussion>();
	@OneToMany(mappedBy="trip",cascade=CascadeType.ALL)
	private Collection<PollQuestions> polls=new ArrayList<PollQuestions>();*/
	@OneToMany(mappedBy="trip",cascade=CascadeType.ALL)
	private Collection<Activity> activities=new ArrayList<Activity>();
	@OneToMany(mappedBy="trip",cascade=CascadeType.ALL)
	private Collection<Accommodation> accommodations=new ArrayList<Accommodation>();
	@OneToMany(mappedBy="trip",cascade=CascadeType.ALL)
	private Collection<Logistics> logistics=new ArrayList<Logistics>();
	
	@Column(name="strudel_lock")
	private int strudellock;
	
	@Column(name="leaderboard_lock")
	private int leaderboardlock;
	
	public int getStrudellock() {
		return strudellock;
	}
	public void setStrudellock(int strudellock) {
		this.strudellock = strudellock;
	}
	public int getLeaderboardlock() {
		return leaderboardlock;
	}
	public void setLeaderboardlock(int leaderboardlock) {
		this.leaderboardlock = leaderboardlock;
	}
	public Collection<TripMember> getTripMember() {
		return tripMember;
	}
	public void setTripMember(Collection<TripMember> tripMember) {
		this.tripMember = tripMember;
	}
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public GolfCourse getGolfCourse() {
		return golfCourse;
	}
	public void setGolfCourse(GolfCourse golfCourse) {
		this.golfCourse = golfCourse;
	}
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	public Integer getAnnualTrip() {
		return annualTrip;
	}
	public void setAnnualTrip(Integer annualTrip) {
		this.annualTrip = annualTrip;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public Double getWagerFee() {
		return wagerFee;
	}
	public void setWagerFee(Double wagerFee) {
		this.wagerFee = wagerFee;
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
/*	public Collection<Wall> getWalls() {
		return walls;
	}
	public void setWalls(Collection<Wall> walls) {
		this.walls = walls;
	}
	public Collection<Discussion> getDiscussions() {
		return discussions;
	}
	public void setDiscussions(Collection<Discussion> discussions) {
		this.discussions = discussions;
	}
	public Collection<PollQuestions> getPolls() {
		return polls;
	}
	public void setPolls(Collection<PollQuestions> polls) {
		this.polls = polls;
	}*/
	public Collection<Activity> getActivities() {
		return activities;
	}
	public void setActivities(Collection<Activity> activities) {
		this.activities = activities;
	}
	public Collection<Accommodation> getAccommodations() {
		return accommodations;
	}
	public void setAccommodations(Collection<Accommodation> accommodations) {
		this.accommodations = accommodations;
	}
	public Collection<Logistics> getLogistics() {
		return logistics;
	}
	public void setLogistics(Collection<Logistics> logistics) {
		this.logistics = logistics;
	}	
	
}
