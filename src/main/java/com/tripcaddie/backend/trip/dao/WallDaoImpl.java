package com.tripcaddie.backend.trip.dao;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.trip.model.Wall;
import com.tripcaddie.backend.trip.model.WallComment;

@Transactional
@Service("wallDao")
public class WallDaoImpl implements WallDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public void writeWall(Wall wall) throws Exception {
		
		hibernateTemplate.saveOrUpdate(wall);
	}

	@Override
	public void writeWallComment(WallComment comment) throws Exception {
		
		hibernateTemplate.saveOrUpdate(comment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Wall> getWallContent(int tripID) throws Exception {
		
		ArrayList<Wall> walls=(ArrayList<Wall>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Wall.class)
				.add(Restrictions.eq("trip.tripId", tripID))
				.addOrder(Order.desc("createdDate"))
				.list();
		return walls;
	}

	@Override
	public void deleteWall(Wall wall) throws Exception {
		
		hibernateTemplate.delete(wall);
		
	}

	@Override
	public void deleteComment(WallComment comment) throws Exception {
		
		hibernateTemplate.delete(comment);
		
	}

	@Override
	public Wall getWall(int wallId) throws Exception {
		return hibernateTemplate.load(Wall.class, wallId);
	}

	@Override
	public WallComment getWallComment(int commentId) throws Exception {
		return hibernateTemplate.load(WallComment.class, commentId);
	}

}
