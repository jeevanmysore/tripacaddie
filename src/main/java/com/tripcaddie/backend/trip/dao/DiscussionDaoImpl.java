package com.tripcaddie.backend.trip.dao;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.trip.model.Discussion;
import com.tripcaddie.backend.trip.model.DiscussionComment;
import com.tripcaddie.backend.trip.model.RatingDiscussion;

@Transactional
@Service("discussionDao")
public class DiscussionDaoImpl implements DiscussionDao{
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public void saveOrUpdateDiscussion(Discussion discussion) throws Exception {
		
		hibernateTemplate.saveOrUpdate(discussion);
		
	}
	
	@Override
	public void saveorUpdateEntity(Object entity) throws Exception {
		hibernateTemplate.saveOrUpdate(entity);
		
	}

	@Override
	public void saveOrUpdateDiscussionComment(
			DiscussionComment discussionComment) throws Exception {
		
		hibernateTemplate.saveOrUpdate(discussionComment);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Discussion> getDiscussions(int tripId) throws Exception {
		
		return (ArrayList<Discussion>)hibernateTemplate.find("from Discussion where trip.tripId="+tripId);
	}

	@Override
	public Discussion getDiscussion(int discussionId) throws Exception {
		return hibernateTemplate.load(Discussion.class, discussionId);
	}

	@Override
	public void deleteComment(DiscussionComment discussionComment)
			throws Exception {
		hibernateTemplate.delete(discussionComment);
		
	}

	@Override
	public void deleteDiscussion(Discussion discussion) throws Exception {
		
		hibernateTemplate.delete(discussion);
		
	}

	@Override
	public DiscussionComment getdiscussionComment(int commentId)
			throws Exception {
		return hibernateTemplate.load(DiscussionComment.class, commentId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<RatingDiscussion> getRatingDiscussion(int discussionId)
			throws Exception {
		return (ArrayList<RatingDiscussion>) hibernateTemplate.find("from RatingDiscussion where ratingDiscussionPK.discussion="+discussionId);
	}

}
