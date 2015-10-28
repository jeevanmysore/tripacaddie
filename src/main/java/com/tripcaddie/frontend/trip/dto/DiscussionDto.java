package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import com.tripcaddie.backend.trip.model.Discussion;

public class DiscussionDto implements Serializable{
	
	private static final long serialVersionUID = -324135451084263400L;
	private int discussionId;
	private TripDto tripDto;
	private String title;
	private String description;
	private Double avgRating;
	private TripMemberDto tripMemberDto;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private ArrayList<DiscussionCommentDto> commentDtos=new ArrayList<DiscussionCommentDto>();
	private Integer noOfComments;
	
	public Integer getNoOfComments() {
		return noOfComments;
	}
	public void setNoOfComments(Integer noOfComments) {
		this.noOfComments = noOfComments;
	}
	public ArrayList<DiscussionCommentDto> getCommentDtos() {
		return commentDtos;
	}
	public void setCommentDtos(ArrayList<DiscussionCommentDto> commentDtos) {
		this.commentDtos = commentDtos;
	}
	public int getDiscussionId() {
		return discussionId;
	}
	public void setDiscussionId(int discussionId) {
		this.discussionId = discussionId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
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
	
	public static void populate(DiscussionDto discussionDto,Discussion discussion) {
		
		discussionDto.setAvgRating(discussion.getAvgRating());
		discussionDto.setCreatedBy(discussion.getCreatedBy());
		discussionDto.setCreatedDate(discussion.getCreatedDate());
		discussionDto.setDescription(discussion.getDescription());
		discussionDto.setDiscussionId(discussion.getDiscussionId());
		discussionDto.setLastUpdatedBy(discussionDto.getLastUpdatedBy());
		discussionDto.setLastUpdatedDate(discussion.getLastUpdatedDate());
		discussionDto.setTitle(discussion.getTitle());
		discussionDto.setTripDto(TripDto.instantiate(discussion.getTrip()));
		discussionDto.setTripMemberDto(TripMemberDto.instantiate(discussion.getTripMember()));
	}

	public static DiscussionDto instantiate(Discussion discussion) {
		
		DiscussionDto discussionDto=new DiscussionDto();
		populate(discussionDto, discussion);
		return discussionDto;
	}
}
