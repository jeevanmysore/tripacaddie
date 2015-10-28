package com.tripcaddie.frontend.trip.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.common.model.PollForm;
import com.tripcaddie.frontend.trip.dto.PollQuestionsDto;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.service.PollService;
import com.tripcaddie.frontend.trip.service.TripService;

@Controller
public class PollController {

	private static Logger logger=Logger.getLogger(PollController.class);
	
	@Resource(name="pollService")
	private PollService pollService;
	@Resource(name="tripService")
	private TripService tripService;
	@Resource(name = "imagePath")
	private ImagePath imagepath;
	//display create Poll page
	@RequestMapping(value="createPoll.do",method=RequestMethod.GET)
	public String createPoll(@RequestParam(value="tripId") int tripId,
			Model model){
		
		try {
			
			TripDto tripDto=tripService.getTrip(tripId);
			
			String imageBase64=null;
			if(tripDto.getImagePath()!=null){
				imageBase64=this.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			
			model.addAttribute("image", imageBase64);
			model.addAttribute("trip", tripDto);
			
			return "createPoll";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
		
	}
	
	//create Poll
	@RequestMapping(value="createPoll.do",method=RequestMethod.POST)
	public @ResponseBody 
	void createPoll(@ModelAttribute("poll") PollForm pollForm,HttpServletResponse response) {
		
		try {
			//System.out.println("In PollController");
			int pollID=pollService.addPollQuestions(pollForm.getTripId(), pollForm.getQuestions());
			
			for (String option : pollForm.getOptions()) {
				pollService.addOption(pollID, option);
			}
			
			response.sendRedirect("/tripcaddie/getTrip.do?tripId="+pollForm.getTripId()+"&tab=poll");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			try {
				response.sendRedirect("/tripcaddie/error.do");
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.info(e1.getMessage()+" "+e.getCause());
				
			}
		}
	}
	
	//Display Poll Page
	@RequestMapping(value="getPoll.do",method=RequestMethod.GET)
	public String displayPollPage(@RequestParam(value="pollId") int pollId,
				Model model){
		
		try {
			
			PollQuestionsDto pollQuestionsDto=pollService.getPollQuestion(pollId);
			
			model.addAttribute("poll", pollQuestionsDto);
			
			return "poll";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	//Display Edit Poll Page
	@RequestMapping(value="editPoll.do",method=RequestMethod.GET)
	public String displayEditPoll(@RequestParam(value="pollId") int pollId,
			Model model){
		
		try {
			
			PollQuestionsDto pollQuestionsDto=pollService.getPollQuestion(pollId);
			
			TripDto tripDto=pollQuestionsDto.getTripDto();
			
			String imageBase64=null;
			if(tripDto.getImagePath()!=null){
				imageBase64=this.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
		
			model.addAttribute("poll", pollQuestionsDto);
			model.addAttribute("image", imageBase64);
			model.addAttribute("trip",tripDto);
			
			return "editPoll";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	//Edit poll
	@RequestMapping(value="editPoll.do",method=RequestMethod.POST)
	public @ResponseBody
	void editPoll(@ModelAttribute("poll") PollForm pollForm,
			HttpServletResponse response) {
		
		try {
			
			ArrayList<Integer> optionId=pollForm.getOptionId();
			ArrayList<String> options=pollForm.getOptions();
			
			int pollId=pollForm.getPollId();
			
			pollService.editPollQuestion(pollId, pollForm.getQuestions());
			
			
			for (int id = 0 ; id < optionId.size(); id++) {
				
				if(optionId.get(id).intValue() != 0){
					pollService.editPollOption(optionId.get(id).intValue(), options.get(id));
				}else {
					pollService.addOption(pollId, options.get(id));
				}
			}
						
			response.sendRedirect("/tripcaddie/getTrip.do?tripId="+pollForm.getTripId()+"&tab=poll");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			try {
				response.sendRedirect("/tripcaddie/error.do");
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.info(e1.getMessage()+" "+e1);
			}
		}
		
	}
	
	//delete Poll
	@RequestMapping(value="deletePoll.do")
	public @ResponseBody 
	String deletePoll(@RequestParam(value="pollId") int pollId){
		
		try {
			pollService.deletePoll(pollId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	//vote in poll
	@RequestMapping(value="voteInPoll.do",method=RequestMethod.POST)
	public @ResponseBody
	String voteNow(@RequestParam(value="optionId") int optionId){
		
		try {
			
			pollService.voteNow(optionId);
			
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	//convert image to base64String
	@SuppressWarnings("resource")
	private String getImageEncodedString(String imagePath) throws Exception{
	
		File file=new File(imagePath);
		byte[] b=new byte[(int) file.length()];
		FileInputStream fileInputStream=new FileInputStream(file);
		fileInputStream.read(b);
		return Base64.encodeBase64String(b);
	}
}
