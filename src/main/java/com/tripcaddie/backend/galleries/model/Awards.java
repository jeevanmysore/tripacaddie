package com.tripcaddie.backend.galleries.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;

@Entity
@Table(name = "Awards")
public class Awards {

	@Id
	@Column(name = "award_id")
	@GeneratedValue
	private int awardid;

	@ManyToOne
	@JoinColumn(name = "trip_id", nullable = false)
	private Trip trip;

	@Column(name = "award_name")
	private String awardname;

	@Column(name = "image_name")
	private String imagename;

	@Column(name = "poll_end_date")
	private Date pollenddate;

	@ManyToOne
	@JoinColumn(name = "createdby_member", nullable = false)
	private TripMember createdby;

	@Column(name = "created_date")
	private Date createddate;
	
	@Column(name="awarded")
	private int awarded;
	
	@OneToMany(mappedBy = "award")
	@Cascade(value = CascadeType.ALL)
	private Collection<AwardVote> awardVotes = new ArrayList<AwardVote>();

	@OneToMany(mappedBy = "award")
	@Cascade(value = CascadeType.ALL)
	private Collection<AwardsComment> awardsComments = new ArrayList<AwardsComment>();
	
	@OneToMany(mappedBy = "award")
	@Cascade(value = CascadeType.ALL)
	private Collection<AwardVoteCount> awardVoteCounts = new ArrayList<AwardVoteCount>();

	public Collection<AwardsComment> getAwardsComments() {
		return awardsComments;
	}

	public void setAwardsComments(Collection<AwardsComment> awardsComments) {
		this.awardsComments = awardsComments;
	}

	public Collection<AwardVoteCount> getAwardVoteCounts() {
		return awardVoteCounts;
	}

	public void setAwardVoteCounts(Collection<AwardVoteCount> awardVoteCounts) {
		this.awardVoteCounts = awardVoteCounts;
	}

	public int getAwardid() {
		return awardid;
	}

	public void setAwardid(int awardid) {
		this.awardid = awardid;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public String getAwardname() {
		return awardname;
	}

	public void setAwardname(String awardname) {
		this.awardname = awardname;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public Date getPollenddate() {
		return pollenddate;
	}

	public void setPollenddate(Date pollenddate) {
		this.pollenddate = pollenddate;
	}

	public TripMember getCreatedby() {
		return createdby;
	}

	public void setCreatedby(TripMember createdby) {
		this.createdby = createdby;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public int getAwarded() {
		return awarded;
	}

	public void setAwarded(int awarded) {
		this.awarded = awarded;
	}

	public Collection<AwardVote> getAwardVotes() {
		return awardVotes;
	}

	public void setAwardVotes(Collection<AwardVote> awardVotes) {
		this.awardVotes = awardVotes;
	}

}
