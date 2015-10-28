package com.tripcaddie.backend.courses.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="guest_policy")
public class GuestPolicy {
	
	@Id @GeneratedValue
	@Column(name="guest_policy_id")
	private int policyId;
	@Column(name="policy")
	private String policy;
	
	public int getPolicyId() {
		return policyId;
	}
	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	
	

}
