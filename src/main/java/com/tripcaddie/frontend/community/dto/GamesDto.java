package com.tripcaddie.frontend.community.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.tripcaddie.backend.community.model.GameComment;
import com.tripcaddie.backend.community.model.Games;

public class GamesDto {

	private int gameId;

	private String gameName;

	private String description;

	private Double avgRating;

	private String createdBy;

	private Calendar createdDate;

	private String lastUpdatedBy;

	private Calendar lastUpdatedDate;

	private Collection<GamesCommentDto> gameComments = new ArrayList<GamesCommentDto>();

	private int noOfComments;
	
	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Collection<GamesCommentDto> getGameComments() {
		return gameComments;
	}

	public void setGameComments(Collection<GamesCommentDto> gameComments) {
		this.gameComments = gameComments;
	}

	public static GamesDto instantiate(Games games) {

		GamesDto gamesDto = new GamesDto();
		populate(gamesDto, games);
		return gamesDto;
	}

	private static void populate(GamesDto gamesDto, Games games) {

		gamesDto.setGameId(games.getGameId());
		gamesDto.setGameName(games.getGameName());
		gamesDto.setDescription(games.getDescription());
		gamesDto.setAvgRating(games.getAvgRating());
		gamesDto.setCreatedBy(games.getCreatedBy());
		gamesDto.setLastUpdatedBy(games.getLastUpdatedBy());
		gamesDto.setCreatedDate(games.getCreatedDate());
		gamesDto.setLastUpdatedDate(games.getLastUpdatedDate());
		List<GamesCommentDto> comments=new ArrayList<GamesCommentDto>();
		for(GameComment gameComment:games.getGameComments()){
			comments.add(GamesCommentDto.instantiate(gameComment));
		
		}
		gamesDto.setGameComments(comments);
		gamesDto.setNoOfComments(comments.size());

	}

	public int getNoOfComments() {
		return noOfComments;
	}

	public void setNoOfComments(int noOfComments) {
		this.noOfComments = noOfComments;
	}
}
