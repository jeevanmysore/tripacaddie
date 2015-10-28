package com.tripcaddie.common.model;

import java.util.ArrayList;

public class PollForm {
	
	private int pollId;
	private int tripId;
	private String questions;
	private ArrayList<String> options;
	private ArrayList<Integer> optionId;
	
	public ArrayList<Integer> getOptionId() {
		return optionId;
	}
	public void setOptionId(ArrayList<Integer> optionId) {
		this.optionId = optionId;
	}
	public int getPollId() {
		return pollId;
	}
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
	}
	public ArrayList<String> getOptions() {
		return options;
	}
	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}
	
}
