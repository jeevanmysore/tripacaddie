package com.tripcaddie.backend.footer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "footer_tipyourcaddie")
public class FooterTipYourCaddie {

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "email")
	private String email;

	@Lob
	@Column(name = "feedback")
	private String feedback;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	
}
