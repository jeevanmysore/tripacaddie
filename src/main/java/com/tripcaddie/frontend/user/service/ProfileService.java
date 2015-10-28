package com.tripcaddie.frontend.user.service;

import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public interface ProfileService {
	
	void updateCurrentUser(String firstName,String lastName,String nickName,
			String phoneno, String city, String state,String golfHandicap,
			String avgScore,String dob,String notify_tripleader,String notify_tripanyone,String notify_acceptedinv,String notify_rejectedinv,String notify_awardcreated,String notify_sendemail);
	TripcaddieUserDto getCurrentUser();
	void uploadPhotoForCurrentUser();
	
	void updateAccountProfile(String email, String password ,String filename );
}
