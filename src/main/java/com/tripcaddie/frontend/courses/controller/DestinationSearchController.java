package com.tripcaddie.frontend.courses.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.spring.web.servlet.view.JsonView;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.frontend.bucketList.dto.GolfClubAdviceResponseDto;
import com.tripcaddie.frontend.bucketList.dto.GolfClubInquiryDto;
import com.tripcaddie.frontend.bucketList.dto.GolfCourseReviewDto;
import com.tripcaddie.frontend.courses.dto.GolfCourseDto;
import com.tripcaddie.frontend.courses.service.DestinationSearchService;
import com.tripcaddie.frontend.user.service.LoginService;

@Controller
public class DestinationSearchController {

	private static Logger logger=Logger.getLogger(DestinationSearchController.class);
	
	@Resource(name="destinationSearch")
	private DestinationSearchService destinationSearchService;
	@Resource(name="jsonView")
	private JsonView jsonView;
		
	@Resource(name="loginService")
	private LoginService loginService;

	
	//get Top destinatons
	@RequestMapping(value="getTopDestinations.do")
	protected @ResponseBody
	ArrayList<GolfCourse> handleRequestInternalForTopDestinations(){
		// TODO Auto-generated method stub
		return null;
	}
	
	//Get destionations - JSON object
	@RequestMapping(value="getDestinations.do",method=RequestMethod.GET)
	protected
	ModelAndView handleRequestInternalForDestinations(@RequestParam(value="places") String destination){
		
		ArrayList<GolfCourseDto> listOfgolfCoursesDtos;
		try {
			listOfgolfCoursesDtos = destinationSearchService.getGolfCourses(destination);
		
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("jsonObject", listOfgolfCoursesDtos);
		
			ModelAndView model=new ModelAndView();
			model.setView(jsonView);
			model.addObject("requestObject", requestJsonObject);
			return model;
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			//e.printStackTrace();
		}
		return new ModelAndView("error");
	}
	
	//get club info
	@RequestMapping(value="getClubDetails.do",method=RequestMethod.GET)
	protected ModelAndView handleRequestInternalForClubDetails(@RequestParam(value="courseId") int courseId,
			@RequestParam(value="adviceId" , required = false) String adviceId ,@RequestParam(value="popup" , required = false) String popup ,HttpServletRequest request){
		
		HttpSession httpSession=request.getSession();
		try{
			Map<String, Object> model=new HashMap<String, Object>();
			String username=loginService.getAuthenticatedUser();
			if(username.equalsIgnoreCase("anonymousUser")){
				model.put("login", false);
			}
			else{
				model.put("login", true);
				loginService.updateLoginDetails(request.getSession());
			}
			GolfCourseDto courseDto=destinationSearchService.getClubDetails(courseId,httpSession);
			ArrayList<GolfCourseReviewDto> reviewDtos=destinationSearchService.getReviews(courseId);
		
			for(GolfCourseReviewDto reviewDto:reviewDtos){
				reviewDto.setLikeCount(destinationSearchService.getNoOfLikes(reviewDto.getReviewId()));
			}
			
			model.put("course", courseDto);
			model.put("review", reviewDtos);
			model.put("popup", false);
			model.put("advicePopup", false);
			model.put("reviewPopup", false);
			model.put("BucketlistPopup", false);
			if(popup!=null && !popup.isEmpty()&&popup.equalsIgnoreCase("Advice")){
				model.put("advicePopup", true);
			}
			if(popup!=null && !popup.isEmpty()&&popup.equalsIgnoreCase("Review")){
				model.put("reviewPopup", true);
			}
			if(popup!=null && !popup.isEmpty()&&popup.equalsIgnoreCase("Bucketlist")){
				model.put("BucketlistPopup", true);
			}
			
			if(adviceId!=null && !adviceId.isEmpty()){
				model.put("popup", true);
				model.put("adviceId", adviceId);
			}
			return new ModelAndView("course","courseDetails",model);
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
	}
	
	
	@RequestMapping(value="getAdvice.do" ,method=RequestMethod.POST)
	protected ModelAndView handleRequestInternalForAdvice(@RequestParam(value="adviceId") int adviceId ,HttpServletRequest request){
		try{
			GolfClubInquiryDto adviceRequestDto=destinationSearchService.getAdvice(adviceId);
			
			for(GolfClubAdviceResponseDto golfClubAdviceResponseDto: adviceRequestDto.getAdviceResponseDtos())
			{
				int difInDays = (int) ((new Date().getTime() - golfClubAdviceResponseDto.getCreatedDate().getTime().getTime())/(1000*60*60*24));
				golfClubAdviceResponseDto.setDays(difInDays);
			}
			JSONObject json=new JSONObject();
			json.accumulate("advice", adviceRequestDto);
			
			String username=loginService.getAuthenticatedUser();
			if(username.equalsIgnoreCase("anonymousUser")){
			json.accumulate("login",false );
			}
			else{
				json.accumulate("login",true );
				loginService.updateLoginDetails(request.getSession());
			}
			ModelAndView model=new ModelAndView();
			model.setView(jsonView);
			model.addObject("json", json);
			return model;
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+ " "+e.getCause());
		}
		return null;
	}
	
