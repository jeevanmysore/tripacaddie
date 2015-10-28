package com.tripcaddie.backend.courses.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="course_type")
public class CourseType {
	
	@Id 
	@Column(name="course_type_id")
	private int courseTypeId;
	@Column(name="type")
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

}
