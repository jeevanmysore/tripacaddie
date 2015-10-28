package com.tripcaddie.backend.courses.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	@Column(name="address_line1")
	private String addressLine1;
	@Column(name="address_line2")
	private String addressLine2;
	@Column(name="address_line3")
	private String addressLine3;
	@Column(name="city")
	private String city;
	@Column(name="state")
	private String state;
	@Column(name="postal")
	private String postal;
	@Column(name="zip")
	private String zip;
	@Column(name="loc_county")
	private String locationCounty;
	@Column(name="country")
	private String country;
	@Column(name="loc_area_code")
	private Integer locationAreaCode;
	@Column(name="locationFIPS")
	private Integer locationFIPS;
	@Column(name="locationMSA")
	private Integer locationMSA;
	@Column(name="locationPMSA")
	private Integer locationPMSA;

	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getLocationCounty() {
		return locationCounty;
	}
	public void setLocationCounty(String locationCounty) {
		this.locationCounty = locationCounty;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getLocationAreaCode() {
		return locationAreaCode;
	}
	public void setLocationAreaCode(Integer locationAreaCode) {
		this.locationAreaCode = locationAreaCode;
	}
	public Integer getLocationFIPS() {
		return locationFIPS;
	}
	public void setLocationFIPS(Integer locationFIPS) {
		this.locationFIPS = locationFIPS;
	}
	public Integer getLocationMSA() {
		return locationMSA;
	}
	public void setLocationMSA(Integer locationMSA) {
		this.locationMSA = locationMSA;
	}
	public Integer getLocationPMSA() {
		return locationPMSA;
	}
	public void setLocationPMSA(Integer locationPMSA) {
		this.locationPMSA = locationPMSA;
	}
}
