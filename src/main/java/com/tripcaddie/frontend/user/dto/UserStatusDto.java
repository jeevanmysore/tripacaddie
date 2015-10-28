package com.tripcaddie.frontend.user.dto;

import java.io.Serializable;

import com.tripcaddie.backend.user.model.UserStatus;

public class UserStatusDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int userStatusId;
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
	
	public static void populate(UserStatusDto userStatusDto,UserStatus userStatus) {
		
		userStatusDto.setUserStatusId(userStatus.getUserStatusId());
		userStatusDto.setStatus(userStatus.getStatus());
	}
	
	public static UserStatusDto instantiate(UserStatus userStatus) {
		
		UserStatusDto userStatusDto=new UserStatusDto();
		populate(userStatusDto, userStatus);
		return userStatusDto;
	}
}
