package com.tripcaddie.backend.itinerary.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.itinerary.model.Activity;
import com.tripcaddie.backend.itinerary.model.ActivityType;
import com.tripcaddie.backend.trip.model.RoundTeeTime;

@Transactional
@Service("activityyDao")
public class ActivityDaoImpl implements ActivityDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getActivities(int tripid) throws Exception{
		return (ArrayList<Activity>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Activity.class)
				.add(Restrictions.eq("trip.tripId", tripid))
				.addOrder(Order.asc("activityDate"))
				.addOrder(Order.asc("activityTime"))
				.list();
		//return (ArrayList<Activity>)hibernateTemplate.find("from Activity where trip.tripId="+tripid+" ORDER BY activityDate asc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ActivityType> getActivityType() throws Exception {
		return (ArrayList<ActivityType>)hibernateTemplate.find("from ActivityType");
	}

	@Override
	public void addActivity(Activity activity) throws Exception {
		hibernateTemplate.saveOrUpdate(activity);
		
	}

	@Override
	public void deleteActivity(Activity activity) throws Exception {
		hibernateTemplate.delete(activity);
		
	}

	@Override
	public void updateActivity(Activity activity) throws Exception {
		hibernateTemplate.saveOrUpdate(activity);
		
	}

	@Override
	public ActivityType getActivityType(int typeId) throws Exception {
		return hibernateTemplate.load(ActivityType.class, typeId);
	}

	@Override
	public Activity getActivity(int activityId) throws Exception {
		return hibernateTemplate.load(Activity.class, activityId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getActivitiesforLeaderbrd(int tripid, int activitytype)
			throws Exception {
		return hibernateTemplate.find("from Activity where trip.tripId="+tripid+" and activityType.activityTypeId="+activitytype);
	}
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Activity> getGolfSchedule(int tripId,
			ActivityType activityType) throws Exception {
		return (ArrayList<Activity>) hibernateTemplate.find("from Activity where trip.tripId="+tripId+" and activityType.activityType='"+activityType.getActivityType()+"' ORDER BY activityDate ASC");
	}

	@Override
	public Integer saveTeeTime(RoundTeeTime roundTeeTime) throws Exception {
		
		hibernateTemplate.saveOrUpdate(roundTeeTime);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		
		return roundTeeTime.getTeeTimeId();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<RoundTeeTime> getTeeTimes(int activityID) throws Exception {
		return (ArrayList<RoundTeeTime>) hibernateTemplate.find("from RoundTeeTime where activity.activityId="+activityID) ;
	}

	@Override
	public RoundTeeTime getTeeTime(int teeTimeId) throws Exception {

		return hibernateTemplate.load(RoundTeeTime.class, teeTimeId);
	}

	@Override
	public void deleteTeeTime(RoundTeeTime roundTeeTime) throws Exception {
		
		hibernateTemplate.delete(roundTeeTime);
		
	}


}
