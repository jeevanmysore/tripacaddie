package com.tripcaddie.frontend.trip.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.common.util.EmailSender;
import com.tripcaddie.frontend.courses.dto.GolfCourseDto;
import com.tripcaddie.frontend.courses.service.DestinationSearchService;
import com.tripcaddie.frontend.galleries.dto.AwardsDto;
import com.tripcaddie.frontend.galleries.service.GalleriesService;
import com.tripcaddie.frontend.itinerary.dto.ActivityDto;
import com.tripcaddie.frontend.itinerary.service.ActivityService;
import com.tripcaddie.frontend.trip.dto.DiscussionDto;
import com.tripcaddie.frontend.trip.dto.PollQuestionsDto;
import com.tripcaddie.frontend.trip.dto.RecentUpdateDto;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripLeaderDelegationDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;
import com.tripcaddie.frontend.trip.dto.WallCommentDto;
import com.tripcaddie.frontend.trip.dto.WallDto;
import com.tripcaddie.frontend.trip.service.DiscussionService;
import com.tripcaddie.frontend.trip.service.PollService;
import com.tripcaddie.frontend.trip.service.RecentUpdateService;
import com.tripcaddie.frontend.trip.service.TripService;
import com.tripcaddie.frontend.trip.service.WallService;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Controller
public class TripController {

	private static Logger logger=Logger.getLogger(TripController.class);
	
