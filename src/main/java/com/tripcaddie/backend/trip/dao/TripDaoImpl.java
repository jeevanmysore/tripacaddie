package com.tripcaddie.backend.trip.dao;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.trip.model.PaidMode;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripLeaderDelegation;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.backend.trip.model.TripMemberStatus;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

@Transactional
@Service("tripDao")
public class TripDaoImpl implements TripDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	
	@Override
	public Integer createTrip(Trip trip) throws Exception{
		hibernateTemplate.save(trip);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return trip.getTripId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TripMember> getTripsOfUser(TripCaddieUser tripCaddieUser) throws Exception{
		return (ArrayList<TripMember>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(TripMember.class)
				.createAlias("trip", "trips")
				.add(Restrictions.eq("tripCaddieUser.userId", tripCaddieUser.getUserId()))
				.addOrder(Order.desc("trips.startDate"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Trip getTrip(TripCaddieUser tripCaddieUser,int tripId) throws Exception{
			
		ArrayList<Trip> trips=new ArrayList<Trip>();
		ArrayList<TripMember> tripMembers=(ArrayList<TripMember>) hibernateTemplate.find("from TripMember where tripCaddieUser.userId="+tripCaddieUser.getUserId()+" and trip.tripId="+tripId);
		if(!tripMembers.isEmpty()){
			trips=(ArrayList<Trip>)hibernateTemplate.find("from Trip where tripId="+tripMembers.get(0).getTrip().getTripId());
		}else{ 
			return null;
		}
		if(!trips.isEmpty())
			return trips.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<PaidMode> getPaidStatus() throws Exception {
		return (ArrayList<PaidMode>) hibernateTemplate.find("from PaidMode");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TripMemberStatus> getTripMemberStatus() throws Exception {
		return (ArrayList<TripMemberStatus>) hibernateTemplate.find("from TripMemberStatus");
	}

	@Override
	public void saveTripMember(TripMember tripMember) throws Exception {
		hibernateTemplate.saveOrUpdate(tripMember);		
		
	}

	@Override
	public Trip getTrip(int tripId) throws Exception {
		Trip trip=(Trip) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Trip.class)
				.add(Restrictions.eq("tripId", tripId))
				.list().get(0);
				
		return trip;
	}

	@Override
	public void deleteTrip(Trip trip) throws Exception {
		
		hibernateTemplate.delete(trip);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TripMember> getTripMembers(Trip trip) throws Exception{
		return (ArrayList<TripMember>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(TripMember.class)
				.add(Restrictions.eq("trip.tripId", trip.getTripId()))
				.list();
	}

	@Override
	public void addTripMembers(TripMember tripMember) throws Exception {
		hibernateTemplate.saveOrUpdate(tripMember);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public TripMember getTripMember(int tripId, String email) throws Exception {
		ArrayList<TripMember> tripMembers=(ArrayList<TripMember>)hibernateTemplate.find("from TripMember where trip.tripId="+tripId+" and invitedEmail='"+email+"'");
		if(!tripMembers.isEmpty())
			return tripMembers.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TripMember getTripMember(int memberId) throws Exception {
		ArrayList<TripMember> tripMembers=(ArrayList<TripMember>)hibernateTemplate.find("from TripMember where memberId="+memberId);
		if(!tripMembers.isEmpty())
			return tripMembers.get(0);
		else
			return null;
	}

	//By jeevan
	
	@Override
	public void updateTrip(Trip trip) throws Exception 
	{		
		hibernateTemplate.update(trip);
			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TripMember> getTripsofUserAsTripLeader(Integer userId)throws Exception{
		
		return (ArrayList<TripMember>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(TripMember.class).add(Restrictions.eq("tripCaddieUser.userId",userId))
				.add(Restrictions.eq("roleInTrip.roleId",3))
				.list();
			
	}
	
	@Override
	public void deleteTripMember(TripMember member) throws Exception {
		
		hibernateTemplate.delete(member);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TripMember getTripMember(TripcaddieUserDto tripCaddieUser,int tripId)
			throws Exception {
		ArrayList<TripMember> tripMembers= (ArrayList<TripMember>) hibernateTemplate.find("from TripMember where tripCaddieUser.userId="+tripCaddieUser.getUserId()+" and trip.tripId="+tripId);
		if(!tripMembers.isEmpty()){
			return tripMembers.get(0);
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TripMember> getTripInvitation(String email,int statusId)
			throws Exception {
		return (ArrayList<TripMember>) hibernateTemplate.find("from TripMember where invitedEmail='"+email+"' and tripMemberStatus.tripMemberStatusId="+statusId);
	}

	@Override
	public void updateTripMember(TripMember member) throws Exception {
		
		hibernateTemplate.saveOrUpdate(member);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public TripMember getTripMember(int userId, int tripId) throws Exception {
		ArrayList<TripMember> tripMembers= (ArrayList<TripMember>) hibernateTemplate.find("from TripMember where tripCaddieUser.userId="+userId+" and trip.tripId="+tripId);
		if(!tripMembers.isEmpty()){
			return tripMembers.get(0);
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TripMember> getTripMembersbyOrder(int tripId)
			throws Exception {
		return (ArrayList<TripMember>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(TripMember.class)
				.add(Restrictions.eq("trip.tripId", tripId)).addOrder(Order.asc("memberId"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TripLeaderDelegation> getTripLeaderDelegations()
			throws Exception {
		
		return (ArrayList<TripLeaderDelegation>) hibernateTemplate.find("from TripLeaderDelegation");
	}

	@Override
	public TripLeaderDelegation getTripLeaderDelegation(int optionId)
			throws Exception {
		
		return hibernateTemplate.load(TripLeaderDelegation.class, optionId);
	}
}
