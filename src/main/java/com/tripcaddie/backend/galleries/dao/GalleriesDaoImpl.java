package com.tripcaddie.backend.galleries.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Transactional
@Service("galleriesDao")
public class GalleriesDaoImpl implements GalleriesDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<PictureFile> getPicturesByDate(int tripId)
			throws Exception {
		ArrayList<PictureFile> updatedPictureObjects=new ArrayList<PictureFile>();
		ArrayList<PictureFile> pictureFiles=(ArrayList<PictureFile>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(PictureFile.class)
				.add(Restrictions.eq("trip.tripId", tripId))
				.addOrder(Order.desc("createdDate"))
				.list();
	updatingPictureEntity(updatedPictureObjects, pictureFiles);
		return updatedPictureObjects;
	}

	private void updatingPictureEntity(
			ArrayList<PictureFile> pictureFileswithrating,
			ArrayList<PictureFile> pictureFiles) {
		for (PictureFile pictureFile : pictureFiles) {
				
				List<RatingPictureFile> ratingPictureFiles=getPictureAvgRating(pictureFile.getPictureId());
				double avg=0;
				for(RatingPictureFile ratingPictureFile:ratingPictureFiles){
					avg=avg+ratingPictureFile.getRating();
				}
				
				//update avg rating in DB
				if(ratingPictureFiles!=null && !ratingPictureFiles.isEmpty()){
				pictureFile.setAvgRating((avg/ratingPictureFiles.size()));
				}
				else{
					pictureFile.setAvgRating((double) 0);
				}
				pictureFile.setNooffav(pictureFile.getFavouritePicture().size());
				updateEntity(pictureFile);
				pictureFileswithrating.add(pictureFile);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PictureFile getPicture(int pictureId,int tripId) throws Exception {
		PictureFile pictureFile =null;
		ArrayList<PictureFile> pictureFiles=(ArrayList<PictureFile>) hibernateTemplate.find("from PictureFile where pictureId="+pictureId+" and trip.tripId="+tripId);
		if(!pictureFiles.isEmpty()){
			pictureFile=pictureFiles.get(0);
		}
		return pictureFile;
	}

	@Override
	public int getMaxIdInPicture() throws Exception {
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(PictureFile.class)
				.setProjection(Projections.rowCount());
				
		return ((Integer) (criteria.list().get(0))).intValue();
	}

	@Override
	public void addPicture(PictureFile pictureFile) throws Exception {
		hibernateTemplate.saveOrUpdate(pictureFile);		
	}

	@Override
	public int getNoOfcomments(int pictureId) throws Exception {
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(PictureComment.class)
				.add(Restrictions.eq("pictureFile.pictureId", pictureId))
				.setProjection(Projections.rowCount());
		return ((Integer)(criteria.list().get(0))).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<PictureComment> getPictureComments(int pictureId)
			throws Exception {
		return (ArrayList<PictureComment>) hibernateTemplate.find("from PictureComment where pictureFile.pictureId="+pictureId+" ORDER BY lastUpdatedDate DESC");
	}

	@Override
	public void deletePicture(PictureFile pictureFile) throws Exception {
		hibernateTemplate.delete(pictureFile);		
	}

	@Override
	public void addComment(PictureComment pictureComment) throws Exception {
		hibernateTemplate.saveOrUpdate(pictureComment);
		
	}

	@Override
	public void deleteComment(Object entity) throws Exception {
		hibernateTemplate.delete(entity);
		
	}

	@Override
	public PictureComment getPictureComment(int commentId) throws Exception {
		
		return hibernateTemplate.load(PictureComment.class, commentId);
	}

	@Override
	public void editComment(Object entity)
			throws Exception {
		
		hibernateTemplate.saveOrUpdate(entity);
		
	}

	@Override
	public void editPicture(PictureFile pictureFile) throws Exception {
		
		hibernateTemplate.saveOrUpdate(pictureFile);
		
		
	}
	
		
	@Override
	public ArrayList<VideoFile> getVideosByDate(int tripId) throws Exception {
		ArrayList<VideoFile> updatedVideoObjects=new ArrayList<VideoFile>();
		ArrayList<VideoFile> videoFiles=(ArrayList<VideoFile>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(VideoFile.class)
				.add(Restrictions.eq("trip.tripId", tripId))
				.addOrder(Order.desc("createdDate"))
				.list();
		updatingVideoEntity(updatedVideoObjects, videoFiles);
		
		return updatedVideoObjects;
	}

	private void updatingVideoEntity(ArrayList<VideoFile> videoFileswithrating,
			ArrayList<VideoFile> videoFiles) {
		for (VideoFile videoFile : videoFiles) {
			List<RatingVideoFile> ratingVideoFiles=getVideoAvgRating(videoFile.getVideoId());
			double avg=0;
			for(RatingVideoFile ratingVideoFile:ratingVideoFiles){
				avg=avg+ratingVideoFile.getRating();
			}
			if(ratingVideoFiles!=null && !ratingVideoFiles.isEmpty()){
			videoFile.setAvgRating((avg/ratingVideoFiles.size()));
			}
			else{
				videoFile.setAvgRating((double) 0);
			}
			videoFile.setNooffav(videoFile.getFavouriteVideo().size());
			updateEntity(videoFile);
			videoFileswithrating.add(videoFile);
		}
	}

	@Override
	public int getNoOfCommentsperVideo(int videoid) throws Exception {
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(VideoComment.class)
				.add(Restrictions.eq("videoFile.videoId", videoid))
				.setProjection(Projections.rowCount());
		return ((Integer)(criteria.list().get(0))).intValue();
	}

	@Override
	public int getMaxIdInVideo() throws Exception {
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(VideoFile.class)
				.setProjection(Projections.rowCount());
				
		return ((Integer) (criteria.list().get(0))).intValue();
	}

	@Override
	public void addVideo(VideoFile videoFile) throws Exception {
		hibernateTemplate.save(videoFile);
	}

	@Override
	public VideoFile getVideo(int videoId, int tripId) throws Exception {
		VideoFile videoFile =null;
		ArrayList<VideoFile> videoFiles=(ArrayList<VideoFile>) hibernateTemplate.find("from VideoFile where videoId="+videoId+" and trip.tripId="+tripId);
		if(!videoFiles.isEmpty()){
			videoFile=videoFiles.get(0);
		}
		return videoFile;
	}

	@Override
	public ArrayList<VideoComment> getVideoComments(int videoId)
			throws Exception {
		return (ArrayList<VideoComment>) hibernateTemplate.find("from VideoComment where videoFile.videoId="+videoId+" ORDER BY lastUpdatedDate DESC");
		
	}

	@Override
	public void addCommentforVideo(VideoComment videoComment) throws Exception {
		hibernateTemplate.save(videoComment);
	}

	@Override
	public VideoComment getVideoComment(int commentId) throws Exception {
		return hibernateTemplate.load(VideoComment.class, commentId);
	}

	@Override
	public void updateEntity(Object entity) {
		hibernateTemplate.update(entity);
	}

	@Override
	public PictureFile getPicture(int pictureId) throws Exception {
		return hibernateTemplate.load(PictureFile.class, pictureId);
	}

	@Override
	public VideoFile getVideo(int videoId) throws Exception {
		return hibernateTemplate.load(VideoFile.class, videoId);
	}

	@Override
	public void saveorUpdateEntity(Object entity) {
		hibernateTemplate.saveOrUpdate(entity);
		
	}

	@Override
	public RatingPictureFile getPictureRating(int pictureId, int tripmemberid) {
		List<RatingPictureFile> ratingPictureFiles=hibernateTemplate.find("from RatingPictureFile where ratingPictureFilePK.pictureFile.pictureId="+pictureId+" and ratingPictureFilePK.tripMember.memberId="+tripmemberid);
		if(ratingPictureFiles!=null && !ratingPictureFiles.isEmpty()){
			return ratingPictureFiles.get(0);
		}
		return null;
	}

	@Override
	public RatingVideoFile getVideoRating(int videoId, int memberId) {
		List<RatingVideoFile> ratingVideoFiles=hibernateTemplate.find("from RatingVideoFile where ratingVideoFilePK.videoFile.videoId="+videoId+" and ratingVideoFilePK.tripMember.memberId="+memberId);
		if(ratingVideoFiles!=null && !ratingVideoFiles.isEmpty()){
			return ratingVideoFiles.get(0);
		}
		return null;
	}

	@Override
	public List<RatingPictureFile> getPictureAvgRating(int pictureId) {
		List<RatingPictureFile> ratingPictureFiles=hibernateTemplate.find("from RatingPictureFile where ratingPictureFilePK.pictureFile.pictureId="+pictureId);
		return ratingPictureFiles;
	}

	@Override
	public List<RatingVideoFile> getVideoAvgRating(int videoId) {
		List<RatingVideoFile> ratingVideoFiles=hibernateTemplate.find("from RatingVideoFile where ratingVideoFilePK.videoFile.videoId="+videoId);
		return ratingVideoFiles;
	}

	@Override
	public ArrayList<PictureFile> getPicturesByRating(int tripId)
			throws Exception {
		ArrayList<PictureFile> updatedPictureObjects=new ArrayList<PictureFile>();
		ArrayList<PictureFile> pictureFiles=(ArrayList<PictureFile>) hibernateTemplate.find("from PictureFile where trip.tripId="+tripId+" ORDER BY avgRating DESC");
		updatingPictureEntity(updatedPictureObjects, pictureFiles);
		return updatedPictureObjects;
	}

	@Override
	public ArrayList<PictureFile> getPicturesByFav(int tripId) throws Exception {
		ArrayList<PictureFile> updatedPictureObjects=new ArrayList<PictureFile>();
		ArrayList<PictureFile> pictureFiles=(ArrayList<PictureFile>) hibernateTemplate.find("from PictureFile where trip.tripId="+tripId+" ORDER BY nooffav DESC");
		updatingPictureEntity(updatedPictureObjects, pictureFiles);
		return updatedPictureObjects;
	}

	@Override
	public ArrayList<VideoFile> getVideosByRating(int tripId) throws Exception {
		ArrayList<VideoFile> updatedVideoObjects=new ArrayList<VideoFile>();
		ArrayList<VideoFile> videoFiles=(ArrayList<VideoFile>) hibernateTemplate.find("from VideoFile where trip.tripId="+tripId+" ORDER BY avgRating DESC");
		updatingVideoEntity(updatedVideoObjects, videoFiles);
		return updatedVideoObjects;
	}

	@Override
	public ArrayList<VideoFile> getVideosByFav(int tripId) throws Exception {
		ArrayList<VideoFile> updatedVideoObjects=new ArrayList<VideoFile>();
		ArrayList<VideoFile> videoFiles=(ArrayList<VideoFile>) hibernateTemplate.find("from VideoFile where trip.tripId="+tripId+" ORDER BY nooffav DESC");
		updatingVideoEntity(updatedVideoObjects, videoFiles);
		return updatedVideoObjects;
	}

	@Override
	public int getMaxAwardId() {
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Awards.class)
				.setProjection(Projections.rowCount());
				
		return ((Integer) (criteria.list().get(0))).intValue();
	}

	@Override
	public List<Awards> getAwardsbyDate(int tripId) {
		ArrayList<Awards> awards=(ArrayList<Awards>) hibernateTemplate.find("from Awards where trip.tripId="+tripId+"and pollenddate < CURDATE() and awarded=0");
		return awards;
	}

	@Override
	public AwardVote getAwardVotebyId(int id) {
		ArrayList<AwardVote> awardVotes=(ArrayList<AwardVote>) hibernateTemplate.find("from AwardVote where award.awardid="+id );
		if(awardVotes!=null && !awardVotes.isEmpty()){
			return awardVotes.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AwardVoteCount getMaxVoteforAward(int id) {
		//ArrayList<AwardVoteCount> awardVoteCounts=(ArrayList<AwardVoteCount>) hibernateTemplate.find("from AwardVoteCount where awardVotePK.award.awardid="+id+" ORDER BY count DESC " );
		ArrayList<AwardVoteCount> awardVoteCounts=(ArrayList<AwardVoteCount>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AwardVoteCount.class)
					.add(Restrictions.eq("award.awardid", id))
					.addOrder(Order.desc("count"))
					.list();
		if(awardVoteCounts!=null && !awardVoteCounts.isEmpty()){
			return awardVoteCounts.get(0);
		}
		return null;
	}

	@Override
	public int getNoOfcommentsAward(int awardId) throws Exception {
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AwardsComment.class)
				.add(Restrictions.eq("award.awardid", awardId))
				.setProjection(Projections.rowCount());
		return ((Integer)(criteria.list().get(0))).intValue();
	}

	@Override
	public ArrayList<AwardsComment> getAwardsComments(int awardId)
			throws Exception {
		return (ArrayList<AwardsComment>) hibernateTemplate.find("from AwardsComment where award.awardid="+awardId+" ORDER BY lastUpdatedDate DESC");
	}

	@Override
	public Awards getAwards(int awardId) throws Exception {
		
		return hibernateTemplate.load(Awards.class, awardId);
	}

	@Override
	public AwardsComment getAwardsCommentbyId(int commentID) {
		return hibernateTemplate.load(AwardsComment.class, commentID);
	}

	@Override
	public void deleteEntity(Object entity) {
		hibernateTemplate.delete(entity);
		
	}

	@Override
	public AwardVoteCount getAwardVoteCount(int awardId, int nomineeId) {
		ArrayList<AwardVoteCount> awardVoteCounts=(ArrayList<AwardVoteCount>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AwardVoteCount.class)
				.add(Restrictions.eq("award.awardid", awardId))
				.add(Restrictions.eq("member.memberId", nomineeId))
				.list();
		if(awardVoteCounts!=null && !awardVoteCounts.isEmpty()){
			return awardVoteCounts.get(0);
		}
		return null;
	}

	@Override
	public List<Awards> getAwardsPendingbyDate(int tripId) {
		ArrayList<Awards> awards=(ArrayList<Awards>) hibernateTemplate.find("from Awards where trip.tripId="+tripId+"and awarded=0 and pollenddate >=CURDATE()-1");
		return awards;
	
		
	}

	@Override
	public List<AwardVoteCount> getTop3Nominees(int awardId) {
		
		@SuppressWarnings("unchecked")
		ArrayList<AwardVoteCount> awardVoteCounts=(ArrayList<AwardVoteCount>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AwardVoteCount.class)
				.add(Restrictions.eq("award.awardid", awardId))
				.addOrder(Order.desc("count"))
				.setMaxResults(3).list();
		
		
		/*hibernateTemplate.getSessionFactory().getCurrentSession().beginTransaction();
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("from AwardVoteCount where awardVotePK.award.awardid="+awardId+" ORDER BY count DESC");
		query.setMaxResults(3); 
		List<AwardVoteCount> awardVoteCounts = query.list();*/
		return awardVoteCounts;
	}

	@Override
	public List<AwardVote> getAwardVoteByid(int awardId) {
		return (ArrayList<AwardVote>) hibernateTemplate.find("from AwardVote where award.awardid="+awardId);
	}

	@Override
	public List<Expense> getExpensesbyTripMember(int memberId) {
		ArrayList<Expense> expenses=(ArrayList<Expense>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Expense.class)
				.add(Restrictions.eq("tripMember.memberId", memberId))
				.addOrder(Order.asc("expenseDate")).list();
		return expenses;
		
	}

	@Override
	public WinningAmount getWinningAmount(int memberId) {
		List<WinningAmount> winningAmounts=hibernateTemplate.find("from WinningAmount where tripMember.memberId="+memberId);
		if(winningAmounts!=null && !winningAmounts.isEmpty()){
			return winningAmounts.get(0);
		}
		return null;
	}

	@Override
	public WinningAmount getWinningbyId(int winningId) {
		return hibernateTemplate.load(WinningAmount.class, winningId);
	}

	@Override
	public Expense getExpensebyId(int expenseId) {
		return hibernateTemplate.load(Expense.class, expenseId);
	}

	@Override
	public void saveEntity(Object entity) {
		hibernateTemplate.save(entity);
		
	}

	@Override
	public List<Awards> getAwardsbydirectlyAwarded(int tripId) {
		ArrayList<Awards> awards=(ArrayList<Awards>) hibernateTemplate.find("from Awards where trip.tripId="+tripId+"and pollenddate < CURDATE()+1 and awarded=1 ");
		return awards;
	}

	@Override
	public int saveAwards(Awards awards) {
		hibernateTemplate.save(awards);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return awards.getAwardid();
		}

}
