package com.tripcaddie.frontend.courses.dto;

import java.io.Serializable;

import com.tripcaddie.backend.courses.model.Address;

public class AddressDto implements Serializable{
	
	private static final long serialVersionUID = 3185441382259790445L;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String state;
	private String postal;
	private String zip;
	private String locationCounty;
	private String country;
	private Integer locationAreaCode;
	private Integer locationFIPS;
	private Integer locationMSA;
	private Integer locationPMSA;
	
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
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
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
	
	public static void populate(AddressDto addressDto,Address address){
		
		addressDto.setAddressLine1(address.getAddressLine1());
		addressDto.setAddressLine2(address.getAddressLine2());
		addressDto.setAddressLine3(address.getAddressLine3());
		addressDto.setCity(address.getCity());
		addressDto.setCountry(address.getCountry());
		addressDto.setLocationAreaCode(address.getLocationAreaCode());
		addressDto.setLocationCounty(address.getLocationCounty());
		addressDto.setPostal(address.getPostal());
		addressDto.setState(address.getState());
		addressDto.setZip(address.getZip());
	}
	public static AddressDto instantiate(Address address){
		AddressDto addressDto=new AddressDto();
		populate(addressDto, address);
		return addressDto;
	}
	
}
