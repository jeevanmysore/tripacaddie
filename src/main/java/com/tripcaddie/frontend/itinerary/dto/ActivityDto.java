package com.tripcaddie.frontend.itinerary.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.tripcaddie.backend.itinerary.model.Activity;
import com.tripcaddie.frontend.trip.dto.RoundTeeTimeDto;
import com.tripcaddie.frontend.trip.dto.TripDto;

public class ActivityDto implements Serializable {

	private static final long serialVersionUID = 2822805365420961736L;
	private int activityId;
	private TripDto tripDto;
	private ActivityTypeDto activityTypeDto;
	private String activityName;
	private Calendar activityDate;
	private Date activityTime;
	private Integer timePending;
	private String url;
	private String mapInfo;
	private String comment;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private long noOfDays;
	private String activitydateString; 
	private String activityTimeString;
	private ArrayList<RoundTeeTimeDto> teeTimeDtos;
	
	public ArrayList<RoundTeeTimeDto> getTeeTimeDtos() {
		return teeTimeDtos;
	}
	public void setTeeTimeDtos(ArrayList<RoundTeeTimeDto> teeTimeDtos) {
		this.teeTimeDtos = teeTimeDtos;
	}
	public String getActivitydateString() {
		return activitydateString;
	}
	public void setActivitydateString(String activitydateString) {
		this.activitydateString = activitydateString;
	}
	public String getActivityTimeString() {
		return activityTimeString;
	}
	public void setActivityTimeString(String activityTimeString) {
		this.activityTimeString = activityTimeString;
	}
	public long getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(long noOfDays) {
		this.noOfDays = noOfDays;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public TripDto getTripDto() {
		return tripDto;
	}
	public void setTripDto(TripDto tripDto) {
		this.tripDto = tripDto;
	}
	public ActivityTypeDto getActivityTypeDto() {
		return activityTypeDto;
	}
	public void setActivityTypeDto(ActivityTypeDto activityTypeDto) {
		this.activityTypeDto = activityTypeDto;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Calendar getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Calendar activityDate) {
		this.activityDate = activityDate;
	}
	public Date getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}
	public Integer getTimePending() {
		return timePending;
	}
	public void setTimePending(Integer timePending) {
		this.timePending = timePending;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMapInfo() {
		return mapInfo;
	}
	public void setMapInfo(String mapInfo) {
		this.mapInfo = mapInfo;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	public static void populate(ActivityDto activityDto,Activity activity){
		
		activityDto.setActivityDate(activity.getActivityDate());
		activityDto.setActivityId(activity.getActivityId());
		activityDto.setActivityName(activity.getActivityName());
		activityDto.setActivityTime(activity.getActivityTime());
		activityDto.setActivityTypeDto(ActivityTypeDto.instantiate(activity.getActivityType()));
		activityDto.setComment(activity.getComment());
		activityDto.setCreatedBy(activity.getCreatedBy());
		activityDto.setCreatedDate(activity.getCreatedDate());
		activityDto.setLastUpdatedBy(activity.getLastUpdatedBy());
		activityDto.setLastUpdatedDate(activity.getLastUpdatedDate());
		activityDto.setMapInfo(activity.getMapInfo());
		activityDto.setTimePending(activity.getTimePending());
		activityDto.setTripDto(TripDto.instantiate(activity.getTrip()));
		activityDto.setUrl(activity.getUrl());
	}
	
	public static ActivityDto instantiate(Activity activity){
		
		ActivityDto activityDto=new ActivityDto();
		populate(activityDto, activity);
		return activityDto;
	}
}
