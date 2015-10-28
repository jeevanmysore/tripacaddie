package com.tripcaddie.frontend.user.service;

import javax.servlet.http.HttpSession;

import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;


public interface LoginService {
	
	TripcaddieUserDto getUserByUserName(String userName) throws Exception;
	TripcaddieUserDto getUserByEmail(String email)  throws Exception;
	void updateLoginDetails(HttpSession session)  throws Exception;
	
	//void logout(String string)  throws Exception;
	//boolean storeUserSessioninTripCaddie(HttpSession session)  throws Exception;
	
	String getAuthenticatedUser()  throws Exception;
	void activate(String username) throws Exception;
	void updatePassword(String password,String username) throws Exception;
	
	TripCaddieUser createUserFrom(TripcaddieUserDto tripcaddieUserDto)  throws Exception;
	TripcaddieUserDto getUserBasedOnId(int userId) throws Exception;
	
}
