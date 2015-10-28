package com.tripcaddie.frontend.itinerary.service;

import java.util.ArrayList;
import com.tripcaddie.frontend.itinerary.dto.ActivityDto;
import com.tripcaddie.frontend.itinerary.dto.ActivityTypeDto;

public interface ActivityService {

	public ArrayList<ActivityDto> getActivities(int tripid) throws Exception;
	
	public ActivityDto getActivity(int activityId) throws Exception;
	public ArrayList<ActivityTypeDto> getActivityTypes() throws Exception;
	public void addActivity(int tripId,int activityTypeId,String activityTitle,String date,String time,String url,String mapInfo,String comments,Integer pending) throws Exception;
	
	public void deleteActivity(int activityId) throws Exception;
	public void editActivity(int activityId,int activityTypeId,String activityTitle,String date,String time,String url,String mapInfo,String comments,Integer pending) throws Exception;
	public void deleteTeeTime(int teeTimeId) throws Exception;
	
	//Tee time and Golf
	public ArrayList<ActivityDto> getTeetimeAndGolfSchedule(int tripId) throws Exception;
	public Integer saveTeeTime(String player1,String player2,String player3,String player4,String player5,int activityId, String timing,int teeTimeId) throws Exception;
}
