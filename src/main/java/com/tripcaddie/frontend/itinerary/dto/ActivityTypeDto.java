package com.tripcaddie.frontend.itinerary.dto;

import java.io.Serializable;

import com.tripcaddie.backend.itinerary.model.ActivityType;

public class ActivityTypeDto implements Serializable{
	
	private static final long serialVersionUID = 7399240743084718821L;
	private int activityTypeId;
	private String activityType;
	
	public int getActivityTypeId() {
		return activityTypeId;
	}
	public void setActivityTypeId(int activityTypeId) {
		this.activityTypeId = activityTypeId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	public static void populate(ActivityTypeDto activityTypeDto,ActivityType activityType){
		
		activityTypeDto.setActivityTypeId(activityType.getActivityTypeId());
		activityTypeDto.setActivityType(activityType.getActivityType());
	}
	
	public static ActivityTypeDto instantiate(ActivityType activityType){
		
		ActivityTypeDto activityTypeDto=new ActivityTypeDto();
		populate(activityTypeDto, activityType);
		return activityTypeDto;
		
	}

}
