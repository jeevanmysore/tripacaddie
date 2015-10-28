package com.tripcaddie.frontend.community.dto;

import java.util.Calendar;

import com.tripcaddie.backend.community.model.GameComment;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public class GamesCommentDto {

	private int gameCommentId;

	private GamesDto games;

	private String comments;

	private String createdBy;

	private Calendar createdDate;

	private String lastUpdatedBy;

	private Calendar lastUpdatedDate;
	
	private TripcaddieUserDto user;
	
	public int getGameCommentId() {
		return gameCommentId;
	}

	public void setGameCommentId(int gameCommentId) {
		this.gameCommentId = gameCommentId;
	}

	public GamesDto getGames() {
		return games;
	}

	public void setGames(GamesDto games) {
		this.games = games;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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
	public static GamesCommentDto instantiate(GameComment gameComment) {

		GamesCommentDto gamesCommentDto = new GamesCommentDto();
		populate(gamesCommentDto, gameComment);
		return gamesCommentDto;
	}

	private static void populate(GamesCommentDto gamesCommentDto,
			GameComment gameComment) {
		
		gamesCommentDto.setComments(gameComment.getComments());
		gamesCommentDto.setGameCommentId(gameComment.getGameCommentId());
		gamesCommentDto.setCreatedBy(gameComment.getCreatedBy());
		gamesCommentDto.setCreatedDate(gameComment.getCreatedDate());
		gamesCommentDto.setLastUpdatedBy(gameComment.getLastUpdatedBy());
		gamesCommentDto.setLastUpdatedDate(gameComment.getLastUpdatedDate());
		gamesCommentDto.setUser(TripcaddieUserDto.instantiate(gameComment.getUser()));
		
		
	}

	public TripcaddieUserDto getUser() {
		return user;
	}

	public void setUser(TripcaddieUserDto user) {
		this.user = user;
	}

}
