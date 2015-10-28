package com.tripcaddie.backend.itinerary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tripcaddie.backend.trip.model.TripMember;

@Entity
@Table(name="Round_Score")
public class RoundScore {

	@GeneratedValue
	@Id
	private int id;
	
	@ManyToOne
	@JoinColumn(name="round_id")
	private Activity activity;
	
	@ManyToOne
	@JoinColumn(name="member_id")
	private TripMember member;
	
	@Column(name="front9")
	private int front9;
	
	@Column(name="back9")
	private int back9;
	
	@Column(name="total")
	private int total;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public TripMember getMember() {
		return member;
	}

	public void setMember(TripMember member) {
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
	
	
	
}
