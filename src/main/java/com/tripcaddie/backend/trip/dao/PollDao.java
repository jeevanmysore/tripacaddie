package com.tripcaddie.backend.trip.dao;

import java.util.ArrayList;

import com.tripcaddie.backend.trip.model.PollOption;
import com.tripcaddie.backend.trip.model.PollQuestions;

public interface PollDao {

	//Generic method save or update
	public void saveOrUpdateEntity(Object entity) throws Exception;
	public void deleteEntity(Object entity) throws Exception;
	
	public int addPollQuestions(PollQuestions pollQuestions)throws Exception;
	
	public PollQuestions getPollQuestion(int pollQuestionId) throws Exception;
	public ArrayList<PollQuestions> getPollQuestions(int tripId) throws Exception;
	
	public PollOption getOption(int optionId) throws Exception;
	
	
}
