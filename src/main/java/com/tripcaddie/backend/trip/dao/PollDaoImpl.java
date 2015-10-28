package com.tripcaddie.backend.trip.dao;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.trip.model.PollOption;
import com.tripcaddie.backend.trip.model.PollQuestions;

@Transactional
@Service("pollDao")
public class PollDaoImpl implements PollDao{

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public void saveOrUpdateEntity(Object entity) throws Exception {
		
		hibernateTemplate.saveOrUpdate(entity);
		
	}

	@Override
	public PollQuestions getPollQuestion(int pollQuestionId) throws Exception {
		return hibernateTemplate.load(PollQuestions.class, pollQuestionId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<PollQuestions> getPollQuestions(int tripId)
			throws Exception {
		return (ArrayList<PollQuestions>) hibernateTemplate.find("from PollQuestions where trip.tripId="+tripId);
	}

	@Override
	public int addPollQuestions(PollQuestions pollQuestions) throws Exception {
		hibernateTemplate.saveOrUpdate(pollQuestions);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return pollQuestions.getPollId();
	}

	@Override
	public PollOption getOption(int optionId) throws Exception {
		
		return hibernateTemplate.load(PollOption.class, optionId);
	}

	@Override
	public void deleteEntity(Object entity) throws Exception {
		
		hibernateTemplate.delete(entity);
		
	}
}
