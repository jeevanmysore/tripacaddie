package com.tripcaddie.frontend.courses.dto;

import java.io.Serializable;

import com.tripcaddie.backend.courses.model.Image;

//import com.tripcaddie.backend.courses.model.Image;

public class ImageDto implements Serializable{
	
	private static final long serialVersionUID = -8491006385658996356L;
	private int imageId;
	private String url;
	private String imageName;
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
	public static void populate(ImageDto imageDto,Image image) {
		
		imageDto.setPrimary(image.getPrimary());
		imageDto.setImageId(image.getImageId());
		imageDto.setImageName(image.getImageName());
		imageDto.setUrl(image.getUrl());
	}
	
	public static ImageDto instantiate(Image image) {
		ImageDto imageDto=new ImageDto();
		populate(imageDto, image);
		return imageDto;
	}
}
