package com.tripcaddie.frontend.community.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.community.dao.CommunityDao;
import com.tripcaddie.backend.community.model.AbuseReasons;
import com.tripcaddie.backend.community.model.GameComment;
import com.tripcaddie.backend.community.model.GameRating;
import com.tripcaddie.backend.community.model.GameRatingPK;
import com.tripcaddie.backend.community.model.Games;
import com.tripcaddie.backend.community.model.GamesAbuse;
import com.tripcaddie.backend.community.model.JokeComment;
import com.tripcaddie.backend.community.model.JokeRating;
import com.tripcaddie.backend.community.model.JokeRatingPK;
import com.tripcaddie.backend.community.model.Jokes;
import com.tripcaddie.backend.community.model.JokesAbuse;
import com.tripcaddie.backend.galleries.model.PictureFile;
import com.tripcaddie.backend.galleries.model.RatingPictureFile;
import com.tripcaddie.backend.galleries.model.RatingPictureFilePK;
import com.tripcaddie.backend.galleries.model.VideoComment;
import com.tripcaddie.backend.galleries.model.VideoFile;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.backend.user.dao.UserDao;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.common.util.EmailSender;
import com.tripcaddie.frontend.community.dto.AbuseReasonsDto;
import com.tripcaddie.frontend.community.dto.GamesCommentDto;
import com.tripcaddie.frontend.community.dto.GamesDto;
import com.tripcaddie.frontend.community.dto.JokesCommentDto;
import com.tripcaddie.frontend.community.dto.JokesDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("communityService")
public class CommunityServiceImpl implements CommunityService {

	@Resource(name = "communityDao")
	private CommunityDao communityDao;

	@Resource(name = "userDao")
	private UserDao userDao;

	@Resource(name = "loginService")
	private LoginService loginService;

	@Resource(name="emailSender")
	private EmailSender emailSender;
	
	@Override
	public List<GamesDto> getGamesbyDate() {
		List<GamesDto> gamesDtos = new ArrayList<GamesDto>();
		List<Games> games = communityDao.getGamesbyDate();
		if (games != null && !games.isEmpty()) {
			for (Games games2 : games) {
				gamesDtos.add(GamesDto.instantiate(games2));
			}
		}
		return gamesDtos;
	}

	@Override
	public List<GamesDto> getGamesbyRating() {
		List<GamesDto> gamesDtos = new ArrayList<GamesDto>();
		List<Games> games = communityDao.getGamesbyRating();
		if (games != null && !games.isEmpty()) {
			for (Games games2 : games) {
				gamesDtos.add(GamesDto.instantiate(games2));
			}
		}
		return gamesDtos;
	}

	@Override
	public GamesDto getGameById(int gameId) {
		Games games = communityDao.getGameById(gameId);
		if (games != null) {
			return GamesDto.instantiate(games);
		}
		return null;
	}

	@Override
	public List<JokesDto> getJokessbyDate() {
		List<JokesDto> jokesDtos = new ArrayList<JokesDto>();
		List<Jokes> jokes = communityDao.getJokessbyDate();
		if (jokes != null && !jokes.isEmpty()) {
			for (Jokes jokes2 : jokes) {
				jokesDtos.add(JokesDto.instantiate(jokes2));
			}
		}
		return jokesDtos;
	}

	@Override
	public List<JokesDto> getJokesbyRating() {
		List<JokesDto> jokesDtos = new ArrayList<JokesDto>();
		List<Jokes> jokes = communityDao.getJokesbyRating();
		if (jokes != null && !jokes.isEmpty()) {
			for (Jokes jokes2 : jokes) {
				jokesDtos.add(JokesDto.instantiate(jokes2));
			}
		}
		return jokesDtos;
	}

	@Override
	public JokesDto getJokeById(int jokeId) {
		Jokes jokes = communityDao.getJokeById(jokeId);
		if (jokes != null) {
			return JokesDto.instantiate(jokes);
		}
		return null;
	}

	@Override
	public void deleteGame(int gameId) {
		Games games = communityDao.getGameById(gameId);
		communityDao.deleteEntity(games);
		//games.setDelete(1);
		//communityDao.updateEntity(games);
	}

