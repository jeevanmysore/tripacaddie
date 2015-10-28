package com.tripcaddie.backend.footer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "footer_bugreport")
public class FooterBugReport {

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "email")
	private String email;

	@Lob
	@Column(name = "issue_description")
	private String issue;

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

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

}
