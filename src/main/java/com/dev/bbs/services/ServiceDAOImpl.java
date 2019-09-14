package com.dev.bbs.services;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dev.bbs.beans.Admin;
import com.dev.bbs.beans.Availability;
import com.dev.bbs.beans.Bus;
import com.dev.bbs.beans.Feedback;
import com.dev.bbs.beans.Ticket;
import com.dev.bbs.beans.User;
import com.dev.bbs.dao.BbsDAO;
import com.dev.bbs.dao.BbsDAOImpl;

public class ServiceDAOImpl implements ServiceDAO {
	BbsDAO dao = new BbsDAOImpl();

	@Override
	public Boolean createUser(User user) {
		return dao.createUser(user);
	}

	@Override
	public Boolean updateUser(User user) {

		return dao.updateUser(user);
	}

	@Override
	public Boolean deleteUser(int userId) {

		return dao.deleteUser(userId);
	}

	@Override
	public User loginUser(int userId, String password) {

		return dao.loginUser(userId, password);
	}

	@Override
	public User searchUser(int userId) {

		return dao.searchUser(userId);
	}

	@Override
	public Boolean createBus(Bus bus) {

		return dao.createBus(bus);
	}

	@Override
	public Boolean updateBus(Bus bus) {

		return dao.updateBus(bus);
	}

	@Override
	public Bus searchBus(int busId) {

		return dao.searchBus(busId);
	}

	@Override
	public Boolean deletebus(int busId) {

		return dao.deletebus(busId);
	}

	@Override
	public Admin adminLogin(int adminId, String password) {

		return dao.adminLogin(adminId, password);
	}

	@Override
	public Boolean writeFeedback(Feedback feed) {

		return dao.writeFeedback(feed);
	}

	@Override
	public Ticket bookTicket(Ticket ticket) {

		return dao.bookTicket(ticket);
	}

	@Override
	public Boolean cancelTicket(int bookingId, int userId) {

		return dao.cancelTicket(bookingId, userId);
	}

	@Override
	public Ticket getTicket(int bookingId) {

		return dao.getTicket(bookingId);
	}

	@Override
	public List<Ticket> getAllTicket(int userId) {

		return dao.getAllTicket(userId);
	}

	@Override
	public List<Availability> checkAvailability(String source, String destination, Date date) {

		return dao.checkAvailability(source, destination, date);
	}

	@Override
	public Integer checkAvailability(int busId, Date date) {

		return dao.checkAvailability(busId, date);
	}

	@Override
	public List<Feedback> viewFeedbac() {
		return dao.viewFeedbac();
	}

	@Override
	public List<Ticket> getTicketByBus(int busId, Date date) {
		return dao.getTicketByBus(busId, date);
	}

	@Override
	public Boolean setBusAvailability(Availability availability) {
		return dao.setBusAvailability(availability);
	}
	public Integer validateId(String id) 
	{
		Pattern pat = Pattern.compile("\\d+");
		Matcher mat = pat.matcher(id);
		if(mat.matches()){
			return Integer.parseInt(id);
		}else{
			return null;
		}

	}
	@Override
	public String validateEmail(String email) {
		Pattern	pat = Pattern.compile("\\w+.\\w+\\@\\w+\\.\\w+"); 
		Matcher	mat = pat.matcher(email);
		if(mat.matches()){
			return email;
		}else{
			return null;
		}

	}

}
