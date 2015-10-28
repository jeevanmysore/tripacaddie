package com.tripcaddie.frontend.bucketList.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.spring.web.servlet.view.JsonView;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tripcaddie.backend.bucketList.model.GolfClubInquiry;
import com.tripcaddie.frontend.bucketList.dto.BucketListDto;
import com.tripcaddie.frontend.bucketList.dto.GolfClubInquiryDto;
import com.tripcaddie.frontend.bucketList.dto.GolfCourseReviewDto;
import com.tripcaddie.frontend.bucketList.service.BucketListService;
import com.tripcaddie.frontend.courses.dto.GolfCourseDto;
import com.tripcaddie.frontend.courses.service.DestinationSearchService;
import com.tripcaddie.frontend.user.service.LoginService;

@Controller
public class BucketListController {

	private static Logger logger=Logger.getLogger(BucketListController.class);
	
	@Resource(name="bucketListService")
	private BucketListService bucketListService;
	@Resource(name="destinationSearch")
	private DestinationSearchService destinationSearchService;
	@Resource(name="jsonView")
	private JsonView jsonView;
	@Resource(name="loginService")
	private LoginService loginService;
	
	@RequestMapping(value="updatePriority.do",method=RequestMethod.POST)
	public 
	ModelAndView updatePriority(@RequestParam(value="move") String updatePriorityway,
			@RequestParam(value="courseId") int courseId,
			@RequestParam(value="visitedId") int visitedId){
		
		try {
			bucketListService.updatePriority(updatePriorityway, courseId, visitedId);
			ArrayList<BucketListDto> bucketListDtos=bucketListService.getBucketLists(visitedId);
			int count=bucketListService.getBucketListCount(visitedId);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("jsonObject", bucketListDtos);
			requestJsonObject.accumulate("rowCount", count);
			
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setView(jsonView);
			modelAndView.addObject("requestObject", requestJsonObject);
			
			return modelAndView;
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		}
		return new ModelAndView("error");
	}
	
	//Add Place or course in bucket List
	@RequestMapping(value="addBucketList.do",method=RequestMethod.GET)
	public @ResponseBody
	String AddtoBucketList(@RequestParam(value="id") int courseId,
			@RequestParam(value="played") int playedId,@RequestParam(value="ranking") String ranking,
			HttpServletRequest request,HttpServletResponse response){
		
		try{	
			bucketListService.addToBucketList(courseId, playedId ,ranking);
			return "success";
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "fail";
		}
	}
	
