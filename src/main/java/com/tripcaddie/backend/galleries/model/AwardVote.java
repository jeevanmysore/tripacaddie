package com.tripcaddie.backend.galleries.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tripcaddie.backend.trip.model.TripMember;

@Entity
@Table(name = "award_vote")
public class AwardVote {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name = "award_id", nullable = false)
	private Awards award;

	@ManyToOne
	@JoinColumn(name = "trip_member_id", nullable = false)
	private TripMember member;

	@ManyToOne
	@JoinColumn(name = "nominee_id", nullable = false)
	private TripMember nomineeid;

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

	public TripMember getNomineeid() {
		return nomineeid;
	}

	public void setNomineeid(TripMember nomineeid) {
		this.nomineeid = nomineeid;
	}
}
