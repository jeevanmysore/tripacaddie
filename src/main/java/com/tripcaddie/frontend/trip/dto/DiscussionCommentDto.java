package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.trip.model.DiscussionComment;

public class DiscussionCommentDto implements Serializable{

	private static final long serialVersionUID = -1674881195598081747L;
	private int discussionCommentId;
	private DiscussionDto discussionDto;
	private String comment;
	private TripMemberDto tripMemberDto;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	public int getDiscussionCommentId() {
		return discussionCommentId;
	}
	public void setDiscussionCommentId(int discussionCommentId) {
		this.discussionCommentId = discussionCommentId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public DiscussionDto getDiscussionDto() {
		return discussionDto;
	}
	public void setDiscussionDto(DiscussionDto discussionDto) {
		this.discussionDto = discussionDto;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public static void populate(DiscussionCommentDto discussionCommentDto,DiscussionComment discussionComment) {
		
		discussionCommentDto.setComment(discussionComment.getComment());
		discussionCommentDto.setCreatedBy(discussionComment.getCreatedBy());
		discussionCommentDto.setCreatedDate(discussionComment.getCreatedDate());
		discussionCommentDto.setDiscussionDto(DiscussionDto.instantiate(discussionComment.getDiscussion()));
		discussionCommentDto.setDiscussionCommentId(discussionComment.getDiscussionCommentId());
		discussionCommentDto.setLastUpdatedBy(discussionComment.getLastUpdatedBy());
		discussionCommentDto.setLastUpdatedDate(discussionComment.getLastUpdatedDate());
		discussionCommentDto.setTripMemberDto(TripMemberDto.instantiate(discussionComment.getTripMember()));
	}

	public static DiscussionCommentDto instantiate(DiscussionComment discussionComment) {
		
		DiscussionCommentDto discussionCommentDto=new DiscussionCommentDto();
		populate(discussionCommentDto, discussionComment);
		return discussionCommentDto;
		
	}
}
