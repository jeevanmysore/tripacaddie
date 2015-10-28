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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="poll_questions")
public class PollQuestions {
	
	@Id @GeneratedValue
	@Column(name="poll_id")
	private int pollId;
	@Column(name="questions")
	private String questions;
	@ManyToOne
	@JoinColumn(name="trip_id")
	private Trip trip;
	@ManyToOne
	@JoinColumn(name="trip_member_id")
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
	@OneToMany(mappedBy="pollQuestions",cascade=CascadeType.ALL)
	private Collection<PollOption> option=new ArrayList<PollOption>();
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="poll_vote",
			joinColumns=@JoinColumn(name="poll_id"),
			inverseJoinColumns=@JoinColumn(name="member_id"))
	private Collection<TripMember> pollParticipants=new ArrayList<TripMember>();
	
	public Collection<PollOption> getOption() {
		return option;
	}
	public void setOption(Collection<PollOption> option) {
		this.option = option;
	}
	public int getPollId() {
		return pollId;
	}
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
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
	public Collection<TripMember> getPollParticipants() {
		return pollParticipants;
	}
	public void setPollParticipants(Collection<TripMember> pollParticipants) {
		this.pollParticipants = pollParticipants;
	}
	
	

}
