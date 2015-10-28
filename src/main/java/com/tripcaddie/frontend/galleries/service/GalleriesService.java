package com.tripcaddie.frontend.galleries.service;

import java.util.ArrayList;
import java.util.List;

import com.tripcaddie.backend.galleries.model.PictureFile;
import com.tripcaddie.backend.galleries.model.VideoFile;
import com.tripcaddie.backend.trip.model.Expense;
import com.tripcaddie.frontend.galleries.dto.AwardCommentDto;
import com.tripcaddie.frontend.galleries.dto.AwardsDto;
import com.tripcaddie.frontend.galleries.dto.PictureCommentDto;
import com.tripcaddie.frontend.galleries.dto.PictureFileDto;
import com.tripcaddie.frontend.galleries.dto.VideoCommentDto;
import com.tripcaddie.frontend.galleries.dto.VideoFileDto;
import com.tripcaddie.frontend.itinerary.dto.RoundScoreDto;
import com.tripcaddie.frontend.trip.dto.ExpenseDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public interface GalleriesService {

	public ArrayList<PictureFileDto> getPicturesByDate(int tripId) throws Exception;
	public ArrayList<PictureFileDto> getPicturesByRating(int tripId) throws Exception;
	public ArrayList<PictureFileDto> getPicturesByFav(int tripId) throws Exception;
	public ArrayList<PictureFile> getPicturesFileByDate(int tripId) throws Exception;
	
	public PictureFileDto getPicture(int pictureId,int tripId) throws Exception;
	public PictureFile getPictureFile(int pictureId,int tripId) throws Exception;
	public void deletePicture(int pictureId,int tripId) throws Exception;
	public void editPicture(int pictureId,int tripId,String description) throws Exception;
	public int getMaxPictureId() throws Exception;
	public void addPicture(int tripId,String filename,String description) throws Exception;
	
	public int getNoOfcomments(int pictureId) throws Exception;
	public ArrayList<PictureCommentDto> getPictureComments(int pictureId) throws Exception;
	public void addComment(int pictureId,String comment) throws Exception;
	public void deleteComment(int commentId) throws Exception;
	public void editPictureComment(int commentId,String comment) throws Exception;
	public void updateorAddPicRating(int tripId, int pictureId, int rating) throws Exception;

	public ArrayList<AwardsDto> getAwardsWonForUserId(int userId) throws Exception;
	
	/**
	 * Generics
	 * 
	 */

	void updateEntity(Object entity);
	
	/**
	 * 
	 *  videos
	 * 
	 */
	public ArrayList<VideoFileDto> getVideosByDate(int tripId) throws Exception;
	public ArrayList<VideoFileDto> getVideosByRating(int tripId) throws Exception;
	public ArrayList<VideoFileDto> getVideosByFav(int tripId) throws Exception;
	public ArrayList<VideoFile> getVideosFileByDate(int tripId) throws Exception;
	
	public int getNoOfCommentsperVideo(int videoid) throws Exception;
	public int getMaxIdInVideo() throws Exception;
	public void addVideo(int tripId,String filename,String description,String tbimagename) throws Exception;
	public VideoFileDto getVideo(int videoId , int tripId) throws Exception;
	public VideoFile getVideoFile(int videoId , int tripId) throws Exception;
	public ArrayList<VideoCommentDto> getVideoComments(int videoId) throws Exception;
	public void addCommentforVideo(int videoId,String comment) throws Exception;
	public void deleteCommentforVideo(int commentId) throws Exception;
	public void editCommentforVideo(int commentId,String comment) throws Exception;
	public void updateorAddVideoRating(int tripId, int videoId, int rating) throws Exception;
	
	
	/**
	 * Awards
	 * @throws Exception 
	 * 
	 * 
	 */
	
	public List<AwardsDto> getAwardsWon(int tripId) throws Exception;
	public List<AwardsDto> getAwardsPending(int tripId) throws Exception;
	public int getMaxAwardId();
	public void createPoll(String awardname, String imagename,
	String pollenddate, String tripId) throws Exception;
	public int insertAwardnow(String awardname, String imagename,
			 String tripId) throws Exception;
	public void insertNomineforAwardNow(int awardId ,String memberId) throws Exception;
	public ArrayList<AwardCommentDto> getAwardComment(int awardId) throws Exception;
	public void editAwardComment(int commentID, String comment) throws Exception;
	public void deleteCommentforAward(int commentID) throws Exception;
	public void addCommentforAward(int awardid, String comment ,String datetime) throws Exception;
	public void deleteAward(int awardId, int tripId) throws Exception;
	public void insertAwardsVote(String tripId, String awardId, String nomineeId) throws Exception;
	
	
	/**
	 * 
	 * Strudel
	 */
	
	public List<Expense> getExpensesbyTripMember(int memberId);
	
	public List<Object> getCalculatedExpense(int tripId) throws Exception;
	public ExpenseDto getExpense(int ExpenseId);
	public void createExpense(String Title ,String Amount ,String expenseDate ,String memberId) throws Exception;
	public void createWinning(String amount, String memberId) throws Exception;
	public void editWinning(String amount, String winningId);
	public void updateWager(String amount, String tripId) throws Exception;
	public void updateExpense(String title, String amount, String date,
			String expenseId) throws Exception;
	public void updateStLock(int tripId,int lock) throws Exception;
	public void deleteWager(int tripId) throws Exception;
	/**
	 * 
	 * leader board
	 * @throws Exception 
	 */
	public List<Object> getLeaderboardbyTrip(int tripId) throws Exception;
	
	public List<RoundScoreDto> getActivities(int roundId);
	public void updateRound(int roundId, int memberId, int front, int back,
			int total);
	public List<TripMemberDto> setPosition(List<TripMemberDto> memberDtos );
	
	public void updateLock(int tripId,int lock) throws Exception;

	
	
}
