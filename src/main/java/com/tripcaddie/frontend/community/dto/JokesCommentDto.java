package com.tripcaddie.frontend.community.dto;

import java.util.Calendar;

import com.tripcaddie.backend.community.model.JokeComment;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

public class JokesCommentDto {

	private int jokeCommentId;

	private JokesDto jokes;

	private String comments;

	private String createdBy;

	private Calendar createdDate;

	private String lastUpdatedBy;

	private Calendar lastUpdatedDate;
	
	private TripcaddieUserDto user;

	public int getJokeCommentId() {
		return jokeCommentId;
	}

	public void setJokeCommentId(int jokeCommentId) {
		this.jokeCommentId = jokeCommentId;
	}

	public JokesDto getJokes() {
		return jokes;
	}

	public void setJokes(JokesDto jokes) {
		this.jokes = jokes;
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

	public static JokesCommentDto instantiate(JokeComment jokeComment) {

		JokesCommentDto jokesCommentDto = new JokesCommentDto();
		populate(jokesCommentDto, jokeComment);
		return jokesCommentDto;
	}

	private static void populate(JokesCommentDto jokesCommentDto,
			JokeComment jokeComment) {
		
		jokesCommentDto.setComments(jokeComment.getComments());
		jokesCommentDto.setJokeCommentId(jokeComment.getJokeCommentId());
		jokesCommentDto.setCreatedBy(jokeComment.getCreatedBy());
		jokesCommentDto.setCreatedDate(jokeComment.getCreatedDate());
		
		jokesCommentDto.setLastUpdatedBy(jokesCommentDto.getLastUpdatedBy());
		jokesCommentDto.setLastUpdatedDate(jokesCommentDto.getLastUpdatedDate());
		jokesCommentDto.setUser(TripcaddieUserDto.instantiate(jokeComment.getUser()));
		
	}

	public TripcaddieUserDto getUser() {
		return user;
	}

	public void setUser(TripcaddieUserDto user) {
		this.user = user;
	}
}
