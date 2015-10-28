package com.tripcaddie.frontend.itinerary.dto;

import java.io.Serializable;

import com.tripcaddie.backend.itinerary.model.RoundScore;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public class RoundScoreDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private ActivityDto activity;

	private TripMemberDto member;
	
	private int front9;

	private int back9;

	private int total;
	
	private int memberId;
	
	private String membername;

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ActivityDto getActivity() {
		return activity;
	}

	public void setActivity(ActivityDto activity) {
		this.activity = activity;
	}

	public TripMemberDto getMember() {
		return member;
	}

	public void setMember(TripMemberDto member) {
		this.member = member;
	}

	public int getFront9() {
		return front9;
	}

	public void setFront9(int front9) {
		this.front9 = front9;
	}

	public int getBack9() {
		return back9;
	}

	public void setBack9(int back9) {
		this.back9 = back9;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public static RoundScoreDto instantiate(RoundScore roundScore) {

		RoundScoreDto roundScoreDto = new RoundScoreDto();
		populate(roundScoreDto, roundScore);
		return roundScoreDto;
	}

	private static void populate(RoundScoreDto roundScoreDto,
			RoundScore roundScore) {
		roundScoreDto.setId(roundScore.getId());
		roundScoreDto.setActivity(ActivityDto.instantiate(roundScore.getActivity()));
		roundScoreDto.setMember(TripMemberDto.instantiate(roundScore.getMember()));
		roundScoreDto.setBack9(roundScore.getBack9());
		roundScoreDto.setFront9(roundScore.getFront9());
		roundScoreDto.setTotal(roundScore.getTotal());
		
	}
	

}
