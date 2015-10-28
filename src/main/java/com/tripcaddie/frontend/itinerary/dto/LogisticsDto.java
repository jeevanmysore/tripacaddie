package com.tripcaddie.frontend.itinerary.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.itinerary.model.Logistics;
import com.tripcaddie.frontend.trip.dto.TripDto;

public class LogisticsDto implements Serializable{

	private static final long serialVersionUID = 6557849566954766973L;
	private int logisticsId;
	private TripDto tripDto;
	private String logisticsContent;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	public int getLogisticsId() {
		return logisticsId;
	}
	public void setLogisticsId(int logisticsId) {
		this.logisticsId = logisticsId;
	}
	public TripDto getTripDto() {
		return tripDto;
	}
	public void setTripDto(TripDto tripDto) {
		this.tripDto = tripDto;
	}
	public String getLogisticsContent() {
		return logisticsContent;
	}
	public void setLogisticsContent(String logisticsContent) {
		this.logisticsContent = logisticsContent;
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
	
	public static void populate(LogisticsDto logisticsDto,Logistics logistics) {
		
		logisticsDto.setCreatedBy(logistics.getCreatedBy());
		logisticsDto.setCreatedDate(logistics.getCreatedDate());
		logisticsDto.setLastUpdatedBy(logistics.getLastUpdatedBy());
		logisticsDto.setLastUpdatedDate(logistics.getLastUpdatedDate());
		logisticsDto.setLogisticsContent(logistics.getLogisticsContent());
		logisticsDto.setLogisticsId(logistics.getLogisticsId());
		logisticsDto.setTripDto(TripDto.instantiate(logistics.getTrip()));
	}
	
	public static LogisticsDto instantiate(Logistics logistics) {
		
		LogisticsDto logisticsDto=new LogisticsDto();
		populate(logisticsDto, logistics);
		return logisticsDto;
	}
	
}