	@RequestMapping(value="getAdvices.do")
	protected 
	ModelAndView handleRequestInternalForAdvices(@RequestParam(value="courseId") int courseId){
		try{
			ArrayList<GolfClubInquiryDto> clubInquiries=destinationSearchService.getAdvices(courseId);
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("jsonObject", clubInquiries);
			
			ModelAndView model=new ModelAndView();
			model.setView(jsonView);
			model.addObject("requestObject", requestJsonObject);
			
			return model;
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
	}
	
	//get particular review for particular course
	@RequestMapping(value="getreview.do")
	protected @ResponseBody 
	GolfCourseReviewDto handleRequestInternalForReview(@RequestParam(value="reviewId") int reviewId){
		try{
			return destinationSearchService.getReview(reviewId);
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return null;	
	}
	
	//get review for particular course
	@RequestMapping(value="getReviews.do")
	protected 
	ModelAndView handleRequestInternalForReviews(@RequestParam(value="courseId") int courseId) throws InvocationTargetException{
		
		ArrayList<GolfCourseReviewDto> courseReviewDtos;
		try {
			courseReviewDtos = destinationSearchService.getReviews(courseId);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("jsonObject", courseReviewDtos);
		
			ModelAndView model=new ModelAndView();
			model.setView(jsonView);
			model.addObject("requestObject", requestJsonObject);
			return model;
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			//e.printStackTrace();
		}
		return new ModelAndView("error");
	}
	
	//update advices response like
	@RequestMapping(value="updateAdviceResponseLike.do")
	protected @ResponseBody 
	String handleRequestInternalForUpdateAdviceResponseLike(@RequestParam(value="responseId") int responseId,
			HttpServletRequest request){
		
		//HttpSession session=request.getSession();
		try{
			destinationSearchService.updateAdviceResponseLike(responseId);
			return "success";
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return "fail";
	}
	
	//	
	@RequestMapping(value="giveResponse.do", method=RequestMethod.POST)
	protected 
	ModelAndView handleRequestInternalForGiveAdviceResponse(@RequestParam(value="inquiryId") int inquiryId,
			@RequestParam(value="responseText") String responseText,
				HttpServletRequest request){
			//	HttpSession session=request.getSession();
				try{
					GolfClubAdviceResponseDto adviceResponseDto=destinationSearchService.giveAdviceResponse(inquiryId, responseText);
					JSONObject requestJsonObject=new JSONObject();
					requestJsonObject.accumulate("jsonObject", adviceResponseDto);
					
					System.out.println(requestJsonObject.getJSONObject("jsonObject"));					
					
					ModelAndView model=new ModelAndView();
					model.setView(jsonView);
					model.addObject("requestObject", requestJsonObject);
					
					return model;
				}catch (Exception e) {
					e.printStackTrace();
					logger.info(e.getMessage()+" "+e.getCause());
				}
		return new ModelAndView("error");
	}
	
	@RequestMapping(value="writeReview.do")
	protected 
	ModelAndView handleRequestInternalForWriteReview(@RequestParam(value="courseId") int courseId,
			@RequestParam(value="review") String review,HttpServletRequest request) {
			
		//HttpSession session=request.getSession();
		try{
			GolfCourseReviewDto golfCourseReviewDto=destinationSearchService.writeReview(review, courseId);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("jsonObject", golfCourseReviewDto);
			
			ModelAndView model=new ModelAndView();
			model.setView(jsonView);
			model.addObject("requestObject", requestJsonObject);
			
			return model;
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
	}
	
	//Like the review functionality
	@RequestMapping(value="updateReviewLike.do")
	protected @ResponseBody
	String handleRequestInternalForUpdateReviewLike(@RequestParam(value="reviewId") int reviewId,
			HttpServletRequest request){
		//Need to think how to update likes in efficient way(ie. By not getting objects)
		//HttpSession session=request.getSession();
		try{
			destinationSearchService.updateReviewLike(reviewId);
			return "success";
		}catch (Exception e) {
			//e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return "fail";
	}
	
	@RequestMapping(value="askAdvice.do")
	protected 
	ModelAndView handleRequestInternalForAskAdvice(@RequestParam(value="courseId") int courseId,
			@RequestParam(value="inquiry") String inquiry,
			HttpServletRequest request){
		
		HttpSession session=request.getSession();
		try{
			GolfClubInquiryDto adviceInquiryDto=destinationSearchService.askAdvice(courseId, inquiry,session);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("jsonObject", adviceInquiryDto);
			
			ModelAndView model=new ModelAndView();
			model.setView(jsonView);
			model.addObject("requestObject", requestJsonObject);
			
			return model;
		}catch (Exception e) {
			System.out.println("In controller");
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
	}
	
	@RequestMapping(value="updateRating.do", method=RequestMethod.POST)
	public @ResponseBody
	String handleRequestInternalForUpdateRating(@RequestParam(value="courseId") int courseId,
			@RequestParam(value="rating") Integer rating){
	
		System.out.println("Rating:"+rating);
		try{
			destinationSearchService.updateorAddDiscussionRating(courseId,rating);
			return "success";
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+ " "+e.getCause());
			return "fail";
		}
	}
	
	//Delete review
	@RequestMapping(value="revomeReview.do",method=RequestMethod.POST)
	public @ResponseBody 
	String deleteReview(@RequestParam(value="reviewId") Integer reviewId){
	
		try {
			
			destinationSearchService.removeReview(reviewId);
			return "success";
					
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+ " "+e);
			return "error";
		}
	}
	
	//Delete question/advice request
	@RequestMapping(value="removeQuestion.do",method=RequestMethod.POST)
	public @ResponseBody
	String deleteQuestion(@RequestParam(value="questionId") int questionId){
		
		try {
			
			destinationSearchService.removeQuestion(questionId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	//Edit review
	@RequestMapping(value="editReview.do",method=RequestMethod.POST)
	public @ResponseBody
	String editReview(@RequestParam(value="reviewId") Integer reviewId,
			@RequestParam(value="reviewTxt") String reviewTxt){
		
		try {
			destinationSearchService.editReview(reviewId, reviewTxt);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	//Edit review
	@RequestMapping(value="editQuestion.do",method=RequestMethod.POST)
	public @ResponseBody
	String editQuestion(@RequestParam(value="questionId") Integer inquiryId,
			@RequestParam(value="questionTxt") String questionTxt){
		
		try {
			destinationSearchService.editInquiry(inquiryId, questionTxt);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	@RequestMapping(value="getSearchDst.do", method=RequestMethod.GET)
	public String getSearchDestination(){
		return "searchDestination";
		
	}
}

