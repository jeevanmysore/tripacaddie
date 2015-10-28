/**
 * 
 */

package com.tripcaddie.frontend.trip.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tripcaddie.frontend.trip.service.WallService;

@Controller
public class WallController {
	
	private static Logger logger=Logger.getLogger(WallController.class);
	
	@Resource(name="wallService")
	private WallService wallService;
		
	@RequestMapping(value="writeOnWall.do",method=RequestMethod.POST)
	public @ResponseBody
	String writeOnWall(@RequestParam(value="tripId") int tripId,
			@RequestParam(value="message") String message){
		
		try {
			wallService.writeWall(tripId, message);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
		
	}
	
	@RequestMapping(value="writeComment.do",method=RequestMethod.POST)
	public @ResponseBody
	String writeComment(@RequestParam(value="wallId") int wallId,
			@RequestParam(value="comment") String comment){
		
		try {
			wallService.writeWallComment(wallId, comment);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
		
	}
	
	@RequestMapping(value="deleteWallPost.do",method=RequestMethod.POST)
	public @ResponseBody
	String deleteWallPost(@RequestParam(value="wallId") int wallId){
		
		try {
			wallService.deleteWall(wallId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
		
	}
	
	@RequestMapping(value="deleteComment.do",method=RequestMethod.POST)
	public @ResponseBody
	String deleteComment(@RequestParam(value="commentId") int commentId){
		
		try {
			wallService.deleteComment(commentId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
		
	}
	
	
	
	
}
