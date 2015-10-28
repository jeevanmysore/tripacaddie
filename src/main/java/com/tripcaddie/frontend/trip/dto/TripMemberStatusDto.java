package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;

import com.tripcaddie.backend.trip.model.TripMemberStatus;

public class TripMemberStatusDto implements Serializable {

	private static final long serialVersionUID = -5949448439790918599L;
	private int tripMemberStatusId;
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
	
	public static void populate(TripMemberStatusDto tripMemberStatusDto,TripMemberStatus tripMemberStatus){
		
		tripMemberStatusDto.setMemberStatus(tripMemberStatus.getMemberStatus());
		tripMemberStatusDto.setTripMemberStatusId(tripMemberStatus.getTripMemberStatusId());
	}
	
	public static TripMemberStatusDto instantiate(TripMemberStatus tripMemberStatus){
		
		TripMemberStatusDto tripMemberStatusDto=new TripMemberStatusDto();
		populate(tripMemberStatusDto, tripMemberStatus);
		return tripMemberStatusDto;
	}
	
}
