package com.tripcaddie.backend.itinerary.dao;


import java.util.ArrayList;
import java.util.List;

import com.tripcaddie.backend.itinerary.model.Activity;
import com.tripcaddie.backend.itinerary.model.ActivityType;
import com.tripcaddie.backend.trip.model.RoundTeeTime;

public interface ActivityDao {

	
	List<Activity> getActivities(int tripid) throws Exception;
	List<Activity> getActivitiesforLeaderbrd(int tripid ,int activitytype) throws Exception;
	
	public Activity getActivity(int activityId) throws Exception;
	public ArrayList<ActivityType> getActivityType() throws Exception;
	public ActivityType getActivityType(int typeId) throws Exception;
	
	public void addActivity(Activity activity) throws Exception;
	public void deleteActivity(Activity activity) throws Exception;
	public void updateActivity(Activity activity) throws Exception;
	
	//Tee time and Golf
	public ArrayList<Activity> getGolfSchedule(int tripId,ActivityType activityType) throws Exception;
	public Integer saveTeeTime(RoundTeeTime roundTeeTime) throws Exception;
	public RoundTeeTime getTeeTime(int teeTimeId) throws Exception;
	public ArrayList<RoundTeeTime> getTeeTimes(int activityID) throws Exception;
	public void deleteTeeTime(RoundTeeTime roundTeeTime) throws Exception;
}
