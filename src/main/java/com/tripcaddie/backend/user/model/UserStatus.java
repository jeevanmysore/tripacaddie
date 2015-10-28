package com.tripcaddie.backend.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_status")
public class UserStatus {

	@Id
	@Column(name="status_id")
	private int userStatusId;
	@Column(name="value")
	private String status;
	
	public int getUserStatusId() {
		return userStatusId;
	}
	public void setUserStatusId(int userStatusId) {
		this.userStatusId = userStatusId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
