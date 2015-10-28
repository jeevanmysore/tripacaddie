package com.tripcaddie.common.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.common.scheduler.dao.SchedulerDao;
import com.tripcaddie.common.util.EmailSender;

@Component("scheduler")
public class SchedulerMethodImpl implements SchedulerMethod {

	@Resource(name="schedulerDao")
	private SchedulerDao schedulerDao;
	@Resource(name="emailSender")
	private EmailSender emailSender;
	
	
	/*Need to find when we have to send trip remainder*/
	@Override
	public void sendAnnualTripRemainder() throws Exception {
		System.out.println("In annual remainder");
		String date;
		Date now=new Date();
		Calendar today=Calendar.getInstance();
		today.setTime(now);
		
		int dayInMonth=today.get(Calendar.DAY_OF_MONTH);
		int month=today.get(Calendar.MONTH)+1;
		
		if(month<10 && dayInMonth<10){
			date="%-0"+month+"-0"+dayInMonth+"%";
		}else{
			if(month<10){
				date="%-0"+month+"-"+dayInMonth+"%";
			}else if(dayInMonth<10){
				date="%-"+month+"-0"+dayInMonth+"%";
			}else{
				date="%-"+month+"-"+dayInMonth+"%";
			}
		}
			
		System.out.println("String:"+date);
		
		ArrayList<Trip> trips=schedulerDao.getAllTrips(date);
		for (Trip trip : trips) {
			System.out.println(trip);
		}
		
	}

	@Override
	public void sendBirthdayMail() throws Exception{
		
		System.out.println("in birthday mail");
		String date;
		Date now=new Date();
		Calendar today=Calendar.getInstance();
		today.setTime(now);
		
		int dayInMonth=today.get(Calendar.DAY_OF_MONTH);
		int month=today.get(Calendar.MONTH)+1;
		
		if(month<10 && dayInMonth<10){
			date="%-0"+month+"-0"+dayInMonth+"%";
		}else{
			if(month<10){
				date="%-0"+month+"-"+dayInMonth+"%";
			}else if(dayInMonth<10){
				date="%-"+month+"-0"+dayInMonth+"%";
			}else{
				date="%-"+month+"-"+dayInMonth+"%";
			}
		}
			
		System.out.println("String:"+date);
						
		ArrayList<TripCaddieUser> users=schedulerDao.getUsers(date);
		for (TripCaddieUser tripCaddieUser : users) {
			emailSender.sendEmail(tripCaddieUser, "Happy Birthday from TripCaddie!", "birthdayMail");
		}
		System.out.println("Users"+users);
	}

}
