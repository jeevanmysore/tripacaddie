package com.tripcaddie.backend.trip.dao;

import java.util.ArrayList;

import com.tripcaddie.backend.trip.model.Discussion;
import com.tripcaddie.backend.trip.model.DiscussionComment;
import com.tripcaddie.backend.trip.model.RatingDiscussion;

public interface DiscussionDao {

	public void saveOrUpdateDiscussion(Discussion discussion) throws Exception;
	public void saveOrUpdateDiscussionComment(DiscussionComment discussionComment) throws Exception;
	
	public ArrayList<Discussion> getDiscussions(int tripId) throws Exception;
	public Discussion getDiscussion(int discussionId) throws Exception;
	
	public void deleteComment(DiscussionComment discussionComment) throws Exception;
	public void deleteDiscussion(Discussion discussion) throws Exception;
	
	public DiscussionComment getdiscussionComment(int commentId) throws Exception;
	
	public void saveorUpdateEntity(Object entity) throws Exception;
	public ArrayList<RatingDiscussion> getRatingDiscussion(int discussionId) throws Exception;
}
