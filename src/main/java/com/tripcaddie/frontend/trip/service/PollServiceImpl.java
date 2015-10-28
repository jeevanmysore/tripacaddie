package com.tripcaddie.frontend.trip.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.trip.dao.PollDao;
import com.tripcaddie.backend.trip.dao.TripDao;
import com.tripcaddie.backend.trip.model.PollOption;
import com.tripcaddie.backend.trip.model.PollQuestions;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.frontend.trip.dto.PollOptionDto;
import com.tripcaddie.frontend.trip.dto.PollQuestionsDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("pollService")
public class PollServiceImpl implements PollService{

	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name="tripDao")
	private TripDao tripDao;
	@Resource(name="pollDao")
	private PollDao pollDao;
	@Resource(name="recentUpdateService")
	private RecentUpdateService recentUpdateService;
	
	@Override
	public int addPollQuestions(int tripId, String questions) throws Exception {
		
		PollQuestions pollQuestions=new PollQuestions();
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto=loginService.getUserByUserName(username);
		TripMember member=tripDao.getTripMember(tripcaddieUserDto, tripId);
		Trip trip=tripDao.getTrip(tripId);
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		pollQuestions.setCreatedBy(username);
		pollQuestions.setCreatedDate(instantTime);
		pollQuestions.setQuestions(questions);
		pollQuestions.setTrip(trip);
		pollQuestions.setTripMember(member);
		
		return pollDao.addPollQuestions(pollQuestions);
	}
	
	@Override
	public void addOption(int pollQuestionsId, String pollOption)
			throws Exception {
		
		PollOption option=new PollOption();
		
		PollQuestions pollQuestions=pollDao.getPollQuestion(pollQuestionsId);
		
		String username=loginService.getAuthenticatedUser();
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		option.setCreatedBy(username);
		option.setCreatedDate(instantTime);
		option.setOptions(pollOption);
		option.setPollQuestions(pollQuestions);
		option.setCount(0);
		
		pollDao.saveOrUpdateEntity(option);
		
	}
	
	@Override
	public ArrayList<PollQuestionsDto> getPollQuestions(int tripId)
			throws Exception {
		
		PollOptionDto pollOptionDto;
		ArrayList<PollOptionDto> pollOptionDtos=new ArrayList<PollOptionDto>();
		PollQuestionsDto pollQuestionsDto=new PollQuestionsDto();
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto userDto=loginService.getUserByUserName(username);
		
		ArrayList<PollQuestionsDto> questionsDtos=new ArrayList<PollQuestionsDto>();
		ArrayList<PollQuestions> pollQuestions=pollDao.getPollQuestions(tripId);
		
		for (PollQuestions pollQuestion : pollQuestions) {
			pollQuestionsDto=PollQuestionsDto.instantiate(pollQuestion);
			
			int totalVote=0;
			for (PollOption option : pollQuestion.getOption()) {
				totalVote+=option.getCount();
			}
			
			TripMember member=tripDao.getTripMember(userDto, pollQuestion.getTrip().getTripId());
			if(isAlreadyVoted(member.getMemberId(), pollQuestion) == true){
				pollQuestionsDto.setVoteOrNot(1);
			}else {
				pollQuestionsDto.setVoteOrNot(0);
			}
			
			for (PollOption pollOption : pollQuestion.getOption()) {
				pollOptionDto=PollOptionDto.instantiate(pollOption);
				if(totalVote > 0)
				pollOptionDto.setPercentageOfVote((((double)pollOptionDto.getCount())/totalVote)*100);
				pollOptionDtos.add(pollOptionDto);
			}
			pollQuestionsDto.setPollOptionDtos(pollOptionDtos);
			questionsDtos.add(pollQuestionsDto);
			pollOptionDtos=new ArrayList<PollOptionDto>();
			pollQuestionsDto=new PollQuestionsDto();
		}
		
		//check voted or not
		
		return questionsDtos;
	}
	
	@Override
	public PollQuestionsDto getPollQuestion(int pollQuestionsId)
			throws Exception {
		
		ArrayList<PollOptionDto> pollOptionDtos=new ArrayList<PollOptionDto>();
		
		PollQuestions pollQuestions=pollDao.getPollQuestion(pollQuestionsId);
		int totalVote=0;
		for (PollOption option : pollQuestions.getOption()) {
			totalVote+=option.getCount();
		}
		PollQuestionsDto pollQuestionsDto=new PollQuestionsDto();
		
		pollQuestionsDto=PollQuestionsDto.instantiate(pollQuestions);
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto userDto=loginService.getUserByUserName(username);
		
		TripMember member=tripDao.getTripMember(userDto, pollQuestions.getTrip().getTripId());
		
		//check voted or not
		if(isAlreadyVoted(member.getMemberId(), pollQuestions) == true){
			pollQuestionsDto.setVoteOrNot(1);
		}else {
			pollQuestionsDto.setVoteOrNot(0);
		}
			
		for (PollOption pollOption : pollQuestions.getOption()) {
			PollOptionDto optionDto=PollOptionDto.instantiate(pollOption);
			if(totalVote > 0)
				optionDto.setPercentageOfVote((((double)optionDto.getCount())/totalVote)*100);
			pollOptionDtos.add(optionDto);			
		}
		
		pollQuestionsDto.setPollOptionDtos(pollOptionDtos);
		
		return pollQuestionsDto;
	}
	
	@Override
	public void voteNow(int optionId) throws Exception {
		
		PollOption pollOption=pollDao.getOption(optionId);
		PollQuestions pollQuestions=pollDao.getPollQuestion(pollOption.getPollQuestions().getPollId());
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto userDto=loginService.getUserByUserName(username);
		
		TripMember member=tripDao.getTripMember(userDto, pollOption.getPollQuestions().getTrip().getTripId());
		pollQuestions.getPollParticipants().add(member);
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		pollOption.setCount(pollOption.getCount()+1);
		pollOption.setLastUpdatedBy(username);
		pollOption.setLastUpdatedDate(instantTime);
		
		pollDao.saveOrUpdateEntity(pollOption);
		pollDao.saveOrUpdateEntity(pollQuestions);
		recentUpdateService.logUpdates(pollOption.getPollQuestions().getTrip().getTripId(), "Responded to Poll","participant");
	}

	@Override
	public void editPollQuestion(int pollId, String questions) throws Exception {
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		String username=loginService.getAuthenticatedUser();
		
		PollQuestions pollQuestions=pollDao.getPollQuestion(pollId);
		pollQuestions.setLastUpdatedBy(username);
		pollQuestions.setLastUpdatedDate(instantTime);
		pollQuestions.setQuestions(questions);
		
		pollDao.saveOrUpdateEntity(pollQuestions);
		
		
	}

	@Override
	public void editPollOption(int optionId, String option) throws Exception {
		
		PollOption pollOption=pollDao.getOption(optionId);
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		String username=loginService.getAuthenticatedUser();
		
		pollOption.setLastUpdatedBy(username);
		pollOption.setLastUpdatedDate(instantTime);
		pollOption.setOptions(option);
		
		pollDao.saveOrUpdateEntity(pollOption);
		
	}

	@Override
	public void deletePoll(int pollId) throws Exception {
		
		PollQuestions pollQuestions=pollDao.getPollQuestion(pollId);
		pollDao.deleteEntity(pollQuestions);
		
	}
	
	private boolean isAlreadyVoted(int memberId,PollQuestions pollQuestions) throws Exception{
	
		boolean flag=false;
		
		for (TripMember tripMember : pollQuestions.getPollParticipants()) {
			//System.out.println("True Or False:"+(tripMember.getMemberId() == memberId));
			if (tripMember.getMemberId() == memberId) {
				flag = true;
				
			}
		}
		return flag;
	}
	
	
	
}
