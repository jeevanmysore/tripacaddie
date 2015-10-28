package com.tripcaddie.frontend.community.dto;

import com.tripcaddie.backend.community.model.AbuseReasons;
import com.tripcaddie.backend.community.model.Games;


public class AbuseReasonsDto {

	private int id;

	private String reason;

	private String description;

	private String argumentation;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getArgumentation() {
		return argumentation;
	}

	public void setArgumentation(String argumentation) {
		this.argumentation = argumentation;
	}
	
	public static AbuseReasonsDto instantiate(AbuseReasons reasons) {

		AbuseReasonsDto abuseReasonsDto = new AbuseReasonsDto();
		populate(abuseReasonsDto, reasons);
		return abuseReasonsDto;
	}

	private static void populate(AbuseReasonsDto abuseReasonsDto,
			AbuseReasons reasons) {
		abuseReasonsDto.setId(reasons.getId());
		abuseReasonsDto.setReason(reasons.getReason());
		abuseReasonsDto.setDescription(reasons.getDescription());
		abuseReasonsDto.setArgumentation(reasons.getArgumentation());
	}
}
