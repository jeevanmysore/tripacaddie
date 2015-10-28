package com.tripcaddie.backend.bucketList.vo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.tripcaddie.backend.user.vo.UserTestBean;

public class GolfClubAdviceResponseTestBean {
	
	private int responseId;
	private String answer;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private GolfClubInquiryTestBean inquiry;
	private UserTestBean user;
	private Collection<UserTestBean> users=new ArrayList<UserTestBean>();
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
	public GolfClubInquiryTestBean getInquiry() {
		return inquiry;
	}
	public void setInquiry(GolfClubInquiryTestBean inquiry) {
		this.inquiry = inquiry;
	}
	public UserTestBean getUser() {
		return user;
	}
	public void setUser(UserTestBean user) {
		this.user = user;
	}
	public Collection<UserTestBean> getUsers() {
		return users;
	}
	public void setUsers(Collection<UserTestBean> users) {
		this.users = users;
	}
	
	

}
