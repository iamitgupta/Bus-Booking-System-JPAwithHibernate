package com.dev.bbs.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="suggestion")
public class Feedback {
	
	@Id @Column(name="sugg_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int suggId;
	

    @Column(name = "user_id")
	private int userId;
	
	private String feedback;

	public int getSuggId() {
		return suggId;
	}

	public void setSuggId(int suggId) {
		this.suggId = suggId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "Feedback [suggId=" + suggId + ", userId=" + userId + ", feedback=" + feedback + "]";
	}

	
	
}
