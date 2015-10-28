package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import com.tripcaddie.backend.trip.model.PollQuestions;

public class PollQuestionsDto implements Serializable {

	private static final long serialVersionUID = -6447440123756394885L;
	private int pollId;
	private String questions;
	private TripDto tripDto;
	private TripMemberDto tripMemberDto;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private Integer voteOrNot;    //0 for No. 1 for yes
	private ArrayList<PollOptionDto> pollOptionDtos=new ArrayList<PollOptionDto>();
		
	public ArrayList<PollOptionDto> getPollOptionDtos() {
		return pollOptionDtos;
	}
	public void setPollOptionDtos(ArrayList<PollOptionDto> pollOptionDtos) {
		this.pollOptionDtos = pollOptionDtos;
	}
	public int getPollId() {
		return pollId;
	}
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
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
	
	public static void populate(PollQuestionsDto pollQuestionsDto,PollQuestions pollQuestions){
		
		pollQuestionsDto.setCreatedBy(pollQuestions.getCreatedBy());
		pollQuestionsDto.setCreatedDate(pollQuestions.getCreatedDate());
		pollQuestionsDto.setLastUpdatedBy(pollQuestions.getLastUpdatedBy());
		pollQuestionsDto.setLastUpdatedDate(pollQuestions.getLastUpdatedDate());
		pollQuestionsDto.setPollId(pollQuestions.getPollId());
		pollQuestionsDto.setQuestions(pollQuestions.getQuestions());
		pollQuestionsDto.setTripDto(TripDto.instantiate(pollQuestions.getTrip()));
		pollQuestionsDto.setTripMemberDto(TripMemberDto.instantiate(pollQuestions.getTripMember()));
		
	}
	
	public static PollQuestionsDto instantiate(PollQuestions pollQuestions){
		
		PollQuestionsDto pollQuestionsDto=new PollQuestionsDto();
		populate(pollQuestionsDto, pollQuestions);
		return pollQuestionsDto;
	}
	public Integer getVoteOrNot() {
		return voteOrNot;
	}
	public void setVoteOrNot(Integer voteOrNot) {
		this.voteOrNot = voteOrNot;
	}

}
