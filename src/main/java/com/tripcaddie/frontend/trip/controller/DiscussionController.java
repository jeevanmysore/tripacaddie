package com.tripcaddie.frontend.trip.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.frontend.trip.dto.DiscussionCommentDto;
import com.tripcaddie.frontend.trip.dto.DiscussionDto;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.service.DiscussionService;
import com.tripcaddie.frontend.trip.service.TripService;

@Controller
public class DiscussionController {

	private static Logger logger=Logger.getLogger(DiscussionController.class);
	
	@Resource(name="tripService")
	private TripService tripService;
	@Resource(name="discussionService")
	private DiscussionService discussionService;
	@Resource(name="imagePath")
	private ImagePath imagepath;
	
	//display Create discussion page
	@RequestMapping(value="displayCreateDiscussion.do",method=RequestMethod.GET)
	public 
	String displayCreateDiscussionPage(@RequestParam(value="tripId") int tripId,
			Model model) {
		
		try {
			
			TripDto tripDto=tripService.getTrip(tripId);
			
			String imageBase64=null;
			if(tripDto.getImagePath()!=null){
				imageBase64=this.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			
			model.addAttribute("image", imageBase64);
			model.addAttribute("trip", tripDto);
			
			return "createDiscussion";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	//store discussion
	@RequestMapping(value="createDiscussion.do",method=RequestMethod.POST)
	public @ResponseBody 
	String createDiscussion(@RequestParam(value="tripId") int tripId,
			@RequestParam(value="title") String title,
			@RequestParam(value="description") String description) {
		
		try {
			discussionService.createDiscussion(title, description, tripId);
			//if it's success,  it should redirect to tripHome 
			return "success";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	//displayDiscussion page
	@RequestMapping(value="getDiscussion.do",method=RequestMethod.GET)
	public String displayDiscussionPage(@RequestParam(value="discussionId") int discussionId,
			Model model){
		
		try {
			
			DiscussionDto discussionDto=discussionService.getDiscussion(discussionId);
			
			discussionDto.setNoOfComments(discussionDto.getCommentDtos().size());
			
			for (DiscussionCommentDto commentDto : discussionDto.getCommentDtos()) {
				if(commentDto.getTripMemberDto().getTripCaddieUserDto().getImageUrl() != null &&
						!commentDto.getTripMemberDto().getTripCaddieUserDto().getImageUrl().isEmpty()){
					commentDto.getTripMemberDto().getTripCaddieUserDto().setImageBase64(
							getImageEncodedString(
									imagepath.getTripimagepathperuser()+commentDto.getTripMemberDto().getTripCaddieUserDto().getImageUrl()));
				}
			}
			
			model.addAttribute("discussion", discussionDto);
			
			return "discussions";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	//delete discussion
	@RequestMapping(value="deleteDiscussion.do",method=RequestMethod.POST)
	public @ResponseBody 
	String deleteDiscussion(@RequestParam(value="discussionId") int discussionId){
		
		try {
			
			discussionService.deleteDiscussion(discussionId);
			
			return "success";
		} catch (Exception e) {
				e.printStackTrace();
				logger.info(e.getMessage()+" "+e);
				return "error";
		}
	}
	
	//display Edit discussion
	@RequestMapping(value="editDiscussion.do",method=RequestMethod.GET)
	public String displayEditDiscussion(@RequestParam(value="discussionId") int discussionId,
			Model model){
		
		try {
			
			DiscussionDto discussionDto=discussionService.getDiscussion(discussionId);
			TripDto tripDto=tripService.getTrip(discussionDto.getTripDto().getTripId());
			
			String imageBase64=null;
			if(tripDto.getImagePath()!=null){
				imageBase64=this.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			
			model.addAttribute("image", imageBase64);
			model.addAttribute("discussion", discussionDto);
			model.addAttribute("trip", tripDto);
			
			return "editDiscussion";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
		
	}
	
	//Edit discussion
	@RequestMapping(value="editDiscussion.do",method=RequestMethod.POST)
	public @ResponseBody 
	String editDiscussion(@RequestParam(value="discussionId") int discussionId,
			@RequestParam(value="title") String title,
			@RequestParam(value="description") String description){
		
		try {
			
			discussionService.EditDiscussion(title, description, discussionId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	/*On comment*/
	
	@RequestMapping(value="commentOnDiscussion.do",method=RequestMethod.POST)
	public @ResponseBody
	String addCommentOnDiscussion(@RequestParam(value="discussionId") int discussionId,
			@RequestParam(value="comment") String comment){
		
		try {
			
			discussionService.addCommentForDiscussion(discussionId, comment);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
	}
	
	@RequestMapping(value="editCommentOfDiscussion.do",method=RequestMethod.POST)
	public @ResponseBody
	String editComment(@RequestParam(value="commentID") int commentId,
			@RequestParam(value="comment") String comment){
		
		try {
			
			discussionService.editCommentForDiscussion(commentId, comment);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	@RequestMapping(value="deleteCommentFromDiscussion.do",method=RequestMethod.POST)
	public @ResponseBody
	String deleteComment(@RequestParam(value="commentId") int commentId){
		
		try {
			
			discussionService.deleteDiscussionComment(commentId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	@RequestMapping(value="ratingDiscussion.do",method=RequestMethod.POST)
	public @ResponseBody
	String addOrUpdateDiscussionRating(@RequestParam(value="tripId") int tripId,
			@RequestParam(value="discussionId") int discussionId,
			@RequestParam(value="rating") int rating){
		
		try {
			
			discussionService.updateorAddDiscussionRating(tripId, discussionId, rating);			
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
		
	}
	//convert image to base64String
	private String getImageEncodedString(String imagePath) throws Exception{
	
		File file=new File(imagePath);
		byte[] b=new byte[(int) file.length()];
		FileInputStream fileInputStream=new FileInputStream(file);
		fileInputStream.read(b);
		return Base64.encodeBase64String(b);
	}
}
