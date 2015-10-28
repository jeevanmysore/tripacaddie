package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.trip.model.TripRound;

public class TripRoundDto implements Serializable{

	private static final long serialVersionUID = -624815511053604451L;
	private int roundId;
	private String roundName;
	private TripDto tripDto;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	public int getRoundId() {
		return roundId;
	}
	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}
	public String getRoundName() {
		return roundName;
	}
	public void setRoundName(String roundName) {
		this.roundName = roundName;
	}
	public TripDto getTripDto() {
		return tripDto;
	}
	public void setTripDto(TripDto tripDto) {
		this.tripDto = tripDto;
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
	
	public static void populate(TripRoundDto tripRoundDto,TripRound tripRound){
		
		tripRoundDto.setCreatedBy(tripRound.getCreatedBy());
		tripRoundDto.setCreatedDate(tripRound.getCreatedDate());
		tripRoundDto.setLastUpdatedBy(tripRound.getLastUpdatedBy());
		tripRoundDto.setLastUpdatedDate(tripRound.getLastUpdatedDate());
		tripRoundDto.setRoundId(tripRound.getRoundId());
		tripRoundDto.setRoundName(tripRound.getRoundName());
		tripRoundDto.setTripDto(TripDto.instantiate(tripRound.getTrip()));
	}
	
	public static TripRoundDto instantiate(TripRound tripRound){
		
		TripRoundDto tripRoundDto=new TripRoundDto();
		populate(tripRoundDto, tripRound);
		return tripRoundDto;
	}
		
}
