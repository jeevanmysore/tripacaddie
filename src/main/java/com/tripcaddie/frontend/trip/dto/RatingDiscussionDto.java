package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;

import com.tripcaddie.backend.trip.model.RatingDiscussion;

public class RatingDiscussionDto implements Serializable {

	private static final long serialVersionUID = 3521009065849567617L;
	private DiscussionDto discussionDto;
	private TripMemberDto tripMemberDto;
	private Double rating;

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
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	public static void populate(RatingDiscussionDto ratingDiscussionDto, RatingDiscussion ratingDiscussion){
		
		ratingDiscussionDto.setDiscussionDto(DiscussionDto.instantiate(ratingDiscussion.getRatingDiscussionPK().getDiscussion()));
		ratingDiscussionDto.setRating(ratingDiscussion.getRating());
		ratingDiscussionDto.setTripMemberDto(TripMemberDto.instantiate(ratingDiscussion.getRatingDiscussionPK().getTripMember()));
	}
	
	public static RatingDiscussionDto instantiate(RatingDiscussion ratingDiscussion){
		
		RatingDiscussionDto ratingDiscussionDto=new RatingDiscussionDto();
		populate(ratingDiscussionDto, ratingDiscussion);
		return ratingDiscussionDto;
	}
	
}
