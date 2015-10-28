package com.tripcaddie.backend.trip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="paid_mode")
public class PaidMode {
	
	@Id
	@Column(name="paid_mode_id",nullable=false)
	private int paidModeId;
	@Column(name="paid_mode")
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
	
}
