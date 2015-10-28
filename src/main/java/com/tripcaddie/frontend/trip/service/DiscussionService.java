package com.tripcaddie.frontend.trip.service;

import java.util.ArrayList;

import com.tripcaddie.frontend.trip.dto.DiscussionDto;

public interface DiscussionService {
	
	public void createDiscussion(String title,String description,int tripId) throws Exception;
	public void EditDiscussion(String title,String description,int discussionId) throws Exception;
	
	public void addCommentForDiscussion(int discussionId,String comment) throws Exception;
	public void editCommentForDiscussion(int commentId,String comment) throws Exception;
	
	public void deleteDiscussion(int discussionId) throws Exception;
	public void deleteDiscussionComment(int commentId) throws Exception;
	
	public ArrayList<DiscussionDto> getDiscussions(int tripId) throws Exception;
	public DiscussionDto getDiscussion(int discussionId) throws Exception;

	public void updateorAddDiscussionRating(int tripId,int discussionId,double rating) throws Exception;
}