	@Override
	public void updateorAddGameRating(int gameId, int rating) throws Exception {
		GameRating gameRating = null;
		Games games = communityDao.getGameById(gameId);
		String username = loginService.getAuthenticatedUser();
		TripCaddieUser user = userDao.getUserByUserName(username);

		// get rating of picture
		gameRating = communityDao.getGameRating(gameId, user.getUserId());

		if (gameRating == null) {
			gameRating = new GameRating();
			gameRating.setRating(Double.parseDouble(String.valueOf(rating)));
			GameRatingPK pk = new GameRatingPK();
			pk.setGame(games);
			pk.setUser(user);
			gameRating.setGameRatingPK(pk);
			communityDao.saveEntity(gameRating);
		} else {
			gameRating.setRating(Double.parseDouble(String.valueOf(rating)));
			communityDao.updateEntity(gameRating);
		}

	}

	@Override
	public void addCommentforGame(int gameId, String comment) throws Exception {
		Games games = communityDao.getGameById(gameId);
		GameComment gameComment = new GameComment();
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);

		String username = loginService.getAuthenticatedUser();
		TripCaddieUser user=userDao.getUserByUserName(username);
		gameComment.setComments(comment);
		gameComment.setCreatedBy(username);
		gameComment.setCreatedDate(instantTime);
		gameComment.setLastUpdatedDate(instantTime);
		gameComment.setGames(games);
		gameComment.setUser(user);

