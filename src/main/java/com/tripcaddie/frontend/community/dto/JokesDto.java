package com.tripcaddie.frontend.community.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.tripcaddie.backend.community.model.JokeComment;
import com.tripcaddie.backend.community.model.Jokes;

public class JokesDto {

	private int jokeId;

	private String jokeName;

	private String description;

	private Double avgRating;

	private String createdBy;

	private Calendar createdDate;

	private String lastUpdatedBy;

	private Calendar lastUpdatedDate;

	private Collection<JokesCommentDto> jokeComments = new ArrayList<JokesCommentDto>();
	
	private int noOfComments;

	public int getJokeId() {
		return jokeId;
	}

	public void setJokeId(int jokeId) {
		this.jokeId = jokeId;
	}

	public String getJokeName() {
		return jokeName;
	}

	public void setJokeName(String jokeName) {
		this.jokeName = jokeName;
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

	public Collection<JokesCommentDto> getJokeComments() {
		return jokeComments;
	}

	public void setJokeComments(Collection<JokesCommentDto> jokeComments) {
		this.jokeComments = jokeComments;
	}

	public static JokesDto instantiate(Jokes jokes) {

		JokesDto jokesDto = new JokesDto();
		populate(jokesDto, jokes);
		return jokesDto;
	}

	private static void populate(JokesDto jokesDto, Jokes jokes) {

		jokesDto.setJokeId(jokes.getJokeId());
		jokesDto.setJokeName(jokes.getJokeName());
		jokesDto.setDescription(jokes.getDescription());
		jokesDto.setAvgRating(jokes.getAvgRating());
		jokesDto.setCreatedBy(jokes.getCreatedBy());
		jokesDto.setLastUpdatedBy(jokes.getLastUpdatedBy());
		jokesDto.setCreatedDate(jokes.getCreatedDate());
		jokesDto.setLastUpdatedDate(jokes.getLastUpdatedDate());
		List<JokesCommentDto> comments=new ArrayList<JokesCommentDto>();
		for(JokeComment jokeComment:jokes.getJokeComments()){
			comments.add(JokesCommentDto.instantiate(jokeComment));
		
		}
		jokesDto.setJokeComments(comments);
		jokesDto.setNoOfComments(comments.size());

	}

	public int getNoOfComments() {
		return noOfComments;
	}

	public void setNoOfComments(int noOfComments) {
		this.noOfComments = noOfComments;
	}
}
