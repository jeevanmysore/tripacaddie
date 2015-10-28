package com.tripcaddie.frontend.user.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tripcaddie.backend.user.dao.UserDao;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.backend.user.model.UserStatus;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.dto.UserStatusDto;

@Transactional
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Resource(name="userDao")
	private UserDao userDao;
	
	public TripcaddieUserDto getUserByUserName(String userName) throws Exception{
		Assert.hasText(userName);
		TripCaddieUser tripCaddieUser=this.userDao.getUserByUserName(userName);
		if(tripCaddieUser!=null){
			TripcaddieUserDto tripcaddieUserDto=TripcaddieUserDto.instantiate(tripCaddieUser);
			return tripcaddieUserDto;
		}else{
			return null;
		}
	}

	public TripcaddieUserDto getUserByEmail(String email)  throws Exception{
		Assert.hasText(email);
		
		TripCaddieUser tripCaddieUser=this.userDao.getUserByEmail(email);
		if(tripCaddieUser != null){
			TripcaddieUserDto tripcaddieUserDto=TripcaddieUserDto.instantiate(tripCaddieUser);
			return tripcaddieUserDto;
		}else
			return null;
		
	}

	public void updateLoginDetails(HttpSession session)  throws Exception{

		
		if(session.getAttribute("username")==null){
			String username=this.getAuthenticatedUser();
			session.setAttribute("username", username);	
			
			TripCaddieUser tripCaddieUser=this.userDao.getUserByUserName(username);
			Calendar calendar=Calendar.getInstance();
			Date date=new Date();
			calendar.setTime(date);
			Integer noOfLogins=tripCaddieUser.getNoOfLogins();
			tripCaddieUser.setLastLoginDate(calendar);
			tripCaddieUser.setNoOfLogins(noOfLogins+1);
			this.userDao.updateNoofLogins(tripCaddieUser);
		}
		
	}

	/*public boolean storeUserSessioninTripCaddie(HttpSession session)  throws Exception{
		Assert.notNull(session);
		//return userDao.storeUserSessioninTripCaddie(session);
		
	}

	@Override
	public void logout(String session)  throws Exception{
		Assert.hasText(session);
		//userDao.clearSessionInTripcaddie(session);
	}*/

	@Override
	public String getAuthenticatedUser()  throws Exception{
		return this.userDao.getAuthenticatedUser();
	}
	
	private Integer getValueFromDto(String option){
		if(option.equalsIgnoreCase("checked")){
			return 1;
		}else{
			return 0;
		}
	}
	public TripCaddieUser createUserFrom(TripcaddieUserDto tripcaddieUserDto)  throws Exception{
		
		TripCaddieUser tripCaddieUser=new TripCaddieUser();
		
		tripCaddieUser.setAverageScore(tripcaddieUserDto.getAverageScore());
		tripCaddieUser.setAwardCreationNotification(getValueFromDto(tripcaddieUserDto.getAwardCreationNotification()));
		tripCaddieUser.setBirthdayEmailSend(getValueFromDto(tripcaddieUserDto.getBirthdayEmailSend()));
		tripCaddieUser.setCity(tripcaddieUserDto.getCity());
		tripCaddieUser.setCreatedBy(tripcaddieUserDto.getCreatedBy());
		tripCaddieUser.setCreatedDate(tripcaddieUserDto.getCreatedDate());
		tripCaddieUser.setDateOfBirth(tripcaddieUserDto.getDateOfBirth());
		tripCaddieUser.setEmail(tripcaddieUserDto.getEmail());
		tripCaddieUser.setFirstName(tripcaddieUserDto.getFirstName());
		tripCaddieUser.setGolfHandicap(tripcaddieUserDto.getGolfHandicap());
		tripCaddieUser.setImageUrl(tripcaddieUserDto.getImageUrl());
		tripCaddieUser.setInitialEmail(tripcaddieUserDto.getInitialEmail());
		tripCaddieUser.setLanguage(tripcaddieUserDto.getLanguage());
		tripCaddieUser.setLastLoginDate(tripcaddieUserDto.getLastLoginDate());
		tripCaddieUser.setLastName(tripcaddieUserDto.getLastName());
		tripCaddieUser.setLastUpdatedDate(tripcaddieUserDto.getLastUpdatedDate());
		tripCaddieUser.setNickName(tripcaddieUserDto.getNickName());
		tripCaddieUser.setNoOfLogins(tripcaddieUserDto.getNoOfLogins());
		tripCaddieUser.setNotifeeAcceptedNotification(getValueFromDto(tripcaddieUserDto.getNotifeeAcceptedNotification()));
		tripCaddieUser.setNotifeeRejectedNotification(getValueFromDto(tripcaddieUserDto.getNotifeeRejectedNotification()));
		tripCaddieUser.setPassword(tripcaddieUserDto.getPassword());
		tripCaddieUser.setPhoneNo(tripcaddieUserDto.getPhoneNo());
		/*for(GolfClubAdviceResponseDto adviceResponseDto:tripcaddieUserDto.getResponsesLikeDtos()){
			tripCaddieUser.getResponsesLike().add()
		}*/
		/*tripCaddieUser.setReviewsLike(reviewsLike);
		tripCaddieUser.setRoles(roles);*/
		tripCaddieUser.setState(tripcaddieUserDto.getState());
		tripCaddieUser.setUpdateByTripLeaderNotification(getValueFromDto(tripcaddieUserDto.getUpdateByTripLeaderNotification()));
		tripCaddieUser.setUpdatedBy(tripcaddieUserDto.getUpdatedBy());
		tripCaddieUser.setUpdateMadeByAnyoneNotification(getValueFromDto(tripcaddieUserDto.getUpdateMadeByAnyoneNotification()));
		tripCaddieUser.setUserId(tripcaddieUserDto.getUserId());
		tripCaddieUser.setUserName(tripcaddieUserDto.getUserName());
		tripCaddieUser.setUserStatus(this.createUserStatusFrom(tripcaddieUserDto.getUserStatusDto()));
		return tripCaddieUser;
	}
	
	public UserStatus createUserStatusFrom(UserStatusDto userStatusDto)  throws Exception{
		
		UserStatus userStatus=new UserStatus();
		userStatus.setStatus(userStatusDto.getStatus());
		userStatus.setUserStatusId(userStatusDto.getUserStatusId());
		return userStatus;
		
	}

	@Override
	public void activate(String username) throws NullPointerException,Exception{
		TripCaddieUser tripCaddieUser=this.userDao.getUserByUserName(username);
		
		ArrayList<UserStatus> userStatus=this.userDao.getUserStatus();
		for(UserStatus status: userStatus){
			if(status.getStatus().equalsIgnoreCase("active")){
				tripCaddieUser.setUserStatus(status);
				break;
			}
		}
		userDao.updateUser(tripCaddieUser);
	}

	@Override
	public void updatePassword(String password,String username) throws Exception {
		
		TripCaddieUser tripCaddieUser=this.userDao.getUserByUserName(username);
		System.out.println(tripCaddieUser.getUserName());
		tripCaddieUser.setPassword(password);
		userDao.updateUser(tripCaddieUser);
		
		
	}

	@Override
	public TripcaddieUserDto getUserBasedOnId(int userId) throws Exception {
		
		return (TripcaddieUserDto.instantiate(userDao.getUserBasedOnId(userId)));
	}
	
	
	
}
