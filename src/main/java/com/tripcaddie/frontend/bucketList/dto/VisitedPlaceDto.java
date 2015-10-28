package com.tripcaddie.frontend.bucketList.dto;

import java.io.Serializable;

import com.tripcaddie.backend.bucketList.model.VisitedPlace;

public class VisitedPlaceDto implements Serializable {
	
	private static final long serialVersionUID = -5585222142656068898L;
	private int placesPlayedId;
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
	
	public static void populate(VisitedPlaceDto visitedPlaceDto,VisitedPlace visitedPlace) {
		
		visitedPlaceDto.setPlacesPlayedId(visitedPlace.getPlacesPlayedId());
		visitedPlaceDto.setStatus(visitedPlace.getStatus());
	}
	
	public static VisitedPlaceDto instantiate(VisitedPlace visitedPlace) {
		VisitedPlaceDto visitedPlaceDto=new VisitedPlaceDto();
		populate(visitedPlaceDto, visitedPlace);
		return visitedPlaceDto;
	}
	

}
