package com.tripcaddie.frontend.bucketList.service;

import java.util.ArrayList;

import org.springframework.dao.DataAccessException;

import com.tripcaddie.frontend.bucketList.dto.BucketListDto;
import com.tripcaddie.frontend.bucketList.dto.GolfClubInquiryDto;
import com.tripcaddie.frontend.bucketList.dto.GolfCourseReviewDto;

public interface BucketListService {
	
	void addToBucketList(int courseId,int playedId ,String ranking) throws Exception;
	void updateAdviceResponse(int responseId,String response) throws Exception;
	void updateAdviceRequest(int responseId,String inquiry) throws Exception;
	void updatePriority(String updatePriorityway,int courseId,int visitedId) throws Exception;
	void deleteFromList(int courseId,int visitId) throws Exception;
	
	ArrayList<BucketListDto> getBucketLists(int placeVisitedId) throws Exception;
	
	boolean updateReview(int reviewId, String review) throws Exception;
	boolean isExistinList(int courseId,int userId) throws Exception;
	String getVisitedorNotStatus(int courseId,int userId) throws Exception;
	
	int getRecommendationsCount(int courseId) throws Exception;
	int getInquiryCount(int courseId) throws Exception;
	int getRatingCount(int courseId,int visitId) throws Exception; //how to do rating count...
	int getBucketListCount(int visitedId) throws Exception;
	
	int getPlacesWantToGoCount() throws Exception;
	int getPlacesIHaveBeenCount() throws Exception;
	int getInquiryCount() throws Exception;
	int getReviewCount() throws Exception;
	
	ArrayList<GolfClubInquiryDto> getMyInquiries(int firstRow) throws DataAccessException, Exception;
	ArrayList<GolfCourseReviewDto> getMyReviews(int firstRow) throws DataAccessException, Exception;
	int getRowCountForMyInquiries() throws DataAccessException, Exception;
	int getRowCountForMyReviews() throws DataAccessException, Exception;
	
	//bucket List categorize
	public Integer categorize(int courseId) throws Exception;
	
	public ArrayList<GolfClubInquiryDto> getInquiries(int golfCourseId) throws Exception;
	
}
