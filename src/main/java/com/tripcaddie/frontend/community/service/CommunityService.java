package com.tripcaddie.frontend.community.service;

import java.util.List;

import com.tripcaddie.backend.community.model.AbuseReasons;
import com.tripcaddie.backend.community.model.GameComment;
import com.tripcaddie.frontend.community.dto.AbuseReasonsDto;
import com.tripcaddie.frontend.community.dto.GamesCommentDto;
import com.tripcaddie.frontend.community.dto.GamesDto;
import com.tripcaddie.frontend.community.dto.JokesCommentDto;
import com.tripcaddie.frontend.community.dto.JokesDto;

public interface CommunityService {

	List<GamesDto> getGamesbyDate();

	List<GamesDto> getGamesbyRating();

	GamesDto getGameById(int gameId);
	
	void deleteGame(int gameId);

	void updateorAddGameRating(int gameId, int rating) throws Exception;

	void addCommentforGame(int gameId, String comment) throws Exception;

	void deleteCommentforGame(int commentId);

	void editCommentforGame(int commentID, String comment) throws Exception;
	
	void addGame(String title , String Desc) throws Exception;
	
	List<GamesCommentDto> getGameComments(int gameId);
	
	void sendReportAbuse(int gameId, int userId, int reason, String message) throws Exception;

	/**
	 * Abuse Reasons
	 * 
	 */
	List<AbuseReasonsDto> getAbuseReasons();
	
	
	/**
	 * Jokes
	 * 
	 */

	List<JokesDto> getJokessbyDate();

	List<JokesDto> getJokesbyRating();

	JokesDto getJokeById(int jokeId);
	
	void deleteJoke(int jokeId);

	void updateorAddJokeRating(int jokeId, int rating) throws Exception;

	void addCommentforJoke(int jokeId, String comment) throws Exception;

	void deleteCommentforJoke(int commentId);

	void editCommentforJoke(int commentID, String comment) throws Exception;
	
	List<JokesCommentDto> getJokeComments(int jokeId);
	
	void addJoke(String title , String Desc) throws Exception;

	void sendReportAbuseJoke(int jokeId, int userId, int reason, String message) throws Exception;

	

	
}
