package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.trip.model.WinningAmount;

public class WinningAmountDto implements Serializable {
	
	private static final long serialVersionUID = 8694306615551135157L;
	private int winningAmountId;
	private TripMemberDto tripMemberDto;
	private Double winningAmount;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	public int getWinningAmountId() {
		return winningAmountId;
	}
	public void setWinningAmountId(int winningAmountId) {
		this.winningAmountId = winningAmountId;
	}
	public TripMemberDto getTripMemberDto() {
		return tripMemberDto;
	}
	public void setTripMemberDto(TripMemberDto tripMemberDto) {
		this.tripMemberDto = tripMemberDto;
	}
	public Double getWinningAmount() {
		return winningAmount;
	}
	public void setWinningAmount(Double winningAmount) {
		this.winningAmount = winningAmount;
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
	
	public static void populate(WinningAmountDto winningAmountDto,WinningAmount winningAmount){
		
		winningAmountDto.setCreatedBy(winningAmount.getCreatedBy());
		winningAmountDto.setCreatedDate(winningAmount.getCreatedDate());
		winningAmountDto.setLastUpdatedBy(winningAmount.getLastUpdatedBy());
		winningAmountDto.setLastUpdatedDate(winningAmount.getLastUpdatedDate());
		winningAmountDto.setTripMemberDto(TripMemberDto.instantiate(winningAmount.getTripMember()));
		winningAmountDto.setWinningAmount(winningAmount.getWinningAmount());
		winningAmountDto.setWinningAmountId(winningAmount.getWinningAmountId());
	}
	
	public static WinningAmountDto instantiate(WinningAmount winningAmount){
		
		WinningAmountDto winningAmountDto=new WinningAmountDto();
		populate(winningAmountDto, winningAmount);
		return winningAmountDto;
	}

}
