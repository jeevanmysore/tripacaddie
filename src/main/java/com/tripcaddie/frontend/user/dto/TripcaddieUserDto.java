package com.tripcaddie.frontend.user.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.tripcaddie.backend.user.model.Roles;
import com.tripcaddie.backend.user.model.TripCaddieUser;

public class TripcaddieUserDto implements Serializable{

	private static final long serialVersionUID = -3530975735428960297L;
	private int userId;
	private String userName;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String imageBase64;
	private Calendar lastLoginDate;
	private Calendar createdDate;
	private Integer noOfLogins;
	private Calendar dateOfBirth;
	private Calendar lastUpdatedDate;
	private String createdBy;
	private String updatedBy;
	private UserStatusDto userStatusDto;  
	private Collection<RolesDto> rolesDtos=new ArrayList<RolesDto>();
	/*private Collection<GolfClubAdviceResponseDto> responsesLikeDtos=new ArrayList<GolfClubAdviceResponseDto>();*/
	/*private Collection<GolfCourseReviewDto> reviewsLikeDtos=new ArrayList<GolfCourseReviewDto>();*/
	private String imageUrl;
	private String nickName;
	private String phoneNo;
	private String city;
	private String state;
	private Integer golfHandicap;
	private Integer averageScore;
	private String language;
	private String initialEmail;
	private String updateByTripLeaderNotification;
	private String updateMadeByAnyoneNotification;
	private String notifeeAcceptedNotification;
	private String notifeeRejectedNotification;
	private String awardCreationNotification;
	private String birthdayEmailSend;
	private String encryptValue;
	
