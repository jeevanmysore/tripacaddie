package com.tripcaddie.frontend.bucketList.controllerTest;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tripcaddie.backend.bucketList.dao.BucketListDao;
import com.tripcaddie.backend.bucketList.model.BucketList;
import com.tripcaddie.backend.bucketList.model.VisitedPlace;
import com.tripcaddie.backend.bucketList.vo.VisitedPlaceTestBean;
import com.tripcaddie.backend.user.model.TripCaddieUser;

public class BucketListControllerTest extends TestCase {
	
	public BucketListDao bucketListDao;

	@Before
	protected void setUp(){
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("/applicationContextTest.xml");
		bucketListDao=(BucketListDao) context.getBean("bucketListDao");
	}
	
	@Test
	public void testGetBucketListCount() throws Exception {
		
		int count=bucketListDao.getBucketListCount(1, 1);
		assertEquals(13, count);	
	}
	
	@Ignore
	public void testGetBucketList() throws Exception{
		
		TripCaddieUser tripCaddieUser=new TripCaddieUser();
		tripCaddieUser.setUserId(1);
		ArrayList<BucketList> bucketLists=bucketListDao.getBucketLists(tripCaddieUser, 1);
		assertEquals(5, bucketLists.size());
		
	}
	
	@Ignore
	public void testGetRecommendationsCount() throws Exception {
		
		int count=bucketListDao.getRecommendationsCount(356);
		assertEquals(3, count);

	}
	
	@Ignore
	public void testGetPlaceVisitedStatus() throws Exception{
		
		VisitedPlaceTestBean placeTestBean=new VisitedPlaceTestBean();
		placeTestBean.setPlacesPlayedId(1);
		placeTestBean.setStatus("visited");
		VisitedPlace visitedPlace = bucketListDao.getPlaceVisitedStatus(1);
		//assertEquals(placeTestBean.getPlacesPlayedId(), visitedPlace.getPlacesPlayedId());
		assertEquals("visited", visitedPlace.getStatus());
		//assertEquals(placeTestBean, visitedPlace);
	}
	
}
