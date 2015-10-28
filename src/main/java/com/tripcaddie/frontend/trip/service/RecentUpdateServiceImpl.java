package com.tripcaddie.frontend.trip.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.trip.dao.RecentUpdateDao;
import com.tripcaddie.backend.trip.dao.TripDao;
import com.tripcaddie.backend.trip.model.RecentUpdate;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.frontend.trip.dto.RecentUpdateDto;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("recentUpdateService")
public class RecentUpdateServiceImpl implements RecentUpdateService{

	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name="recentUpdateDao")
	private RecentUpdateDao recentUpdateDao;
	@Resource(name="tripDao")
	private TripDao tripDao;
	@Resource(name="tripService")
	private TripService tripService;
	
	@Override
	public void logUpdates(int tripId, String message,String typeOfUser) throws Exception {
		
		//created by and created date is not needed
		RecentUpdate recentUpdate=new RecentUpdate();
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto=loginService.getUserByUserName(username);
		TripMember member=tripDao.getTripMember(tripcaddieUserDto, tripId);
		Trip trip=tripDao.getTrip(tripId);
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		recentUpdate.setCreatedBy(username);
		recentUpdate.setCreatedDate(instantTime);
		recentUpdate.setMessage(message);
		recentUpdate.setTrip(trip);
		recentUpdate.setTripMember(member);
		recentUpdate.setTypeOfUser(typeOfUser);
		recentUpdate.setUpdatedDate(instantTime);
		
		recentUpdateDao.saveEntity(recentUpdate);
		
	}

	@Override
	public ArrayList<RecentUpdateDto> getrecentUpdateByName(int tripId,
			String order) throws Exception {
		ArrayList<RecentUpdateDto> updateDtos = getRecentUpdate(tripId);
		
		Collections.sort(updateDtos, new Comparator<RecentUpdateDto>() {

			@Override
			public int compare(RecentUpdateDto arg0, RecentUpdateDto arg1) {
				
				return arg0.getTripMemberDto().getTripCaddieUserDto().getFirstName().compareTo(arg1.getTripMemberDto().getTripCaddieUserDto().getFirstName());
			}
		});
		
		if(order.equalsIgnoreCase("desc")){
			Collections.reverse(updateDtos);
			return updateDtos;
			
		}
		return updateDtos;
	}

	@Override
	public ArrayList<RecentUpdateDto> getrecentUpdateByDate(int tripId,
			String order) throws Exception {
		ArrayList<RecentUpdateDto> updateDtos = getRecentUpdate(tripId);
		if(order.equalsIgnoreCase("desc")){
		return updateDtos;
		}
		else if(order.equalsIgnoreCase("asc")){
			Collections.reverse(updateDtos);
			return updateDtos;
		}
		return null;
		
	}

	private ArrayList<RecentUpdateDto> getRecentUpdate(int tripId)
			throws Exception {
		String typeOfUser=null;
		String username=loginService.getAuthenticatedUser();
		TripDto tripDto=tripService.getTrip(tripId);
		
		System.out.println("TripLeader:"+tripDto.getTripLeader());
		System.out.println("Trip Id:"+tripDto.getTripId());
		
		if(tripDto.getTripLeader().equals(username)){
			typeOfUser = "leader";
		}else{
			typeOfUser = "participant";
		}
		
		ArrayList<RecentUpdateDto> updateDtos=new ArrayList<RecentUpdateDto>();
		ArrayList<RecentUpdate> updates=recentUpdateDao.getRecentUpdatesByDate(tripDto, typeOfUser);
		
		for (RecentUpdate recentUpdate : updates) {
			updateDtos.add(RecentUpdateDto.instantiate(recentUpdate));
		}
		return updateDtos;
	}

	@Override
	public ArrayList<RecentUpdateDto> getrecentUpdateByType(int tripId,
			String order) throws Exception {
ArrayList<RecentUpdateDto> updateDtos = getRecentUpdate(tripId);
		
		Collections.sort(updateDtos, new Comparator<RecentUpdateDto>() {

			@Override
			public int compare(RecentUpdateDto arg0, RecentUpdateDto arg1) {
				
				return arg0.getMessage().compareTo(arg1.getMessage());
			}
		});
		
		if(order.equalsIgnoreCase("desc")){
			Collections.reverse(updateDtos);
			return updateDtos;
			
		}
		return updateDtos;
	}

	@Override
	public List<RecentUpdateDto> getrecentUpdateByDateAllTrips(
			List<TripDto> trips, String order) throws Exception {
		List<RecentUpdateDto> recentUpdateDtos=new ArrayList<RecentUpdateDto>();
		for(TripDto tripDto:trips){
			recentUpdateDtos.addAll(getrecentUpdateByDate(tripDto.getTripId(),"desc"));
		}
		Collections.sort(recentUpdateDtos, new Comparator<RecentUpdateDto>() {

			@Override
			public int compare(RecentUpdateDto o1, RecentUpdateDto o2) {
				return o2.getUpdatedDate().compareTo(o1.getUpdatedDate());
			}
		});
		if(recentUpdateDtos.size()>19){
		List<RecentUpdateDto> sublist=recentUpdateDtos.subList(0, 19);
		return sublist;
		}
		return recentUpdateDtos;
	}

	@Override
	public List<RecentUpdateDto> getRecntUpdatAllTripByName(
			List<RecentUpdateDto> recentUpdateDtos) throws Exception {
		Collections.sort(recentUpdateDtos,new Comparator<RecentUpdateDto>() {

			@Override
			public int compare(RecentUpdateDto o1, RecentUpdateDto o2) {
				return o1.getTripMemberDto().getTripCaddieUserDto().getFirstName().compareTo(o2.getTripMemberDto().getTripCaddieUserDto().getFirstName());
			}
		});
		return recentUpdateDtos;
	}

	@Override
	public List<RecentUpdateDto> getRecntUpdatAllTripByTrip(
			List<RecentUpdateDto> recentUpdateDtos) throws Exception {
		Collections.sort(recentUpdateDtos,new Comparator<RecentUpdateDto>() {

			@Override
			public int compare(RecentUpdateDto o1, RecentUpdateDto o2) {
				return o1.getTripMemberDto().getTripDto().getTripName().compareTo(o2.getTripMemberDto().getTripDto().getTripName());
			}
		});
		return recentUpdateDtos;
	}

	@Override
	public List<RecentUpdateDto> getRecntUpdatAllTripByMsg(
			List<RecentUpdateDto> recentUpdateDtos) throws Exception {
		Collections.sort(recentUpdateDtos,new Comparator<RecentUpdateDto>() {

			@Override
			public int compare(RecentUpdateDto o1, RecentUpdateDto o2) {
				return o1.getMessage().compareTo(o2.getMessage());
			}
		});
		return recentUpdateDtos;
	}

}
