package com.tripcaddie.frontend.galleries.dto;

import org.springframework.web.multipart.MultipartFile;

public class uploadVideoForm {

	private int tripId;
	private MultipartFile video;
	private String description;

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public MultipartFile getVideo() {
		return video;
	}

	public void setVideo(MultipartFile video) {
		this.video = video;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
