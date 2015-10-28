package com.tripcaddie.backend.itinerary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activity_type")
public class ActivityType {
	
	@Id @GeneratedValue
	@Column(name="activity_type_id")
	private int activityTypeId;
	@Column(name="activity_type")
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
}
