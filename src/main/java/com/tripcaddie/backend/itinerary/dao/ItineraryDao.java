package com.tripcaddie.backend.itinerary.dao;

import java.util.ArrayList;
import java.util.List;

import com.tripcaddie.backend.itinerary.model.Accommodation;
import com.tripcaddie.backend.itinerary.model.Logistics;
import com.tripcaddie.backend.itinerary.model.RoundScore;

public interface ItineraryDao {
	
	public ArrayList<Accommodation> getAccommodations(int tripId) throws Exception; 
	public Accommodation getAccommoationbyAccommodationId(int accommodationID) throws Exception;
	public Accommodation getAccommoationbyTripId(int tripId) throws Exception;
	public void addAccommodation(Accommodation accommodation) throws Exception;
	public void editAccommodation(Accommodation accommodation) throws Exception;
	public void deleteAccommodation(Accommodation accommodation) throws Exception;
		
	public ArrayList<Logistics> getLogistics(int tripId) throws Exception; 
	public Logistics getLogisticsbyLogisticsId(int logisticsID) throws Exception;
	public Logistics getLogisticsbyTripId(int tripId) throws Exception;
	public void addLogistics(Logistics logistics) throws Exception;
	public void editLogistics(Logistics logistics) throws Exception;
	public void deleteLogistics(Logistics logistics) throws Exception;
	
	
	/*
	 * 
	 * Generics
	 */
	
	public void insertEntity(Object entity);
	
	/**
	 * 
	 * Round Score
	 */
	public List<RoundScore> getRoundScore(int memberId);
	
	public List<RoundScore> getRoundScores(int activityId);
	
	public RoundScore getRoundScorebyActivityndTrip(int activityId , int memberId);
}
