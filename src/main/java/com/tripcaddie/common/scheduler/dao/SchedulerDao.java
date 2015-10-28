package com.tripcaddie.common.scheduler.dao;

import java.util.ArrayList;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.user.model.TripCaddieUser;

public interface SchedulerDao {

	public TripCaddieUser getUserByUsername(String username) throws Exception;
	public ArrayList<Trip> getAllTrips(String today)throws Exception;
	public ArrayList<TripCaddieUser> getUsers(String today) throws Exception;
}
