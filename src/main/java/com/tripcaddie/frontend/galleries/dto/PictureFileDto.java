package com.tripcaddie.frontend.galleries.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.tripcaddie.backend.galleries.model.PictureFile;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public class PictureFileDto implements Serializable {

	private static final long serialVersionUID = -2632177348004093744L;
	private int pictureId;
	private String pictureName;
	private TripDto tripDto;
	private TripMemberDto tripMemberDto;
	private String description;
	private int avgRating;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private String imageInBase64;
	private Integer noOfComments;
	private boolean favpic;
	private Collection<TripMember> favouritePicture;
	
	public Integer getNoOfComments() {
		return noOfComments;
	}
	public void setNoOfComments(Integer noOfComments) {
		this.noOfComments = noOfComments;
	}
	public String getImageInBase64() {
		return imageInBase64;
	}
	public void setImageInBase64(String imageInBase64) {
		this.imageInBase64 = imageInBase64;
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
	
	public static void populate(PictureFileDto pictureFileDto,PictureFile pictureFile) {
		//Autoboxing
		double avg=pictureFile.getAvgRating();
		//type casting
	    pictureFileDto.setAvgRating((int)avg);
		pictureFileDto.setCreatedBy(pictureFileDto.getCreatedBy());
		pictureFileDto.setCreatedDate(pictureFile.getCreatedDate());
		pictureFileDto.setDescription(pictureFile.getDescription());
		pictureFileDto.setLastUpdatedBy(pictureFile.getLastUpdatedBy());
		pictureFileDto.setLastUpdatedDate(pictureFile.getLastUpdatedDate());
		pictureFileDto.setPictureId(pictureFile.getPictureId());
		pictureFileDto.setPictureName(pictureFile.getPictureName());
		pictureFileDto.setTripDto(TripDto.instantiate(pictureFile.getTrip()));
		pictureFileDto.setTripMemberDto(TripMemberDto.instantiate(pictureFile.getTripMember()));
		pictureFileDto.setFavouritePicture(pictureFile.getFavouritePicture());
		List<TripMember> members=(List<TripMember>) pictureFile.getFavouritePicture();
		
		if(members!=null && !members.isEmpty() && members.contains(pictureFile.getTripMember())){
			pictureFileDto.setFavpic(true);
		}else{
			pictureFileDto.setFavpic(false);
		}
	}
	
	public static PictureFileDto instantiate(PictureFile pictureFile) {
		
		PictureFileDto pictureFileDto=new PictureFileDto();
		populate(pictureFileDto, pictureFile);
		return pictureFileDto;
		
	}
	public boolean isFavpic() {
		return favpic;
	}
	public void setFavpic(boolean favpic) {
		this.favpic = favpic;
	}
	public Collection<TripMember> getFavouritePicture() {
		return favouritePicture;
	}
	public void setFavouritePicture(Collection<TripMember> favouritePicture) {
		this.favouritePicture = favouritePicture;
	}
	public int getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(int avgRating) {
		this.avgRating = avgRating;
	}
	
}
