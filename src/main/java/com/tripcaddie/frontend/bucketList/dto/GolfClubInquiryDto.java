package com.tripcaddie.frontend.bucketList.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.tripcaddie.backend.bucketList.model.GolfClubInquiry;
import com.tripcaddie.frontend.courses.dto.GolfCourseDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public class GolfClubInquiryDto implements Serializable {
		
	private static final long serialVersionUID = -8942204975991903228L;
	private int golfClubInquiryId;
	private String question;
	private Calendar lastUpdatedDate;
	private Calendar createdDate;
	private String createdBy;
	private String lastUpdatedBy;
	private TripcaddieUserDto userDto;
	private GolfCourseDto golfCourseDto;
	private Collection<GolfClubAdviceResponseDto> adviceResponseDtos=new ArrayList<GolfClubAdviceResponseDto>();
	private Integer noOfAdvices;
	private String currentUser;
	
	public Integer getNoOfAdvices() {
		return noOfAdvices;
	}
	public void setNoOfAdvices(Integer noOfAdvices) {
		this.noOfAdvices = noOfAdvices;
	}
	public int getGolfClubInquiryId() {
		return golfClubInquiryId;
	}
	public void setGolfClubInquiryId(int golfClubInquiryId) {
		this.golfClubInquiryId = golfClubInquiryId;
	}
	public Collection<GolfClubAdviceResponseDto> getAdviceResponseDtos() {
		return adviceResponseDtos;
	}
	public void setAdviceResponseDtos(
			Collection<GolfClubAdviceResponseDto> adviceResponseDtos) {
		this.adviceResponseDtos = adviceResponseDtos;
	}
	public TripcaddieUserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(TripcaddieUserDto userDto) {
		this.userDto = userDto;
	}
	public GolfCourseDto getGolfCourseDto() {
		return golfCourseDto;
	}
	public void setGolfCourseDto(GolfCourseDto golfCourseDto) {
		this.golfCourseDto = golfCourseDto;
	}
	public int getgolfClubInquiryId() {
		return golfClubInquiryId;
	}
	public void setgolfClubInquiryId(int golfClubInquiryId) {
		this.golfClubInquiryId = golfClubInquiryId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public static void populate(GolfClubInquiryDto golfClubInquiryDto,GolfClubInquiry golfClubInquiry) {
		
		golfClubInquiryDto.setCreatedBy(golfClubInquiry.getCreatedBy());
		golfClubInquiryDto.setCreatedDate(golfClubInquiry.getCreatedDate());
		golfClubInquiryDto.setgolfClubInquiryId(golfClubInquiry.getGolfClubInquiryId());
		golfClubInquiryDto.setGolfCourseDto(GolfCourseDto.instantiate(golfClubInquiry.getGolfCourse()));
		golfClubInquiryDto.setLastUpdatedBy(golfClubInquiry.getLastUpdatedBy());
		golfClubInquiryDto.setLastUpdatedDate(golfClubInquiry.getLastUpdatedDate());
		golfClubInquiryDto.setQuestion(golfClubInquiry.getQuestion());
		golfClubInquiryDto.setUserDto(TripcaddieUserDto.instantiate(golfClubInquiry.getUser()));
	}
	
	public static GolfClubInquiryDto instantiate(GolfClubInquiry golfClubInquiry) {
		
		GolfClubInquiryDto golfClubInquiryDto=new GolfClubInquiryDto();
		populate(golfClubInquiryDto, golfClubInquiry);
		return golfClubInquiryDto;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	
}
