package com.tripcaddie.frontend.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tripcaddie.common.util.EmailSender;
import com.tripcaddie.frontend.user.service.LoginService;
import com.tripcaddie.frontend.user.service.RegistrationService;

@Controller
public class RegistrationController {

	private static Logger logger = Logger
			.getLogger(RegistrationController.class);

	@Resource(name="emailSender")
	private EmailSender emailSender;
	@Resource(name="loginService") 
	private LoginService loginService;
	@Resource(name = "registrationService")
	private RegistrationService registrationService;

	// For redirecting registration page
	@RequestMapping(value = "/registration.do", method = RequestMethod.GET)
	public String registrationPage() {
		// System.out.println("Here in registration");
		return "registration";
	}

	// Need to be done in better way
	// Validate captcha before registration
	@RequestMapping(value = "/reCaptchaValidation.do", method = RequestMethod.POST)
	public @ResponseBody
	String captchaValidation(
			HttpServletRequest request,
			@RequestParam(value = "username") String userName,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "fname") String firstName,
			@RequestParam(value = "lname") String lastName,
			@RequestParam(value = "recaptcha_challenge_field") String challengeField,
			@RequestParam(value = "recaptcha_response_field") String responseField) {

		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey("6Lcm7tUSAAAAAJi0BxzsmQEKuCqJqS9YdcBZu1V4");

		String remoteAddr = "www.kns.com";
		System.out.println("Remote addr:" + remoteAddr);
		System.out.println("challengeField:" + challengeField);
		System.out.println("responseField:" + responseField);
		ReCaptchaResponse captchaResponse = reCaptcha.checkAnswer(remoteAddr,
				challengeField, responseField);
		System.out.println("In captcha validation: "
				+ captchaResponse.isValid());
		if (captchaResponse.isValid())
			return "success";
		else
			return "fail";
	}

	// For registring user
	@RequestMapping(value = "/registerUser.do", method = RequestMethod.POST)
	public String registerUser(
			@RequestParam(value = "username") String userName,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "fname") String firstName,
			@RequestParam(value = "lname") String lastName,
			@RequestParam(value = "recaptcha_challenge_field") String challengeField,
			@RequestParam(value = "recaptcha_response_field") String responseField,
			HttpServletResponse response, HttpServletRequest request,
			Model model) {

		try {
			
			registrationService.registerUser(userName, password, email,
					firstName, lastName);
			StringBuffer url=request.getRequestURL();
			String urlPath[]=url.toString().split("/");
			System.out.println("url:"+url);
			String contextPath=urlPath[0]+"/"+urlPath[1]+"/"+urlPath[2]+"/"+urlPath[3];
			
			try{
				emailSender.sendEmail(email,userName,"Confirmation on account registration","confirmationTemplate",contextPath);
			}catch(Exception e){
				logger.error(e);
			}
			
			return "successfulRegistration";
			
		} catch (Exception e) {
			logger.info(e.getMessage() + " " + e.getCause());
			// e.printStackTrace();
		}
		return null;
	}

	// Check existence of user name
	@RequestMapping(value = "/userExistance.do", method = RequestMethod.POST)
	public @ResponseBody
	String isExistUser(@RequestParam(value = "username") String username) {

		boolean flag;
		try {
			flag = registrationService.isExistUser(username);
			if (flag) {
				return "success";
			} else {
				return "failure";
			}
		} catch (Exception e) {
			logger.info(e.getMessage() + " " + e.getCause());
			// e.printStackTrace();
		}
		return "failure";
	}

	// check email existence
	@RequestMapping(value = "/emailExistance.do", method = RequestMethod.POST)
	public @ResponseBody
	String isExistEmail(@RequestParam(value = "email") String email) {

		boolean flag;
		try {
			flag = registrationService.isExistEmail(email);
			if (flag) {
				return "success";
			} else {
				return "failure";
			}
		} catch (Exception e) {
			logger.info(e.getMessage() + " " + e.getCause());
		}
		return "failure";
	}

	public void validate() {
		logger.info("in confirmation hash");
	}
}
