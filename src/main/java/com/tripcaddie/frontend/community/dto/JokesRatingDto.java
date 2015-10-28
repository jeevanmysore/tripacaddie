package com.tripcaddie.frontend.community.dto;

import com.tripcaddie.backend.community.model.JokeRating;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public class JokesRatingDto {

	private JokesDto jokes;

	private TripcaddieUserDto user;

	private Double rating;
	
	public JokesDto getJokes() {
		return jokes;
	}

	public void setJokes(JokesDto jokes) {
		this.jokes = jokes;
	}

	

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public static JokesRatingDto instantiate(JokeRating jokeRating) {

		JokesRatingDto jokesRatingDto = new JokesRatingDto();
		populate(jokesRatingDto, jokeRating);
		return jokesRatingDto;
	}

	private static void populate(JokesRatingDto jokesRatingDto,
			JokeRating jokeRating) {
		
		jokesRatingDto.setJokes(JokesDto.instantiate(jokeRating.getJokeRatingPK().getJokes()));
		jokesRatingDto.setUser(TripcaddieUserDto.instantiate(jokeRating.getJokeRatingPK().getUser()));
		jokesRatingDto.setRating(jokeRating.getRating());
		
	}

	public TripcaddieUserDto getUser() {
		return user;
	}

	public void setUser(TripcaddieUserDto user) {
		this.user = user;
	}

}
