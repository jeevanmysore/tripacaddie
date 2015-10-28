package com.tripcaddie.backend.courses.vo;

import java.util.ArrayList;
import java.util.Collection;

public class GolfCourseTestBean {
	
	private Integer golfCourseId;
	private String courseName;
	private AddressTestBean address;
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
	private CourseTypeTestBean courseType; 
	private String guestPolicy;
	private DressCodeTestBean dressCode;
	private Collection<ImageTestBean> golfImage = new ArrayList<ImageTestBean>();
	
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
	public AddressTestBean getAddress() {
		return address;
	}
	public void setAddress(AddressTestBean address) {
		this.address = address;
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
	public CourseTypeTestBean getCourseType() {
		return courseType;
	}
	public void setCourseType(CourseTypeTestBean courseType) {
		this.courseType = courseType;
	}
	public String getGuestPolicy() {
		return guestPolicy;
	}
	public void setGuestPolicy(String guestPolicy) {
		this.guestPolicy = guestPolicy;
	}
	public DressCodeTestBean getDressCode() {
		return dressCode;
	}
	public void setDressCode(DressCodeTestBean dressCode) {
		this.dressCode = dressCode;
	}
	public Collection<ImageTestBean> getGolfImage() {
		return golfImage;
	}
	public void setGolfImage(Collection<ImageTestBean> golfImage) {
		this.golfImage = golfImage;
	}
}
