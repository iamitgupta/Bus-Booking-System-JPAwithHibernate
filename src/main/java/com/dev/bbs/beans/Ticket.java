package com.dev.bbs.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="booking_info")
public class Ticket {
	
	@Id @Column(name="booking_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bookingId;
	
	
    @Column(name ="bus_id")
	private int busId;
	
    @Column(name = "user_id")
	private int  userId;
	
	@Temporal(TemporalType.DATE)
	private Date journeyDate;
	
	private int numofseats;
	
	@Temporal(TemporalType.DATE)
	private Date bookingDate;

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}

	public int getNumofseats() {
		return numofseats;
	}

	public void setNumofseats(int numofseats) {
		this.numofseats = numofseats;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	@Override
	public String toString() {
		return "Ticket [bookingId=" + bookingId + ", busId=" + busId + ", userId=" + userId + ", journeyDate="
				+ journeyDate + ", numofseats=" + numofseats + ", bookingDate=" + bookingDate + "]";
	}

	

	
	
}
