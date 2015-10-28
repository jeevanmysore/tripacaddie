package com.tripcaddie.backend.courses.searchDao;

import java.util.ArrayList;
import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.bucketList.model.GolfClubInquiry;
import com.tripcaddie.backend.bucketList.model.GolfClubAdviceResponse;
import com.tripcaddie.backend.bucketList.model.GolfCourseReview;
import com.tripcaddie.backend.courses.model.CourseRatingDetails;
import com.tripcaddie.backend.courses.model.GolfCourse;
@Transactional
@Service("courseDao")
public class CourseDaoImpl implements CourseDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
		
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GolfCourse> getGolfCourses(String keyColumn,
			String destination) throws DataAccessException,Exception{
		try{
			return (ArrayList<GolfCourse>) hibernateTemplate.find("from GolfCourse where "+keyColumn+" ='"+destination+"'");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public GolfCourse getGolfCourse(int courseId) throws DataAccessException,Exception {
		GolfCourse golfCourse= hibernateTemplate.load(GolfCourse.class, courseId);
		return golfCourse;
	}

	@Override
	public void updateCourseDetails(GolfCourse course) throws DataAccessException,Exception{
			hibernateTemplate.update(course);	
	}

	@Override
	public GolfClubInquiry getAdvice(int adviceId) throws DataAccessException,Exception {
		
		GolfClubInquiry inquiry= hibernateTemplate.load(GolfClubInquiry.class, adviceId);
		return inquiry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GolfClubInquiry> getAdvices(int courseId) throws DataAccessException,Exception{
		return (ArrayList<GolfClubInquiry>) hibernateTemplate.find("from GolfClubInquiry where golfCourse.golfCourseId="+courseId);
	}

	@Override
	public GolfCourseReview getReview(int reviewId) throws DataAccessException,Exception{
		return hibernateTemplate.get(GolfCourseReview.class, reviewId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GolfCourseReview> getReviews(int courseId) throws DataAccessException,Exception{
		return (ArrayList<GolfCourseReview>) hibernateTemplate.find("from GolfCourseReview where golfCourse.golfCourseId="+courseId);
	}

	

	@Override
	public void updateAdviceResponse(
			GolfClubAdviceResponse golfClubAdviceResponse) throws DataAccessException,Exception{
		hibernateTemplate.update(golfClubAdviceResponse);		
	}

	@Override
	public GolfClubAdviceResponse getAdviceResponse(int responseId) throws DataAccessException,Exception{
		return hibernateTemplate.load(GolfClubAdviceResponse.class, responseId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GolfClubAdviceResponse> getAdviceResponses(int adviceId) throws DataAccessException,Exception{
		return (ArrayList<GolfClubAdviceResponse>) hibernateTemplate.find("from GolfClubAdviceResponse where inquiry.golfClubInquiryId="+adviceId);
	}

	@Override
	public int storeResponse(GolfClubAdviceResponse golfClubAdviceResponse) throws DataAccessException,Exception {
		hibernateTemplate.save(golfClubAdviceResponse);		
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return golfClubAdviceResponse.getResponseId();
	}

	@Override
	public int storeAdvice(GolfClubInquiry golfClubAdviceRequest) throws DataAccessException,Exception{
		try{
			hibernateTemplate.save(golfClubAdviceRequest);	
			hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		}catch (Exception e) {
			System.out.println("In couresDao Impl");
			e.printStackTrace();
		}
		return golfClubAdviceRequest.getGolfClubInquiryId();
	}

	@Override
	public int storeReview(GolfCourseReview golfCourseReview) throws DataAccessException,Exception{
		hibernateTemplate.save(golfCourseReview);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return golfCourseReview.getReviewId();
	}

	@Override
	public void updateReview(GolfCourseReview golfCourseReview) throws DataAccessException,Exception{
		try{
			hibernateTemplate.merge(golfCourseReview);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getNoOfLikes(int reviewId) throws Exception{
		GolfCourseReview courseReview=(GolfCourseReview)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(GolfCourseReview.class)
				.add(Restrictions.eq("reviewId", reviewId))
				.list().get(0);
		
		return ((Integer)courseReview.getUsers().size()).intValue();
	}

	/*@SuppressWarnings("unchecked")
	public int getMaxIdForGolfClubInquiry(){
		try{
			String sql="select max(golfClubAdviceRequestId) from GolfClubInquiry";
			List<Integer> list=hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql).list();
			return list.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
		
	}*/
	



	@Override
	public void saveOrUpdateEntity(Object entity) throws Exception {
		
		hibernateTemplate.saveOrUpdate(entity);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<CourseRatingDetails> getCourseRatingDetails(int courseId)
			throws Exception {
		return (ArrayList<CourseRatingDetails>) hibernateTemplate.find("from CourseRatingDetails where courseRatingDetailsPK.golfCourse.golfCourseId="+courseId);
	}

	@Override
	public void deleteEntity(Object entity) throws Exception {
		
		hibernateTemplate.delete(entity);
		
	}
}
