package com.tripcaddie.frontend.itinerary.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.itinerary.dao.ItineraryDao;
import com.tripcaddie.backend.itinerary.model.Accommodation;
import com.tripcaddie.backend.itinerary.model.Logistics;
import com.tripcaddie.backend.trip.dao.TripDao;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.frontend.itinerary.dto.AccommodationDto;
import com.tripcaddie.frontend.itinerary.dto.LogisticsDto;
import com.tripcaddie.frontend.trip.service.RecentUpdateService;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("itineraryService")
public class ItineraryServiceImpl implements ItineraryService {
	
	@Resource(name="itineraryDao")
	private ItineraryDao itineraryDao;
	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name="tripDao")
	private TripDao tripDao;
	@Resource(name="recentUpdateService")
	private RecentUpdateService recentUpdateService;
	
	@Override
	public ArrayList<AccommodationDto> getAccommodations(int tripId)throws Exception {
		ArrayList<Accommodation> accommodations=itineraryDao.getAccommodations(tripId);
		ArrayList<AccommodationDto> accommodationDtos=new ArrayList<AccommodationDto>();
		for (Accommodation accommodation : accommodations) {
			accommodationDtos.add(AccommodationDto.instantiate(accommodation));
		}
		return accommodationDtos;
	}

	@Override
	public AccommodationDto getAccommodation(int tripId) throws Exception {
		
		Accommodation accommodation=itineraryDao.getAccommoationbyTripId(tripId);
		if(accommodation != null)
			return AccommodationDto.instantiate(accommodation);
		else
			return null;
	}

	@Override
	public AccommodationDto addAccommodation(int tripId,
			String accommodationText) throws Exception {
		
		Accommodation accommodation=new Accommodation();
		String username=loginService.getAuthenticatedUser();
		
		Calendar createdDate=Calendar.getInstance();
		Date date=new Date();
		createdDate.setTime(date);
		
		Trip trip=tripDao.getTrip(tripId);
		accommodation.setTrip(trip);
		accommodation.setAccomadationContent(accommodationText);
		accommodation.setCreatedBy(username);
		accommodation.setCreatedDate(createdDate);
		
		itineraryDao.addAccommodation(accommodation);
		recentUpdateService.logUpdates(tripId, "Edited Accommodation", "leader");
		return this.getAccommodation(tripId);
	}

	@Override
	public void deleteAccommodation(int accommodationId) throws Exception {
		
		Accommodation accommodation=itineraryDao.getAccommoationbyAccommodationId(accommodationId);
		itineraryDao.deleteAccommodation(accommodation);
		recentUpdateService.logUpdates(accommodation.getTrip().getTripId(), "Edited Accommodation", "leader");
	}

	@Override
	public ArrayList<LogisticsDto> getLogistics(int tripId) throws Exception{
		ArrayList<LogisticsDto> logisticsDtos=new ArrayList<LogisticsDto>();
		ArrayList<Logistics> listOfLogistics=itineraryDao.getLogistics(tripId);
		for (Logistics logistics : listOfLogistics) {
			logisticsDtos.add(LogisticsDto.instantiate(logistics));			
		}
		return logisticsDtos;
	}
	
	@Override
	public LogisticsDto getLogistic(int tripId) throws Exception {
		
		Logistics logistics=itineraryDao.getLogisticsbyTripId(tripId);
		if(logistics != null)
			return LogisticsDto.instantiate(logistics);
		else
			return null;
	}

	@Override
	public LogisticsDto addLogistics(int tripId, String logisticsContent)
			throws Exception {
		
		Logistics logistics=new Logistics();
		String username=loginService.getAuthenticatedUser();
		
		Calendar createdDate=Calendar.getInstance();
		Date date=new Date();
		createdDate.setTime(date);
		
		Trip trip=tripDao.getTrip(tripId);
		logistics.setTrip(trip);
		logistics.setLogisticsContent(logisticsContent);
		logistics.setCreatedBy(username);
		logistics.setCreatedDate(createdDate);
			
		itineraryDao.addLogistics(logistics);
		recentUpdateService.logUpdates(tripId, "Edited Logistic", "leader");
		return this.getLogistic(tripId);
		
	}

	@Override
	public void deleteLogistics(int logisticsID) throws Exception {
		
		Logistics logistics=itineraryDao.getLogisticsbyLogisticsId(logisticsID);
		itineraryDao.deleteLogistics(logistics);
		recentUpdateService.logUpdates(logistics.getTrip().getTripId(), "Edited Logistic", "leader");
	}

	@Override
	public AccommodationDto editAccommodation(int accommodationId,
			String accommodationText) throws Exception {
		
		String username=loginService.getAuthenticatedUser();
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
				
		Accommodation accommodation=itineraryDao.getAccommoationbyAccommodationId(accommodationId);
		accommodation.setAccomadationContent(accommodationText);
		accommodation.setLastUpdatedBy(username);
		accommodation.setLastUpdatedDate(instantTime);
		
		itineraryDao.editAccommodation(accommodation);
		recentUpdateService.logUpdates(accommodation.getTrip().getTripId(), "Edited Accommodation", "leader");
		return AccommodationDto.instantiate(itineraryDao.getAccommoationbyAccommodationId(accommodationId));
	}

	@Override
	public LogisticsDto editLogistics(int logisticsId, String logisticsText)
			throws Exception {
		
		String username=loginService.getAuthenticatedUser();
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		Logistics logistics=itineraryDao.getLogisticsbyLogisticsId(logisticsId);
		logistics.setLogisticsContent(logisticsText);
		logistics.setCreatedBy(username);
		logistics.setCreatedDate(instantTime);
		recentUpdateService.logUpdates(logistics.getTrip().getTripId(), "Edited Logistic", "leader");
		return LogisticsDto.instantiate(itineraryDao.getLogisticsbyLogisticsId(logisticsId));
	}

}
