package com.tripcaddie.frontend.galleries.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.tripcaddie.backend.galleries.model.VideoFile;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public class VideoFileDto implements Serializable {
	
	private static final long serialVersionUID = 7584199046937599702L;
	private int videoId;
	private String videoName;
	private TripDto tripDto;
	private TripMemberDto tripMemberDto;
	private String description;
	private int avgRating;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private String videothumbnailname;
	private String tbimginbase64;
	private Integer noOfComments;
	private String videoUrl;
	private boolean favvideo;
	private Collection<TripMember> favouriteVideo;

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
	public TripDto getTripDto() {
		return tripDto;
	}
	public void setTripDto(TripDto tripDto) {
		this.tripDto = tripDto;
	}
	public TripMemberDto getTripMemberDto() {
		return tripMemberDto;
	}
	public void setTripMemberDto(TripMemberDto tripMemberDto) {
		this.tripMemberDto = tripMemberDto;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

	public static void populate(VideoFileDto videoFileDto, VideoFile videoFile) {
        double avg=videoFile.getAvgRating();
		videoFileDto.setAvgRating((int) avg);
		videoFileDto.setCreatedBy(videoFile.getCreatedBy());
		videoFileDto.setCreatedDate(videoFile.getCreatedDate());
		videoFileDto.setDescription(videoFile.getDescription());
		videoFileDto.setLastUpdatedBy(videoFile.getLastUpdatedBy());
		videoFileDto.setLastUpdatedDate(videoFile.getLastUpdatedDate());
		videoFileDto.setTripDto(TripDto.instantiate(videoFile.getTrip()));
		videoFileDto.setTripMemberDto(TripMemberDto.instantiate(videoFile.getTripMember()));
		videoFileDto.setVideoId(videoFile.getVideoId());
		videoFileDto.setVideoName(videoFile.getVideoName());
		videoFileDto.setVideothumbnailname(videoFile.getVideothumbnailname());
		videoFileDto.setFavouriteVideo(videoFile.getFavouriteVideo());
		List<TripMember> members = (List<TripMember>) videoFile
				.getFavouriteVideo();
		if (members != null && !members.isEmpty()
				&& members.contains(videoFile.getTripMember())) {
			videoFileDto.setFavvideo(true);
		} else {
			videoFileDto.setFavvideo(false);
		}
	}
	
	public static VideoFileDto instantiate(VideoFile videoFile){
		
		VideoFileDto videoFileDto=new VideoFileDto();
		populate(videoFileDto, videoFile);
		return videoFileDto;
	}

	

	public String getVideothumbnailname() {
		return videothumbnailname;
	}

	public void setVideothumbnailname(String videothumbnailname) {
		this.videothumbnailname = videothumbnailname;
	}

	public String getTbimginbase64() {
		return tbimginbase64;
	}

	public void setTbimginbase64(String tbimginbase64) {
		this.tbimginbase64 = tbimginbase64;
	}

	public Integer getNoOfComments() {
		return noOfComments;
	}

	public void setNoOfComments(Integer noOfComments) {
		this.noOfComments = noOfComments;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public boolean isFavvideo() {
		return favvideo;
	}

	public void setFavvideo(boolean favvideo) {
		this.favvideo = favvideo;
	}

	public Collection<TripMember> getFavouriteVideo() {
		return favouriteVideo;
	}

	public void setFavouriteVideo(Collection<TripMember> favouriteVideo) {
		this.favouriteVideo = favouriteVideo;
	}

	public int getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(int avgRating) {
		this.avgRating = avgRating;
	}

}