	public String getEncryptValue() {
		return encryptValue;
	}
	public void setEncryptValue(String encryptValue) {
		this.encryptValue = encryptValue;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Calendar getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Calendar lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getNoOfLogins() {
		return noOfLogins;
	}
	public void setNoOfLogins(Integer noOfLogins) {
		this.noOfLogins = noOfLogins;
	}
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public UserStatusDto getUserStatusDto() {
		return userStatusDto;
	}
	public void setUserStatusDto(UserStatusDto userStatusDto) {
		this.userStatusDto = userStatusDto;
	}
	public Collection<RolesDto> getRolesDtos() {
		return rolesDtos;
	}
	public void setRolesDtos(Collection<RolesDto> rolesDtos) {
		this.rolesDtos = rolesDtos;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getGolfHandicap() {
		return golfHandicap;
	}
	public void setGolfHandicap(Integer golfHandicap) {
		this.golfHandicap = golfHandicap;
	}
	public Integer getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(Integer averageScore) {
		this.averageScore = averageScore;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getInitialEmail() {
		return initialEmail;
	}
	public void setInitialEmail(String initialEmail) {
		this.initialEmail = initialEmail;
	}
	public String getUpdateByTripLeaderNotification() {
		return updateByTripLeaderNotification;
	}
	public void setUpdateByTripLeaderNotification(
			Integer updateByTripLeaderNotification) {
		if(updateByTripLeaderNotification==null || updateByTripLeaderNotification==1 )
			this.updateByTripLeaderNotification = "yes";
		else
			this.updateByTripLeaderNotification = "no";
	}
	public String getUpdateMadeByAnyoneNotification() {
		return updateMadeByAnyoneNotification;
	}
	public void setUpdateMadeByAnyoneNotification(
			Integer updateMadeByAnyoneNotification) {
		if(updateMadeByAnyoneNotification==null || updateMadeByAnyoneNotification==1)
			this.updateMadeByAnyoneNotification = "yes";
		else
			this.updateMadeByAnyoneNotification = "no";
	}
	public String getNotifeeAcceptedNotification() {
		return notifeeAcceptedNotification;
	}
	public void setNotifeeAcceptedNotification(Integer notifeeAcceptedNotification) {
		if(notifeeAcceptedNotification==null || notifeeAcceptedNotification==1)
			this.notifeeAcceptedNotification = "yes";
		else
			this.notifeeAcceptedNotification = "no";
	}
	public String getNotifeeRejectedNotification() {
		return notifeeRejectedNotification;
	}
	public void setNotifeeRejectedNotification(Integer notifeeRejectedNotification) {
		if(notifeeRejectedNotification==null || notifeeRejectedNotification==1)
			this.notifeeRejectedNotification = "yes";
		else
			this.notifeeRejectedNotification = "no";
	}
	public String getAwardCreationNotification() {
		return awardCreationNotification;
	}
	public void setAwardCreationNotification(Integer awardCreationNotification) {
		if(awardCreationNotification==null || awardCreationNotification==1)
			this.awardCreationNotification = "yes";
		else
			this.awardCreationNotification = "no";
	}
	public String getBirthdayEmailSend() {
		return birthdayEmailSend;
	}
	public void setBirthdayEmailSend(Integer birthdayEmailSend) {
		if(birthdayEmailSend==null || birthdayEmailSend==1)
			this.birthdayEmailSend = "yes";
		else
			this.birthdayEmailSend = "no";
	}
	
	public static void populate(TripcaddieUserDto tripcaddieUserDto,TripCaddieUser tripCaddieUser) {
		
		tripcaddieUserDto.setAverageScore(tripCaddieUser.getAverageScore());
		tripcaddieUserDto.setAwardCreationNotification(tripCaddieUser.getAwardCreationNotification());
		tripcaddieUserDto.setBirthdayEmailSend(tripCaddieUser.getBirthdayEmailSend());
		tripcaddieUserDto.setCity(tripCaddieUser.getCity());
		tripcaddieUserDto.setGolfHandicap(tripCaddieUser.getGolfHandicap());
		tripcaddieUserDto.setImageUrl(tripCaddieUser.getImageUrl());
		tripcaddieUserDto.setInitialEmail(tripCaddieUser.getInitialEmail());
		tripcaddieUserDto.setLanguage(tripCaddieUser.getLanguage());
		tripcaddieUserDto.setNickName(tripCaddieUser.getNickName());
		tripcaddieUserDto.setNotifeeAcceptedNotification(tripCaddieUser.getNotifeeAcceptedNotification());
		tripcaddieUserDto.setNotifeeRejectedNotification(tripCaddieUser.getNotifeeRejectedNotification());
		tripcaddieUserDto.setPhoneNo(tripCaddieUser.getPhoneNo());
		tripcaddieUserDto.setState(tripCaddieUser.getState());
		tripcaddieUserDto.setUpdateByTripLeaderNotification(tripCaddieUser.getUpdateByTripLeaderNotification());
		tripcaddieUserDto.setUserId(tripCaddieUser.getUserId());
		tripcaddieUserDto.setUserName(tripCaddieUser.getUserName());
		tripcaddieUserDto.setPassword(tripCaddieUser.getPassword());
		tripcaddieUserDto.setEmail(tripCaddieUser.getEmail());
		tripcaddieUserDto.setFirstName(tripCaddieUser.getFirstName());
		tripcaddieUserDto.setLastLoginDate(tripCaddieUser.getLastLoginDate());
		tripcaddieUserDto.setLastName(tripCaddieUser.getLastName());
		tripcaddieUserDto.setLastUpdatedDate(tripCaddieUser.getLastUpdatedDate());
		tripcaddieUserDto.setNoOfLogins(tripCaddieUser.getNoOfLogins());
		/*for(GolfClubAdviceResponse response:tripCaddieUser.getResponsesLike()){
			tripcaddieUserDto.getResponsesLikeDtos().add(GolfClubAdviceResponseDto.instantiate(response));
		}*/
		/*for(GolfCourseReview courseReview:tripCaddieUser.getReviewsLike()){
			tripcaddieUserDto.getReviewsLikeDtos().add(GolfCourseReviewDto.instantiate(courseReview));
		}*/
		for(Roles roles:tripCaddieUser.getRoles()){
			tripcaddieUserDto.getRolesDtos().add(RolesDto.instantiate(roles));
		}
		tripcaddieUserDto.setUserStatusDto(UserStatusDto.instantiate(tripCaddieUser.getUserStatus()));
		tripcaddieUserDto.setUpdatedBy(tripCaddieUser.getUpdatedBy());
		tripcaddieUserDto.setCreatedBy(tripCaddieUser.getCreatedBy());
		tripcaddieUserDto.setCreatedDate(tripCaddieUser.getCreatedDate());
		tripcaddieUserDto.setDateOfBirth(tripCaddieUser.getDateOfBirth());
		tripcaddieUserDto.setUpdateMadeByAnyoneNotification(tripCaddieUser.getUpdateMadeByAnyoneNotification());
		
	}
	
	public static TripcaddieUserDto instantiate(TripCaddieUser tripCaddieUser) {
		
		TripcaddieUserDto tripcaddieUserDto =new TripcaddieUserDto();
		populate(tripcaddieUserDto, tripCaddieUser);
		return tripcaddieUserDto;
	}
	
	public String getImageBase64() {
		return imageBase64;
	}
	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
	
	
}
