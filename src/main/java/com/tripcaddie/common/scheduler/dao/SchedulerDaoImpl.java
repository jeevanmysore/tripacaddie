package com.tripcaddie.common.scheduler.dao;

import java.util.ArrayList;
import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.user.model.TripCaddieUser;

@Transactional
@Service("schedulerDao")
public class SchedulerDaoImpl implements SchedulerDao{

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Trip> getAllTrips(String today) throws Exception {

		return (ArrayList<Trip>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Trip.class)
				.add(Restrictions.like("startDate",today ))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TripCaddieUser> getUsers(String today) throws Exception {
		
		/*ArrayList<TripCaddieUser> users=(ArrayList<TripCaddieUser>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(TripCaddieUser.class)
				.add(Restrictions.eq("birthdayEmailSend", 1))
				.add(Restrictions.like("dateOfBirth", date))
				.list();*/
		ArrayList<TripCaddieUser> users=(ArrayList<TripCaddieUser>) hibernateTemplate.find("from TripCaddieUser where dateOfBirth like '"+today+"'");
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TripCaddieUser getUserByUsername(String username) throws Exception {
		
		ArrayList<TripCaddieUser> users=(ArrayList<TripCaddieUser>) hibernateTemplate.find("from TripCaddieUser where userName='"+username+"'");
		if(!users.isEmpty())
			return users.get(0);
		else
			return null;
	}

}
