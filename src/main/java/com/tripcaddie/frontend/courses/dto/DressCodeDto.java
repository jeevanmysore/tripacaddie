package com.tripcaddie.frontend.courses.dto;

import java.io.Serializable;

import com.tripcaddie.backend.courses.model.DressCode;

public class DressCodeDto implements Serializable{
		
	private static final long serialVersionUID = -5374015406877104131L;
	private int dressCodeId;
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
	
	public static void populate(DressCodeDto dressCodeDto,DressCode dressCode) {
		
		dressCodeDto.setDressCode(dressCode.getDressCode());
		dressCodeDto.setDressCodeId(dressCode.getDressCodeId());
		
	}
	public static DressCodeDto instantiate(DressCode dressCode) {
		
		DressCodeDto dressCodeDto=new DressCodeDto();
		populate(dressCodeDto, dressCode);
		return dressCodeDto;
	}

}
