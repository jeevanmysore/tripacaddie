package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.trip.model.PollOption;

public class PollOptionDto implements Serializable {

	private static final long serialVersionUID = 6466977265391600948L;
	private int optionId;
	private PollQuestionsDto pollQuestionsDto;
	private String options;
	private Integer count;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private Double percentageOfVote;
	
	public int getOptionId() {
		return optionId;
	}
	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}
	public PollQuestionsDto getPollQuestionsDto() {
		return pollQuestionsDto;
	}
	public void setPollQuestionsDto(PollQuestionsDto pollQuestionsDto) {
		this.pollQuestionsDto = pollQuestionsDto;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
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
	
	public static void populate(PollOptionDto pollOptionDto,PollOption pollOption) {
		
		pollOptionDto.setCount(pollOption.getCount());
		pollOptionDto.setCreatedBy(pollOption.getCreatedBy());
		pollOptionDto.setCreatedDate(pollOption.getCreatedDate());
		pollOptionDto.setLastUpdatedBy(pollOption.getLastUpdatedBy());
		pollOptionDto.setLastUpdatedDate(pollOption.getLastUpdatedDate());
		pollOptionDto.setOptionId(pollOption.getOptionId());
		pollOptionDto.setOptions(pollOption.getOptions());
		pollOptionDto.setPollQuestionsDto(PollQuestionsDto.instantiate(pollOption.getPollQuestions()));
	}
	
	public static PollOptionDto instantiate(PollOption pollOption) {
		
		PollOptionDto pollOptionDto=new PollOptionDto();
		populate(pollOptionDto, pollOption);
		return pollOptionDto;
		
	}
	public Double getPercentageOfVote() {
		return percentageOfVote;
	}
	public void setPercentageOfVote(Double percentageOfVote) {
		this.percentageOfVote = percentageOfVote;
	}
}
