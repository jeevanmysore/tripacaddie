package com.tripcaddie.backend.trip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="trip_leader_delegation")
public class TripLeaderDelegation {
	
	@Id
	@Column(name="option_id")
	private int delegationOptionId;
	@Column(name="options")
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

}
