package com.tripcaddie.backend.bucketList.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.bucketList.model.BucketList;
import com.tripcaddie.backend.bucketList.model.GolfClubInquiry;
import com.tripcaddie.backend.bucketList.model.GolfCourseReview;
import com.tripcaddie.backend.bucketList.model.VisitedPlace;
import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.backend.user.model.TripCaddieUser;

@Transactional
@Service("bucketListDao")
public class BucketListDaoImpl implements BucketListDao {
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public int getBucketListCount(int userId, int playedId) throws DataAccessException{
		
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BucketList.class)
				.add(Restrictions.eq("bucketListPK.tripCaddieUser.userId", userId))
				.add(Restrictions.eq("placesPlayed.placesPlayedId", playedId))
				.setProjection(Projections.rowCount());
				
		return ((Integer)criteria.list().get(0)).intValue();
	}

	@Override
	public void saveOrUpdateBucketList(BucketList bucketList) throws DataAccessException {
		
		hibernateTemplate.saveOrUpdate(bucketList);
	}

	@Override
	public VisitedPlace getPlaceVisitedStatus(int playedId) throws DataAccessException {
		return hibernateTemplate.load(VisitedPlace.class, playedId);
	}

	@Override
	public ArrayList<VisitedPlace> getPlaceVisitedStatus() throws DataAccessException {
		return (ArrayList<VisitedPlace>) hibernateTemplate.loadAll(VisitedPlace.class);
	}
	
	@Override
	public ArrayList<BucketList> getBucketLists(TripCaddieUser user,
			int placeVisitedId) throws DataAccessException{
		
		@SuppressWarnings("unchecked")
		ArrayList<BucketList> bucketLists=(ArrayList<BucketList>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BucketList.class)
				.add(Restrictions.eq("bucketListPK.tripCaddieUser.userId", user.getUserId()))
				.add(Restrictions.eq("placesPlayed.placesPlayedId", placeVisitedId))
				.setFirstResult(0)
				.setMaxResults(10)
				.addOrder(Order.asc("priority"))
				.list();
		
		return bucketLists;
	}
	
	@Override
	public void updateReview(GolfCourseReview review) throws DataAccessException{
		hibernateTemplate.update(review);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public BucketList getBucketList(int userId, int courseId, int visitedId) {
		
		BucketList bucketList=null;
		ArrayList<BucketList> bucketLists=(ArrayList<BucketList>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BucketList.class)
				.add(Restrictions.eq("bucketListPK.tripCaddieUser.userId", userId))
				.add(Restrictions.eq("placesPlayed.placesPlayedId", visitedId))
				.add(Restrictions.eq("bucketListPK.golfCourse.golfCourseId", courseId))
				.list();
		
		if(bucketLists != null && !bucketLists.isEmpty())
			bucketList=bucketLists.get(0);
		return bucketList;
	}

	@Override
	public BucketList getBucketListByPriority(int userId, int priority,
			int visitedId) {
		
		BucketList bucketList=null;
		@SuppressWarnings("unchecked")
		ArrayList<BucketList> bucketLists=(ArrayList<BucketList>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BucketList.class)
				.add(Restrictions.eq("bucketListPK.tripCaddieUser.userId", userId))
				.add(Restrictions.eq("placesPlayed.placesPlayedId", visitedId))
				.add(Restrictions.eq("priority", priority))
				.list();
		
		if(bucketLists !=null && !bucketLists.isEmpty())
			bucketList=bucketLists.get(0);
		return bucketList;
	}
	
	@Override
	public void deleteFromList(BucketList bucketList) throws DataAccessException{
			hibernateTemplate.delete(bucketList);	
	}

	@Override
	public void updateBucketList(BucketList bucketList) throws DataAccessException{
		hibernateTemplate.merge(bucketList);
	}

	@Override
	public int getRecommendationsCount(int courseId) {
		
		Integer count=((Integer)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(GolfCourseReview.class)
				.add(Restrictions.eq("golfCourse.golfCourseId", courseId))
				.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		return count;
	}

	@Override
	public int getInquiryCount(int courseId) {
		
		Integer count=((Integer)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(GolfClubInquiry.class)
				.add(Restrictions.eq("golfCourse.golfCourseId", courseId))
				.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		return count;
	}

	@Override
	public int getRatingCount(int courseId, int visitId) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*@Override
	public int getPlacesWantToGoCount(TripCaddieUser tripCaddieUser) {
		
		int count=((Integer)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BucketList.class)
				.add(Restrictions.eq("bucketListPK.tripCaddieUser.userId", tripCaddieUser.getUserId()))
				.add(Restrictions.eq("placesPlayed.status", "Not visited"))
				.setProjection(Projections.rowCount())
				.list().get(0)).intValue();				
		return count;
	}

	@Override
	public int getPlacesIHaveBeenCount(TripCaddieUser tripCaddieUser) {
		int count=((Integer)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BucketList.class)
				.add(Restrictions.eq("bucketListPK.tripCaddieUser.userId", tripCaddieUser.getUserId()))
				.add(Restrictions.eq("placesPlayed.status", "visited"))
				.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
				
		return count;
	}*/

	@Override
	public int getInquiryCount(TripCaddieUser tripCaddieUser) {
		//Need clarifications				
		return 0;
	}

	@Override
	public int getReviewCount(TripCaddieUser tripCaddieUser) {
		int count=((Integer)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(GolfCourseReview.class)
				.add(Restrictions.eq("user.userId", tripCaddieUser.getUserId()))
				.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		return count;
	}

	@Override
	public boolean isExistInList(int courseId, int userId) {
		
		boolean flag;
		int exist=((Integer)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BucketList.class)
				.add(Restrictions.eq("bucketListPK.tripCaddieUser.userId", userId))
				.add(Restrictions.eq("bucketListPK.golfCourse.golfCourseId", courseId))
				.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		if(exist==1)
			flag=true;
		else
			flag=false;
		return flag;
	}

	@Override
	public String getVisitedorNotStatus(int courseId, int userId) {
		@SuppressWarnings("unchecked")
		ArrayList<BucketList> bucketLists=(ArrayList<BucketList>)hibernateTemplate.find("from BucketList where bucketListPK.tripCaddieUser.userId="+userId+" and bucketListPK.golfCourse.golfCourseId="+courseId);
		if(!bucketLists.isEmpty()){
			return bucketLists.get(0).getPlacesPlayed().getStatus();
		}else
			return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GolfClubInquiry> getMyInquiries(int userId,int firstRow) throws DataAccessException,Exception{
		//return (ArrayList<GolfClubInquiry>)hibernateTemplate.find("from GolfClubInquiry where user.userId="+userId);
		return (ArrayList<GolfClubInquiry>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(GolfClubInquiry.class)
				.add(Restrictions.eq("user.userId", userId))
				.addOrder(Order.desc("createdDate"))
				.setFirstResult(firstRow)
				.setMaxResults(10)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GolfCourseReview> getMyReviews(int userId,int firstRow) throws DataAccessException,Exception {
		//return (ArrayList<GolfCourseReview>)hibernateTemplate.find("from GolfCourseReview where user.userId="+userId);
		return (ArrayList<GolfCourseReview>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(GolfCourseReview.class)
				.add(Restrictions.eq("user.userId", userId))
				.addOrder(Order.desc("createdDate"))
				.setFirstResult(firstRow)
				.setMaxResults(10)
				.list();
	}

	@Override
	public int getRowCountForMyInquiries(int userId) throws DataAccessException,
			Exception {
		return ((Integer)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(GolfClubInquiry.class)
				.add(Restrictions.eq("user.userId", userId))
				.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
	}

	@Override
	public int getRowCountForMyReviews(int userId) throws DataAccessException, Exception {
		
		return ((Integer)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(GolfCourseReview.class)
				.add(Restrictions.eq("user.userId", userId))
				.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public BucketList getBucketList(TripCaddieUser user, GolfCourse course)
			throws Exception {
		
		ArrayList<BucketList> bucketLists=(ArrayList<BucketList>)hibernateTemplate.find("from BucketList where bucketListPK.tripCaddieUser.userId="+user.getUserId()+" and bucketListPK.golfCourse.golfCourseId="+course.getGolfCourseId());
		
		if (!bucketLists.isEmpty()) {
			return bucketLists.get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public ArrayList<BucketList> getAllBucketLists(int userID,
			int placeVisitedId,int priority) throws Exception {
		ArrayList<BucketList> bucketLists=(ArrayList<BucketList>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BucketList.class)
				.add(Restrictions.eq("bucketListPK.tripCaddieUser.userId", userID))
				.add(Restrictions.eq("placesPlayed.placesPlayedId", placeVisitedId))
				.add(Restrictions.ge("priority", priority))
				.addOrder(Order.asc("priority"))
				.list();
		
		return bucketLists;
	}

	@Override
	public void saveOrUpdateCollection(List<BucketList> bucketLists) {
		hibernateTemplate.saveOrUpdateAll(bucketLists);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GolfClubInquiry> getInquiries(int golfCourseId)
			throws Exception {
		
		return (ArrayList<GolfClubInquiry>)hibernateTemplate.find("from GolfClubInquiry where golfCourse.golfCourseId="+golfCourseId);
		
	}	

}
