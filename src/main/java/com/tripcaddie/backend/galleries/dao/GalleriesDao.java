package com.tripcaddie.backend.galleries.dao;

import java.util.ArrayList;
import java.util.List;

import com.tripcaddie.backend.galleries.model.AwardVote;
import com.tripcaddie.backend.galleries.model.AwardVoteCount;
import com.tripcaddie.backend.galleries.model.Awards;
import com.tripcaddie.backend.galleries.model.AwardsComment;
import com.tripcaddie.backend.galleries.model.PictureComment;
import com.tripcaddie.backend.galleries.model.PictureFile;
import com.tripcaddie.backend.galleries.model.RatingPictureFile;
import com.tripcaddie.backend.galleries.model.RatingVideoFile;
import com.tripcaddie.backend.galleries.model.VideoComment;
import com.tripcaddie.backend.galleries.model.VideoFile;
import com.tripcaddie.backend.trip.model.Expense;
import com.tripcaddie.backend.trip.model.WinningAmount;
import com.tripcaddie.frontend.galleries.dto.AwardsDto;
import com.tripcaddie.frontend.galleries.dto.VideoCommentDto;

public interface GalleriesDao {
	
	public ArrayList<PictureFile> getPicturesByDate(int tripId) throws Exception;
	public ArrayList<PictureFile> getPicturesByRating(int tripId) throws Exception;
	public ArrayList<PictureFile> getPicturesByFav(int tripId) throws Exception;
	public PictureFile getPicture(int pictureId,int tripId) throws Exception;
	public PictureFile getPicture(int pictureId) throws Exception;
	public void deletePicture(PictureFile pictureFile) throws Exception;
	public int getMaxIdInPicture() throws Exception;
	public void addPicture(PictureFile pictureFile) throws Exception;
	public void editPicture(PictureFile pictureFile) throws Exception;
	
	public int getNoOfcomments(int pictureId) throws Exception;
	public ArrayList<PictureComment> getPictureComments(int pictureId) throws Exception;
	public PictureComment getPictureComment(int commentId) throws Exception;
	public void addComment(PictureComment pictureComment) throws Exception;
	public void deleteComment(Object entity) throws Exception;
	public void editComment(Object entity) throws Exception;
	public RatingPictureFile getPictureRating(int pictureId ,int tripmemberid);
	public List<RatingPictureFile> getPictureAvgRating(int pictureId );
	/**
	 * Generics
	 * 
	 */
	void updateEntity(Object entity);
	void saveorUpdateEntity(Object entity);
	void deleteEntity(Object entity);
	void saveEntity(Object entity);
	
	/**
	 * 
	 * Videos 
	 * 
	 */

	public ArrayList<VideoFile> getVideosByDate(int tripId) throws Exception;
	public ArrayList<VideoFile> getVideosByRating(int tripId) throws Exception;
	public ArrayList<VideoFile> getVideosByFav(int tripId) throws Exception;
	public int getNoOfCommentsperVideo(int videoid) throws Exception;
	public int getMaxIdInVideo() throws Exception;
	public void addVideo(VideoFile videoFile) throws Exception;
	public VideoFile getVideo(int videoId,int tripId) throws Exception;
	public VideoFile getVideo(int videoId) throws Exception;
	public ArrayList<VideoComment> getVideoComments(int videoId) throws Exception;
	public void addCommentforVideo(VideoComment videoComment) throws Exception;
	public VideoComment getVideoComment(int commentId) throws Exception;
	public RatingVideoFile getVideoRating(int videoId, int memberId);
	public List<RatingVideoFile> getVideoAvgRating(int videoId );
	
	/**
	 * awards
	 * 
	 */
	
	public int getMaxAwardId();
	public List<Awards> getAwardsbyDate(int tripId);
	public List<Awards> getAwardsbydirectlyAwarded(int tripId);
	public List<Awards> getAwardsPendingbyDate(int tripId);
	public AwardVote getAwardVotebyId(int id);
	public AwardVoteCount getMaxVoteforAward(int id);
	public int getNoOfcommentsAward(int awardId) throws Exception;
	public ArrayList<AwardsComment> getAwardsComments(int awardId) throws Exception;
	public Awards getAwards(int awardId) throws Exception;
	public AwardsComment getAwardsCommentbyId(int commentID);
	public AwardVoteCount getAwardVoteCount(int awardId , int nomineeId);
	public List<AwardVoteCount> getTop3Nominees(int awardId);
	public List<AwardVote> getAwardVoteByid(int awardId);
	public int saveAwards(Awards awards);
	
	/**
	 * 
	 * Strudel 
	 */
	public List<Expense> getExpensesbyTripMember(int memberId);
	public WinningAmount getWinningAmount(int memberId);
	public WinningAmount getWinningbyId(int winningId);
	public Expense getExpensebyId(int expenseId);
}
