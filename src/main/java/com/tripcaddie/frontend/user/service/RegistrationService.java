package com.tripcaddie.frontend.user.service;

public interface RegistrationService {
	
	void registerUser(String userName,String password,String email,String firstName,String lastName)  throws Exception;
	boolean isExistUser(String userName)  throws Exception;
	boolean isExistEmail(String email)  throws Exception;
}
