package com.tripcaddie.backend.courses.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dress_code")
public class DressCode {

	@Id @GeneratedValue
	@Column(name="dress_code_id")
	private int dressCodeId;
	@Column(name="dress_code",length=255)
	private String dressCode;
	
	public int getDressCodeId() {
		return dressCodeId;
	}
	public void setDressCodeId(int dressCodeId) {
		this.dressCodeId = dressCodeId;
	}
	public String getDressCode() {
		return dressCode;
	}
	public void setDressCode(String dressCode) {
		this.dressCode = dressCode;
	}
	
}
