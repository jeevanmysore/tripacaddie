package com.tripcaddie.backend.trip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="trip_member_status")
public class TripMemberStatus {
	
	@Id
	@Column(name="trip_member_status_id",nullable=false)
	private int tripMemberStatusId;
	@Column(name="member_status")
	private String memberStatus;
	
	public int getTripMemberStatusId() {
		return tripMemberStatusId;
	}
	public void setTripMemberStatusId(int tripMemberStatusId) {
		this.tripMemberStatusId = tripMemberStatusId;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

}
