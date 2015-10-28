package com.tripcaddie.backend.galleries.model;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;

@Entity
@Table(name="video_files")
public class VideoFile {

	@Id @GeneratedValue
	@Column(name="video_id")
	private int videoId;
	@Column(name="video_name")
	private String videoName;
	@ManyToOne
	@JoinColumn(name="trip_id",nullable=false)
	private Trip trip;
	@ManyToOne
	@JoinColumn(name="trip_member_id",nullable=false)
	private TripMember tripMember;
	@Lob
	@Column(name="description")
	private String description;
	@Column(name="avg_rating")
	private Double avgRating;
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
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="favourite_videos",
			joinColumns=@JoinColumn(name="video_id",nullable=false),
			inverseJoinColumns=@JoinColumn(name="member_id",nullable=false))
	private Collection<TripMember> favouriteVideo;
	@Column(name="video_thumbnail_name")
	private String videothumbnailname;
	
	@Column(name="no_of_fav")
	private Integer nooffav;
	
	public Collection<TripMember> getFavouriteVideo() {
		return favouriteVideo;
	}
	public void setFavouriteVideo(Collection<TripMember> favouriteVideo) {
		this.favouriteVideo = favouriteVideo;
	}
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	public TripMember getTripMember() {
		return tripMember;
	}
	public void setTripMember(TripMember tripMember) {
		this.tripMember = tripMember;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
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
	public String getVideothumbnailname() {
		return videothumbnailname;
	}
	public void setVideothumbnailname(String videothumbnailname) {
		this.videothumbnailname = videothumbnailname;
	}
	public Integer getNooffav() {
		return nooffav;
	}
	public void setNooffav(Integer nooffav) {
		this.nooffav = nooffav;
	}
	
	
}
