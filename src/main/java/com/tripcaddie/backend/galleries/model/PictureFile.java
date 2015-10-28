package com.tripcaddie.backend.galleries.model;

import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;

@Entity
@Table(name="picture_files")
public class PictureFile {
	
	@Id @GeneratedValue
	@Column(name="picture_id")
	private int pictureId;
	@Column(name="picture_name")
	private String pictureName;
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
	@JoinTable(name="favourite_pictures",
		joinColumns=@JoinColumn(name="picture_id",nullable=false),
		inverseJoinColumns=@JoinColumn(name="member_id",nullable=false))
	private Collection<TripMember> favouritePicture;
	@OneToMany(mappedBy="pictureFile")
	@Cascade(value=CascadeType.ALL)
	private Collection<PictureComment> pictureComments=new ArrayList<PictureComment>();
	
	@Column(name="no_of_fav")
	private Integer nooffav;
	
	public Collection<PictureComment> getPictureComments() {
		return pictureComments;
	}
	public void setPictureComments(Collection<PictureComment> pictureComments) {
		this.pictureComments = pictureComments;
	}
	public Collection<TripMember> getFavouritePicture() {
		return favouritePicture;
	}
	public void setFavouritePicture(Collection<TripMember> favouritePicture) {
		this.favouritePicture = favouritePicture;
	}
	public int getPictureId() {
		return pictureId;
	}
	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
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
	public Integer getNooffav() {
		return nooffav;
	}
	public void setNooffav(Integer nooffav) {
		this.nooffav = nooffav;
	}
	
}
