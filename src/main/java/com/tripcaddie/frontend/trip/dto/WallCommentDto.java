package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.backend.trip.model.Wall;
import com.tripcaddie.backend.trip.model.WallComment;

public class WallCommentDto implements Serializable {

	private static final long serialVersionUID = 29977664050919759L;
	private int wallCommentId;
	private WallDto wallDto;
	private String comment;
	private TripMemberDto tripMemberDto;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	public int getWallCommentId() {
		return wallCommentId;
	}
	public void setWallCommentId(int wallCommentId) {
		this.wallCommentId = wallCommentId;
	}	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public WallDto getWallDto() {
		return wallDto;
	}
	public void setWallDto(WallDto wallDto) {
		this.wallDto = wallDto;
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
	
	public static void populate(WallCommentDto wallCommentDto,WallComment wallComment){
		
		wallCommentDto.setComment(wallComment.getComment());
		wallCommentDto.setCreatedBy(wallComment.getCreatedBy());
		wallCommentDto.setCreatedDate(wallComment.getCreatedDate());
		wallCommentDto.setLastUpdatedBy(wallComment.getLastUpdatedBy());
		wallCommentDto.setLastUpdatedDate(wallComment.getLastUpdatedDate());
		wallCommentDto.setTripMemberDto(TripMemberDto.instantiate(wallComment.getTripMember()));
		wallCommentDto.setWallCommentId(wallComment.getWallCommentId());
		wallCommentDto.setWallDto(WallDto.instantiate(wallComment.getWall()));
	}
	
	public static WallCommentDto instantiate(WallComment wallComment){
		
		WallCommentDto wallCommentDto=new WallCommentDto();
		populate(wallCommentDto, wallComment);
		return wallCommentDto;
	}
}
