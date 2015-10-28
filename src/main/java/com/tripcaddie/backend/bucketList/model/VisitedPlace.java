package com.tripcaddie.backend.bucketList.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="places_played")
public class VisitedPlace {

	@Id
	@Column(name="places_played_id")
	private int placesPlayedId;
	@Column(name="value")
	private String status;
	
	public int getPlacesPlayedId() {
		return placesPlayedId;
	}
	public void setPlacesPlayedId(int placesPlayedId) {
		this.placesPlayedId = placesPlayedId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
