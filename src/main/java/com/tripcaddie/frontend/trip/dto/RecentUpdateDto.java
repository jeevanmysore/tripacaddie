package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.trip.model.RecentUpdate;

public class RecentUpdateDto implements Serializable{

	private static final long serialVersionUID = -2321272158635651199L;
	private int updateId;
	private TripDto tripDto;
	private TripMemberDto tripMemberDto;
	private String typeOfUser;
	private Calendar updatedDate;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getUpdateId() {
		return updateId;
	}
	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}
	public TripDto getTripDto() {
		return tripDto;
	}
	public void setTripDto(TripDto tripDto) {
		this.tripDto = tripDto;
	}
	public TripMemberDto getTripMemberDto() {
		return tripMemberDto;
	}
	public void setTripMemberDto(TripMemberDto tripMemberDto) {
		this.tripMemberDto = tripMemberDto;
	}
	public String getTypeOfUser() {
		return typeOfUser;
	}
	public void setTypeOfUser(String typeOfUser) {
		this.typeOfUser = typeOfUser;
	}
	public Calendar getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Calendar updatedDate) {
		this.updatedDate = updatedDate;
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
	
	public static void populate(RecentUpdateDto recentUpdateDto,RecentUpdate recentUpdate){
		
		recentUpdateDto.setCreatedBy(recentUpdate.getCreatedBy());
		recentUpdateDto.setCreatedDate(recentUpdate.getCreatedDate());
		recentUpdateDto.setLastUpdatedBy(recentUpdate.getLastUpdatedBy());
		recentUpdateDto.setLastUpdatedDate(recentUpdate.getLastUpdatedDate());
		recentUpdateDto.setMessage(recentUpdate.getMessage());
		recentUpdateDto.setTripDto(TripDto.instantiate(recentUpdate.getTrip()));
		recentUpdateDto.setTripMemberDto(TripMemberDto.instantiate(recentUpdate.getTripMember()));
		recentUpdateDto.setTypeOfUser(recentUpdate.getTypeOfUser());
		recentUpdateDto.setUpdatedDate(recentUpdate.getUpdatedDate());
		recentUpdateDto.setUpdateId(recentUpdate.getUpdateId());
	}
	
	public static RecentUpdateDto instantiate(RecentUpdate recentUpdate){
		
		RecentUpdateDto recentUpdateDto=new RecentUpdateDto();
		populate(recentUpdateDto, recentUpdate);
		return recentUpdateDto;
	}
	
}
