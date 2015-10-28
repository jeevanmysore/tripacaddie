package com.tripcaddie.backend.trip.tripController;

import java.util.ArrayList;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.service.TripService;

public class TripControllerTest {

	private TripService tripService;
	private ArrayList<TripDto> tripDtos;
	
	@BeforeClass
	protected void setUp(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContextTest.xml");
		tripService=(TripService) ctx.getBean("tripService");
	}
	
	@Test
	public void testGetTrips(){
		try {
			tripDtos=tripService.getTripsOfUser();
			Assert.assertEquals(0, tripDtos.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void createTrip(){
		
	}
	
	@Test
	public void testGetTrip(){
				
	}
}
