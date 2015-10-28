package com.tripcaddie.backend.user.dao;

import java.util.ArrayList;
import com.tripcaddie.backend.user.model.Roles;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.backend.user.model.UserStatus;

public interface UserDao {

	boolean registerUser(TripCaddieUser tripCaddieUser);
	boolean isExistUser(String userName);
	boolean isExistEmail(String email);
	TripCaddieUser getUserByUserName(String userName);
	TripCaddieUser getUserByEmail(String email);
	
	//boolean storeUserSessioninTripCaddie(HttpSession session);
	
	UserStatus getUserStatus(int statusId);
	ArrayList<UserStatus> getUserStatus();
	
	ArrayList<Roles> getRoles();
	Roles getRoles(int roleId);
	
	void updateNoofLogins(TripCaddieUser user);
	void updateUser(TripCaddieUser tripCaddieUser);
	String getAuthenticatedUser();
//	void clearSessionInTripcaddie(String session);
	
	public TripCaddieUser getUserBasedOnId(int userId) throws Exception;
}
