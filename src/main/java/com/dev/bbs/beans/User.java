package com.dev.bbs.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users_info")
public class User {
	
	@Id @Column(name="user_id")
	private int userId;
	private String username;
	private String email;
	private String password;
	private long contact;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="user_id")
	private List<Ticket> ticket;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="user_id")
	private List<Feedback> feedback;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", contact=" + contact + "]";
	}

}
