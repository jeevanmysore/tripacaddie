package com.tripcaddie.backend.galleries.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tripcaddie.backend.trip.model.TripMember;


@Embeddable
public class RatingVideoFilePK implements Serializable {

	private static final long serialVersionUID = -1172518882639817613L;
	@ManyToOne
	@JoinColumn(name="video_file_id",nullable=false)
	private VideoFile videoFile;
	@ManyToOne
	@JoinColumn(name="trip_member_id",nullable=false)
	private TripMember tripMember;
	
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
	
}
