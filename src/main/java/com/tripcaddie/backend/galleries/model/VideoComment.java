package com.tripcaddie.backend.galleries.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.trip.model.TripMember;

@Entity
@Table(name="video_comment")
public class VideoComment {
	
	@Id @GeneratedValue
	@Column(name="video_comment_id")
	private int videoCommentId;
	@ManyToOne
	@JoinColumn(name="video_files_id")
	private VideoFile videoFile;
	@ManyToOne
	@JoinColumn(name="trip_member_id",nullable=false)
	private TripMember tripMember;
	@Lob
	@Column(name="comment")
	private String comment;
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
	
	public int getVideoCommentId() {
		return videoCommentId;
	}
	public void setVideoCommentId(int videoCommentId) {
		this.videoCommentId = videoCommentId;
	}
	public VideoFile getVideoFile() {
		return videoFile;
	}
	public void setVideoFile(VideoFile videoFile) {
		this.videoFile = videoFile;
	}
	public TripMember getTripMember() {
		return tripMember;
	}
	public void setTripMember(TripMember tripMember) {
		this.tripMember = tripMember;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
