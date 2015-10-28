package com.tripcaddie.frontend.galleries.dto;

import java.io.Serializable;

import com.tripcaddie.backend.galleries.model.RatingVideoFile;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public class RatingVideoFileDto implements Serializable {

	private static final long serialVersionUID = 474405976657678796L;
	private VideoFileDto videoFileDto;
	private TripMemberDto tripMemberDto;
	private Double rating;
	
	public VideoFileDto getVideoFileDto() {
		return videoFileDto;
	}
	public void setVideoFileDto(VideoFileDto videoFileDto) {
		this.videoFileDto = videoFileDto;
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
	
	public static void populate(RatingVideoFileDto ratingVideoFileDto,RatingVideoFile ratingVideoFile){
		
		ratingVideoFileDto.setRating(ratingVideoFile.getRating());
		ratingVideoFileDto.setTripMemberDto(TripMemberDto.instantiate(ratingVideoFile.getRatingVideoFilePK().getTripMember()));
		ratingVideoFileDto.setVideoFileDto(VideoFileDto.instantiate(ratingVideoFile.getRatingVideoFilePK().getVideoFile()));
	}
	
	public static RatingVideoFileDto instantiate(RatingVideoFile ratingVideoFile) {
		
		RatingVideoFileDto ratingVideoFileDto=new RatingVideoFileDto();
		populate(ratingVideoFileDto, ratingVideoFile);
		return ratingVideoFileDto;
		
	}
	

}
