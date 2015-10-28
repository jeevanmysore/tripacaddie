package com.tripcaddie.frontend.courses.controllerTest;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tripcaddie.backend.bucketList.model.GolfClubAdviceResponse;
import com.tripcaddie.backend.bucketList.model.GolfClubInquiry;
import com.tripcaddie.backend.bucketList.model.GolfCourseReview;
import com.tripcaddie.backend.bucketList.vo.GolfClubAdviceResponseTestBean;
import com.tripcaddie.backend.courses.searchDao.CourseDao;

import junit.framework.TestCase;

public class DestinationSearchControllerTest extends TestCase{
	
	public CourseDao courseDao;
	
	@Before
	protected void setUp() {
		
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("/applicationContextTest.xml");
		courseDao=(CourseDao)context.getBean("courseDao");
		
	}
	
	@Test
	public void testGetNoOfLikes() throws Exception{
		
		int nooflikes=courseDao.getNoOfLikes(1);
		assertEquals(2, nooflikes);
	}
	
	@Test
	public void testGetReviews() throws Exception{
		
		ArrayList<GolfCourseReview> courseReviews=courseDao.getReviews(356);
		assertEquals(3, courseReviews.size());
	}
	
	@Test
	public void testGetAdviceResponses() throws Exception{
		
		ArrayList<GolfClubAdviceResponse> adviceResponses=courseDao.getAdviceResponses(1);
		assertEquals(0, adviceResponses.size());
	}
	
	@Test
	public void testgetAdvices() throws Exception{
		
		ArrayList<GolfClubInquiry> golfClubInquiries=courseDao.getAdvices(356);
		assertEquals(4, golfClubInquiries.size());
	}
	
	@Test
	public void testGetAdviceResponse() throws Exception{
		
		GolfClubAdviceResponseTestBean adviceResponseTestBean=new GolfClubAdviceResponseTestBean();
		adviceResponseTestBean.setAnswer("Ya...");
		GolfClubAdviceResponse clubAdviceResponse=courseDao.getAdviceResponse(1);
		assertEquals(adviceResponseTestBean.getAnswer(), clubAdviceResponse.getAnswer());
	}
}
