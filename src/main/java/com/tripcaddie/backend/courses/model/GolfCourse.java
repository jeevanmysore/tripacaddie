package com.tripcaddie.backend.courses.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="golf_course")
public class GolfCourse {
	
	@Id @GeneratedValue
	@Column(name="golf_course_id")
	private Integer golfCourseId;
	@Column(name="course_name")
	private String courseName;
	@Embedded
	private Address address;
	@Column(name="phone")
	private String phone;
	@Column(name="loc_tz",length=5)
	private String locationTZ;
	@Column(name="loc_dst",length=5)
	private String locationDST;
	@Column(name="loc_lat_centroid")
	private Double locationLattitudeCentroid;
	@Column(name="loc_lat_poly")
	private Double locationLattitudePoly;
	@Column(name="loc_long_centroid")
	private Double locationLongitudeCentroid;
	@Column(name="loc_long_poly")
	private Double locationLongitudePoly;
	@Column(name="metal_spikes")
	private Character metalSpikes;
	@Column(name="play_five")
	private Character playFive;
	@Column(name="c_holes")
	private Integer holes;
	@Column(name="year_built")
	private Integer yearBuilt;
	@Column(name="green_fees")
	private String greenFees;
	@Column(name="description")
	private String description;
	@Column(name="season")
	private String season;
	@Column(name="c_designer")
	private String designer;
	@Column(name="weekend_rates")
	private String weeekendFees;
	@Column(name="adv_tee")
	private String advanceTee;
	@Column(name="rating")
	private Double rating;
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="course_type_id")
	private CourseType courseType; 
	@Column(name="guest_policy")
	private String guestPolicy;
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="dress_code_id")
	private DressCode dressCode;
	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="golf_image",
			joinColumns=@JoinColumn(name="golf_course_id",nullable=true),
			inverseJoinColumns=@JoinColumn(name="image_id",nullable=true))
	private Collection<Image> golfImage = new ArrayList<Image>();
	
	public Collection<Image> getGolfImage() {
		return golfImage;
	}
	public void setGolfImage(Collection<Image> golfImage) {
		this.golfImage = golfImage;
	}
	public CourseType getCourseType() {
		return courseType;
	}
	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}
	public String getGuestPolicy() {
		return guestPolicy;
	}
	public void setGuestPolicy(String guestPolicy) {
		this.guestPolicy = guestPolicy;
	}
	public DressCode getDressCode() {
		return dressCode;
	}
	public void setDressCode(DressCode dressCode) {
		this.dressCode = dressCode;
	}
	public String getLocationTZ() {
		return locationTZ;
	}
	public Integer getGolfCourseId() {
		return golfCourseId;
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
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
}
