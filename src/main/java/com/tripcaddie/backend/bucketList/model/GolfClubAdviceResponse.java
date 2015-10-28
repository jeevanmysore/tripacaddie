package com.tripcaddie.backend.bucketList.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.user.model.TripCaddieUser;

@Entity
@Table(name="golf_club_advice_response")
public class GolfClubAdviceResponse {
	
	@Id @GeneratedValue
	@Column(name="response_id")
	private int responseId;
	@Lob
	@Column(name="answer")
	private String answer;
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
	@ManyToOne
	@JoinColumn(name="golf_course_advice_id")
	private GolfClubInquiry inquiry;
	@ManyToOne
	@JoinColumn(name="user_id")
	private TripCaddieUser user;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="advice_response_like",
			joinColumns=@JoinColumn(name="user_id",nullable=true),
			inverseJoinColumns=@JoinColumn(name="response_id",nullable=true))
	private Collection<TripCaddieUser> users=new ArrayList<TripCaddieUser>();
	
	public TripCaddieUser getUser() {
		return user;
	}
	public void setUser(TripCaddieUser user) {
		this.user = user;
	}
	public Collection<TripCaddieUser> getUsers() {
		return users;
	}
	public void setUsers(Collection<TripCaddieUser> users) {
		this.users = users;
	}
	public int getResponseId() {
		return responseId;
	}
	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public GolfClubInquiry getInquiry() {
		return inquiry;
	}
	public void setInquiry(GolfClubInquiry inquiry) {
		this.inquiry = inquiry;
	}
	
}
