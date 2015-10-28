package com.tripcaddie.frontend.courses.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.tripcaddie.backend.courses.model.GolfCourse;

public class GolfCourseDto implements Serializable {
	
	private static final long serialVersionUID = -8689440765629542608L;
	private Integer golfCourseId;
	private String courseName;
	private AddressDto addressDto;
	private String phone;
	private String locationTZ;
	private String locationDST;
	private Double locationLattitudeCentroid;
	private Double locationLattitudePoly;
	private Double locationLongitudeCentroid;
	private Double locationLongitudePoly;
	private Character metalSpikes;
	private Character playFive;
	private Integer holes;
	private Integer yearBuilt;
	private String greenFees;
	private String description;
	private String season;
	private String designer;
	private String weeekendFees;
	private String advanceTee;
	private Double rating;
	private CourseTypeDto courseTypeDto; 
	private String guestPolicy;
	private DressCodeDto dressCodeDto;
	private Collection<ImageDto> golfImageDtos = new ArrayList<ImageDto>();
	private boolean exist;
	private String bucketListStatus;
	
	public String getBucketListStatus() {
		return bucketListStatus;
	}
	public void setBucketListStatus(String bucketListStatus) {
		this.bucketListStatus = bucketListStatus;
	}
	public boolean isExist() {
		return exist;
	}
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	public Integer getGolfCourseId() {
		return golfCourseId;
	}
	public void setGolfCourseId(Integer golfCourseId) {
		this.golfCourseId = golfCourseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public AddressDto getAddressDto() {
		return addressDto;
	}
	public void setAddressDto(AddressDto addressDto) {
		this.addressDto = addressDto;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLocationTZ() {
		return locationTZ;
	}
	public void setLocationTZ(String locationTZ) {
		this.locationTZ = locationTZ;
	}
	public String getLocationDST() {
		return locationDST;
	}
	public void setLocationDST(String locationDST) {
		this.locationDST = locationDST;
	}
	public Double getLocationLattitudeCentroid() {
		return locationLattitudeCentroid;
	}
	public void setLocationLattitudeCentroid(Double locationLattitudeCentroid) {
		this.locationLattitudeCentroid = locationLattitudeCentroid;
	}
	public Double getLocationLattitudePoly() {
		return locationLattitudePoly;
	}
	public void setLocationLattitudePoly(Double locationLattitudePoly) {
		this.locationLattitudePoly = locationLattitudePoly;
	}
	public Double getLocationLongitudeCentroid() {
		return locationLongitudeCentroid;
	}
	public void setLocationLongitudeCentroid(Double locationLongitudeCentroid) {
		this.locationLongitudeCentroid = locationLongitudeCentroid;
	}
	public Double getLocationLongitudePoly() {
		return locationLongitudePoly;
	}
	public void setLocationLongitudePoly(Double locationLongitudePoly) {
		this.locationLongitudePoly = locationLongitudePoly;
	}
	public Character getMetalSpikes() {
		return metalSpikes;
	}
	public void setMetalSpikes(Character metalSpikes) {
		this.metalSpikes = metalSpikes;
	}
	public Character getPlayFive() {
		return playFive;
	}
	public void setPlayFive(Character playFive) {
		this.playFive = playFive;
	}
	public Integer getHoles() {
		return holes;
	}
	public void setHoles(Integer holes) {
		this.holes = holes;
	}
	public Integer getYearBuilt() {
		return yearBuilt;
	}
	public void setYearBuilt(Integer yearBuilt) {
		this.yearBuilt = yearBuilt;
	}
	public String getGreenFees() {
		return greenFees;
	}
	public void setGreenFees(String greenFees) {
		this.greenFees = greenFees;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getDesigner() {
		return designer;
	}
	public void setDesigner(String designer) {
		this.designer = designer;
	}
	public String getWeeekendFees() {
		return weeekendFees;
	}
	public void setWeeekendFees(String weeekendFees) {
		this.weeekendFees = weeekendFees;
	}
	public String getAdvanceTee() {
		return advanceTee;
	}
	public void setAdvanceTee(String advanceTee) {
		this.advanceTee = advanceTee;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public CourseTypeDto getCourseTypeDto() {
		return courseTypeDto;
	}
	public void setCourseTypeDto(CourseTypeDto courseTypeDto) {
		this.courseTypeDto = courseTypeDto;
	}
	public String getGuestPolicy() {
		return guestPolicy;
	}
	public void setGuestPolicy(String guestPolicy) {
		this.guestPolicy = guestPolicy;
	}
	public DressCodeDto getDressCodeDto() {
		return dressCodeDto;
	}
	public void setDressCodeDto(DressCodeDto dressCodeDto) {
		this.dressCodeDto = dressCodeDto;
	}
	public Collection<ImageDto> getGolfImageDtos() {
		return (ArrayList<ImageDto>) golfImageDtos;
	}
	public void setGolfImageDtos(Collection<ImageDto> golfImageDtos) {
		this.golfImageDtos = golfImageDtos;
	}
	public static void populate(GolfCourseDto golfCourseDto,GolfCourse golfCourse) {
		
		golfCourseDto.setAddressDto(AddressDto.instantiate(golfCourse.getAddress()));
		golfCourseDto.setAdvanceTee(golfCourse.getAdvanceTee());
		golfCourseDto.setCourseName(golfCourse.getCourseName());
		golfCourseDto.setCourseTypeDto(CourseTypeDto.instantiate(golfCourse.getCourseType()));
		golfCourseDto.setDescription(golfCourse.getDescription());
		golfCourseDto.setDesigner(golfCourse.getDesigner());
		golfCourseDto.setDressCodeDto(DressCodeDto.instantiate(golfCourse.getDressCode()));
		golfCourseDto.setGolfCourseId(golfCourse.getGolfCourseId());
		
		//May not be needed
		/*for(Image image:golfCourse.getGolfImage()){
			golfCourseDto.getGolfImageDtos().add(ImageDto.instantiate(image));			
		}*/
	
		golfCourseDto.setGreenFees(golfCourse.getGreenFees());
		golfCourseDto.setGuestPolicy(golfCourse.getGuestPolicy());
		golfCourseDto.setHoles(golfCourse.getHoles());
		golfCourseDto.setLocationDST(golfCourse.getLocationDST());
		golfCourseDto.setLocationLattitudeCentroid(golfCourse.getLocationLattitudeCentroid());
		golfCourseDto.setLocationLattitudePoly(golfCourse.getLocationLattitudePoly());
		golfCourseDto.setLocationLongitudeCentroid(golfCourse.getLocationLongitudeCentroid());
		golfCourseDto.setLocationLongitudePoly(golfCourse.getLocationLongitudePoly());
		golfCourseDto.setLocationTZ(golfCourse.getLocationTZ());
		golfCourseDto.setMetalSpikes(golfCourse.getMetalSpikes());
		golfCourseDto.setPhone(golfCourse.getPhone());
		golfCourseDto.setPlayFive(golfCourseDto.getPlayFive());
		golfCourseDto.setRating(golfCourse.getRating());
		golfCourseDto.setSeason(golfCourse.getSeason());
		golfCourseDto.setWeeekendFees(golfCourse.getWeeekendFees());
		golfCourseDto.setYearBuilt(golfCourse.getYearBuilt());
		
	}
	
	public static GolfCourseDto instantiate(GolfCourse golfCourse) {
		GolfCourseDto golfCourseDto=new GolfCourseDto();
		populate(golfCourseDto, golfCourse);
		return golfCourseDto;
	}
	
}
