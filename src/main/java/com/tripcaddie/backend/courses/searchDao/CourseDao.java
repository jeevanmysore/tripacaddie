package com.tripcaddie.backend.courses.searchDao;

import java.util.ArrayList;

import com.tripcaddie.backend.bucketList.model.GolfClubInquiry;
import com.tripcaddie.backend.bucketList.model.GolfClubAdviceResponse;
import com.tripcaddie.backend.bucketList.model.GolfCourseReview;
import com.tripcaddie.backend.courses.model.CourseRatingDetails;
import com.tripcaddie.backend.courses.model.GolfCourse;

public interface CourseDao {
	
	ArrayList<GolfCourse> getGolfCourses(String keyColumn,String destination) throws Exception;
	GolfCourse getGolfCourse(int courseId) throws Exception;
	
	GolfClubInquiry getAdvice(int adviceId) throws Exception;
	ArrayList<GolfClubInquiry> getAdvices(int courseId) throws Exception;
	
	GolfClubAdviceResponse getAdviceResponse(int responseId) throws Exception;
	ArrayList<GolfClubAdviceResponse> getAdviceResponses(int adviceId) throws Exception;
	
	void updateCourseDetails(GolfCourse course) throws Exception;
	
	GolfCourseReview getReview(int reviewId) throws Exception;
	ArrayList<GolfCourseReview> getReviews(int courseId) throws Exception;
	
	void updateAdviceResponse(GolfClubAdviceResponse golfClubAdviceResponse) throws Exception;
	void updateReview(GolfCourseReview golfCourseReview) throws Exception;

	int storeResponse(GolfClubAdviceResponse golfClubAdviceResponse) throws Exception;
	int storeAdvice(GolfClubInquiry golfClubAdviceRequest) throws Exception;
	int storeReview(GolfCourseReview golfCourseReview) throws Exception;
	
	int getNoOfLikes(int reviewId) throws Exception;
	
	public ArrayList<CourseRatingDetails> getCourseRatingDetails(int courseId) throws Exception;
	
	//Generics
	public void saveOrUpdateEntity(Object entity) throws Exception;
	public void deleteEntity(Object entity) throws Exception;
}
