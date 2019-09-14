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
@Table(name="availability")
public class Availability {
	
	@Id @Column(name="avail_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int availId;
	
	@Temporal(TemporalType.DATE)
	private Date availDate;
	
	private int availSeats;
	
    @Column(name ="bus_id")
	private int busId;

	public int getAvailId() {
		return availId;
	}

	public void setAvailId(int availId) {
		this.availId = availId;
	}

	public Date getAvailDate() {
		return availDate;
	}

	public void setAvailDate(Date availDate) {
		this.availDate = availDate;
	}

	public int getAvailSeats() {
		return availSeats;
	}

	public void setAvailSeats(int availSeats) {
		this.availSeats = availSeats;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	@Override
	public String toString() {
		return "Availability [availId=" + availId + ", availDate=" + availDate + ", availSeats=" + availSeats
				+ ", busId=" + busId + "]";
	}

}
