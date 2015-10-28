package com.tripcaddie.backend.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "abuse_reasons")
public class AbuseReasons {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "reason")
	private String reason;

	@Lob
	@Column(name = "description")
	private String description;

	@Lob
	@Column(name = "argumentation")
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

}
