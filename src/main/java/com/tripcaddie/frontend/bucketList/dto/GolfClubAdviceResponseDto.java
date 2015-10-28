package com.tripcaddie.frontend.bucketList.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.tripcaddie.backend.bucketList.model.GolfClubAdviceResponse;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public class GolfClubAdviceResponseDto implements Serializable {
	
	private static final long serialVersionUID = 2197778485919451963L;
	private int responseId;
	private String answer;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private GolfClubInquiryDto adviceRequestDto;
	private TripcaddieUserDto userDto;
	private Collection<TripcaddieUserDto> usersDtos=new ArrayList<TripcaddieUserDto>();
	private int days;
	public int getResponseId() {
		return responseId;
	}
	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
	public GolfClubInquiryDto getAdviceRequestDto() {
		return adviceRequestDto;
	}
	public void setAdviceRequestDto(GolfClubInquiryDto adviceRequestDto) {
		this.adviceRequestDto = adviceRequestDto;
	}
	public TripcaddieUserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(TripcaddieUserDto userDto) {
		this.userDto = userDto;
	}
	public Collection<TripcaddieUserDto> getUsersDtos() {
		return usersDtos;
	}
	public void setUsersDtos(Collection<TripcaddieUserDto> usersDtos) {
		this.usersDtos = usersDtos;
	}
	
	public static void populate(GolfClubAdviceResponseDto adviceResponseDto,GolfClubAdviceResponse adviceResponse) {
		
		adviceResponseDto.setAdviceRequestDto(GolfClubInquiryDto.instantiate(adviceResponse.getInquiry()));
		adviceResponseDto.setAnswer(adviceResponse.getAnswer());
		adviceResponseDto.setCreatedBy(adviceResponse.getCreatedBy());
		adviceResponseDto.setCreatedDate(adviceResponse.getCreatedDate());
		adviceResponseDto.setLastUpdatedBy(adviceResponse.getLastUpdatedBy());
		adviceResponseDto.setLastUpdatedDate(adviceResponse.getLastUpdatedDate());
		adviceResponseDto.setResponseId(adviceResponse.getResponseId());
		adviceResponseDto.setUserDto(TripcaddieUserDto.instantiate(adviceResponse.getUser()));
	}
	
	public static GolfClubAdviceResponseDto instantiate(GolfClubAdviceResponse adviceResponse) {
		
		GolfClubAdviceResponseDto adviceResponseDto=new GolfClubAdviceResponseDto();
		populate(adviceResponseDto, adviceResponse);
		return adviceResponseDto;
		
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	
}
