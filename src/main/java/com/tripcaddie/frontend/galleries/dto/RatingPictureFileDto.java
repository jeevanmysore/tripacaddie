package com.tripcaddie.frontend.galleries.dto;

import java.io.Serializable;

import com.tripcaddie.backend.galleries.model.RatingPictureFile;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public class RatingPictureFileDto implements Serializable {

	private static final long serialVersionUID = -5478076666249430254L;
	private PictureFileDto pictureFileDto;
	private TripMemberDto tripMemberDto;
	private Double rating;
	
	public PictureFileDto getPictureFileDto() {
		return pictureFileDto;
	}
	public void setPictureFileDto(PictureFileDto pictureFileDto) {
		this.pictureFileDto = pictureFileDto;
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
	
	public static void populate(RatingPictureFileDto ratingPictureFileDto,RatingPictureFile ratingPictureFile){
		
		ratingPictureFileDto.setPictureFileDto(PictureFileDto.instantiate(ratingPictureFile.getRatingPictureFilePK().getPictureFile()));
		ratingPictureFileDto.setRating(ratingPictureFile.getRating());
		ratingPictureFileDto.setTripMemberDto(TripMemberDto.instantiate(ratingPictureFile.getRatingPictureFilePK().getTripMember()));
	}
	
	public static RatingPictureFileDto instantiate(RatingPictureFile ratingPictureFile){
		
		RatingPictureFileDto ratingPictureFileDto=new RatingPictureFileDto();
		populate(ratingPictureFileDto, ratingPictureFile);
		return ratingPictureFileDto;
	}
}
