package com.tripcaddie.backend.trip.dao;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.trip.model.RecentUpdate;
import com.tripcaddie.frontend.trip.dto.TripDto;

@Transactional
@Service("recentUpdateDao")
public class RecentUpdateDaoImpl implements RecentUpdateDao{

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public void saveEntity(Object entity) throws Exception {
		
		hibernateTemplate.save(entity);
		
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<RecentUpdate> getRecentUpdatesByDate(TripDto trip,String typeOfUser) throws Exception {
		ArrayList<RecentUpdate> updates=null;
		if(typeOfUser.equalsIgnoreCase("participant")){
			 updates=(ArrayList<RecentUpdate>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(RecentUpdate.class)
					.add(Restrictions.eq("trip.tripId", trip.getTripId()))
					.add(Restrictions.eq("typeOfUser", typeOfUser))
					.addOrder(Order.desc("updatedDate"))
					.setFirstResult(0)
					.setMaxResults(20)
					.list();
		}else{
		 updates=(ArrayList<RecentUpdate>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(RecentUpdate.class)
					.add(Restrictions.eq("trip.tripId", trip.getTripId()))
					.addOrder(Order.desc("updatedDate"))
					.setFirstResult(0)
					.setMaxResults(20)
					.list();
		}		
		return updates;
		
	}
	
}
