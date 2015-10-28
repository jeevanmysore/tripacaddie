package com.tripcaddie.frontend.itinerary.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.itinerary.model.Accommodation;
import com.tripcaddie.frontend.trip.dto.TripDto;

public class AccommodationDto implements Serializable {
	
	private static final long serialVersionUID = 2372339645815242142L;
	private int accommodationId;
	private TripDto tripDto;
	private String accomadationContent;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	public int getAccommodationId() {
		return accommodationId;
	}
	public void setAccommodationId(int accommodationId) {
		this.accommodationId = accommodationId;
	}
	public TripDto getTripDto() {
		return tripDto;
	}
	public void setTripDto(TripDto tripDto) {
		this.tripDto = tripDto;
	}
	public String getAccomadationContent() {
		return accomadationContent;
	}
	public void setAccomadationContent(String accomadationContent) {
		this.accomadationContent = accomadationContent;
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
	
	public static void populate(Accommodation accommodation,AccommodationDto accommodationDto) {
		
		accommodationDto.setAccomadationContent(accommodation.getAccomadationContent());
		accommodationDto.setAccommodationId(accommodation.getAccommodationId());
		accommodationDto.setCreatedBy(accommodation.getCreatedBy());
		accommodationDto.setCreatedDate(accommodation.getCreatedDate());
		accommodationDto.setLastUpdatedBy(accommodation.getLastUpdatedBy());
		accommodationDto.setLastUpdatedDate(accommodation.getLastUpdatedDate());
		accommodationDto.setTripDto(TripDto.instantiate(accommodation.getTrip()));
	}
	
	public static AccommodationDto instantiate(Accommodation accommodation){
		
		AccommodationDto accommodationDto=new AccommodationDto();
		populate(accommodation, accommodationDto);
		return accommodationDto;
	}
	

}
