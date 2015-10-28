package com.tripcaddie.frontend.itinerary.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.itinerary.dao.ActivityDao;
import com.tripcaddie.backend.itinerary.model.Activity;
import com.tripcaddie.backend.itinerary.model.ActivityType;
import com.tripcaddie.backend.trip.dao.TripDao;
import com.tripcaddie.backend.trip.model.RoundTeeTime;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.frontend.itinerary.dto.ActivityDto;
import com.tripcaddie.frontend.itinerary.dto.ActivityTypeDto;
import com.tripcaddie.frontend.trip.dto.RoundTeeTimeDto;
import com.tripcaddie.frontend.trip.service.RecentUpdateService;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

	@Resource(name = "activityyDao")
	private ActivityDao activityDao;
	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name="tripDao")
	private TripDao tripDao;
	@Resource(name="recentUpdateService")
	private RecentUpdateService recentUpdateService;
	
	@Override
	public ArrayList<ActivityDto> getActivities(int tripid) throws Exception{

		ArrayList<ActivityDto> activityDtos = new ArrayList<ActivityDto>();
		for (Activity activity : activityDao.getActivities(tripid)) {
			activityDtos.add(ActivityDto.instantiate(activity));
		}
		return activityDtos;
	}

	@Override
	public ArrayList<ActivityTypeDto> getActivityTypes() throws Exception {
		
		ArrayList<ActivityTypeDto> activityTypeDtos=new ArrayList<ActivityTypeDto>();
		ArrayList<ActivityType> activityTypes=activityDao.getActivityType();
		
		for (ActivityType activityType : activityTypes) {
			activityTypeDtos.add(ActivityTypeDto.instantiate(activityType));
		}
		return activityTypeDtos;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addActivity(int tripId, int activityTypeId,
			String activityTitle, String date, String time, String url,
			String mapInfo, String comments,Integer pending) throws Exception {
		
		Activity activity=new Activity();
		ActivityType activityType=activityDao.getActivityType(activityTypeId);
		Trip trip=tripDao.getTrip(tripId);
		
		System.out.println("time:"+time);
		String timeArray[]=time.split(":");
		System.out.println(timeArray);
		String username=loginService.getAuthenticatedUser();
		
		
		Calendar instantTime=Calendar.getInstance();
		Date today=new Date();
		instantTime.setTime(today);
		
		DateFormat dateFormat;
		Date activityDate=null;
		Calendar activitydate=Calendar.getInstance();
				
		dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		activityDate=(Date)dateFormat.parse(date);
		activitydate.setTime(activityDate);
		
		//For activity Time
		Date activityTime=new Date();
		if(pending==0){
			if(timeArray[2].equalsIgnoreCase("pm")){
				if(Integer.parseInt(timeArray[0]) == 12){
					activityTime.setHours(Integer.parseInt(timeArray[0]));
				}else{
					activityTime.setHours(Integer.parseInt(timeArray[0])+12);
				}				
			}else {
				if(Integer.parseInt(timeArray[0]) == 12){
					activityTime.setHours(0);
				}else{
					activityTime.setHours(Integer.parseInt(timeArray[0]));
				}
			}
			activityTime.setMinutes(Integer.parseInt(timeArray[1]));
			
			if(pending==0){
				activity.setActivityTime(activityTime);
			}
		}	
		//Seting Activity Object
		activity.setActivityDate(activitydate);
		activity.setActivityName(activityTitle);
		
		activity.setActivityType(activityType);
		activity.setComment(comments);
		activity.setCreatedBy(username);
		activity.setCreatedDate(instantTime);
		activity.setMapInfo(mapInfo);
		activity.setTimePending(pending);
		activity.setTrip(trip);
		activity.setUrl(url);
		
		activityDao.addActivity(activity);
		recentUpdateService.logUpdates(tripId, "Edited Itinerary", "leader");
		
	}

	@Override
	public void deleteActivity(int activityId) throws Exception {
		
		Activity activity=activityDao.getActivity(activityId);
		
		activityDao.deleteActivity(activity);
		recentUpdateService.logUpdates(activity.getTrip().getTripId(), "Edited Itinerary", "leader");
		
	}

	@Override
	public ActivityDto getActivity(int activityId) throws Exception {
		
		Activity activity=activityDao.getActivity(activityId);
		return ActivityDto.instantiate(activity);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void editActivity(int activityId, int activityTypeId,
			String activityTitle, String date, String time, String url,
			String mapInfo, String comments, Integer pending) throws Exception {
		
		Activity activity=activityDao.getActivity(activityId);
		ActivityType activityType=activityDao.getActivityType(activityTypeId);
				
		String timeArray[]=time.split(":");
		
		String username=loginService.getAuthenticatedUser();
		
		
		Calendar instantTime=Calendar.getInstance();
		Date today=new Date();
		instantTime.setTime(today);
		
		DateFormat dateFormat;
		Date activityDate=null;
		Calendar activitydate=Calendar.getInstance();
				
		dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		activityDate=(Date)dateFormat.parse(date);
		activitydate.setTime(activityDate);
		
		//For activity Time
		Date activityTime=new Date();
		if(timeArray[2].equalsIgnoreCase("pm")){
			activityTime.setHours(Integer.parseInt(timeArray[0])+12);
		}else {
			activityTime.setHours(Integer.parseInt(timeArray[0]));
		}
		activityTime.setMinutes(Integer.parseInt(timeArray[1]));
		
		if(pending==0){
			activity.setActivityTime(activityTime);
		}
		
		//Seting Activity Object
		activity.setActivityDate(activitydate);
		activity.setActivityName(activityTitle);
		
		activity.setActivityType(activityType);
		activity.setComment(comments);
		activity.setCreatedBy(username);
		activity.setCreatedDate(instantTime);
		activity.setMapInfo(mapInfo);
		activity.setTimePending(pending);
		activity.setUrl(url);
		
		activityDao.updateActivity(activity);
		recentUpdateService.logUpdates(activity.getTrip().getTripId(), "Edited Itinerary", "leader");
	}

	@Override
	public ArrayList<ActivityDto> getTeetimeAndGolfSchedule(int tripId)
			throws Exception {
		
		ArrayList<RoundTeeTimeDto> teeTimeDtos=new ArrayList<RoundTeeTimeDto>();
		ArrayList<ActivityType> activityTypes=activityDao.getActivityType();
		ActivityType type=null;
		for (ActivityType activityType : activityTypes) {
			if(activityType.getActivityType().equalsIgnoreCase("golf")){
				type=activityType;
			}
		}
		
		ArrayList<ActivityDto> activityDtos=new ArrayList<ActivityDto>();
		ArrayList<Activity> activities=activityDao.getGolfSchedule(tripId, type);
		
		for (Activity activity : activities) {
			ActivityDto activityDto=ActivityDto.instantiate(activity);
			ArrayList<RoundTeeTime> teeTimes=activityDao.getTeeTimes(activity.getActivityId());
			for (RoundTeeTime roundTeeTime : teeTimes) {
				teeTimeDtos.add(RoundTeeTimeDto.instantiate(roundTeeTime));
				//activityDto.getTeeTimeDtos().add(RoundTeeTimeDto.instantiate(roundTeeTime));
			}
			activityDto.setTeeTimeDtos(teeTimeDtos);
			teeTimeDtos=new ArrayList<RoundTeeTimeDto>();
			activityDtos.add(activityDto);
		}
		
		return activityDtos;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer saveTeeTime(String player1, String player2, String player3,
			String player4, String player5, int activityId, String timing,
			int teeTimeId)throws Exception {
		
		TripMember teeTimePlayer1=null;
		TripMember teeTimePlayer2=null;
		TripMember teeTimePlayer3=null;
		TripMember teeTimePlayer4=null;
		TripMember teeTimePlayer5=null;
		
		Date timings=new Date();
		
		//For Timings in tee time
		String timeArray[]=timing.split(":");
		
		if(timeArray[2].equalsIgnoreCase("pm")){
			timings.setHours(Integer.parseInt(timeArray[0])+12);
		}else {
			timings.setHours(Integer.parseInt(timeArray[0]));
		}
		timings.setMinutes(Integer.parseInt(timeArray[1]));
		
		String username=null;
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		if(teeTimeId == 0){
			RoundTeeTime roundTeeTime=new RoundTeeTime();
			Activity activity=activityDao.getActivity(activityId);
			if(!player1.equalsIgnoreCase("null")){
				teeTimePlayer1=tripDao.getTripMember(Integer.parseInt(player1));
			}
			if(!player2.equalsIgnoreCase("null")){
				teeTimePlayer2=tripDao.getTripMember(Integer.parseInt(player2));
			}
			if(!player3.equalsIgnoreCase("null")){
				teeTimePlayer3=tripDao.getTripMember(Integer.parseInt(player3));
			}
			if(!player4.equalsIgnoreCase("null")){
				teeTimePlayer4=tripDao.getTripMember(Integer.parseInt(player4));
			}
			if(!player5.equalsIgnoreCase("null")){
				teeTimePlayer5=tripDao.getTripMember(Integer.parseInt(player5));
			}
			
			roundTeeTime.setActivity(activity);
			roundTeeTime.setCreatedBy(username);
			roundTeeTime.setCreatedDate(instantTime);
			roundTeeTime.setPlayer1(teeTimePlayer1);
			roundTeeTime.setPlayer2(teeTimePlayer2);
			roundTeeTime.setPlayer3(teeTimePlayer3);
			roundTeeTime.setPlayer4(teeTimePlayer4);
			roundTeeTime.setPlayer5(teeTimePlayer5);
			roundTeeTime.setTimings(timings);
			
			int roundTeeTimeId=activityDao.saveTeeTime(roundTeeTime);
			recentUpdateService.logUpdates(roundTeeTime.getActivity().getTrip().getTripId(),"Edited the Golf Pairings", "leader");
			return roundTeeTimeId;
		}else{
			RoundTeeTime roundTeeTime=activityDao.getTeeTime(teeTimeId);
			if(!player1.equalsIgnoreCase("null")){
				teeTimePlayer1=tripDao.getTripMember(Integer.parseInt(player1));
			}
			if(!player2.equalsIgnoreCase("null")){
				teeTimePlayer2=tripDao.getTripMember(Integer.parseInt(player2));
			}
			if(!player3.equalsIgnoreCase("null")){
				teeTimePlayer3=tripDao.getTripMember(Integer.parseInt(player3));
			}
			if(!player4.equalsIgnoreCase("null")){
				teeTimePlayer4=tripDao.getTripMember(Integer.parseInt(player4));
			}
			if(!player5.equalsIgnoreCase("null")){
				teeTimePlayer5=tripDao.getTripMember(Integer.parseInt(player5));
			}
			
			roundTeeTime.setLastUpdatedBy(username);
			roundTeeTime.setLastUpdatedDate(instantTime);
			roundTeeTime.setPlayer1(teeTimePlayer1);
			roundTeeTime.setPlayer2(teeTimePlayer2);
			roundTeeTime.setPlayer3(teeTimePlayer3);
			roundTeeTime.setPlayer4(teeTimePlayer4);
			roundTeeTime.setPlayer5(teeTimePlayer5);
			roundTeeTime.setTimings(timings);
			
			activityDao.saveTeeTime(roundTeeTime);
			recentUpdateService.logUpdates(roundTeeTime.getActivity().getTrip().getTripId(),"Edited the Golf Pairings", "leader");
			return roundTeeTime.getTeeTimeId();
		}	
	}

	@Override
	public void deleteTeeTime(int teeTimeId) throws Exception {
		
		RoundTeeTime roundTeeTime = activityDao.getTeeTime(teeTimeId);
		
		activityDao.deleteTeeTime(roundTeeTime);
		
	}
	
	
	

}
