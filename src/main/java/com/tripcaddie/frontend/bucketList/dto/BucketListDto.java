package com.tripcaddie.frontend.bucketList.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.bucketList.model.BucketList;
import com.tripcaddie.frontend.courses.dto.GolfCourseDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public class BucketListDto implements Serializable{
	
	private static final long serialVersionUID = -47241366019821822L;
	private int bucketListId;
	private Integer priority;
	private GolfCourseDto golfCourseDto;
	private TripcaddieUserDto tripCaddieUserDto;
	private VisitedPlaceDto placesPlayedDto;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private Integer recommendationCount;
	private Integer inquiryCount;
	private Integer ratingCount;
	
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
	public Integer getRecommendationCount() {
		return recommendationCount;
	}
	public void setRecommendationCount(Integer recommendationCount) {
		this.recommendationCount = recommendationCount;
	}
	public Integer getInquiryCount() {
		return inquiryCount;
	}
	public void setInquiryCount(Integer inquiryCount) {
		this.inquiryCount = inquiryCount;
	}
	public Integer getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}
	public int getBucketListId() {
		return bucketListId;
	}
	public void setBucketListId(int bucketListId) {
		this.bucketListId = bucketListId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public GolfCourseDto getGolfCourseDto() {
		return golfCourseDto;
	}
	public void setGolfCourseDto(GolfCourseDto golfCourseDto) {
		this.golfCourseDto = golfCourseDto;
	}
	public TripcaddieUserDto getTripCaddieUserDto() {
		return tripCaddieUserDto;
	}
	public void setTripCaddieUserDto(TripcaddieUserDto tripCaddieUserDto) {
		this.tripCaddieUserDto = tripCaddieUserDto;
	}
	public VisitedPlaceDto getPlacesPlayedDto() {
		return placesPlayedDto;
	}
	public void setPlacesPlayedDto(VisitedPlaceDto placesPlayedDto) {
		this.placesPlayedDto = placesPlayedDto;
	}
	
	public static void populate(BucketListDto bucketListDto,BucketList bucketList) {
		
		bucketListDto.setGolfCourseDto(GolfCourseDto.instantiate(bucketList.getBucketListPK().getGolfCourse()));
		bucketListDto.setPlacesPlayedDto(VisitedPlaceDto.instantiate(bucketList.getPlacesPlayed()));
		bucketListDto.setPriority(bucketList.getPriority());
		bucketListDto.setTripCaddieUserDto(TripcaddieUserDto.instantiate(bucketList.getBucketListPK().getTripCaddieUser()));
		bucketListDto.setCreatedBy(bucketList.getCreatedBy());
		bucketListDto.setCreatedDate(bucketList.getCreatedDate());
		bucketListDto.setLastUpdatedBy(bucketList.getLastUpdatedBy());
		bucketListDto.setLastUpdatedDate(bucketList.getLastUpdatedDate());
	}
	public static BucketListDto instantiate(BucketList bucketList) {
		
		BucketListDto bucketListDto=new BucketListDto();
		populate(bucketListDto, bucketList);
		return bucketListDto;
	}
}
