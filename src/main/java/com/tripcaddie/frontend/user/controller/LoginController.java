package com.tripcaddie.frontend.user.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tripcaddie.common.util.EmailSender;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Controller
public class LoginController{
	
	private static Logger logger=Logger.getLogger(LoginController.class);

	@Resource(name="emailSender")
	private EmailSender emailSender;
	@Resource(name="loginService")
	private LoginService loginService;
		
	/*@RequestMapping(value="/login.do" , method=RequestMethod.GET)
	public ModelAndView loginToTripCaddie(HttpServletRequest request,HttpServletResponse response){
		
		HttpSession session=request.getSession();
		
		String username;
		try {
			username = loginService.getAuthenticatedUser();
			session.setAttribute("username", username);
			loginService.updateLoginDetails();
			ArrayList<TripDto> tripDtos=tripService.getTripsOfUser();
			return new ModelAndView("userHome","trips",tripDtos);
		} catch (Exception exp) {
			exp.printStackTrace();
			logger.info(exp.getMessage()+" "+exp.getCause());
			return new ModelAndView("error");
		}
			
		try {
			if(loginService.storeUserSessioninTripCaddie(session)){
				Cookie userCookie=new Cookie("SESS21768931420c9936aef3f4909c3229e8", session.getId());
				userCookie.setPath("/");
				userCookie.setMaxAge(20000);
				userCookie.setDomain(".kns.com");
				response.addCookie(userCookie);
				loginService.updateLoginDetails();
				//return "userHomeDemo";
				try {
					response.sendRedirect("http://www.kns.com/tripcaddie/user-home");
				} catch (IOException e) {
					logger.info(e.getMessage()+" "+e.getCause());
				}
			}else{
				System.out.println("Fails in login tripcaddie");
			}
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		}
	
	}*/
	
	@RequestMapping(value="/forgetPassword.do", method=RequestMethod.GET)
	public @ResponseBody
	String forgetPassword(@RequestParam(value="email") String email,
			HttpServletRequest request) {
		Assert.hasText(email);
		
		TripcaddieUserDto userDto;
		try {
			userDto = loginService.getUserByEmail(email);
		
			if(userDto != null){
				StringBuffer url=request.getRequestURL();
				String urlPath[]=url.toString().split("/");
				String contextPath=urlPath[0]+"/"+urlPath[1]+"/"+urlPath[2]+"/"+urlPath[3];
				
				emailSender.sendEmail(userDto,"forget Password Link","forgetPasswordTemplate",contextPath);
				return "success";
			}else{
				return "fail";
			}
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		//	e.printStackTrace();
		}
		return "fail";
	}
	
	@RequestMapping(value="resetPassword.do")
	public String resetPassword(@RequestParam(value="ID") String encryptedString,
			HttpServletRequest request){
		
		String result=new String(Base64.decodeBase64(encryptedString.getBytes()));
		String username=result.substring(0, result.indexOf(','));
		HttpSession session=request.getSession();
		session.setAttribute("username", username);
		
		return "resetPassword";
	}
	
	@RequestMapping(value="/updatePassword.do",method=RequestMethod.POST)
	public String updatePassword(@RequestParam(value="passwordtxt") String password,
			HttpServletRequest request){
		
		HttpSession session=request.getSession();
		String username=(String)session.getAttribute("username");
		try {
			loginService.updatePassword(password, username);
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			//e.printStackTrace();
		}
		return "login";
	}
	
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session=request.getSession();
		
		logger.info(session.getAttribute("username") + " is logged out");
		session.invalidate();
		try {
			response.sendRedirect("./");
		} catch (IOException e) {
			logger.info(e.getMessage()+" "+e.getCause());
		}
	}
	
	@RequestMapping(value="confirmation.do")
	public void activateAccount(@RequestParam(value="ID") String encryptedString){
		
		//Decrypeted string has username and email in this format like username,email
		String result=new String(Base64.decodeBase64(encryptedString.getBytes()));
		String username=result.substring(0, result.indexOf(','));
		System.out.println("username:"+username);
		try {
			loginService.activate(username);
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		}		
	}
	
	@RequestMapping(value="tellAFriend.do",method=RequestMethod.POST)
	public void tellAFriend(@RequestParam(value="senderName") String username,
			@RequestParam(value="recipentName") String recipentName,
			@RequestParam(value="recipentEmail") String recipentEmail,
			HttpServletRequest request){
		Assert.hasText(username);
		Assert.hasText(recipentName);
		Assert.hasText(recipentEmail);
		
		try {
			StringBuffer url=request.getRequestURL();
			String urlPath[]=url.toString().split("/");
			String contextPath=urlPath[0]+"/"+urlPath[1]+"/"+urlPath[2]+request.getRequestURI();
			
			emailSender.sendEmail(username, recipentName, recipentEmail, "Checkout TripCaddie.com!!!", "tellFriend",contextPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="error.do",method=RequestMethod.GET)
	public ModelAndView errorPage(){
		
		return new ModelAndView("error");
		
	}
	
	
}
