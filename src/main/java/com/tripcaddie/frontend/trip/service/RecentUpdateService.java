package com.tripcaddie.frontend.trip.service;

import java.util.ArrayList;
import java.util.List;

import com.tripcaddie.frontend.trip.dto.RecentUpdateDto;
import com.tripcaddie.frontend.trip.dto.TripDto;


public interface RecentUpdateService {

	public void logUpdates(int tripId,String message,String typeOfUser) throws Exception;
	public List<RecentUpdateDto> getrecentUpdateByDateAllTrips(List<TripDto> trips,String order) throws Exception;
	public ArrayList<RecentUpdateDto> getrecentUpdateByName(int tripId,String order) throws Exception;
	public ArrayList<RecentUpdateDto> getrecentUpdateByDate(int tripId,String order) throws Exception;
	public ArrayList<RecentUpdateDto> getrecentUpdateByType(int tripId,String order) throws Exception;
	
	public List<RecentUpdateDto> getRecntUpdatAllTripByName(List<RecentUpdateDto> recentUpdateDtos) throws Exception;
	
	public List<RecentUpdateDto> getRecntUpdatAllTripByTrip(List<RecentUpdateDto> recentUpdateDtos) throws Exception;
	
	public List<RecentUpdateDto> getRecntUpdatAllTripByMsg(List<RecentUpdateDto> recentUpdateDtos) throws Exception;
	
}
