package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.tripcaddie.backend.trip.model.Blog;

public class BlogDto implements Serializable{
	
	private static final long serialVersionUID = -2569379140332762159L;
	private int blogId;
	private TripDto tripDto;
	private TripMemberDto tripMemberDto;
	private String post;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
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
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
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
	
	public static void populate(BlogDto blogDto,Blog blog){
		
		blogDto.setBlogId(blog.getBlogId());
		blogDto.setCreatedBy(blog.getCreatedBy());
		blogDto.setCreatedDate(blog.getCreatedDate());
		blogDto.setLastUpdatedBy(blog.getLastUpdatedBy());
		blogDto.setLastUpdatedDate(blog.getLastUpdatedDate());
		blogDto.setPost(blog.getPost());
		blogDto.setTripDto(TripDto.instantiate(blog.getTrip()));
		blogDto.setTripMemberDto(TripMemberDto.instantiate(blog.getTripMember()));
	}
	
	public static BlogDto instantiate(Blog blog){
		
		BlogDto blogDto=new BlogDto();
		populate(blogDto, blog);
		return blogDto;
	}

}
