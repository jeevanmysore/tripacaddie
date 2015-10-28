package com.tripcaddie.frontend.itinerary.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.spring.web.servlet.view.JsonView;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.frontend.itinerary.dto.AccommodationDto;
import com.tripcaddie.frontend.itinerary.dto.ActivityDto;
import com.tripcaddie.frontend.itinerary.dto.ActivityTypeDto;
import com.tripcaddie.frontend.itinerary.dto.LogisticsDto;
import com.tripcaddie.frontend.itinerary.service.ActivityService;
import com.tripcaddie.frontend.itinerary.service.ItineraryService;
import com.tripcaddie.frontend.trip.controller.TripController;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;
import com.tripcaddie.frontend.trip.service.TripService;

@Controller
public class ItineraryController {

	private static Logger logger=Logger.getLogger(TripController.class);
	
	@Resource(name="jsonView")
	private JsonView jsonView;
	@Resource(name="tripService")
	private TripService tripService;
	@Resource(name="itineraryService")
	private ItineraryService itineraryService;
	@Resource(name="activityService")
	private ActivityService activityService;
	@Resource(name = "imagePath")
	private ImagePath imagepath;
	/*---------------Activities start----------------*/
	
	@RequestMapping(value="addActivities.do")
	public String displayAddActivity(@RequestParam(value="tripId") int tripId,
			Model model) {
	
		try {
			
			ArrayList<ActivityTypeDto> activityTypeDtos=activityService.getActivityTypes();
			TripDto tripDto=tripService.getTrip(tripId);
			
			model.addAttribute("activityType", activityTypeDtos);
			model.addAttribute("trip", tripDto);
		
			return "addActivity";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	@RequestMapping(value="addActivities.do",method=RequestMethod.POST)
	public	@ResponseBody 
	String addActivity(@RequestParam(value="tripId") int tripId,
			@RequestParam(value="activityTypeId") int activityTypeId,
			@RequestParam(value="activityTitle") String activityTitle,
			@RequestParam(value="date") String date,
			@RequestParam(value="time") String time,
			@RequestParam(value="url") String url,
			@RequestParam(value="mapInfo" , required = false) String mapInfo,
			@RequestParam(value="comment") String comment,
			@RequestParam(value="pending") Integer pending){
		
		System.out.println("---------------Add activity-----------------");
		try {			
			activityService.addActivity(tripId, activityTypeId, activityTitle, date, time, url, mapInfo, comment, pending);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
		
	//Display Activities
	@RequestMapping(value="activities.do")
	public ModelAndView displayActivities(@RequestParam(value="tripId") int tripId){
		
		long diff=0;
		try {
			
			TripDto tripDto=tripService.getTrip(tripId);
			String imageBase64=null;
			if(tripDto.getImagePath()!=null){
				imageBase64=this.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			
			ArrayList<ActivityDto> activityDtos=activityService.getActivities(tripId);
			
			for (ActivityDto activityDto : activityDtos) {
				diff=(activityDto.getActivityDate().getTimeInMillis()-tripDto.getStartDate().getTimeInMillis())/(1000*60*60*24);
				activityDto.setNoOfDays(diff+1);
			}
			
			System.out.println("Size:"+activityDtos.size());
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("trip", tripDto);
			model.put("image", imageBase64);
			model.put("activity", activityDtos);
			
			return new ModelAndView("activities","activities",model);
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		}
		return new ModelAndView("error");
	}
	
	//display Golf/Pairings
	@RequestMapping(value="trip/pairings.do",method=RequestMethod.GET)
	public String displayPairings(@RequestParam(value="tripId") int tripId,
			Model model) {
		
		try {
			
			TripDto tripDto=tripService.getTrip(tripId);
			ArrayList<ActivityDto> activityDtos=activityService.getTeetimeAndGolfSchedule(tripId);
			
			String imageBase64=null;
			if(tripDto.getImagePath()!=null){
				imageBase64=this.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			
			model.addAttribute("trip", tripDto);
			model.addAttribute("image", imageBase64);
			model.addAttribute("activities", activityDtos);
			
			return "trip/pairings";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	//Display Tee time
	@SuppressWarnings("deprecation")
	@RequestMapping(value="trip/teeTime.do",method=RequestMethod.GET)
	public String displayTeeTime(@RequestParam(value="tripId") Integer tripId,
			Model model) {
		
		try {
			TripDto tripDto=tripService.getTrip(tripId);
			ArrayList<ActivityDto> activityDtos=activityService.getTeetimeAndGolfSchedule(tripId);
			ArrayList<TripMemberDto> tripMemberDtos=tripService.getTripMembers(tripId);
			
			for (ActivityDto activityDto : activityDtos) {
				if(activityDto.getActivityDate() != null){
					activityDto.setActivitydateString((activityDto.getActivityDate().getTime().getMonth() + 1)+"/"+activityDto.getActivityDate().getTime().getDate()+"/"+(activityDto.getActivityDate().getTime().getYear()+1900));
				//	System.out.println("Activity Date:"+activityDto.getActivityDate().getTime());
				}
				if(activityDto.getActivityTime()!=null){
					activityDto.setActivityTimeString(activityDto.getActivityTime().getHours()+":"+activityDto.getActivityTime().getMinutes());
				}
				//System.out.println("Player5:"+activityDto.getTeeTimeDtos().get(0).getPlayer5().getMemberId());
			}
			String imageBase64=null;
			if(tripDto.getImagePath()!=null){
				imageBase64=this.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			
			model.addAttribute("tripMembers", tripMemberDtos);
			model.addAttribute("trip", tripDto);
			model.addAttribute("image", imageBase64);
			model.addAttribute("activities", activityDtos);
			
			return "trip/teeTime";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
		
	}
	
	//save Tee time
	@RequestMapping(value="trip/saveTeeTime.do",method=RequestMethod.POST)
	public @ResponseBody 
	String saveTeeTime(@RequestParam(value="player1") String player1,
			@RequestParam(value="player2") String player2,
			@RequestParam(value="player3") String player3,
			@RequestParam(value="player4") String player4,
			@RequestParam(value="player5") String player5,
			@RequestParam(value="activityId") Integer activityId,
			@RequestParam(value="teeTimeId") Integer teeTimeId,
			@RequestParam(value="timing") String timing) {
		
		try {
			System.out.println("TeeTimeId="+teeTimeId);
			int roundteeTimeId=activityService.saveTeeTime(player1, player2, player3, player4, player5, activityId, timing, teeTimeId);
			
			System.out.println("activityId="+activityId);
			return "success_"+roundteeTimeId;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
		
	}
	
	//delete teetime
	@RequestMapping(value="trip/deleteTeeTime.do",method=RequestMethod.POST)
	public @ResponseBody
	String deleteTeeTime(@RequestParam(value="teeTimeId") int teeTimeId){
		
		try {
			
			activityService.deleteTeeTime(teeTimeId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	// display Edit Activity
	@SuppressWarnings("deprecation")
	@RequestMapping(value="editActivity.do")
	public String displayEditActivity(@RequestParam(value="activityId") int activityId,
			@RequestParam(value="tripId") int tripId,
			Model model){
		
		try {
			
			ArrayList<ActivityTypeDto> activityTypeDtos=activityService.getActivityTypes();
			TripDto tripDto=tripService.getTrip(tripId);
			ActivityDto activityDto=activityService.getActivity(activityId);
			
			if(activityDto.getActivityDate() != null){
				activityDto.setActivitydateString((activityDto.getActivityDate().getTime().getMonth() + 1)+"/"+activityDto.getActivityDate().getTime().getDate()+"/"+(activityDto.getActivityDate().getTime().getYear()+1900));
				System.out.println("Activity Date:"+activityDto.getActivityDate().getTime());
			}
			if(activityDto.getActivityTime()!=null){
				activityDto.setActivityTimeString(activityDto.getActivityTime().getHours()+":"+activityDto.getActivityTime().getMinutes());
			}
			
			model.addAttribute("activityType", activityTypeDtos);
			model.addAttribute("activity", activityDto);
			model.addAttribute("trip", tripDto);
			
			return "editActivity";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	//  Edit Activity
	@RequestMapping(value="editActivity.do",method=RequestMethod.POST)
	public @ResponseBody 
	String editActivity(@RequestParam(value="activityId") int activityId,
			@RequestParam(value="activityTypeId") int activityTypeId,
			@RequestParam(value="activityTitle") String activityTitle,
			@RequestParam(value="date") String date,
			@RequestParam(value="time") String time,
			@RequestParam(value="url") String url,
			@RequestParam(value="mapInfo",required=false) String mapInfo,
			@RequestParam(value="comment") String comment,
			@RequestParam(value="pending") Integer pending){
		
		try {
			System.out.println("Its come here");
			activityService.editActivity(activityId, activityTypeId, activityTitle, date, time, url, mapInfo, comment, pending);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
			
	}
	
	@RequestMapping(value="deleteActivity.do",method=RequestMethod.POST)
	public @ResponseBody
	String deleteActivity(@RequestParam(value="activityId") int activityId){
		
		try {
			activityService.deleteActivity(activityId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	/*---------------Activities End----------------*/

	@RequestMapping(value = "pdf.do")
	public ModelAndView viewPdf(@RequestParam(value = "tripId") int tripId,
			@RequestParam(value = "format") String output) {

		try {
			TripDto tripDto = tripService.getTrip(tripId);
			/*
			 * ArrayList<AccommodationDto>
			 * accommodationDtos=tripService.getAccommodations(tripId);
			 */
			AccommodationDto accommodationDto = itineraryService
					.getAccommodation(tripId);
			List<ActivityDto> actList = activityService.getActivities(tripId);
			LogisticsDto logistics = itineraryService.getLogistic(tripId);
			List<TripMemberDto> members = tripService.getTripMembers(tripId);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("trip", tripDto);
			model.put("accommodation", accommodationDto);
			model.put("activitylist", actList);
			model.put("logistics", logistics);
			model.put("members", members);
			if (output.equalsIgnoreCase("view")) {
				return new ModelAndView("pdfViewer", "model", model);
			} else if (output.equalsIgnoreCase("download")) {
				return new ModelAndView("pdfDownload", "model", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return new ModelAndView("error");
		}
		return null;
	}

	//Done by praga
	@RequestMapping(value="teeTimePdf.do")
	public ModelAndView viewTeeTimePdfGenerator(@RequestParam(value="tripId") int tripId,
			@RequestParam(value="format") String outputFormat){
		
		try {
			System.out.println("Format:"+outputFormat);
			TripDto tripDto=tripService.getTrip(tripId);
			ArrayList<ActivityDto> activityDtos=activityService.getTeetimeAndGolfSchedule(tripId);
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("trip", tripDto);
			model.put("activities", activityDtos);
			
			if(outputFormat.equalsIgnoreCase("view")){
				return new ModelAndView("teeTimePdfViewer","teeTime",model);
			}else if(outputFormat.equalsIgnoreCase("download")){
				return new ModelAndView("teeTimePdfDownload","teeTime",model);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return new ModelAndView("error");
		}
		return new ModelAndView("error");
	}
	/*---------------PDF END---------------------*/
	/*-------------Accommodations Start------------*/
	
	@RequestMapping(value="accommodation.do")
	public ModelAndView displayAccommodationPage(@RequestParam(value="tripId") int tripId){
		
		try {
			TripDto tripDto=tripService.getTrip(tripId);
			/*ArrayList<AccommodationDto> accommodationDtos=tripService.getAccommodations(tripId);*/
			AccommodationDto accommodationDto=itineraryService.getAccommodation(tripId);
			
			String imageBase64=null;
			if(tripDto.getImagePath()!=null){
				imageBase64=this.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("trip", tripDto);
			model.put("accommodation", accommodationDto);
			model.put("image", imageBase64);
			
			return new ModelAndView("accommodation","accommodation",model);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return new ModelAndView("error");
		}	
	}
	
	@RequestMapping(value="addAccommodation.do",method=RequestMethod.POST)
	public 
	ModelAndView saveAccommodationDetails(@RequestParam(value="tripId") Integer tripId,
			@RequestParam(value="accommodationTxt") String accommodationTxt){
		
		try{
			AccommodationDto accommodationDto=itineraryService.addAccommodation(tripId, accommodationTxt);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("accommodation",accommodationDto);
						
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setView(jsonView);
			modelAndView.addObject("jsonObject", requestJsonObject);
			
			return modelAndView;
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
		
	}
	
	@RequestMapping(value="editAccommodation.do",method=RequestMethod.POST)
	public
	ModelAndView editAccommodation(@RequestParam(value="accommodationId") int accommodationId,
			@RequestParam(value="accommodationTxt") String accommodationText){
		
		try {
			AccommodationDto accommodationDto=itineraryService.editAccommodation(accommodationId, accommodationText);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("accommodation", accommodationDto);
			
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setView(jsonView);
			modelAndView.addObject("jsonObject", requestJsonObject);
			
			return modelAndView;
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		}
		return new ModelAndView("error");
	}
	
	@RequestMapping(value="deleteAccommodation.do",method=RequestMethod.POST)
	public @ResponseBody
	String deleteAccommodation(@RequestParam(value="accommodationId") Integer accommodationId){
		try{
			System.out.println("AccommodationId"+accommodationId);
			itineraryService.deleteAccommodation(accommodationId);
			return "success"; 
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	/*-------------Accommodations End------------*/
	/*---------------Logistics Start------------*/
	
	@RequestMapping(value="logistics.do")
	public ModelAndView displayLogisticsPage(@RequestParam(value="tripId") int tripId){
		
		try {
			TripDto tripDto=tripService.getTrip(tripId);
			/*ArrayList<LogisticsDto> logisticsDtos=itineraryService.getLogistics(tripId);*/
			LogisticsDto logisticsDto=itineraryService.getLogistic(tripId);
			
			String imageBase64=null;
			if(tripDto.getImagePath()!=null){
				imageBase64=this.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("trip", tripDto);
			model.put("image", imageBase64);
			model.put("logistics", logisticsDto);
			
			return new ModelAndView("logistics","logistics",model);
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			return new ModelAndView("error");
		}
		
	}
	
	@RequestMapping(value="addLogistic.do",method=RequestMethod.POST)
	public 
	ModelAndView saveLogisticDetails(@RequestParam(value="tripId") Integer tripId,
			@RequestParam(value="logisticsTxt") String logisticsContent){
		System.out.println("logisticsTxt"+logisticsContent);
		try{
			/*AccommodationDto accommodationDto=itineraryService.addAccommodation(tripId, accommodationTxt);*/
			LogisticsDto logisticsDto=itineraryService.addLogistics(tripId, logisticsContent);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("logistics",logisticsDto);
						
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setView(jsonView);
			modelAndView.addObject("jsonObject", requestJsonObject);
			
			return modelAndView;
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return new ModelAndView("error");
		
	}
	
	@RequestMapping(value="editLogistics.do",method=RequestMethod.POST)
	public
	ModelAndView editLogistics(@RequestParam(value="logisticsId") int logisticsId,
			@RequestParam(value="logisticsText") String logisticsText){
	
		try {
			LogisticsDto logisticsDto=itineraryService.editLogistics(logisticsId, logisticsText);
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("logistics", logisticsDto);
			
			ModelAndView model=new ModelAndView();
			model.setView(jsonView);
			model.addObject("jsonObject", requestJsonObject);
			
			return model;
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		}
		return new ModelAndView("error");
	}
	
	@RequestMapping(value="deleteLogistics.do",method=RequestMethod.POST)
	public @ResponseBody
	String deleteLogistics(@RequestParam(value="logisticsId") Integer logisticsId){
		try{
			System.out.println("logisticsId"+logisticsId);
			itineraryService.deleteLogistics(logisticsId);
			return "success"; 
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	/*---------------Logistics End------------*/
	
	private String getImageEncodedString(String imagePath) throws Exception{
		
		File file=new File(imagePath);
		byte[] b=new byte[(int) file.length()];
		FileInputStream fileInputStream=new FileInputStream(file);
		fileInputStream.read(b);
		return Base64.encodeBase64String(b);
	}
}
