package com.tripcaddie.backend.user.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.bucketList.model.GolfClubAdviceResponse;
import com.tripcaddie.backend.bucketList.model.GolfCourseReview;

@Entity
@Table(name="tripcaddie_user")
public class TripCaddieUser{

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="User_id")
	private int userId;
	@Column(name="username")
	private String userName;
	@Column(name="password")
	private String password;
	@Column(name="email", nullable=false, unique=true)
	private String email;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="last_login_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastLoginDate;
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdDate;
	@Column(name="no_of_logins")
	private Integer noOfLogins;
	@Column(name="date_of_birth")
	@Temporal(TemporalType.DATE)
	private Calendar dateOfBirth;
	@Column(name="last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdatedDate;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="updated_by")
	private String updatedBy;
	@ManyToOne
	@JoinColumn(name="status_id")
	private UserStatus userStatus;  
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="user_roles",
			joinColumns=@JoinColumn(name="User_id",nullable=false,updatable=false),
			inverseJoinColumns=@JoinColumn(name="role_id",nullable=false,updatable=false)
	       )
	private Collection<Roles> roles=new ArrayList<Roles>();
	@ManyToMany(mappedBy="users")
	private Collection<GolfClubAdviceResponse> responsesLike=new ArrayList<GolfClubAdviceResponse>();
	@ManyToMany(mappedBy="users")
	private Collection<GolfCourseReview> reviewsLike=new ArrayList<GolfCourseReview>();
	
	@Column(name="image_url")
	private String imageUrl;
	@Column(name="nickname")
	private String nickName;
	@Column(name="phoneno")
	private String phoneNo;
	@Column(name="city")
	private String city;
	@Column(name="state")
	private String state;
	@Column(name="golf_handicap")
	private Integer golfHandicap;
	@Column(name="average_score")
	private Integer averageScore;
	@Column(name="language")
	private String language;
	@Column(name="init_email")
	private String initialEmail;
	@Column(name="up_trip_lead")
	private Integer updateByTripLeaderNotification;
	@Column(name="Up_made_by_any")
	private Integer updateMadeByAnyoneNotification;
	@Column(name="notifee_accepted")
	private Integer notifeeAcceptedNotification;
	@Column(name="notifee_rejected")
	private Integer notifeeRejectedNotification;
	@Column(name="award_creation")
	private Integer awardCreationNotification;
	@Column(name="birthday_email")
	private Integer birthdayEmailSend;
			
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
	public UserStatus getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	public Collection<Roles> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Roles> roles) {
		this.roles = roles;
	}
	public Collection<GolfClubAdviceResponse> getResponsesLike() {
		return responsesLike;
	}
	public void setResponsesLike(Collection<GolfClubAdviceResponse> responsesLike) {
		this.responsesLike = responsesLike;
	}
	public Collection<GolfCourseReview> getReviewsLike() {
		return reviewsLike;
	}
	public void setReviewsLike(Collection<GolfCourseReview> reviewsLike) {
		this.reviewsLike = reviewsLike;
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
	public Integer getUpdateByTripLeaderNotification() {
		return updateByTripLeaderNotification;
	}
	public void setUpdateByTripLeaderNotification(
			Integer updateByTripLeaderNotification) {
		this.updateByTripLeaderNotification = updateByTripLeaderNotification;
	}
	public Integer getUpdateMadeByAnyoneNotification() {
		return updateMadeByAnyoneNotification;
	}
	public void setUpdateMadeByAnyoneNotification(
			Integer updateMadeByAnyoneNotification) {
		this.updateMadeByAnyoneNotification = updateMadeByAnyoneNotification;
	}
	public Integer getNotifeeAcceptedNotification() {
		return notifeeAcceptedNotification;
	}
	public void setNotifeeAcceptedNotification(Integer notifeeAcceptedNotification) {
		this.notifeeAcceptedNotification = notifeeAcceptedNotification;
	}
	public Integer getNotifeeRejectedNotification() {
		return notifeeRejectedNotification;
	}
	public void setNotifeeRejectedNotification(Integer notifeeRejectedNotification) {
		this.notifeeRejectedNotification = notifeeRejectedNotification;
	}
	public Integer getAwardCreationNotification() {
		return awardCreationNotification;
	}
	public void setAwardCreationNotification(Integer awardCreationNotification) {
		this.awardCreationNotification = awardCreationNotification;
	}
	public Integer getBirthdayEmailSend() {
		return birthdayEmailSend;
	}
	public void setBirthdayEmailSend(Integer birthdayEmailSend) {
		this.birthdayEmailSend = birthdayEmailSend;
	}
	
}
