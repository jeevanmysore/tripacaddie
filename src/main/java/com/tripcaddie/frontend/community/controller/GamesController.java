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

import com.tripcaddie.backend.community.model.GameComment;
import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.frontend.community.dto.AbuseReasonsDto;
import com.tripcaddie.frontend.community.dto.GamesCommentDto;
import com.tripcaddie.frontend.community.dto.GamesDto;
import com.tripcaddie.frontend.community.service.CommunityService;
import com.tripcaddie.frontend.galleries.controller.AwardsController;
import com.tripcaddie.frontend.galleries.dto.AwardsDto;
import com.tripcaddie.frontend.galleries.service.GalleriesService;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.service.TripService;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Controller
@RequestMapping("/games/*")
public class GamesController {

	private static Logger logger = Logger.getLogger(GamesController.class);

	@Resource(name = "communityService")
	private CommunityService communityService;

	@Resource(name = "tripService")
	private TripService tripService;

	@Resource(name = "imagePath")
	private ImagePath imagePath;

	@Resource(name="loginService")
	private LoginService loginService;
	
	@RequestMapping(value = "recent.do", method = RequestMethod.GET)
	public String recentGames(@RequestParam(value="order" ,required=false) String order,Model model) {
		try {
			List<GamesDto> gamesDtos ;
			if(order!=null && order.equalsIgnoreCase("rating")){
				gamesDtos = communityService.getGamesbyRating();
				model.addAttribute("rating", true);
			}else{
				gamesDtos =  communityService.getGamesbyDate();
				model.addAttribute("rating", false);
			}
			
			for (GamesDto gamesDto : gamesDtos) {
				if (gamesDto.getDescription() != null
						&& gamesDto.getDescription().length() > 300) {
					gamesDto.setDescription(gamesDto.getDescription()
							.substring(0, 300));
				}
			}

			model.addAttribute("games", gamesDtos);

			return "community/games";
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


	@RequestMapping(value = "getGame.do", method = RequestMethod.GET)
	public String getGame(@RequestParam("gameId") int gameId, Model model) {
		try {

			GamesDto gamesDto = communityService.getGameById(gameId);

			List<GamesCommentDto> comments=communityService.getGameComments(gameId);
			
			for(GamesCommentDto gamesCommentDto:comments)
			{
				gamesCommentDto.getUser().setImageBase64(getImageEncodedString(imagePath.getTripimagepathperuser()+gamesCommentDto.getUser().getImageUrl()));
			}
			
			model.addAttribute("game", gamesDto);
			model.addAttribute("comments", comments);

			return "community/displayGame";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "error";
		}

	}

	/**
	 * 
	 * Delete Game
	 * 
	 * @param pictureID
	 * @param tripId
	 * @return
	 */

	// delete a game
	@RequestMapping(value = "deleteGame.do", method = RequestMethod.POST)
	public @ResponseBody
	String deleteGame(@RequestParam(value = "gameId") int gameId) {
		try {
			communityService.deleteGame(gameId);
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
	 * @param videoId
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "addGameRating.do", method = RequestMethod.POST)
	public @ResponseBody
	String ratingGame(@RequestParam(value = "rating") int rating,
			@RequestParam(value = "gameId") int gameId, Model model) {
		try {
			communityService.updateorAddGameRating(gameId, rating);
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

	@RequestMapping(value = "addGameComment.do", method = RequestMethod.POST)
	public @ResponseBody
	String addCommentforGame(@RequestParam("gameId") int gameId,
			@RequestParam("comment") String comment) {
		try {
			communityService.addCommentforGame(gameId, comment);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}

	@RequestMapping(value = "deleteGameComment.do", method = RequestMethod.POST)
	public @ResponseBody
	String deleteCommentforGame(@RequestParam(value = "commentId") int commentId) {
		try {

			communityService.deleteCommentforGame(commentId);
			return "success";

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}

	@RequestMapping(value = "editGameComment.do", method = RequestMethod.POST)
	public @ResponseBody
	String editCommentforVideo(
			@RequestParam(value = "commentId") int commentID,
			@RequestParam(value = "comment") String comment) {
		try {
			communityService.editCommentforGame(commentID, comment);
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
	@RequestMapping(value = "AddGame.do", method = RequestMethod.GET)
	public String addGame(Model model) {
		try {

			return "community/addGame";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "error";
		}

	}

	@RequestMapping(value = "AddGame.do", method = RequestMethod.POST)
	public @ResponseBody String addGame(@RequestParam("gameName") String gameName,
			@RequestParam("Description") String description, Model model,
			HttpServletResponse response) {
		try {
			communityService.addGame(gameName, description);
			

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();

		}
		return "success";

	}
	
	@RequestMapping(value = "reportAbuse.do", method = RequestMethod.GET)
	public String getreportAbuse(@RequestParam("gameId") int gameId ,Model model) {
		try {
			
			GamesDto gamesDto = communityService.getGameById(gameId);
			
			TripcaddieUserDto userDto=loginService.getUserByUserName(loginService.getAuthenticatedUser());
			
			List<AbuseReasonsDto> reasonsDtos=communityService.getAbuseReasons();
			
			model.addAttribute("user", userDto);
			model.addAttribute("reasons", reasonsDtos);
			model.addAttribute("game", gamesDto);
			
			return "community/GamereportAbuse";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}

	
	@RequestMapping(value = "reportAbuse.do", method = RequestMethod.POST)
	public @ResponseBody String sendReportAbuse(@RequestParam("gameId") int gameId ,@RequestParam("userId") int userId ,
			@RequestParam("reason") int reason ,@RequestParam("message") String message ,Model model) {
		try {
			
		    communityService.sendReportAbuse(gameId ,userId ,reason ,message);
			
			
			
			return "success";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e.getCause());
			return "error";
		}
	}
}
