package com.tripcaddie.backend.galleries.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tripcaddie.backend.trip.model.TripMember;

@Entity
@Table(name = "award_vote_count")
public class AwardVoteCount {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name = "award_id", nullable = false)
	private Awards award;

	@ManyToOne
	@JoinColumn(name = "trip_member_id", nullable = false)
	private TripMember member;

	@Column(name = "count")
	private Integer count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Awards getAward() {
		return award;
	}

	public void setAward(Awards award) {
		this.award = award;
	}

	public TripMember getMember() {
		return member;
	}

	public void setMember(TripMember member) {
		this.member = member;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
