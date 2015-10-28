package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;

import com.tripcaddie.backend.trip.model.TripLeaderDelegation;

public class TripLeaderDelegationDto implements Serializable {
	
	private static final long serialVersionUID = 211808837751230419L;
	private int delegationOptionId;
	private String delegationOption;
	
	public int getDelegationOptionId() {
		return delegationOptionId;
	}
	public void setDelegationOptionId(int delegationOptionId) {
		this.delegationOptionId = delegationOptionId;
	}
	public String getDelegationOption() {
		return delegationOption;
	}
	public void setDelegationOption(String delegationOption) {
		this.delegationOption = delegationOption;
	}

	public static void populate(TripLeaderDelegation delegation,TripLeaderDelegationDto delegationDto){
		
		delegationDto.setDelegationOption(delegation.getDelegationOption());
		delegationDto.setDelegationOptionId(delegation.getDelegationOptionId());
		
	}
	
	public static TripLeaderDelegationDto instantiate(TripLeaderDelegation tripLeaderDelegation){
		
		TripLeaderDelegationDto delegationDto=new TripLeaderDelegationDto();
		populate(tripLeaderDelegation, delegationDto);
		return delegationDto;
		
	}
}