	//Doubt: Is it need?that means how to get
	@RequestMapping(value="/updateAdiceResponse.do")
	protected @ResponseBody
	String handleRequestInternalForUpdateAdviceResponse(@RequestParam(value="responseId") int responseId,
			@RequestParam(value="response") String response){
		
		try{
			bucketListService.updateAdviceResponse(responseId, response);
			return "success";
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return "fail";
	}
	
	//Update Advice
	@RequestMapping(value="/updateAdvice.do")
	protected @ResponseBody 
	GolfClubInquiry handleRequestInternalForUpdateAdvice(){
		try{
			
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return null;
	}
	
	//get bucket List for users. And send values in JSON format  
	@RequestMapping(value="/ajax/getBucketList.do", method=RequestMethod.POST)
	protected ModelAndView getJsonBucketList(@RequestParam(value="places") int visitedId,
			HttpServletRequest request,HttpServletResponse response) {
			
			try{
				ArrayList<BucketListDto> bucketListsDtos=bucketListService.getBucketLists(visitedId);
				int count=bucketListService.getBucketListCount(visitedId);
				
				JSONObject requestJsonObject=new JSONObject();
				requestJsonObject.accumulate("jsonObject", bucketListsDtos);
				requestJsonObject.accumulate("rowCount", count);
				
				ModelAndView model=new ModelAndView();
				model.setView(jsonView);
				model.addObject("requestObject", requestJsonObject);
							
				return model;
			}catch (Exception e) {
				//e.printStackTrace();
				logger.info(e.getMessage()+" "+e.getCause());
				return new ModelAndView("error");
			}
	}
	
	//get bucket List for users
	@RequestMapping(value="getBucketList.do")
	protected ModelAndView displayBucketList(@RequestParam(value="places") int visitedId,
			HttpServletRequest request,HttpServletResponse response) {
			
			try{
				loginService.updateLoginDetails(request.getSession());	
				ArrayList<BucketListDto> bucketListsDtos=bucketListService.getBucketLists(visitedId);
				int count=bucketListService.getBucketListCount(visitedId);
				
				Map<String, Object> buckListMap=new HashMap<String, Object>();
				buckListMap.put("bucketLists", bucketListsDtos);
				buckListMap.put("rowCount", count);
				
				return new ModelAndView("bucketList","bucketLists",buckListMap);
			}catch (Exception e) {
				e.printStackTrace();
				logger.info(e.getMessage()+" "+e.getCause());
				return new ModelAndView("error");
			}
	}
	
	//Update review which user gave	
	@RequestMapping(value="updateReview.do")
	protected 
	String handleRequestInternalForUpdateReview(@RequestParam(value="reviewId") int reviewId,
			@RequestParam(value="review") String review){
		boolean flag;
		try {
			flag = bucketListService.updateReview(reviewId, review);
			if(flag)
				return "success";
			else{
				return "fail";
			}
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		//	e.printStackTrace();
		}
		return "fail";
	}
	
	//Remove places from bucket List
	@RequestMapping(value="removeFromList.do",method=RequestMethod.POST)
	protected
	ModelAndView handleRequestInternalForRemoveFromList(@RequestParam(value="courseId") int courseId,
			@RequestParam("visitId") int visitId ){
		try{
			
			bucketListService.deleteFromList(courseId, visitId);
			ArrayList<BucketListDto> bucketListDtos=bucketListService.getBucketLists(visitId);
			int count=bucketListService.getBucketListCount(visitId);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("jsonObject", bucketListDtos);
			requestJsonObject.accumulate("rowCount", count);
			
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setView(jsonView);
			modelAndView.addObject("requestObject", requestJsonObject);
			
			return modelAndView;
		}catch (Exception e) {
			//e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
	}
	
	@RequestMapping(value="/ajax/myInquiries.do",method=RequestMethod.POST)
	protected 
	ModelAndView handleAjaxRequestForInternalForGetMyInquiry(@RequestParam(value="firstRow") int firstrow){
		
		int firstRow=0;
		try {
			int rowCount=bucketListService.getRowCountForMyInquiries();
			//have to check this logic
			if(rowCount<=firstRow){
				return null;
			}else{
				firstRow=firstrow;
			}
			ArrayList<GolfClubInquiryDto> clubInquiryDtos=bucketListService.getMyInquiries(firstrow);
			for (GolfClubInquiryDto golfClubInquiryDto : clubInquiryDtos) {
				golfClubInquiryDto.setAdviceResponseDtos(destinationSearchService.getAdviceResponses(golfClubInquiryDto.getgolfClubInquiryId()));
			}
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("inquries", clubInquiryDtos);
			requestJsonObject.accumulate("rowCount", rowCount);
			requestJsonObject.accumulate("firstRow",firstRow);
			
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setView(jsonView);
			modelAndView.addObject("requestObject", requestJsonObject);
			
			return modelAndView;
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
	}
	
	@RequestMapping(value="myInquiries.do",method=RequestMethod.GET)
	protected 
	ModelAndView handleRequestForInternalForGetMyInquiry(){
		
		int firstRow=0;
		try {
		
			ArrayList<GolfClubInquiryDto> clubInquiryDtos=bucketListService.getMyInquiries(firstRow);
			for (GolfClubInquiryDto golfClubInquiryDto : clubInquiryDtos) {
				golfClubInquiryDto.setAdviceResponseDtos(destinationSearchService.getAdviceResponses(golfClubInquiryDto.getgolfClubInquiryId()));
			}
			return new ModelAndView("advice","myInquiries",clubInquiryDtos);
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
	}
	
	@RequestMapping(value="/ajax/myReviews.do",method=RequestMethod.POST)
	protected 
	ModelAndView handleAjaxRequestForInternalForGetMyreviews(@RequestParam(value="firstRow") int firstrow){
		
		int start=0;
		
		try {
			int rowCount=bucketListService.getRowCountForMyReviews();
			//System.out.println("rowCount:"+rowCount+" firstrow:"+firstrow);
			//have to check this logic
			if(rowCount<=firstrow){
				//System.out.println("In myreview first one");
				start=rowCount;
				return null;
			}else{
				start=firstrow;
			}
			//System.out.println("firstrow:"+firstrow);
			ArrayList<GolfCourseReviewDto> courseReviewDtos=bucketListService.getMyReviews(firstrow);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("firstRow", start);
			requestJsonObject.accumulate("rowCount", rowCount);
			requestJsonObject.accumulate("reviews", courseReviewDtos);
						
			ModelAndView model=new ModelAndView();
			model.setView(jsonView);
			model.addObject("requestObject", requestJsonObject);
						
			return model;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
	}
	
	@RequestMapping(value="myReviews.do",method=RequestMethod.GET)
	protected 
	ModelAndView handleRequestForInternalForGetMyreviews(){
		
		int firstRow=0;
		try {		
			ArrayList<GolfCourseReviewDto> courseReviewDtos=bucketListService.getMyReviews(firstRow);
			return new ModelAndView("review","myReviews",courseReviewDtos);
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
	}
	
	@RequestMapping(value="categorize.do",method=RequestMethod.POST)
	public @ResponseBody
	String categorizeinCoursePage(@RequestParam(value="courseId") int courseId){
	
		try {
			bucketListService.categorize(courseId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
		
	}
	
	@RequestMapping(value="categorizeinBucketList.do",method=RequestMethod.POST)
	public
	ModelAndView categorizeinBucketList(@RequestParam(value="courseId") int courseId,
			HttpServletResponse response){
	
		System.out.println("--------------In bucketList categorize--------------");
		try {
			int visitedId=bucketListService.categorize(courseId);
			ArrayList<BucketListDto> bucketListDtos=bucketListService.getBucketLists(visitedId);
			int count=bucketListService.getBucketListCount(visitedId);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("jsonObject", bucketListDtos);
			requestJsonObject.accumulate("rowCount", count);
			
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setView(jsonView);
			modelAndView.addObject("requestObject", requestJsonObject);
			System.out.println("Success");
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			try {
				response.sendRedirect("/tripcaddie/error.do");
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		return new ModelAndView("error");
		
	}
	//Display give advice page
	@RequestMapping(value="giveAdvice.do",method=RequestMethod.GET)
	public String displayGiveAdvicePage(@RequestParam(value="courseId") Integer golfCourseId,
			Model model,HttpServletRequest request){
		
		try {
			HttpSession session = request.getSession();
			
			ArrayList<GolfClubInquiryDto> clubInquiryDtos = bucketListService.getInquiries(golfCourseId);
			GolfCourseDto courseDto = destinationSearchService.getClubDetails(golfCourseId, session);
			
			model.addAttribute("inquiries", clubInquiryDtos);
			model.addAttribute("course",courseDto);
			return "giveAdvice";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
		
	}
	
	//Display review for course.. Flow: From bucketList
	@RequestMapping(value="postReview.do",method=RequestMethod.GET)
	public String displayReviewForCourse(@RequestParam(value="courseId") int courseId,
			Model model) {
		
		try {
			
			ArrayList<GolfCourseReviewDto> courseReviewDtos=destinationSearchService.getReviews(courseId);
			model.addAttribute("reviews", courseReviewDtos);
			return "postReview";
		} catch (Exception e) {	
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
}
