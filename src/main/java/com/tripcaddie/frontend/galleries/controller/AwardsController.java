package com.tripcaddie.frontend.galleries.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.frontend.galleries.dto.AwardCommentDto;
import com.tripcaddie.frontend.galleries.dto.AwardsDto;
import com.tripcaddie.frontend.galleries.service.GalleriesService;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;
import com.tripcaddie.frontend.trip.service.TripService;

@Controller
@RequestMapping("/trip/*")
public class AwardsController {

	private static Logger logger = Logger.getLogger(AwardsController.class);

	@Resource(name = "galleriesService")
	private GalleriesService galleriesService;

	@Resource(name = "tripService")
	private TripService tripService;

	@Resource(name = "imagePath")
	private ImagePath imagePath;
	
	
	
	@RequestMapping(value = "allAwards.do", method = RequestMethod.GET)
	public String getAllAwards(Model model) {
		try {

			List<TripDto> trips = tripService.getTripsOfUser();

			for (TripDto trip : trips) {
				trip.setGalleriespresent(false);
				// get Imageasbase64
				if (trip.getImagePath() != null) {

					trip.setImagebase64(getImageEncodedString(imagePath.getTripimagepathperuser()+trip
							.getImagePath()));
				}
				List<AwardsDto> awardsDtos = new ArrayList<AwardsDto>();
				List<AwardsDto> awardsDtoss=galleriesService.getAwardsWon(trip.getTripId());

				for (AwardsDto awardsDto : awardsDtoss) {
					
                        String imageUrl = imagePath.getTripAwardpath()+awardsDto.getImagename();
                        awardsDto.setAwardImgbas64(getImageEncodedString(imageUrl));
												
                        awardsDtos.add(awardsDto);

					
				}
				
				if(awardsDtos!=null && !awardsDtos.isEmpty()){
					trip.setGalleriespresent(true);
					trip.setAwards(awardsDtos);
				}

			}

			
			model.addAttribute("trips", trips);

			return "allAwards";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e);
			return "error";
		}

	}
	
	@RequestMapping(value = "getAwardsWon.do", method = RequestMethod.GET)
	public String getAwardsWon(@RequestParam(value = "tripId") int tripId,
			Model model) {
		try {
			TripDto tripDto = tripService.getTrip(tripId);
			if (tripDto.getImagePath() != null) {
				tripDto.setImagebase64(getImageEncodedString(imagePath.getTripimagepathperuser()+tripDto
						.getImagePath()));
			}

			model.addAttribute("trip", tripDto);
			
			List<AwardsDto> awardsDtos=galleriesService.getAwardsWon(tripId);
			for(AwardsDto awardsDto:awardsDtos){
				awardsDto.setAwardImgbas64(getImageEncodedString(imagePath.getTripAwardpath()+awardsDto.getImagename()));
			}
			model.addAttribute("awards", awardsDtos);
			setCreateAwardModel(tripId, model, tripDto);

			return "trip/awardsWon";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "error";
		}

	}
	
	
	@RequestMapping(value = "displayAward.do", method = RequestMethod.GET)
	public String DisplayAward(@RequestParam(value = "tripId") int tripId,@RequestParam(value = "awardId") int awardId,
			Model model) {
		try {
			TripDto tripDto = tripService.getTrip(tripId);
			if (tripDto.getImagePath() != null) {
				tripDto.setImagebase64(getImageEncodedString(imagePath.getTripimagepathperuser()+tripDto
						.getImagePath()));
			}

			model.addAttribute("trip", tripDto);
			List<AwardsDto> awardsDtos=galleriesService.getAwardsWon(tripId);
			AwardsDto awards=null;
			for(AwardsDto awardsDto:awardsDtos){
				if(awardsDto.getAwardid()==awardId){
				awardsDto.setAwardImgbas64(getImageEncodedString(imagePath.getTripAwardpath()+awardsDto.getImagename()));
				awards=awardsDto;
				}
			}
			
			List<AwardCommentDto> awardCommentDtos=galleriesService.getAwardComment(awardId);
			
			for (AwardCommentDto awardCommentDto : awardCommentDtos) {
				if (awardCommentDto.getTripMemberDto().getTripCaddieUserDto()
						.getImageUrl() != null
						&& !awardCommentDto.getTripMemberDto()
								.getTripCaddieUserDto().getImageUrl().isEmpty())
					awardCommentDto
							.getTripMemberDto()
							.getTripCaddieUserDto()
							.setImageBase64(
									getImageEncodedString(imagePath
											.getTripimagepathperuser()
											+ awardCommentDto
													.getTripMemberDto()
													.getTripCaddieUserDto()
													.getImageUrl()));
			}
			
			model.addAttribute("awards", awards);
			model.addAttribute("awardComment", awardCommentDtos);
			

			return "trip/awardDisplay";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "error";
		}

	}
	
		
	

	private void setCreateAwardModel(int tripId, Model model, TripDto tripDto)
			throws Exception {
		// Model for creating new award
		AwardsDto awardsDto = new AwardsDto();
		awardsDto.setSlverimginbas64(getImageEncodedString(imagePath
				.getTripAwardpath() + "Silver.jpg"));
		awardsDto.setGoldimginbas64(getImageEncodedString(imagePath
				.getTripAwardpath() + "Gold.jpg"));
		awardsDto.setBronzimginbas64(getImageEncodedString(imagePath
				.getTripAwardpath() + "Bronze.jpg"));
		if (tripDto.getStartDate().getTime().after(new Date())) {
			awardsDto.setOnlycreatepol(true);
		} else {
			awardsDto.setOnlycreatepol(false);
		}
		// to get trip member
		List<TripMemberDto> members = tripService
				.getTripMemberswithoutCurrentmember(tripId);

		model.addAttribute("newaward", awardsDto);
		model.addAttribute("tripmembers", members);
	}

	private String getImageEncodedString(String imagePath) throws Exception {

		File file = new File(imagePath);
		byte[] b = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(b);
		return Base64.encodeBase64String(b);
	}

	@RequestMapping(value = "createAward.do", method = RequestMethod.POST)
	public void CreateAward(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {

			// getMax award id
			int maxId = galleriesService.getMaxAwardId() + 1;
			// default image name is award
			String imagename = "award.jpg";
			MultipartHttpServletRequest request2 = (MultipartHttpServletRequest) request;
			if (!request.getParameter("selectImage").equalsIgnoreCase("-1")) {
				imagename = new StringBuffer(
						request.getParameter("selectImage")).append(".jpg")
						.toString();
			} else if (request2.getFile("imageupload").getSize() != 0) {
				imagename = request2.getFile("imageupload")
						.getOriginalFilename();
				imagename = imagename.substring(0, imagename.indexOf("."))
						+ "_"
						+ maxId
						+ imagename.substring(imagename.indexOf("."),
								imagename.length());

				File file = new File(imagePath.getTripAwardpath() + imagename);
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				byte[] image = request2.getFile("imageupload").getBytes();
				fileOutputStream.write(image);

			}
			// insert for create poll
			if (request.getParameter("awardtypeId").equalsIgnoreCase(
					"createpoll")) {
				galleriesService.createPoll(request.getParameter("title"),
						imagename, request.getParameter("EndPolldate"),
						request.getParameter("tripId"));
			}
			// insert for award now
			else if (request.getParameter("awardtypeId").equalsIgnoreCase(
					"awardnow")) {
				int awardId=galleriesService.insertAwardnow(request.getParameter("title"),
						imagename,request.getParameter("tripId"));
				galleriesService.insertNomineforAwardNow(awardId, request.getParameter("tripmember"));
			}
			response.sendRedirect("/tripcaddie/trip/getAwardsWon.do?tripId="
					+ request.getParameter("tripId"));
			
		} catch (Exception e) {
			
			logger.error(e);
			e.printStackTrace();
			

		}

	}
	
	
	
	
	@RequestMapping(value="addAwardComment.do")
	public @ResponseBody
	String addComment(@RequestParam(value="awardid") int awardid,
			@RequestParam(value="comment") String comment ,@RequestParam(value="date") String datetime) {
		try {
			galleriesService.addCommentforAward(awardid, comment ,datetime);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	@RequestMapping(value="deleteAwardComment.do",method=RequestMethod.POST)
	public @ResponseBody
	String deleteComment(@RequestParam(value="commentId") int commentID) {
		try {
			
			galleriesService.deleteCommentforAward(commentID);
			return "success";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	@RequestMapping(value="editAwardComment.do",method=RequestMethod.POST)
	public @ResponseBody
	String editComment(@RequestParam(value="commentId") int commentID,
			@RequestParam(value="comment") String comment) {
		try {
			galleriesService.editAwardComment(commentID, comment);
			
			return "success";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	@RequestMapping(value="deleteAward.do",method=RequestMethod.POST)
	public	@ResponseBody
	String deleteAward(@RequestParam(value="awardId") int awardId,
			@RequestParam(value="tripId") int tripId) {
		try {
			galleriesService.deleteAward(awardId, tripId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			return "error";
		}
	}
	
	
	
	@RequestMapping(value = "getAwardsPending.do", method = RequestMethod.GET)
	public String getAwardsPending(@RequestParam(value = "tripId") int tripId,
			Model model) {
		try {
			TripDto tripDto = tripService.getTrip(tripId);
			if (tripDto.getImagePath() != null) {
				tripDto.setImagebase64(getImageEncodedString(imagePath.getTripimagepathperuser()+tripDto
						.getImagePath()));
			}

			model.addAttribute("trip", tripDto);
			
			List<AwardsDto> awardsDtos=galleriesService.getAwardsPending(tripId);
			for(AwardsDto awardsDto:awardsDtos){
				awardsDto.setAwardImgbas64(getImageEncodedString(imagePath.getTripAwardpath()+awardsDto.getImagename()));
			}
			model.addAttribute("awards", awardsDtos);
			setCreateAwardModel(tripId, model, tripDto);

			return "trip/awardsPending";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "error";
		}

	}
	
	
	
	
	@RequestMapping(value = "voteNow.do", method = RequestMethod.POST)
	public @ResponseBody String VoteNow(@RequestParam("tripId")String tripId,@RequestParam("awardId")String awardId, @RequestParam("tripmember")String nomineeId,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			//insert into vote and votecount
			galleriesService.insertAwardsVote(tripId, awardId,nomineeId);
			
			return "success";
		} catch (Exception e) {
			
			logger.error(e);
			e.printStackTrace();
			return "error";
		}
		

	}
	
}
