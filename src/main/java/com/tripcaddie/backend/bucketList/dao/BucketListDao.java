package com.tripcaddie.backend.bucketList.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tripcaddie.backend.bucketList.model.BucketList;
import com.tripcaddie.backend.bucketList.model.GolfClubInquiry;
import com.tripcaddie.backend.bucketList.model.GolfCourseReview;
import com.tripcaddie.backend.bucketList.model.VisitedPlace;
import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.backend.user.model.TripCaddieUser;

public interface BucketListDao {
	
	int getBucketListCount(int userId,int playedId) throws Exception;
	void saveOrUpdateBucketList(BucketList bucketList) throws Exception;
	BucketList getBucketList(int userId,int courseId,int visitedId) throws Exception;
	BucketList getBucketListByPriority(int userId,int priority,int visitedId) throws Exception;
	BucketList getBucketList(TripCaddieUser user,GolfCourse course) throws Exception;
	
	ArrayList<BucketList> getBucketLists(TripCaddieUser user,int placeVisitedId) throws Exception;
	
	VisitedPlace getPlaceVisitedStatus(int playedId) throws Exception;
	ArrayList<VisitedPlace> getPlaceVisitedStatus() throws Exception;
	
	void updateBucketList(BucketList bucketList) throws Exception;
	void updateReview(GolfCourseReview review) throws Exception;
	void deleteFromList(BucketList bucketList) throws Exception;
	
	int getRecommendationsCount(int courseId) throws Exception;
	int getInquiryCount(int courseId) throws Exception;
	int getRatingCount(int courseId,int visitId) throws Exception;
	
	/*int getPlacesWantToGoCount(TripCaddieUser tripCaddieUser);
	int getPlacesIHaveBeenCount(TripCaddieUser tripCaddieUser);*/
	int getInquiryCount(TripCaddieUser tripCaddieUser) throws Exception;
	int getReviewCount(TripCaddieUser tripCaddieUser) throws Exception;
	
	boolean isExistInList(int courseId,int userId) throws Exception;
	String getVisitedorNotStatus(int courseId,int userId) throws Exception;
	ArrayList<GolfClubInquiry> getMyInquiries(int userID,int firstRow) throws DataAccessException,Exception;
	ArrayList<GolfCourseReview> getMyReviews(int userID,int firstRow) throws DataAccessException,Exception;
	
	int getRowCountForMyInquiries(int userId) throws DataAccessException, Exception;
	int getRowCountForMyReviews(int userId) throws DataAccessException, Exception;
	
	ArrayList<BucketList> getAllBucketLists(int userID,int placeVisitedId ,int priority) throws Exception;
	
	void saveOrUpdateCollection(List<BucketList>bucketLists);

	public ArrayList<GolfClubInquiry> getInquiries(int golfCourseId) throws Exception;
}
