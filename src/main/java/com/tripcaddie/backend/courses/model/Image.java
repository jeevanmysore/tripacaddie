package com.tripcaddie.backend.courses.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="image")
public class Image {

	@Id @GeneratedValue
	@Column(name="image_id")
	private int imageId;
	@Column(name="url")
	private String url;
	@Column(name="image_name")
	private String imageName;
	@Column(name="primary_image")
	private Integer primary;
	
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Integer getPrimary() {
		return primary;
	}
	public void setPrimary(Integer primary) {
		this.primary = primary;
	}
		
}
