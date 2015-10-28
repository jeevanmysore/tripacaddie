package com.tripcaddie.frontend.galleries.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.annotation.Resource;

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
import org.springframework.web.servlet.ModelAndView;

import com.tripcaddie.backend.itinerary.model.Activity;
import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.frontend.galleries.service.GalleriesService;
import com.tripcaddie.frontend.itinerary.dto.ActivityList;
import com.tripcaddie.frontend.itinerary.dto.RoundScoreDto;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;
import com.tripcaddie.frontend.trip.service.TripService;


@Controller
@RequestMapping("/trip/leaderboard/*")
public class LeaderBoardController {
	private static Logger logger = Logger.getLogger(StrudelController.class);

	@Resource(name = "galleriesService")
	private GalleriesService galleriesService;

	@Resource(name = "tripService")
	private TripService tripService;

	@Resource(name = "imagePath")
	private ImagePath imagePath;

	@Resource(name = "jsonView")
	private JsonView jsonView;

	@RequestMapping("getleaderboard.do")
	private String getLeaderBoard(
			@RequestParam("tripId") int tripId,
			@RequestParam(value = "userhome", required = false) String userhome,
			Model model) {
		try {
			if (userhome != null && !userhome.isEmpty()
					&& userhome.equalsIgnoreCase("1")) {
				List<TripDto> tripDtos = tripService.getTripsOfUser();
				model.addAttribute("trips", tripDtos);
				model.addAttribute("homePage", true);

			} else {
				model.addAttribute("homePage", false);
			}
			model.addAttribute("tripId", tripId);
			if (tripId != -1) {
				TripDto tripDto = tripService.getTrip(tripId);
				if (tripDto.getImagePath() != null) {
					tripDto.setImagebase64(getImageEncodedString(imagePath.getTripimagepathperuser()+tripDto
							.getImagePath()));
				}
				// service to display the leader board
				List<Object> objects = galleriesService
						.getLeaderboardbyTrip(tripId);
				List<Activity> activities = (List<Activity>) objects.get(0);
				List<TripMemberDto> memberDtos = (List<TripMemberDto>) objects
						.get(1);

				// memberDtos set position
				memberDtos = galleriesService.setPosition(memberDtos);

				model.addAttribute("members", memberDtos);
				model.addAttribute("trip", tripDto);
				model.addAttribute("activities", activities);
				model.addAttribute("activityList", new ActivityList());
				if (memberDtos != null && !memberDtos.isEmpty())
					model.addAttribute("roundsize", memberDtos.get(0)
							.getRoundSize());
			}
			return "trip/leaderboard";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return "error";
		}

	}

	private String getImageEncodedString(String imagePath) throws Exception {
		File file = new File(imagePath);
		byte[] b = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(b);
		return Base64.encodeBase64String(b);
	}

	@RequestMapping(value = "getRound.do", method = RequestMethod.POST)
	public ModelAndView getRound(@RequestParam("roundId") String roundId) {
		try{
		List<RoundScoreDto> scores = galleriesService.getActivities(Integer
				.parseInt(roundId));
		for(RoundScoreDto roundScoreDto:scores){
			roundScoreDto.setActivity(null);
			roundScoreDto.setMemberId(roundScoreDto.getMember().getMemberId());
			roundScoreDto.setMembername(roundScoreDto.getMember().getMembername());
			roundScoreDto.setMember(null);
		}
		JSONObject json = new JSONObject();
		json.accumulate("scores", scores);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(jsonView);
		modelAndView.addObject("json", json);
		return modelAndView;
		}
		catch (Exception e) {
		 e.printStackTrace();
		 return new ModelAndView("error");
		}

	}

	@RequestMapping(value = "updateRound.do", method = RequestMethod.POST)
	public @ResponseBody
	String updateRound(@RequestParam("roundId") int roundId,
			@RequestParam("memberId") int memberId,
			@RequestParam("front") int front, @RequestParam("back") int back,
			@RequestParam("total") int total) {
		galleriesService.updateRound(roundId, memberId, front, back, total);

		return "success";

	}
	
	@RequestMapping(value = "updateLock.do", method = RequestMethod.POST)
	public @ResponseBody
	String updateLock(@RequestParam("lblock") int lblock,
			@RequestParam("tripId") int tripId) {
		try{
		galleriesService.updateLock(tripId, lblock);
		return "success";
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		

	}
}
