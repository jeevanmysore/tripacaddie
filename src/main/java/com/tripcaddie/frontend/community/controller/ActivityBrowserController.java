package com.tripcaddie.frontend.community.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tripcaddie.frontend.community.service.LongLatService;

@Controller
@RequestMapping("/browse/*")
public class ActivityBrowserController {
	
	private static Logger logger = Logger.getLogger(ActivityBrowserController.class);

	@RequestMapping(value = "activitybrowser.do", method = RequestMethod.GET)
	public String browseActivity(@RequestParam(value="DSearch" ,required=false) String DSearch,Model model) {
		try {
			model.addAttribute("lati", 0);
			model.addAttribute("longi", 0);
			if(DSearch==null){
				model.addAttribute("map", false);
			}else{
				
				model.addAttribute("map", true);
				
				if(!DSearch.isEmpty()){
					LongLatService latService=new LongLatService();
					Double[] position=latService.getLongitudeLatitude(DSearch);
					if(position[0]!=0){
						model.addAttribute("lati", position[0]);
						model.addAttribute("longi", position[1]);
					}
				}
			}
			
			return "activity/activityBrowser";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "error";
		}

	}
	
}
