package com.tripcaddie.frontend.user.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.user.dao.UserDao;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

@Transactional
@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

	@Resource(name = "userDao")
	private UserDao userDao;

	@Override
	public TripcaddieUserDto getCurrentUser() {

		return null;
	}

	@Override
	public void uploadPhotoForCurrentUser() {

	}

	@Override
	public void updateCurrentUser(String firstName, String lastName,
			String nickName, String phoneno, String city, String state,
			String golfHandicap, String avgScore, String dob,
			String notify_tripleader, String notify_tripanyone,
			String notify_acceptedinv, String notify_rejectedinv,
			String notify_awardcreated, String notify_sendemail) {

		String username = userDao.getAuthenticatedUser();
		TripCaddieUser user = userDao.getUserByUserName(username);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setNickName(nickName);
		user.setPhoneNo(phoneno);
		user.setCity(city);
		user.setState(state);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		user.setLastUpdatedDate(calendar);
		user.setUpdatedBy(username);
		if(!golfHandicap.isEmpty())
		user.setGolfHandicap(Integer.valueOf(golfHandicap));
		if(!avgScore.isEmpty())
		user.setAverageScore(Integer.valueOf(avgScore));
		if (notify_tripleader == null) {
			System.out.println("notify_tripleader"+notify_tripleader);
			user.setUpdateByTripLeaderNotification(0);
		} else {
			System.out.println("notify_tripleader"+notify_tripleader);
			user.setUpdateByTripLeaderNotification(1);
		}

		if (notify_tripanyone == null) {
			user.setUpdateMadeByAnyoneNotification(0);
		} else {
			user.setUpdateMadeByAnyoneNotification(1);
		}

		if (notify_acceptedinv == null) {
			user.setNotifeeAcceptedNotification(0);
		} else {
			user.setNotifeeAcceptedNotification(1);
		}

		if (notify_rejectedinv == null) {
			user.setNotifeeRejectedNotification(0);
		} else {
			user.setNotifeeRejectedNotification(1);
		}

		if (notify_awardcreated == null) {
			user.setAwardCreationNotification(0);
		} else {
			user.setAwardCreationNotification(1);
		}

		if (notify_sendemail == null) {
			user.setBirthdayEmailSend(0);
		} else {
			user.setBirthdayEmailSend(1);
		}

		userDao.updateUser(user);
	}

	@Override
	public void updateAccountProfile(String email, String password,
			String filename) {
		String username = userDao.getAuthenticatedUser();
		TripCaddieUser user = userDao.getUserByUserName(username);
		user.setEmail(email);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		user.setLastUpdatedDate(calendar);
		user.setUpdatedBy(username);
		if(password!=null && !password.isEmpty())
		user.setPassword(password);
		if(filename!=null && !filename.isEmpty())
		user.setImageUrl(filename);
		userDao.updateUser(user);
	}

}
