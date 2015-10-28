package com.tripcaddie.backend.footer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "footer_suggestfeature")
public class FooterSuggestFeature {

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "email")
	private String email;

	@Lob
	@Column(name = "idea_description")
	private String idea;

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

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

}
