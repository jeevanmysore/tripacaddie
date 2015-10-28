package com.tripcaddie.backend.trip.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="winning_amount")
public class WinningAmount {
	
	@Id @GeneratedValue
	@Column(name="winning_amount_id")
	private int winningAmountId;
	@ManyToOne
	@JoinColumn(name="trip_member_id",nullable=false)
	private TripMember tripMember;
	@Column(name="winning_amount")
	private Double winningAmount;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdDate;
	@Column(name="last_updated_by")
	private String lastUpdatedBy;
	@Column(name="last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdatedDate;
	
	public int getWinningAmountId() {
		return winningAmountId;
	}
	public void setWinningAmountId(int winningAmountId) {
		this.winningAmountId = winningAmountId;
	}
	public TripMember getTripMember() {
		return tripMember;
	}
	public void setTripMember(TripMember tripMember) {
		this.tripMember = tripMember;
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
	
	
}
