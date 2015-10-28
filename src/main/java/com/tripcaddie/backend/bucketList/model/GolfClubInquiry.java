package com.tripcaddie.backend.bucketList.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.backend.user.model.TripCaddieUser;

@Entity
@Table(name="golf_club_advices")
public class GolfClubInquiry{

	@Id @GeneratedValue
	@Column(name="advice_id")
	private int golfClubInquiryId;
	@Lob
	@Column(name="question")
	private String question;
	@Column(name="last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdatedDate;
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdDate;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="last_updated_by")
	private String lastUpdatedBy;
	@ManyToOne
	@JoinColumn(name="user_id")
	private TripCaddieUser user;
	@ManyToOne
	@JoinColumn(name="golf_course_id")
	private GolfCourse golfCourse;
	@OneToMany(mappedBy="inquiry",cascade=CascadeType.ALL)
	private Collection<GolfClubAdviceResponse> adviceResponse=new ArrayList<GolfClubAdviceResponse>();
	
	public Collection<GolfClubAdviceResponse> getAdviceResponse() {
		return adviceResponse;
	}
	public void setAdviceResponse(Collection<GolfClubAdviceResponse> adviceResponse) {
		this.adviceResponse = adviceResponse;
	}
	public int getGolfClubInquiryId() {
		return golfClubInquiryId;
	}
	public void setGolfClubInquiryId(int golfClubInquiryId) {
		this.golfClubInquiryId = golfClubInquiryId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public TripCaddieUser getUser() {
		return user;
	}
	public void setUser(TripCaddieUser user) {
		this.user = user;
	}
	public GolfCourse getGolfCourse() {
		return golfCourse;
	}
	public void setGolfCourse(GolfCourse golfCourse) {
		this.golfCourse = golfCourse;
	}
	
	
}
