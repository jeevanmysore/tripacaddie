package com.tripcaddie.common.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm {
	
	private int tripId;
	private List<MultipartFile> files;
	private String description;
	
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
