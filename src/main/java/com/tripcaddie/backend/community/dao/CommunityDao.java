package com.tripcaddie.backend.community.dao;

import java.util.List;

import com.tripcaddie.backend.community.model.AbuseReasons;
import com.tripcaddie.backend.community.model.GameComment;
import com.tripcaddie.backend.community.model.GameRating;
import com.tripcaddie.backend.community.model.Games;
import com.tripcaddie.backend.community.model.JokeComment;
import com.tripcaddie.backend.community.model.JokeRating;
import com.tripcaddie.backend.community.model.Jokes;
import com.tripcaddie.frontend.community.dto.AbuseReasonsDto;

public interface CommunityDao {

	List<Games> getGamesbyDate();

	List<Games> getGamesbyRating();

	Games getGameById(int gameId);
	
	GameComment getGameCommentbyId(int commentId);
	
	GameRating getGameRating(int gameId ,int userId);
	
	List<GameComment> getGameComments(int gameId);
	
	List<GameRating> getGameAvgRating(int gameId);
	
	
	/**
	 * Abuse Reasons
	 * 
	 */
	List<AbuseReasons> getAbuseReasons();
	
	AbuseReasons getAbuseReason(int reasonId);
	
	/**
	 * Generics
	 * 
	 */
	
	void saveEntity(Object entity);
	
	void updateEntity(Object entity);
	
	void deleteEntity(Object entity);
	

	/**
	 * Jokes
	 * 
	 */

	List<Jokes> getJokessbyDate();

	List<Jokes> getJokesbyRating();

	Jokes getJokeById(int jokeId);
	
	JokeComment getJokeCommentbyId(int commentId);
	
	JokeRating getJokeRating(int jokeId ,int userId);
	
	List<JokeComment> getJokeComments(int jokeId);
	
	List<JokeRating> getJokeAvgRating(int jokeId);
}
