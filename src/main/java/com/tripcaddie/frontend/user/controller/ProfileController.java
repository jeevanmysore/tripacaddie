package com.tripcaddie.frontend.user.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.spring.web.servlet.view.JsonView;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;
import com.tripcaddie.frontend.user.service.ProfileService;

@Controller
public class ProfileController {

	private static Logger logger = Logger.getLogger(ProfileController.class);

	@Resource(name = "profileService")
	private ProfileService profileService;
	@Resource(name = "loginService")
	private LoginService loginService;
	@Resource(name = "imagePath")
	private ImagePath imagepath;
	@Resource(name="jsonView")
	private JsonView jsonView;

	@RequestMapping(value = "/user/profile.do", method = RequestMethod.GET)
	public String getProfileDetails(Model model) {
		try {
			String username = loginService.getAuthenticatedUser();
			TripcaddieUserDto user = loginService.getUserByUserName(username);
			if(user.getImageUrl()!=null && !user.getImageUrl().isEmpty())
				user.setImageBase64(getImageEncodedString(imagepath.getTripimagepathperuser()+user.getImageUrl()));
			model.addAttribute("userDetails", user);
			return "user/profile";
		} catch (Exception e) {
			logger.error("profile controller", e);
			return "error";
		}

	}

	@RequestMapping(value = "/user/profile.do", method = RequestMethod.POST)
	public ModelAndView getProfileDetails() {
		
		try {
			String username = loginService.getAuthenticatedUser();
			TripcaddieUserDto user = loginService.getUserByUserName(username);
			if(user.getImageUrl()!=null && !user.getImageUrl().isEmpty())
				user.setImageBase64(getImageEncodedString(imagepath.getTripimagepathperuser()+user.getImageUrl()));
			
			JSONObject requestJsonObject=new JSONObject();
			requestJsonObject.accumulate("user", user);
		
			ModelAndView model=new ModelAndView();
			model.setView(jsonView);
			model.addObject("json", requestJsonObject);
			
			return model;
		} catch (Exception e) {
			logger.error("profile controller", e);
			return new ModelAndView("error");
		}

	}
	
	@RequestMapping(value = "/user/profile/editAccount.do", method = RequestMethod.GET)
	public String editAccountProfile(Model model) {
		try {
			String username = loginService.getAuthenticatedUser();
			TripcaddieUserDto user = loginService.getUserByUserName(username);
			if(user.getImageUrl()!=null && !user.getImageUrl().isEmpty())
				user.setImageBase64(getImageEncodedString(imagepath.getTripimagepathperuser()+user.getImageUrl()));
			model.addAttribute("userDetails", user);
			return "user/editAccountProfile";
		} catch (Exception e) {
			logger.error("profile controller", e);
			return "error";
		}

	}

	@RequestMapping(value = "/user/profile/editAccount.do", method = RequestMethod.POST)
	public String saveAccountProfile(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			String filename = multipartHttpServletRequest.getFile("fileupload")
					.getOriginalFilename();
			if (!filename.isEmpty()) {

				File file = new File(imagepath.getTripimagepathperuser() + filename);
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				byte[] image = multipartHttpServletRequest
						.getFile("fileupload").getBytes();
				fileOutputStream.write(image);
			}

			profileService.updateAccountProfile(request.getParameter("email"),
					request.getParameter("password"), filename);

			TripcaddieUserDto user = loginService.getUserByUserName(request
					.getParameter("username"));
			if(user.getImageUrl()!=null && !user.getImageUrl().isEmpty())
				user.setImageBase64(getImageEncodedString(imagepath.getTripimagepathperuser()+user.getImageUrl()));
			model.addAttribute("userDetails", user);
			return "user/profile";
		} catch (Exception e) {
			logger.error("profile controller", e);
			return "error";
		}

	}

	@RequestMapping(value = "/user/profile/editProfile.do", method = RequestMethod.GET)
	public String editPersonalProfile(Model model) {
		try {
			String username = loginService.getAuthenticatedUser();
			TripcaddieUserDto user = loginService.getUserByUserName(username);
			model.addAttribute("userDetails", user);
			
			return "user/editPersonalProfile";
		} catch (Exception e) {
			logger.error("profile controller", e);
			return "error";
		}

	}

	@RequestMapping(value = "/user/profile/editProfile.do", method = RequestMethod.POST)
	public String savePersonalProfile(HttpServletRequest request, Model model) {
		try {

			profileService.updateCurrentUser(request.getParameter("firstname"),
					request.getParameter("lastname"),
					request.getParameter("nickname"),
					request.getParameter("phonenumber"),
					request.getParameter("city"), "1",
					request.getParameter("golfHandicap"),
					request.getParameter("averageScore"),
					request.getParameter("date"),
					request.getParameter("notify_tripleader"),
					request.getParameter("notify_tripanyone"),
					request.getParameter("notify_acceptedinv"),
					request.getParameter("notify_rejectedinv"),
					request.getParameter("notify_awardcreated"),
					request.getParameter("notify_sendemail"));
			String username = loginService.getAuthenticatedUser();
			TripcaddieUserDto user = loginService.getUserByUserName(username);
			if(user.getImageUrl()!=null && !user.getImageUrl().isEmpty())
				user.setImageBase64(getImageEncodedString(imagepath.getTripimagepathperuser()+user.getImageUrl()));
			model.addAttribute("userDetails", user);
			
			return "user/profile";
		} catch (Exception e) {
			logger.error("profile controller", e);
			return "error";
		}

	}

	private static String getImageEncodedString(String imagePath) throws Exception {

		File file = new File(imagePath);
		byte[] b = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(b);
		return Base64.encodeBase64String(b);
	}
}
