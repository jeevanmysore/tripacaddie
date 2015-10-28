package com.tripcaddie.frontend.footer.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tripcaddie.frontend.footer.service.FooterService;

@Controller
@RequestMapping("/footer/*")
public class FooterController {

	private static Logger logger = Logger.getLogger(FooterController.class);

	@Resource(name = "footerService")
	private FooterService footerService;

	@RequestMapping(value = "sendReportBug.do", method = RequestMethod.POST)
	public @ResponseBody
	String sendReportBug(@RequestParam("email") String email,
			@RequestParam("issue") String issue) {
		try{
		
		footerService.sendReportBug(email, issue);
		return "success";
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return "error";
		}

	}

	@RequestMapping(value = "sendSuggestFeature.do", method = RequestMethod.POST)
	public @ResponseBody
	String sendSuggestFeature(@RequestParam("email") String email,
			@RequestParam("idea") String idea) {
		try{
			footerService.sendSuggestFeature(email, idea);
			return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				return "error";
			}


	}

	@RequestMapping(value = "sendTipYourCaddie.do", method = RequestMethod.POST)
	public @ResponseBody
	String sendTipYourCaddie(@RequestParam("email") String email,
			@RequestParam("feedback") String feedback) {
		try{
			footerService.sendTipYourCaddie(email, feedback);
			return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				return "error";
			}


	}
}
