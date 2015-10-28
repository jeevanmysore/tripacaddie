package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.frontend.courses.dto.GolfCourseDto;
import com.tripcaddie.frontend.galleries.dto.AwardsDto;
import com.tripcaddie.frontend.galleries.dto.PictureFileDto;
import com.tripcaddie.frontend.galleries.dto.VideoFileDto;

public class TripDto implements Serializable{

	private static final long serialVersionUID = -2785353246160742807L;
	private int tripId;
	private String tripName;
	private Calendar startDate;
	private Calendar endDate;
	private String imagePath;
	private String welcomeMessage;
	private Integer annualTrip;
	private String promoCode;
	private Double wagerFee;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private GolfCourseDto golfCourseDto;
	private String tripLeader;
	private String phone;
	private boolean galleriespresent;
	private String imagebase64;
	private List<PictureFileDto> pictures;
	private List<VideoFileDto> videos;
	private List<AwardsDto> awards;
	private int strudellock;
	private int leaderboardlock;
	
	public List<AwardsDto> getAwards() {
		return awards;
	}
	public void setAwards(List<AwardsDto> awards) {
		this.awards = awards;
	}
	public String getTripLeader() {
		return tripLeader;
	}
	public void setTripLeader(String tripLeader) {
		this.tripLeader = tripLeader;
	}
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	public GolfCourseDto getGolfCourseDto() {
		return golfCourseDto;
	}
	public void setGolfCourseDto(GolfCourseDto golfCourseDto) {
		this.golfCourseDto = golfCourseDto;
	}
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public Integer getAnnualTrip() {
		return annualTrip;
	}
	public void setAnnualTrip(Integer annualTrip) {
		this.annualTrip = annualTrip;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public Double getWagerFee() {
		return wagerFee;
	}
	public void setWagerFee(Double wagerFee) {
		this.wagerFee = wagerFee;
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
	
	public static void populate(TripDto tripDto,Trip trip){		
		tripDto.setAnnualTrip(trip.getAnnualTrip());
		tripDto.setCreatedBy(trip.getCreatedBy());
		tripDto.setCreatedDate(trip.getCreatedDate());
		tripDto.setEndDate(trip.getEndDate());
		tripDto.setLastUpdatedBy(trip.getLastUpdatedBy());
		tripDto.setLastUpdatedDate(trip.getLastUpdatedDate());
		tripDto.setImagePath(trip.getImagePath());
		tripDto.setPromoCode(trip.getPromoCode());
		tripDto.setStartDate(trip.getStartDate());
		tripDto.setTripId(trip.getTripId());
		tripDto.setTripName(trip.getTripName());
		tripDto.setWagerFee(trip.getWagerFee());
		tripDto.setWelcomeMessage(trip.getWelcomeMessage());
		tripDto.setGolfCourseDto(GolfCourseDto.instantiate(trip.getGolfCourse()));
		tripDto.setLeaderboardlock(trip.getLeaderboardlock());
		tripDto.setStrudellock(trip.getStrudellock());
		
		for(TripMember tripMember:trip.getTripMember()){
			/*if(tripMember.getRoleInTrip().getRoleName().equalsIgnoreCase("TRIP LEADER")){
				tripDto.setTripLeader(tripMember.getTripCaddieUser().getUserName());
				tripDto.setPhone(tripMember.getTripCaddieUser().getPhoneNo());
			}*/
			if(tripMember.getTripMemberStatus().getMemberStatus().equalsIgnoreCase("TRIP LEADER")){
				tripDto.setTripLeader(tripMember.getTripCaddieUser().getUserName());
				tripDto.setPhone(tripMember.getTripCaddieUser().getPhoneNo());
			}
		}
		
	}
	
	public static TripDto instantiate(Trip trip){
	
		TripDto tripDto=new TripDto();
		populate(tripDto, trip);
		return tripDto;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isGalleriespresent() {
		return galleriespresent;
	}
	public void setGalleriespresent(boolean galleriespresent) {
		this.galleriespresent = galleriespresent;
	}
	public String getImagebase64() {
		return imagebase64;
	}
	public void setImagebase64(String imagebase64) {
		this.imagebase64 = imagebase64;
	}
	public List<PictureFileDto> getPictures() {
		return pictures;
	}
	public void setPictures(List<PictureFileDto> pictures) {
		this.pictures = pictures;
	}
	public List<VideoFileDto> getVideos() {
		return videos;
	}
	public void setVideos(List<VideoFileDto> videos) {
		this.videos = videos;
	}
	public int getStrudellock() {
		return strudellock;
	}
	public void setStrudellock(int strudellock) {
		this.strudellock = strudellock;
	}
	public int getLeaderboardlock() {
		return leaderboardlock;
	}
	public void setLeaderboardlock(int leaderboardlock) {
		this.leaderboardlock = leaderboardlock;
	}
	
}
