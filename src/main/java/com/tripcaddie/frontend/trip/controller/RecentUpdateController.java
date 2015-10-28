package com.tripcaddie.frontend.trip.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.spring.web.servlet.view.JsonView;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tripcaddie.frontend.trip.dto.RecentUpdateDto;
import com.tripcaddie.frontend.trip.service.RecentUpdateService;

@Controller
public class RecentUpdateController {

	private static Logger logger=Logger.getLogger(RecentUpdateController.class);
	
	@Resource(name="recentUpdateService")
	private RecentUpdateService recentUpdateService;
	@Resource
	private JsonView jsonView;
	
	@RequestMapping(value="getRecentUpdates.do",method=RequestMethod.POST)
	public ModelAndView getRecentUpdates(@RequestParam(value="order") String order,
			@RequestParam(value="type") String type,
			@RequestParam(value="tripId") int tripId) {
		
		System.out.println("--------------------Recent Updates------------------------");
		System.out.println("Type:"+type);
		try {
			ArrayList<RecentUpdateDto> recentUpdates=new ArrayList<RecentUpdateDto>(); 
			
			if(type.equalsIgnoreCase("who")){
				recentUpdates = recentUpdateService.getrecentUpdateByName(tripId, order);
			}else if (type.equalsIgnoreCase("what")) {
				recentUpdates = recentUpdateService.getrecentUpdateByType(tripId, order);
			}else {
				recentUpdates = recentUpdateService.getrecentUpdateByDate(tripId, order);
			}			
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("recentUpdates", recentUpdates);
			
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setView(jsonView);
			modelAndView.addObject("jsonObject", requestJsonObject);
			
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return new ModelAndView("error");
		}
		
	}
	
}
