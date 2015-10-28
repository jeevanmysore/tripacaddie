package com.tripcaddie.frontend.courses.dto;

import java.io.Serializable;

import com.tripcaddie.backend.courses.model.CourseType;

public class CourseTypeDto implements Serializable{

	private static final long serialVersionUID = -2029300772955412156L;
	private int courseTypeId;
	private String courseType;
	
	public int getCourseTypeId() {
		return courseTypeId;
	}
	public void setCourseTypeId(int courseTypeId) {
		this.courseTypeId = courseTypeId;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	
	public static void popualte(CourseTypeDto courseTypeDto,CourseType courseType) {
		
		courseTypeDto.setCourseType(courseType.getCourseType());
		courseTypeDto.setCourseTypeId(courseType.getCourseTypeId());
	}
	public static CourseTypeDto instantiate(CourseType courseType) {
		
		CourseTypeDto courseTypeDto=new CourseTypeDto();
		popualte(courseTypeDto, courseType);
		return courseTypeDto;
	}
	
}
