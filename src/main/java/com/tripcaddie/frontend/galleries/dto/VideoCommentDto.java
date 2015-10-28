package com.tripcaddie.frontend.galleries.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.galleries.model.VideoComment;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public class VideoCommentDto implements Serializable{

	private static final long serialVersionUID = 4331808781566288094L;
	private int videoCommentId;
	private VideoFileDto videoFileDto;
	private TripMemberDto tripMemberDto;
	private String comment;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	public int getVideoCommentId() {
		return videoCommentId;
	}
	public void setVideoCommentId(int videoCommentId) {
		this.videoCommentId = videoCommentId;
	}
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	
	public static void populate(VideoCommentDto videoCommentDto,VideoComment videoComment){
		
		videoCommentDto.setComment(videoComment.getComment());
		videoCommentDto.setCreatedBy(videoComment.getCreatedBy());
		videoCommentDto.setCreatedDate(videoComment.getCreatedDate());
		videoCommentDto.setLastUpdatedBy(videoComment.getLastUpdatedBy());
		videoCommentDto.setLastUpdatedDate(videoComment.getLastUpdatedDate());
		videoCommentDto.setTripMemberDto(TripMemberDto.instantiate(videoComment.getTripMember()));
		videoCommentDto.setVideoCommentId(videoComment.getVideoCommentId());
		videoCommentDto.setVideoFileDto(VideoFileDto.instantiate(videoComment.getVideoFile()));
	}
	
	public static VideoCommentDto instantiate(VideoComment videoComment){
		
		VideoCommentDto videoCommentDto=new VideoCommentDto();
		populate(videoCommentDto, videoComment);
		return videoCommentDto;
	}
}
