package com.tripcaddie.frontend.galleries.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.galleries.model.PictureComment;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public class PictureCommentDto implements Serializable {

	private static final long serialVersionUID = 611019401270514467L;
	private int pictureCommentId;
	private PictureFileDto pictureFileDto;
	private TripMemberDto tripMemberDto;
	private String comments;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	public int getPictureCommentId() {
		return pictureCommentId;
	}
	public void setPictureCommentId(int pictureCommentId) {
		this.pictureCommentId = pictureCommentId;
	}
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
	
	public static void populate(PictureCommentDto pictureCommentDto, PictureComment pictureComment) {
		
		pictureCommentDto.setComments(pictureComment.getComments());
		pictureCommentDto.setCreatedBy(pictureComment.getCreatedBy());
		pictureCommentDto.setCreatedDate(pictureComment.getCreatedDate());
		pictureCommentDto.setLastUpdatedBy(pictureComment.getLastUpdatedBy());
		pictureCommentDto.setLastUpdatedDate(pictureComment.getLastUpdatedDate());
		pictureCommentDto.setPictureCommentId(pictureComment.getPictureCommentId());
		pictureCommentDto.setPictureFileDto(PictureFileDto.instantiate(pictureComment.getPictureFile()));
		pictureCommentDto.setTripMemberDto(TripMemberDto.instantiate(pictureComment.getTripMember()));
	}
	
	public static PictureCommentDto instantiate(PictureComment pictureComment) {
		
		PictureCommentDto pictureCommentDto=new PictureCommentDto();
		populate(pictureCommentDto, pictureComment);
		return pictureCommentDto;
		
	}

}