	@Resource(name="wallService")
	private WallService wallService;
	@Resource(name="tripService")
	private TripService tripService;
	@Resource(name="destinationSearch")
	private DestinationSearchService destinationSearchService;
	@Resource(name="emailSender")
	private EmailSender emailSender;
	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name = "imagePath")
	private ImagePath imagepath;
	@Resource(name="jsonView")
	private JsonView jsonView;
	@Resource(name="discussionService")
	private DiscussionService discussionService;
	@Resource(name="pollService")
	private PollService pollService;
	@Resource(name="recentUpdateService")
	private RecentUpdateService recentUpdateService;
	@Resource(name="galleriesService")
	private GalleriesService galleriesService;
   
	@Resource(name = "activityService")
	private ActivityService activityService;

	@RequestMapping(value = "createTrip.do", method = RequestMethod.GET)
	public ModelAndView displayCreateTripPage(
			@RequestParam(value = "courseId") int courseId,
			HttpServletRequest request) {

		GolfCourseDto courseDto;
		try {
			loginService.updateLoginDetails(request.getSession());
			courseDto = destinationSearchService.getClubDetails(courseId,
					request.getSession());
			return new ModelAndView("createTrip", "golfCourse", courseDto);
		} catch (Exception e) {
			logger.info(e.getMessage() + " " + e.getCause());
			return new ModelAndView("error");
		}

	}

	@RequestMapping(value = "/createTrip.do", method = RequestMethod.POST)
	public void createTrip(HttpServletRequest request,
			HttpServletResponse response) {

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		String name = "no-image.gif";
		String imageBase64 = null;
		try {
			String tripName = request.getParameter("tripName");
			String courseId = request.getParameter("courseId");
			String startDate = request.getParameter("startDate");
			String message = request.getParameter("message");
			String endDate = request.getParameter("endDate");
			String promoCode = request.getParameter("promoCode");
			String annualTrip = request.getParameter("annualTrip");
			// store Image
			String filename = multipartHttpServletRequest.getFile("image")
					.getOriginalFilename();
			if (!filename.isEmpty()) {
				name=multipartHttpServletRequest.getFile("image")
						.getOriginalFilename();
				String path = imagepath.getTripimagepathperuser()
						+ multipartHttpServletRequest.getFile("image")
								.getOriginalFilename();
				File file = new File(path);
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				byte[] image = multipartHttpServletRequest.getFile("image")
						.getBytes();
				fileOutputStream.write(image);
			}
			Integer tripId = tripService.createTrip(tripName, courseId,
					startDate, message, endDate, promoCode, annualTrip, name);
			TripDto tripDto = tripService.getTrip(tripId);
			if (tripDto.getImagePath() != null) {
				imageBase64 = this
						.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			ArrayList<TripMemberDto> tripMemberDtos = tripService
					.getTripMembers(tripId);
			Map<String, Object> tripModel = new HashMap<String, Object>();
			tripModel.put("trip", tripDto);
			tripModel.put("image", imageBase64);
			tripModel.put("tripMembers", tripMemberDtos);

			String username = loginService.getAuthenticatedUser();
			TripcaddieUserDto tripcaddieUserDto = loginService
					.getUserByUserName(username);
			String subject = tripDto.getTripName()
					+ " created successfully at TripCaddie.com!";

			String template = "tripCreation";

			StringBuffer url = request.getRequestURL();
			String urlPath[] = url.toString().split("/");
			String contextPath = urlPath[0] + "/" + urlPath[1] + "/"
					+ urlPath[2] + "/" + urlPath[3] + "/getTrip.do?tripId="
					+ tripId;

			emailSender.sendEmail(tripcaddieUserDto, subject, template,
					contextPath);
			response.sendRedirect("./getTrip.do?tripId=" + tripId);

		} catch (Exception e) {
			logger.info(e.getMessage() + " " + e.getCause());
			e.printStackTrace();
			try {
				response.sendRedirect("/tripcaddie/error.do");
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.info(e1.getMessage() + " " + e1.getCause());
			}
		}

	}

	// done by Jeevan.
	@RequestMapping(value = "/editTrip.do", method = RequestMethod.GET)
	public ModelAndView editTrip(@RequestParam(value = "tripId") int tripId,
			HttpServletRequest request) {
		try {
			TripDto tripDto = tripService.getTrip(tripId);
			String imageBase64 = null;
			if (tripDto.getImagePath() != null) {
				imageBase64 = this
						.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			ArrayList<TripMemberDto> tripMemberDtos = tripService
					.getTripMembers(tripId);

			Map<String, Object> tripModel = new HashMap<String, Object>();
			if (tripDto.getImagePath().contains("no-image.gif")) {
				tripModel.put("noimage", true);
			} else {
				tripModel.put("noimage", false);
			}
			tripModel.put("trip", tripDto);
			tripModel.put("image", imageBase64);
			tripModel.put("tripMembers", tripMemberDtos);

			return new ModelAndView("editTrip", "tripMap", tripModel);
		} catch (Exception e) {
			logger.info(e.getMessage() + " " + e.getCause());
			e.printStackTrace();
			return new ModelAndView("error");
		}
	}

	@RequestMapping(value = "editTrip.do", method = RequestMethod.POST)
	public void updateTrip(HttpServletRequest request,
			HttpServletResponse response) {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		String path;
		//String imageBase64 = null;
		try {
			Integer tripId = Integer.parseInt(request.getParameter("tripId"));
			String tripName = request.getParameter("tripName");
			String courseId = request.getParameter("courseId");
			String startDate = request.getParameter("startDate");
			String message = request.getParameter("message");
			String endDate = request.getParameter("endDate");
			String promoCode = request.getParameter("promoCode");
			String annualTrip = request.getParameter("annualTrip");
			byte[] image = null;
			// store Image
			String filename = multipartHttpServletRequest.getFile("image")
					.getOriginalFilename();
			if (!filename.isEmpty()) {
				path = imagepath.getTripimagepathperuser()
						+ multipartHttpServletRequest.getFile("image")
								.getOriginalFilename();
				File file = new File(path);
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				image = multipartHttpServletRequest.getFile("image").getBytes();
				fileOutputStream.write(image);
			} else if (!request.getParameter("removeImg").isEmpty()
					&& request.getParameter("removeImg").equalsIgnoreCase(
							"removed")) {
				filename = "no-image.gif";
			} else {
				filename = null;
			}
			tripService.editTrip(tripId, tripName, courseId, startDate,
					message, endDate, promoCode, annualTrip, filename);
			// TripDto tripDto=tripService.getTrip(tripId);
			// imageBase64=this.getImageEncodedString(tripDto.getImagePath());

			// Setting Model Attributes.
			response.sendRedirect("/tripcaddie/getTrip.do?tripId="+tripId);
		} catch (Exception e) {
			logger.info(e.getMessage() + " " + e.getCause());
			e.printStackTrace();
			try {
				response.sendRedirect("/tripcaddie/error.do");
			} catch (IOException e1) {
				logger.info(e.getMessage() + " " + e.getCause());
				e1.printStackTrace();
			}
		}

	}

	@RequestMapping(value = "/tripExistance.do", method = RequestMethod.POST)
	public @ResponseBody
	String verifyTrip(@RequestParam(value = "tripName") String tripName,
			HttpServletRequest request) {
		try {
			String username = loginService.getAuthenticatedUser();
			System.out.println("username= " + username);
			System.out.println("TripName: " + tripName);
			boolean status = tripService.isExistTrip(tripName, username);

			if (status == true)
				return "success";
			else
				return "failure";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failure";
	}

	@RequestMapping(value = "userHome.do", method = RequestMethod.GET)
	public String getTrips(@RequestParam(value="order",required=false) String order,
			@RequestParam(value="type",required=false) String type, HttpServletRequest request,
			Model model) {

		try {
			loginService.updateLoginDetails(request.getSession());
			String userName = loginService.getAuthenticatedUser();

			ArrayList<TripDto> tripDtos = tripService.getTripsOfUser();
			TripcaddieUserDto userDto = loginService
					.getUserByUserName(userName);
			userDto.setImageBase64(getImageEncodedString(imagepath
					.getTripimagepathperuser() + userDto.getImageUrl()));
			ArrayList<TripMemberDto> invitations = tripService
					.getTripInvitation(userDto.getEmail());

			// get RecentUpdates for all Trips
			List<RecentUpdateDto> recentUpdateDtos = recentUpdateService
					.getrecentUpdateByDateAllTrips(tripDtos, "desc");

			if (order!=null &&order.equalsIgnoreCase("asc") && type!=null && type.equalsIgnoreCase("date")) {
                Collections.reverse(recentUpdateDtos);
			}

			if (type!=null  && type.equalsIgnoreCase("msg")) {

				recentUpdateDtos=recentUpdateService.getRecntUpdatAllTripByMsg(recentUpdateDtos);
				if(order!=null && order.equalsIgnoreCase("desc")){
					Collections.reverse(recentUpdateDtos);
				}
			}
			if (type!=null  && type.equalsIgnoreCase("name")) {
				recentUpdateDtos=recentUpdateService.getRecntUpdatAllTripByName(recentUpdateDtos);
				if( order!=null && order.equalsIgnoreCase("desc")){
					Collections.reverse(recentUpdateDtos);
				}
			}
			if (type!=null  && type.equalsIgnoreCase("trip")) {
				recentUpdateDtos=recentUpdateService.getRecntUpdatAllTripByTrip(recentUpdateDtos);
				if( order!=null && order.equalsIgnoreCase("desc")){
					Collections.reverse(recentUpdateDtos);
				}
			}

			model.addAttribute("invitations", invitations);
			model.addAttribute("user", userDto);
			model.addAttribute("trips", tripDtos);
			model.addAttribute("updates", recentUpdateDtos);

			return "userHome";
		} catch (Exception exp) {
			logger.info(exp.getMessage() + " " + exp.getCause());
			exp.printStackTrace();
		}
		return "error";
	}

	@RequestMapping(value = "getTrip.do", method = RequestMethod.GET)
	public ModelAndView getTrip(@RequestParam(value = "tripId") Integer tripId,@RequestParam(value="tab" ,required=false) String tab,
			HttpServletRequest request) {

			
		long diff=0;
		try {
			Map<String, Object> tripModel=new HashMap<String, Object>();
			if(tab!=null && !tab.isEmpty()){
				if(tab.equalsIgnoreCase("poll")){
					tripModel.put("polltab", true);
					tripModel.put("discussiontab", false);
					
				}
				else if(tab.equalsIgnoreCase("discussion")){
					tripModel.put("discussiontab", true);
					tripModel.put("polltab", false);
					
				}
			}
			loginService.updateLoginDetails(request.getSession());	
			TripDto tripDto=tripService.getTrip(tripId);
			String imageBase64=null;
			if(tripDto.getImagePath()!=null){
				imageBase64=this.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			
			ArrayList<TripMemberDto> tripMemberDtos=tripService.getTripMembers(tripId);
			ArrayList<ActivityDto> activityDtos=activityService.getActivities(tripId);
			ArrayList<WallDto> wallDtos=wallService.getWallContent(tripId);
			ArrayList<DiscussionDto> discussionDtos=discussionService.getDiscussions(tripId);
			
			for (ActivityDto activityDto : activityDtos) {
				diff=(activityDto.getActivityDate().getTimeInMillis()-tripDto.getStartDate().getTimeInMillis())/(1000*60*60*24);
				activityDto.setNoOfDays(diff+1);
			}
			
			
			//For wall
			for (WallDto wallDto : wallDtos) {
				if(wallDto.getTripMemberDto().getTripCaddieUserDto().getImageUrl() != null &&
						!wallDto.getTripMemberDto().getTripCaddieUserDto().getImageUrl().isEmpty()){
					
					wallDto
						.getTripMemberDto()
						.getTripCaddieUserDto()
						.setImageBase64(
								getImageEncodedString(imagepath.getTripimagepathperuser()+wallDto
												.getTripMemberDto().getTripCaddieUserDto().getImageUrl()));
				}
				for (WallCommentDto wallCommentDto : wallDto.getCommentDtos()) {
					if(wallCommentDto.getTripMemberDto().getTripCaddieUserDto().getImageUrl() != null
						&& !wallCommentDto.getTripMemberDto().getTripCaddieUserDto().getImageUrl().isEmpty()){
				
						wallCommentDto.getTripMemberDto().getTripCaddieUserDto().setImageBase64(
										getImageEncodedString(imagepath
										.getTripimagepathperuser()
										+wallCommentDto.getTripMemberDto().getTripCaddieUserDto().getImageUrl()));
					}
						
				}
			}
			
			ArrayList<PollQuestionsDto> pollQuestions=pollService.getPollQuestions(tripId);
			ArrayList<RecentUpdateDto> recentUpdateDtos=recentUpdateService.getrecentUpdateByDate(tripId, "desc");
			ArrayList<TripLeaderDelegationDto> delegationDtos=tripService.getTripLeaderDelegations();
			
			
			tripModel.put("activity", activityDtos);
			tripModel.put("trip", tripDto);
			tripModel.put("image", imageBase64);
			tripModel.put("tripMembers", tripMemberDtos);
			tripModel.put("walls", wallDtos);
			tripModel.put("discussions", discussionDtos);
			tripModel.put("poll", pollQuestions);
			tripModel.put("updates", recentUpdateDtos);
			tripModel.put("delegationTypes", delegationDtos);
			
			return new ModelAndView("tripHome","tripMap",tripModel);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return new ModelAndView("error");
		}
	}

	@RequestMapping(value = "deleteTrip.do", method = RequestMethod.POST)
	public @ResponseBody
	String deleteTrip(@RequestParam(value = "tripId") Integer tripId) {
		try {
			System.out.println("TripId:" + tripId);
			tripService.deleteTrip(tripId);
			return "success";
		} catch (Exception e) {
			logger.info(e.getMessage() + " " + e.getCause());
			return "failure";
		}
	}

	//Display Invitation page
	@RequestMapping(value = "invitation.do", method = RequestMethod.GET)
	public String getInvitationPage(@RequestParam(value = "tripId") int tripId,
			Model model) {
		System.out.println("tripId:" + tripId);
		try {
			TripDto tripDto = tripService.getTrip(tripId);
			String imageBase64 = null;
			if (tripDto.getImagePath() != null) {
				imageBase64 = this
						.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			model.addAttribute("trip", tripDto);
			model.addAttribute("image", imageBase64);
			return "trip/invitation";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}

	//Send invitation to user. Ajax request
	@RequestMapping(value = "invitation.do", method = RequestMethod.POST)
	public @ResponseBody
	String sendInvitations(@RequestParam(value = "tripId") int tripId,
			@RequestParam(value = "message") String message,
			@RequestParam(value = "userRole") String role,
			@RequestParam(value = "emailArray") String[] emailAddress,
			HttpServletRequest request) {

		try {
			StringBuffer url = request.getRequestURL();
			String urlPath[] = url.toString().split("/");
			String contextPath = urlPath[0] + "/" + urlPath[1] + "/"
					+ urlPath[2] + request.getRequestURI();

			String username = loginService.getAuthenticatedUser();
			TripcaddieUserDto userDto = loginService
					.getUserByUserName(username);

			String subject = "ACTION REQUESTED: " + userDto.getFirstName()
					+ " " + userDto.getLastName()
					+ " has invited you to a Golf Trip on TripCaddie.com!";
			for (String email : emailAddress) {
				emailSender.sendEmail(email, userDto, subject,
						"tripInvitation", contextPath, message);
				tripService.addTripMember(tripId, email, role);
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}

	}

	@RequestMapping(value = "email.do", method = RequestMethod.GET)
	public String getEmailPage(@RequestParam(value = "tripId") int tripId,
			@RequestParam(value = "members") int memberId[], Model model) {

		try {
			TripDto tripDto = tripService.getTrip(tripId);
			String imageBase64 = null;
			if (tripDto.getImagePath() != null) {
				imageBase64 = this
						.getImageEncodedString(imagepath.getTripimagepathperuser()+tripDto.getImagePath());
			}
			ArrayList<TripMemberDto> tripMemberDtos = new ArrayList<TripMemberDto>();
			for (int i = 0; i < memberId.length; i++) {
				TripMemberDto memberDto = tripService
						.getTripMember(memberId[i]);
				tripMemberDtos.add(memberDto);
			}
			System.out.println("Length:" + memberId.length);
			System.out.println("Size:" + tripMemberDtos.size());
			model.addAttribute("trip", tripDto);
			model.addAttribute("image", imageBase64);
			model.addAttribute("tripmembers", tripMemberDtos);

			return "trip/email";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}

	}

	@RequestMapping(value = "email.do", method = RequestMethod.POST)
	public @ResponseBody
	String sendEmail(@RequestParam(value = "subject") String subject,
			@RequestParam(value = "body") String body,
			@RequestParam(value = "tripId") int tripId,
			@RequestParam(value = "email") String emailArray[],
			HttpServletRequest request) {

		try {
			String username = loginService.getAuthenticatedUser();
			TripcaddieUserDto userDto = loginService
					.getUserByUserName(username);
			TripDto tripDto = tripService.getTrip(tripId);

			StringBuffer url = request.getRequestURL();
			String urlPath[] = url.toString().split("/");
			String contextPath = urlPath[0] + "/" + urlPath[1] + "/"
					+ urlPath[2] + "/" + urlPath[3];
			contextPath = contextPath + "/getTrip.do?tripId="
					+ tripDto.getTripId();

			for (String email : emailArray) {
				emailSender.sendEmail(email, userDto, subject,
						"emailParticipant", contextPath, body, tripDto);
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}

	@RequestMapping(value = "revoke.do", method = RequestMethod.POST)
	public @ResponseBody
	String revoke(@RequestParam(value = "members") int[] members,
			@RequestParam(value = "tripId") int tripId,
			HttpServletRequest request) {

		try {
			TripDto trip = tripService.getTrip(tripId);
			TripcaddieUserDto userDto = loginService
					.getUserByUserName(loginService.getAuthenticatedUser());

			String emailAddress = null;

			StringBuffer url = request.getRequestURL();
			String urlPath[] = url.toString().split("/");
			String contextPath = urlPath[0] + "/" + urlPath[1] + "/"
					+ urlPath[2] + "/" + urlPath[3];

			for (int memberId : members) {
				TripMemberDto memberDto = tripService.getTripMember(memberId);
				if (memberDto.getTripCaddieUserDto().getUserId() == 0) {
					emailAddress = memberDto.getInvitedEmail();
				} else {
					emailAddress = memberDto.getTripCaddieUserDto().getEmail();
				}
				tripService.deleteTripMember(memberId);
				emailSender.sendEmail(emailAddress, userDto,
						"TripCaddie Participant Removal Update", "revoke",
						contextPath, "", trip);
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}

	// For Invitation

	@RequestMapping(value = "getInvites.do", method = RequestMethod.POST)
	public ModelAndView getInvites(@RequestParam(value = "email") String email) {

		try {

			ArrayList<TripMemberDto> invitations = tripService
					.getTripInvitation(email);

			JSONObject requestJsonObject = new JSONObject();
			requestJsonObject.accumulate("invitations", invitations);

			ModelAndView model = new ModelAndView();
			model.setView(jsonView);
			model.addObject("inviteDetails", requestJsonObject);

			return model;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e);
			return new ModelAndView("error");
		}
	}

	@RequestMapping(value = "acceptInvite.do", method = RequestMethod.POST)
	public @ResponseBody
	String acceptInvite(@RequestParam(value = "memberId") int tripMemberId) {

		try {
			System.out.println("MemberId:" + tripMemberId);
			tripService.acceptInvite(tripMemberId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}

	}

	@RequestMapping(value = "declineInvite.do", method = RequestMethod.POST)
	public @ResponseBody
	String declineInvite(@RequestParam(value = "memberId") int tripMemberId) {

		try {
			System.out.println("MemberId:" + tripMemberId);
			tripService.declineInvite(tripMemberId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e);
			return "error";
		}

	}
	
	
	//Trip Leader delegation
	@RequestMapping(value="leaderDelegation.do",method=RequestMethod.POST)
	public @ResponseBody
	String tripLeaderDelegation(@RequestParam(value="memberId") int memberId,
			@RequestParam(value="optionId") int optionId){
		
		try {
			
			tripService.changeTripMemberRole(optionId, memberId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e);
			return "error";
		}
		
		
	}
	
	@RequestMapping(value="displayProfilePopup.do",method=RequestMethod.POST)
	public
	ModelAndView displayProfilePopup(@RequestParam(value="userId") int userId){
		
		try {
			
			TripcaddieUserDto userDto=loginService.getUserBasedOnId(userId);
			
			if(userDto.getImageUrl()!=null && !userDto.getImageUrl().isEmpty())
				userDto.setImageBase64(getImageEncodedString(imagepath.getTripimagepathperuser()+userDto.getImageUrl()));
			
			ArrayList<AwardsDto> awardsDtos=galleriesService.getAwardsWonForUserId(userId);
			for (AwardsDto awardsDto : awardsDtos) {
				String imageUrl=imagepath.getTripAwardpath()+awardsDto.getImagename();
				awardsDto.setAwardImgbas64(getImageEncodedString(imageUrl));
			}
			
			JSONObject user=new JSONObject();
			user.accumulate("user", userDto);
			user.accumulate("awards", awardsDtos);
			
			ModelAndView view=new ModelAndView();
			view.setView(jsonView);
			view.addObject("tripcaddieUserObject", user);
			
			return view;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return new ModelAndView("error");
		}
		
	}

	// convert image to base64String
	private String getImageEncodedString(String imagePath) throws Exception {

		File file = new File(imagePath);
		byte[] b = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(b);
		return Base64.encodeBase64String(b);
	}
}
