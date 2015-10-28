package com.tripcaddie.frontend.galleries.dto;


import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.galleries.model.AwardsComment;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public class AwardCommentDto implements Serializable {

	private static final long serialVersionUID = 611019401270514467L;
	private int awardCommentId;
	private AwardsDto awardsDto;
	private TripMemberDto tripMemberDto;
	private String comments;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	
	public TripMemberDto getTripMemberDto() {
		return tripMemberDto;
	}
	public void setTripMemberDto(TripMemberDto tripMemberDto) {
		this.tripMemberDto = tripMemberDto;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public static void populate(AwardCommentDto awardCommentDto, AwardsComment awardsComment) {
		
		awardCommentDto.setComments(awardsComment.getComment());
		awardCommentDto.setCreatedBy(awardsComment.getCreatedBy());
		awardCommentDto.setCreatedDate(awardsComment.getCreatedDate());
		awardCommentDto.setLastUpdatedBy(awardsComment.getLastUpdatedBy());
		awardCommentDto.setLastUpdatedDate(awardsComment.getLastUpdatedDate());
		awardCommentDto.setAwardCommentId(awardsComment.getAwardCommentId());
		awardCommentDto.setAwardsDto(AwardsDto.instantiate(awardsComment.getAward()));
		awardCommentDto.setTripMemberDto(TripMemberDto.instantiate(awardsComment.getTripMember()));
	}
	
	public static AwardCommentDto instantiate( AwardsComment awardsComment) {
		
		AwardCommentDto awardCommentDto=new AwardCommentDto();
		populate(awardCommentDto, awardsComment);
		return awardCommentDto;
		
	}
	public int getAwardCommentId() {
		return awardCommentId;
	}
	public void setAwardCommentId(int awardCommentId) {
		this.awardCommentId = awardCommentId;
	}
	public AwardsDto getAwardsDto() {
		return awardsDto;
	}
	public void setAwardsDto(AwardsDto awardsDto) {
		this.awardsDto = awardsDto;
	}

}
