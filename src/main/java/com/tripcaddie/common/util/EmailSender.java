package com.tripcaddie.common.util;

import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public interface EmailSender {
	
	void sendEmail(TripcaddieUserDto userDto,String subject,String template,String contextPath);
	void sendEmail(String emailAddress,String username,String subject,String template,String contextPath);
	void sendEmail(String username,String recipentName,String recipentEmail,String subject,String template,String contextPath);

	void sendEmail(String emailAddress,TripcaddieUserDto userDto,String subject,String template,String contextPath,String personalMessage);
	void sendEmail(String emailAddress,TripcaddieUserDto userDto,String subject,String template,String contextPath,String personalMessage,TripDto trip);
	
	//For Birthday and annual trip
	void sendEmail(TripCaddieUser tripCaddieUser,String subject,String template);
	void sendEmailForAbuse(String from,String name ,String reason ,String message);
	
	void sendEmailFeedback(String email , String message);
}
