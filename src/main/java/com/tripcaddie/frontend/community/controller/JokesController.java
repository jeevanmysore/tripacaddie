package com.tripcaddie.frontend.community.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.frontend.community.dto.AbuseReasonsDto;
import com.tripcaddie.frontend.community.dto.GamesDto;
import com.tripcaddie.frontend.community.dto.JokesCommentDto;
import com.tripcaddie.frontend.community.dto.JokesDto;
import com.tripcaddie.frontend.community.service.CommunityService;
import com.tripcaddie.frontend.trip.service.TripService;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Controller
@RequestMapping("/jokes/*")
public class JokesController {

	private static Logger logger = Logger.getLogger(JokesController.class);

	@Resource(name = "communityService")
	private CommunityService communityService;

	@Resource(name = "tripService")
	private TripService tripService;

	@Resource(name = "imagePath")
	private ImagePath imagePath;
	
	@Resource(name="loginService")
	private LoginService loginService;
	

	@RequestMapping(value = "recent.do", method = RequestMethod.GET)
	public String recentGames(@RequestParam(value="order" ,required=false) String order ,Model model) {
		try {
			List<JokesDto> jokesDtos ;
			if(order!=null && order.equalsIgnoreCase("rating")){
				 jokesDtos = communityService.getJokesbyRating();
				 model.addAttribute("rating", true);
			}else{
			 jokesDtos = communityService.getJokessbyDate();
			 model.addAttribute("rating", false);
			}
			for (JokesDto jokesDto : jokesDtos) {
				if (jokesDto.getDescription() != null
						&& jokesDto.getDescription().length() > 300) {
					jokesDto.setDescription(jokesDto.getDescription()
							.substring(0, 300));
				}
			}

			model.addAttribute("jokes", jokesDtos);

			return "community/jokes";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
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


	@RequestMapping(value = "getJoke.do", method = RequestMethod.GET)
	public String getGame(@RequestParam("jokeId") int jokeId, Model model) {
		try {

			JokesDto jokesDto = communityService.getJokeById(jokeId);

			List<JokesCommentDto> comments=communityService.getJokeComments(jokeId);
			
			for(JokesCommentDto jokesCommentDto:comments)
			{
				jokesCommentDto.getUser().setImageBase64(getImageEncodedString(imagePath.getTripimagepathperuser()+jokesCommentDto.getUser().getImageUrl()));
			}
			
			model.addAttribute("joke", jokesDto);
			model.addAttribute("comments", comments);

			return "community/displayJoke";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "error";
		}

	}

	/**
	 * 
	 * Delete Joke
	 * 
	 * @param 
	 * @return
	 */

	// delete a game
	@RequestMapping(value = "deleteJoke.do", method = RequestMethod.POST)
	public @ResponseBody
	String deleteGame(@RequestParam(value = "jokeId") int jokeId) {
		try {
			communityService.deleteJoke(jokeId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}

	/**
	 * 
	 * 
	 * Rating
	 * 
	 * @param tripId
	 * @param rating
	 * @param 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "addJokeRating.do", method = RequestMethod.POST)
	public @ResponseBody
	String ratingGame(@RequestParam(value = "rating") int rating,
			@RequestParam(value = "jokeId") int jokeId, Model model) {
		try {
			communityService.updateorAddJokeRating(jokeId, rating);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * comments
	 * 
	 * 
	 * 
	 * 
	 */

	@RequestMapping(value = "addJokeComment.do", method = RequestMethod.POST)
	public @ResponseBody
	String addCommentforGame(@RequestParam("jokeId") int jokeId,
			@RequestParam("comment") String comment) {
		try {
			communityService.addCommentforJoke(jokeId, comment);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}

	@RequestMapping(value = "deleteJokeComment.do", method = RequestMethod.POST)
	public @ResponseBody
	String deleteCommentforGame(@RequestParam(value = "commentId") int commentId) {
		try {

			communityService.deleteCommentforJoke(commentId);
			return "success";

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}

	@RequestMapping(value = "editJokeComment.do", method = RequestMethod.POST)
	public @ResponseBody
	String editCommentforVideo(
			@RequestParam(value = "commentId") int commentID,
			@RequestParam(value = "comment") String comment) {
		try {
			communityService.editCommentforJoke(commentID, comment);
			// galleriesService.deleteComment(commentID);
			return "success";

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}

	/**
	 * Add Games
	 * 
	 */
	@RequestMapping(value = "AddJoke.do", method = RequestMethod.GET)
	public String addGame(Model model) {
		try {

			return "community/addJoke";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "error";
		}

	}

	@RequestMapping(value = "AddJoke.do", method = RequestMethod.POST)
	public @ResponseBody String addGame(@RequestParam("jokeName") String jokeName,
			@RequestParam("Description") String description, Model model,
			HttpServletResponse response) {
		try {
			communityService.addJoke(jokeName, description);
			

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();

		}
		return "success";
	}
	@RequestMapping(value = "reportAbuse.do", method = RequestMethod.GET)
	public String getreportAbuse(@RequestParam("jokeId") int jokeId ,Model model) {
		try {
			
			JokesDto jokesDto = communityService.getJokeById(jokeId);
			
			TripcaddieUserDto userDto=loginService.getUserByUserName(loginService.getAuthenticatedUser());
			
			List<AbuseReasonsDto> reasonsDtos=communityService.getAbuseReasons();
			
			model.addAttribute("user", userDto);
			model.addAttribute("reasons", reasonsDtos);
			model.addAttribute("joke", jokesDto);
			
			return "community/JokereportAbuse";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}
	
	@RequestMapping(value = "reportAbuse.do", method = RequestMethod.POST)
	public @ResponseBody String sendReportAbuse(@RequestParam("jokeId") int jokeId ,@RequestParam("userId") int userId ,
			@RequestParam("reason") int reason ,@RequestParam("message") String message ,Model model) {
		try {
			
		    communityService.sendReportAbuseJoke(jokeId ,userId ,reason ,message);
		
			return "success";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}
}
