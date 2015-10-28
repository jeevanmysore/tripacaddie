package com.tripcaddie.backend.trip.dao;

import java.util.ArrayList;

import com.tripcaddie.backend.trip.model.PaidMode;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripLeaderDelegation;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.backend.trip.model.TripMemberStatus;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public interface TripDao {
	
	public Integer createTrip(Trip trip) throws Exception;
	public void saveTripMember(TripMember tripMember) throws Exception;
	public void deleteTrip(Trip trip) throws Exception;
	public Trip getTrip(TripCaddieUser tripCaddieUser,int tripId) throws Exception;
	public Trip getTrip(int tripId) throws Exception;
	public TripMember getTripMember(int tripId,String email) throws Exception;
	public TripMember getTripMember(int memberId) throws Exception;
	
	public TripMember getTripMember(TripcaddieUserDto tripCaddieUser,int tripId) throws Exception;
	public TripMember getTripMember(int userId,int tripId) throws Exception;
	
	public void addTripMembers(TripMember tripMember)throws Exception;
	public ArrayList<TripMember> getTripsOfUser(TripCaddieUser tripCaddieUser) throws Exception;
	public ArrayList<PaidMode> getPaidStatus() throws Exception;
	public ArrayList<TripMemberStatus> getTripMemberStatus() throws Exception;
	public ArrayList<TripMember> getTripMembers(Trip trip) throws Exception;
	public ArrayList<TripMember> getTripMembersbyOrder(int tripId) throws Exception;

	public void deleteTripMember(TripMember member) throws Exception;
	//By jeevan
	public void updateTrip(Trip trip)throws Exception;
	public ArrayList<TripMember> getTripsofUserAsTripLeader(Integer userId) throws Exception;
	
	//For invitation
	public ArrayList<TripMember> getTripInvitation(String email,int statusId) throws Exception;
	public void updateTripMember(TripMember member) throws Exception;
	
	//For Trip Leader deegation
	public ArrayList<TripLeaderDelegation> getTripLeaderDelegations() throws Exception;
	public TripLeaderDelegation getTripLeaderDelegation(int optionId) throws Exception;
}
