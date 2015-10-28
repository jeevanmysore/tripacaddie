package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.tripcaddie.backend.trip.model.RoundTeeTime;
import com.tripcaddie.frontend.itinerary.dto.ActivityDto;

public class RoundTeeTimeDto implements Serializable{

	private static final long serialVersionUID = -4470313854149348869L;
	private int teeTimeId;
	private ActivityDto activityDto;
	private TripMemberDto player1;
	private TripMemberDto player2;
	private TripMemberDto player3;
	private TripMemberDto player4;
	private TripMemberDto player5;
	private Date timings;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;

	public int getTeeTimeId() {
		return teeTimeId;
	}
	public void setTeeTimeId(int teeTimeId) {
		this.teeTimeId = teeTimeId;
	}
	public ActivityDto getActivityDto() {
		return activityDto;
	}
	public void setActivityDto(ActivityDto activityDto) {
		this.activityDto = activityDto;
	}
	public TripMemberDto getPlayer1() {
		return player1;
	}
	public void setPlayer1(TripMemberDto player1) {
		this.player1 = player1;
	}
	public TripMemberDto getPlayer2() {
		return player2;
	}
	public void setPlayer2(TripMemberDto player2) {
		this.player2 = player2;
	}
	public TripMemberDto getPlayer3() {
		return player3;
	}
	public void setPlayer3(TripMemberDto player3) {
		this.player3 = player3;
	}
	public TripMemberDto getPlayer4() {
		return player4;
	}
	public void setPlayer4(TripMemberDto player4) {
		this.player4 = player4;
	}
	public TripMemberDto getPlayer5() {
		return player5;
	}
	public void setPlayer5(TripMemberDto player5) {
		this.player5 = player5;
	}
	public Date getTimings() {
		return timings;
	}
	public void setTimings(Date timings) {
		this.timings = timings;
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
	
	public static void populate(RoundTeeTimeDto roundTeeTimeDto,RoundTeeTime roundTeeTime){
		
		roundTeeTimeDto.setActivityDto(ActivityDto.instantiate(roundTeeTime.getActivity()));
		roundTeeTimeDto.setCreatedBy(roundTeeTime.getCreatedBy());
		roundTeeTimeDto.setCreatedDate(roundTeeTime.getCreatedDate());
		roundTeeTimeDto.setLastUpdatedBy(roundTeeTime.getLastUpdatedBy());
		roundTeeTimeDto.setLastUpdatedDate(roundTeeTime.getLastUpdatedDate());
		roundTeeTimeDto.setPlayer1(TripMemberDto.instantiate(roundTeeTime.getPlayer1()));
		roundTeeTimeDto.setPlayer2(TripMemberDto.instantiate(roundTeeTime.getPlayer2()));
		roundTeeTimeDto.setPlayer3(TripMemberDto.instantiate(roundTeeTime.getPlayer3()));
		roundTeeTimeDto.setPlayer4(TripMemberDto.instantiate(roundTeeTime.getPlayer4()));
		roundTeeTimeDto.setPlayer5(TripMemberDto.instantiate(roundTeeTime.getPlayer5()));
		roundTeeTimeDto.setTeeTimeId(roundTeeTime.getTeeTimeId());
		roundTeeTimeDto.setTimings(roundTeeTime.getTimings());
		
	}
	
	public static RoundTeeTimeDto instantiate(RoundTeeTime roundTeeTime){
		
		RoundTeeTimeDto roundTeeTimeDto=new RoundTeeTimeDto();
		populate(roundTeeTimeDto, roundTeeTime);
		return roundTeeTimeDto;				
	}

}
