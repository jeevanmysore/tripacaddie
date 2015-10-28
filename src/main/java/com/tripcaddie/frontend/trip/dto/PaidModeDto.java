package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;

import com.tripcaddie.backend.trip.model.PaidMode;

public class PaidModeDto implements Serializable{

	private static final long serialVersionUID = -7956981414309580567L;
	private int paidModeId;
	private String paidMode;
	
	public int getPaidModeId() {
		return paidModeId;
	}
	public void setPaidModeId(int paidModeId) {
		this.paidModeId = paidModeId;
	}
	public String getPaidMode() {
		return paidMode;
	}
	public void setPaidMode(String paidMode) {
		this.paidMode = paidMode;
	}
	
	public static void populate(PaidModeDto paidModeDto,PaidMode paidMode) {
		
		paidModeDto.setPaidMode(paidMode.getPaidMode());
		paidModeDto.setPaidModeId(paidMode.getPaidModeId());
	}
	
	public static PaidModeDto instantiate(PaidMode paidMode) {
		
		PaidModeDto paidModeDto=new PaidModeDto();
		populate(paidModeDto, paidMode);
		return paidModeDto;
	}
	

}
