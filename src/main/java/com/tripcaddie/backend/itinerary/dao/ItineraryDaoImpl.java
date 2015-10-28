package com.tripcaddie.backend.itinerary.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.itinerary.model.Accommodation;
import com.tripcaddie.backend.itinerary.model.Logistics;
import com.tripcaddie.backend.itinerary.model.RoundScore;
import com.tripcaddie.backend.trip.model.Expense;

@Transactional
@Service("itineraryDao")
public class ItineraryDaoImpl implements ItineraryDao {
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Accommodation> getAccommodations(int tripId) throws Exception{
		return (ArrayList<Accommodation>) hibernateTemplate.find("from Accommodation where trip.tripId="+tripId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Logistics> getLogistics(int tripId) throws Exception {
		return (ArrayList<Logistics>) hibernateTemplate.find("from Logistics where trip.tripId="+tripId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Accommodation getAccommoationbyTripId(int tripId) throws Exception {
		
		ArrayList<Accommodation> accommodations=(ArrayList<Accommodation>) hibernateTemplate.find("from Accommodation where trip.tripId="+tripId);
		if (!accommodations.isEmpty()) {
			Accommodation accommodation=accommodations.get(0);
			return accommodation;
		}else
			return null;
	}

	@Override
	public void addAccommodation(Accommodation accommodation)
			throws Exception {
		hibernateTemplate.save(accommodation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Accommodation getAccommoationbyAccommodationId(int accommodationID)
			throws Exception {
		
		ArrayList<Accommodation> accommodations=(ArrayList<Accommodation>) hibernateTemplate.find("from Accommodation where accommodationId="+accommodationID);
		if (!accommodations.isEmpty()) {
			Accommodation accommodation=accommodations.get(0);
			return accommodation;
		}else
			return null;
	}
	
	@Override
	public void deleteAccommodation(Accommodation accommodation)
			throws Exception {
		hibernateTemplate.delete(accommodation);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Logistics getLogisticsbyLogisticsId(int logisticsID)
			throws Exception {
		ArrayList<Logistics> logistics=(ArrayList<Logistics>)hibernateTemplate.find("from Logistics where logisticsId="+logisticsID);
		if(!logistics.isEmpty()){
			Logistics logistic=logistics.get(0);
			return logistic;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Logistics getLogisticsbyTripId(int tripId) throws Exception {
	
		ArrayList<Logistics> logistics=(ArrayList<Logistics>) hibernateTemplate.find("from Logistics where trip.tripId="+tripId);
		if (!logistics.isEmpty()) {
			Logistics logistic=logistics.get(0);
			return logistic;
		}else
			return null;
	}

	@Override
	public void addLogistics(Logistics logistics) throws Exception {
		hibernateTemplate.save(logistics);
	}

	@Override
	public void deleteLogistics(Logistics logistics) throws Exception {
		hibernateTemplate.delete(logistics);
		
	}

	@Override
	public void editAccommodation(Accommodation accommodation) throws Exception {
		hibernateTemplate.update(accommodation);		
	}

	@Override
	public void editLogistics(Logistics logistics) throws Exception {
		hibernateTemplate.update(logistics);
		
	}

	@Override
	public List<RoundScore> getRoundScore(int memberId) {
		return (ArrayList<RoundScore>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(RoundScore.class)
				.add(Restrictions.eq("member.memberId", memberId))
				.addOrder(Order.asc("activity.activityId")).list();
		 
	}

	@Override
	public RoundScore getRoundScorebyActivityndTrip(int activityId, int memberId) {
		Criterion rest1= Restrictions.eq("member.memberId", memberId);
		Criterion rest2= Restrictions.eq("activity.activityId", activityId);
		List<RoundScore> scores=(ArrayList<RoundScore>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(RoundScore.class)
				.add(Restrictions.and(rest1, rest2)).list();
		if(scores!=null && !scores.isEmpty()){
			return scores.get(0);
		}
		return null;
	}

	@Override
	public void insertEntity(Object entity) {
		hibernateTemplate.save(entity);
	}

	@Override
	public List<RoundScore> getRoundScores(int activityId) {
		return (ArrayList<RoundScore>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(RoundScore.class)
				.add(Restrictions.eq("activity.activityId", activityId)).list();
	}

}
