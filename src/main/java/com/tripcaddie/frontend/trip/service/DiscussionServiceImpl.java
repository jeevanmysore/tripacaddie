package com.tripcaddie.frontend.trip.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.trip.dao.DiscussionDao;
import com.tripcaddie.backend.trip.dao.TripDao;
import com.tripcaddie.backend.trip.model.Discussion;
import com.tripcaddie.backend.trip.model.DiscussionComment;
import com.tripcaddie.backend.trip.model.RatingDiscussion;
import com.tripcaddie.backend.trip.model.RatingDiscussionPK;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.frontend.trip.dto.DiscussionCommentDto;
import com.tripcaddie.frontend.trip.dto.DiscussionDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("discussionService")
public class DiscussionServiceImpl implements DiscussionService {

	@Resource(name="discussionDao")
	private DiscussionDao discussionDao;
	@Resource(name="tripDao")
	private TripDao tripDao;
	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name="recentUpdateService")
	private RecentUpdateService recentUpdateService;
	
	@Override
	public void createDiscussion(String title, String description, int tripId)
			throws Exception {
		
		Discussion discussion=new Discussion();
		Trip trip=tripDao.getTrip(tripId);
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto userDto=loginService.getUserByUserName(username);
		
		TripMember member=tripDao.getTripMember(userDto, tripId);
		
		discussion.setCreatedBy(username);
		discussion.setCreatedDate(instantTime);
		discussion.setDescription(description);
		discussion.setTitle(title);
		discussion.setTrip(trip);
		discussion.setTripMember(member);
		
		discussionDao.saveOrUpdateDiscussion(discussion);
		
	}

	@Override
	public void EditDiscussion(String title, String description,
			int discussionId) throws Exception {
		
		Discussion discussion=discussionDao.getDiscussion(discussionId);
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		String username=loginService.getAuthenticatedUser();
		
		discussion.setDescription(description);
		discussion.setLastUpdatedBy(username);
		discussion.setLastUpdatedDate(instantTime);
		discussion.setTitle(title);
		
		discussionDao.saveOrUpdateDiscussion(discussion);
		
	}

	@Override
	public void addCommentForDiscussion(int discussionId, String comment)
			throws Exception {
		
		DiscussionComment discussionComment=new DiscussionComment();
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto userDto=loginService.getUserByUserName(username);
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		Discussion discussion=discussionDao.getDiscussion(discussionId);
		TripMember member=tripDao.getTripMember(userDto, discussion.getTrip().getTripId());
		
		discussionComment.setComment(comment);
		discussionComment.setCreatedBy(username);
		discussionComment.setCreatedDate(instantTime);
		discussionComment.setDiscussion(discussion);
		discussionComment.setTripMember(member);
		
		discussionDao.saveOrUpdateDiscussionComment(discussionComment);
		recentUpdateService.logUpdates(discussionComment.getTripMember().getTrip().getTripId(), "Commented in Discussion","participant");
	}

	@Override
	public void editCommentForDiscussion(int commentId, String comment)
			throws Exception {
		
		DiscussionComment discussionComment=discussionDao.getdiscussionComment(commentId);
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		String username=loginService.getAuthenticatedUser();
		
		discussionComment.setComment(comment);
		discussionComment.setLastUpdatedBy(username);
		discussionComment.setLastUpdatedDate(instantTime);
		
		discussionDao.saveOrUpdateDiscussionComment(discussionComment);
		
	}

	@Override
	public void deleteDiscussion(int discussionId) throws Exception {
		
		Discussion discussion=discussionDao.getDiscussion(discussionId);
		discussionDao.deleteDiscussion(discussion);
		
	}

	@Override
	public void deleteDiscussionComment(int commentId) throws Exception {
		
		DiscussionComment discussionComment=discussionDao.getdiscussionComment(commentId);
		discussionDao.deleteComment(discussionComment);
		
	}

	@Override
	public ArrayList<DiscussionDto> getDiscussions(int tripId) throws Exception {
		
		ArrayList<DiscussionDto> discussionDtos=new ArrayList<DiscussionDto>();
		DiscussionDto discussionDto;
		
		ArrayList<Discussion> discussions=discussionDao.getDiscussions(tripId);
		
		for (Discussion discussion : discussions) {
			discussionDto=DiscussionDto.instantiate(discussion);
			discussionDto.setNoOfComments(discussion.getDiscussionComments().size());
			discussionDtos.add(discussionDto);		
		}
		return discussionDtos;
	}

	@Override
	public DiscussionDto getDiscussion(int discussionId) throws Exception {
		
		Discussion discussion=discussionDao.getDiscussion(discussionId);
		ArrayList<DiscussionCommentDto> commentDtos=new ArrayList<DiscussionCommentDto>();
		DiscussionCommentDto commentDto=new DiscussionCommentDto();
		
		for(DiscussionComment comment : discussion.getDiscussionComments()){
			commentDto=DiscussionCommentDto.instantiate(comment);
			commentDtos.add(commentDto);
		}
		DiscussionDto discussionDto=DiscussionDto.instantiate(discussion);
		discussionDto.setCommentDtos(commentDtos);
		return discussionDto;
	}

	@Override
	public void updateorAddDiscussionRating(int tripId, int discussionId, double rating)
			throws Exception {
		
		int totRating=0;
		RatingDiscussion ratingDiscussion=new RatingDiscussion();
		RatingDiscussionPK ratingDiscussionPK=new RatingDiscussionPK();
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto=loginService.getUserByUserName(username);
		
		TripMember member=tripDao.getTripMember(tripcaddieUserDto, tripId);
		Discussion discussion=discussionDao.getDiscussion(discussionId);
		
		ratingDiscussion.setRating(rating);
		ratingDiscussionPK.setDiscussion(discussion);
		ratingDiscussionPK.setTripMember(member);
		
		ratingDiscussion.setRatingDiscussionPK(ratingDiscussionPK);

		discussionDao.saveorUpdateEntity(ratingDiscussion);
		
		//Update avg rating
		ArrayList<RatingDiscussion> ratingDiscussions=discussionDao.getRatingDiscussion(discussionId);
		for (RatingDiscussion ratingDiscussion2 : ratingDiscussions) {
			totRating+=ratingDiscussion2.getRating();
		}
		discussion.setAvgRating((double)totRating/ratingDiscussions.size());
		
		discussionDao.saveOrUpdateDiscussion(discussion);
		
		
	}
	
	
}
