package com.tripcaddie.frontend.trip.service;

import java.util.ArrayList;

import com.tripcaddie.frontend.trip.dto.PollQuestionsDto;

public interface PollService {
	
	public int addPollQuestions(int tripId,String questions) throws Exception;
	public void addOption(int pollQuestionsId,String pollOption) throws Exception;
	
	public ArrayList<PollQuestionsDto> getPollQuestions(int tripId) throws Exception;
	public PollQuestionsDto getPollQuestion(int pollQuestionsId) throws Exception;
	public void editPollQuestion(int pollId,String questions) throws Exception;
	public void editPollOption(int optionId,String option) throws Exception;
	
	public void voteNow(int optionId) throws Exception;
	public void deletePoll(int pollId) throws Exception;
	

}
