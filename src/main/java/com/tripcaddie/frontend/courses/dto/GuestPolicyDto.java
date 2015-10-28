package com.tripcaddie.frontend.courses.dto;

import java.io.Serializable;

import com.tripcaddie.backend.courses.model.GuestPolicy;

public class GuestPolicyDto implements Serializable{
	
	private static final long serialVersionUID = 4367317154889883111L;
	private int policyId;
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
	
	public static void populate(GuestPolicyDto guestPolicyDto,GuestPolicy guestPolicy) {
		
		guestPolicyDto.setPolicy(guestPolicy.getPolicy());
		guestPolicyDto.setPolicyId(guestPolicy.getPolicyId());
	}
	
	public static GuestPolicyDto instantiate(GuestPolicy guestPolicy) {
		GuestPolicyDto guestPolicyDto =new GuestPolicyDto();
		return guestPolicyDto;
	}
	
	
}
