package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import com.tripcaddie.backend.trip.model.Wall;

public class WallDto implements Serializable {
	
	private static final long serialVersionUID = -3943593516821247706L;
	private int wallId;
	private String message;
	private TripDto tripDto;
	private TripMemberDto tripMemberDto;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private ArrayList<WallCommentDto> commentDtos=new ArrayList<WallCommentDto>();
	
	public ArrayList<WallCommentDto> getCommentDtos() {
		return commentDtos;
	}
	public void setCommentDtos(ArrayList<WallCommentDto> commentDtos) {
		this.commentDtos = commentDtos;
	}
	public int getWallId() {
		return wallId;
	}
	public void setWallId(int wallId) {
		this.wallId = wallId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	
	public static void populate(WallDto wallDto,Wall wall){
	
		wallDto.setCreatedBy(wall.getCreatedBy());
		wallDto.setCreatedDate(wall.getCreatedDate());
		wallDto.setLastUpdatedBy(wall.getLastUpdatedBy());
		wallDto.setLastUpdatedDate(wall.getLastUpdatedDate());
		wallDto.setMessage(wall.getMessage());
		wallDto.setTripDto(TripDto.instantiate(wall.getTrip()));
		wallDto.setTripMemberDto(TripMemberDto.instantiate(wall.getTripMember()));
		wallDto.setWallId(wall.getWallId());
	}
	
	public static WallDto instantiate(Wall wall){
		
		WallDto wallDto=new WallDto();
		populate(wallDto, wall);
		return wallDto;
	}
	
}
