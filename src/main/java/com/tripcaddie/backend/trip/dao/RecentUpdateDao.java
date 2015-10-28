package com.tripcaddie.backend.trip.dao;

import java.util.ArrayList;

import com.tripcaddie.backend.trip.model.RecentUpdate;
import com.tripcaddie.frontend.trip.dto.TripDto;

public interface RecentUpdateDao {

	public void saveEntity(Object entity) throws Exception;
	
	
	public ArrayList<RecentUpdate> getRecentUpdatesByDate(TripDto trip,String typeOfUser) throws Exception;
}
