package com.tripcaddie.backend.galleries.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tripcaddie.backend.trip.model.TripMember;


@Embeddable
public class RatingPictureFilePK implements Serializable {
	
	private static final long serialVersionUID = -1786935826896927065L;
	@ManyToOne
	@JoinColumn(name="picture_file_id",nullable=false)
	private PictureFile pictureFile;
	@ManyToOne
	@JoinColumn(name="trip_member_id",nullable=false)
	private TripMember tripMember;
	
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
	
}
