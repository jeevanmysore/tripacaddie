package com.tripcaddie.frontend.community.dto;

import com.tripcaddie.backend.community.model.GameRating;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public class GamesRatingDto {

	private GamesDto games;

	private TripcaddieUserDto user;

	private Double rating;

	public GamesDto getGames() {
		return games;
	}

	public void setGames(GamesDto games) {
		this.games = games;
	}

	
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	public static GamesRatingDto instantiate(GameRating gameRating) {

		GamesRatingDto gamesRatingDto = new GamesRatingDto();
		populate(gamesRatingDto, gameRating);
		return gamesRatingDto;
	}

	private static void populate(GamesRatingDto gamesRatingDto,
			GameRating gameRating) {
		
		gamesRatingDto.setUser(TripcaddieUserDto.instantiate(gameRating.getGameRatingPK().getUser()));
		gamesRatingDto.setGames(GamesDto.instantiate(gameRating.getGameRatingPK().getGame()));
		gamesRatingDto.setRating(gameRating.getRating());
		
		
	}

	public TripcaddieUserDto getUser() {
		return user;
	}

	public void setUser(TripcaddieUserDto user) {
		this.user = user;
	}

}