		communityDao.saveEntity(gameComment);

	}

	@Override
	public void deleteCommentforGame(int commentId) {
		GameComment gameComment = communityDao.getGameCommentbyId(commentId);
		communityDao.deleteEntity(gameComment);

	}

	@Override
	public void editCommentforGame(int commentID, String comment)
			throws Exception {
		GameComment gameComment = communityDao.getGameCommentbyId(commentID);
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);
		String username = loginService.getAuthenticatedUser();

		gameComment.setComments(comment);
		gameComment.setLastUpdatedBy(username);
		gameComment.setLastUpdatedDate(instantTime);

		communityDao.updateEntity(gameComment);

	}

	@Override
	public void deleteJoke(int jokeId) {
		Jokes jokes = communityDao.getJokeById(jokeId);
		communityDao.deleteEntity(jokes);

	}

	@Override
	public void updateorAddJokeRating(int jokeId, int rating) throws Exception {
		JokeRating jokeRating = null;
		Jokes jokes = communityDao.getJokeById(jokeId);
		String username = loginService.getAuthenticatedUser();
		TripCaddieUser user = userDao.getUserByUserName(username);

		// get rating of picture
		jokeRating = communityDao.getJokeRating(jokeId, user.getUserId());

		if (jokeRating == null) {
			jokeRating = new JokeRating();
			jokeRating.setRating(Double.parseDouble(String.valueOf(rating)));
			JokeRatingPK pk = new JokeRatingPK();
			pk.setJokes(jokes);
			pk.setUser(user);
			jokeRating.setJokeRatingPK(pk);
			communityDao.saveEntity(jokeRating);
		} else {
			jokeRating.setRating(Double.parseDouble(String.valueOf(rating)));
			communityDao.updateEntity(jokeRating);
		}

	}

	@Override
	public void addCommentforJoke(int jokeId, String comment) throws Exception {
		Jokes jokes = communityDao.getJokeById(jokeId);
		JokeComment jokeComment = new JokeComment();
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);

		String username = loginService.getAuthenticatedUser();
		TripCaddieUser user=userDao.getUserByUserName(username);

		jokeComment.setComments(comment);
		jokeComment.setCreatedBy(username);
		jokeComment.setCreatedDate(instantTime);
		jokeComment.setLastUpdatedDate(instantTime);
		jokeComment.setJokes(jokes);
		jokeComment.setUser(user);

		communityDao.saveEntity(jokeComment);

	}

	@Override
	public void deleteCommentforJoke(int commentId) {
		JokeComment jokeComment = communityDao.getJokeCommentbyId(commentId);
		communityDao.deleteEntity(jokeComment);

	}

	@Override
	public void editCommentforJoke(int commentID, String comment)
			throws Exception {
		JokeComment jokeComment = communityDao.getJokeCommentbyId(commentID);
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);
		String username = loginService.getAuthenticatedUser();

		jokeComment.setComments(comment);
		jokeComment.setLastUpdatedBy(username);
		jokeComment.setLastUpdatedDate(instantTime);

		communityDao.updateEntity(jokeComment);

	}

	@Override
	public void addGame(String title, String Desc) throws Exception {
		Games games = new Games();
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);

		String username = loginService.getAuthenticatedUser();
		TripcaddieUserDto user = loginService.getUserByUserName(username);

		games.setCreatedBy(username);
		games.setCreatedDate(instantTime);
		games.setDescription(Desc);
		games.setGameName(title);
		games.setAvgRating((double) 0);
		games.setAbuse(0);
		

		communityDao.saveEntity(games);
	}

	@Override
	public List<GamesCommentDto> getGameComments(int gameId) {
		List<GamesCommentDto> commentDtos = new ArrayList<GamesCommentDto>();
		List<GameComment> comments = communityDao.getGameComments(gameId);
		if (comments != null && !comments.isEmpty()) {
			for (GameComment gameComment : comments) {
				commentDtos.add(GamesCommentDto.instantiate(gameComment));
			}
		}
		return commentDtos;
	}

	@Override
	public List<JokesCommentDto> getJokeComments(int jokeId) {
		List<JokesCommentDto> commentDtos = new ArrayList<JokesCommentDto>();
		List<JokeComment> comments = communityDao.getJokeComments(jokeId);
		if (comments != null && !comments.isEmpty()) {
			for (JokeComment jokeComment : comments) {
				commentDtos.add(JokesCommentDto.instantiate(jokeComment));
			}
		}
		return commentDtos;
	}

	@Override
	public void addJoke(String title, String Desc) throws Exception {
		Jokes jokes = new Jokes();
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);

		String username = loginService.getAuthenticatedUser();
		TripcaddieUserDto user = loginService.getUserByUserName(username);

		jokes.setCreatedBy(username);
		jokes.setCreatedDate(instantTime);
		jokes.setDescription(Desc);
		jokes.setJokeName(title);
		jokes.setAvgRating((double) 0);
		jokes.setAbuse(0);
		

		communityDao.saveEntity(jokes);
	}

	@Override
	public List<AbuseReasonsDto> getAbuseReasons() {
		List<AbuseReasonsDto> abuseReasonsDtos=new ArrayList<AbuseReasonsDto>();
		for(AbuseReasons abuseReasons: communityDao.getAbuseReasons()){
			abuseReasonsDtos.add(AbuseReasonsDto.instantiate(abuseReasons));
		}
		return abuseReasonsDtos;
	}

	@Override
	public void sendReportAbuse(int gameId, int userId, int reason,
			String message) throws Exception {
		
		Games games=communityDao.getGameById(gameId);
		games.setAbuse(1);
		communityDao.updateEntity(games);
		
		TripCaddieUser user=userDao.getUserByUserName(userDao.getAuthenticatedUser());
		AbuseReasons abuseReasons=communityDao.getAbuseReason(reason);
		
		GamesAbuse gamesAbuse=new GamesAbuse();
		gamesAbuse.setUser(user);
		gamesAbuse.setReasons(abuseReasons);
		gamesAbuse.setMessage(message);
		gamesAbuse.setGames(games);
		gamesAbuse.setCreatedBy(user.getFirstName());
		gamesAbuse.setCreatedDate(Calendar.getInstance());
		
		communityDao.saveEntity(gamesAbuse);
		
		//send mail
		emailSender.sendEmailForAbuse(user.getUserName(), games.getGameName(), abuseReasons.getReason(), message);
	}

	@Override
	public void sendReportAbuseJoke(int jokeId, int userId, int reason,
			String message) throws Exception {
		Jokes jokes=communityDao.getJokeById(jokeId);
		jokes.setAbuse(1);
		communityDao.updateEntity(jokes);
		
		TripCaddieUser user=userDao.getUserByUserName(userDao.getAuthenticatedUser());
		AbuseReasons abuseReasons=communityDao.getAbuseReason(reason);
		
		JokesAbuse jokesAbuse=new JokesAbuse();
		jokesAbuse.setUser(user);
		jokesAbuse.setReasons(abuseReasons);
		jokesAbuse.setMessage(message);
		jokesAbuse.setJokes(jokes);
		jokesAbuse.setCreatedBy(user.getFirstName());
		jokesAbuse.setCreatedDate(Calendar.getInstance());
		
		communityDao.saveEntity(jokesAbuse);
		
		//send mail
		emailSender.sendEmailForAbuse(user.getUserName(), jokes.getJokeName(), abuseReasons.getReason(), message);
	
	}

}
