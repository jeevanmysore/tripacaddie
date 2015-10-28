package com.tripcaddie.frontend.courses.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.frontend.bucketList.dto.GolfClubAdviceResponseDto;
import com.tripcaddie.frontend.bucketList.dto.GolfClubInquiryDto;
import com.tripcaddie.frontend.bucketList.dto.GolfCourseReviewDto;
import com.tripcaddie.frontend.courses.dto.GolfCourseDto;

public interface DestinationSearchService {
	
	ArrayList<GolfCourseDto> getTopDestinations();
	GolfCourseDto getClubDetails(int courseId,HttpSession session) throws Exception;
	ArrayList<GolfCourseDto> getGolfCourses(String destination) throws Exception;
	
	GolfClubInquiryDto getAdvice(int adviceId) throws Exception;
	ArrayList<GolfClubInquiryDto> getAdvices(int courseId) throws Exception;
	GolfClubInquiryDto askAdvice(int courseId,String adviceText,HttpSession session) throws Exception;
	
	ArrayList<GolfCourseReviewDto> getReviews(int courseId) throws Exception;
	GolfCourseReviewDto getReview(int reviewId) throws Exception;
	GolfCourseReviewDto writeReview(String reviewText,int courseId) throws Exception;
	
	void updateorAddDiscussionRating(int courseId,Integer rating) throws Exception;
	void updateAdviceResponseLike(int responseId) throws Exception;
	void updateReviewLike(int reviewId) throws Exception;
	
	GolfClubAdviceResponseDto getAdviceResponse(int responseId) throws Exception;
	GolfClubAdviceResponseDto giveAdviceResponse(int adviceId,String responseText) throws Exception;
	ArrayList<GolfClubAdviceResponseDto> getAdviceResponses(int adviceId) throws Exception;
	
	GolfCourse createGolfCourseFrom(GolfCourseDto golfCourseDto) throws Exception;
	
	int getNoOfLikes(int reviewId) throws Exception;
	
	public void removeReview(int reviewId) throws Exception;
	public void removeQuestion(int questionId) throws Exception;
	
	public void editReview(int reviewId,String review) throws Exception;
	public void editInquiry(int inquiryId,String questionTxt) throws Exception;
	
}
