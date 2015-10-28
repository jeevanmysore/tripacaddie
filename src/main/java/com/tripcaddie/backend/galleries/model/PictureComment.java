package com.tripcaddie.backend.galleries.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.trip.model.TripMember;

@Entity
@Table(name="picture_comment")
public class PictureComment {
	
	@Id @GeneratedValue
	@Column(name="picture_comment_id")
	private int pictureCommentId;
	@ManyToOne
	@JoinColumn(name="picture_file_id",nullable=false)
	private PictureFile pictureFile;
	@ManyToOne
	@JoinColumn(name="trip_member_id")
	private TripMember tripMember;
	@Column(name="comment")
	private String comments;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdDate;
	@Column(name="last_updated_by")
	private String lastUpdatedBy;
	@Column(name="last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdatedDate;
	
	public int getPictureCommentId() {
		return pictureCommentId;
	}
	public void setPictureCommentId(int pictureCommentId) {
		this.pictureCommentId = pictureCommentId;
	}
	public PictureFile getPictureFile() {
		return pictureFile;
	}
	public void setPictureFile(PictureFile pictureFile) {
		this.pictureFile = pictureFile;
	}
	public TripMember getTripMember() {
		return tripMember;
	}
	public void setTripMember(TripMember tripMember) {
		this.tripMember = tripMember;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	
}
