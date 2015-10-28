package com.tripcaddie.frontend.itinerary.service;

import java.util.ArrayList;

import com.tripcaddie.frontend.itinerary.dto.AccommodationDto;
import com.tripcaddie.frontend.itinerary.dto.LogisticsDto;

public interface ItineraryService {

	public ArrayList<AccommodationDto> getAccommodations(int tripId) throws Exception; 
	public AccommodationDto getAccommodation(int tripId) throws Exception;
	public AccommodationDto addAccommodation(int tripId,String accommodationText) throws Exception;
	public AccommodationDto editAccommodation(int accommodationId,String accommodationText) throws Exception;
	public void deleteAccommodation(int accommodationId) throws Exception;
	
	public ArrayList<LogisticsDto> getLogistics(int tripId) throws Exception; 
	public LogisticsDto getLogistic(int tripId) throws Exception;
	public LogisticsDto addLogistics(int tripId,String logisticsContent) throws Exception;
	public LogisticsDto editLogistics(int logisticsId,String logisticsText) throws Exception;
	public void deleteLogistics(int logisticsId) throws Exception;
	
	
}
