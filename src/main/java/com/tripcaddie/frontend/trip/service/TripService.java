package com.tripcaddie.frontend.trip.service;

import java.util.ArrayList;

import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripLeaderDelegationDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public interface TripService {
	
	public Integer createTrip(String tripName,String destination,String startDate,String message,String endDate,String promoCode,String annualTrip,String path) throws Exception;
	public ArrayList<TripDto> getTripsOfUser() throws Exception;
	public ArrayList<TripDto> getTripsOfUser(int userId) throws Exception;
	public TripDto getTrip(int tripId) throws Exception;
	public ArrayList<TripMemberDto> getTripMembers(int tripId) throws Exception;
	public ArrayList<TripMember> getTripMembersbyOrder(int tripId) throws Exception;
	public ArrayList<TripMemberDto> getTripMemberswithoutCurrentmember(int tripId) throws Exception;
	public void deleteTrip(int tripId) throws Exception;
	public TripMemberDto getTripMember(int memberId) throws Exception;
	
	public void addTripMember(int tripId,String email,String role) throws Exception;
	public void deleteTripMember(int memberId) throws Exception;	
	
	public void editTrip(Integer tripId, String tripName,String courseId,String startDate,String message, String endDate,String promoCode,String annualTrip, String path)throws Exception;
	
	public boolean isExistTrip(String tripName,String userName) throws Exception;
	
	//For invitation
	public ArrayList<TripMemberDto> getTripInvitation(String email) throws Exception;
	public void acceptInvite(int memberId) throws Exception;
	public void declineInvite(int memberId) throws Exception;
	
	//For Trip Leader deegation
	public ArrayList<TripLeaderDelegationDto> getTripLeaderDelegations() throws Exception;
	public void changeTripMemberRole(int optionId,int memberId) throws Exception;
}
