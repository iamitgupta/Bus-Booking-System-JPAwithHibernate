package com.dev.bbs.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.dev.bbs.beans.Admin;
import com.dev.bbs.beans.Availability;
import com.dev.bbs.beans.Bus;
import com.dev.bbs.beans.Feedback;
import com.dev.bbs.beans.Ticket;
import com.dev.bbs.beans.User;

public class BbsDAOImpl implements BbsDAO {

	private final static EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("MySQLUnit");


	@Override
	public Boolean createUser(User user) {
		//persist user in table
		Boolean state = false;
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			em.close();
			state = true;
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public Boolean updateUser(User user) {
		//get the user and update 
		Boolean state = false;
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			User dbuser = em.find(User.class, user.getUserId());
			dbuser.setUsername(user.getUsername());
			dbuser.setEmail(user.getEmail());
			dbuser.setContact(user.getContact());
			dbuser.setPassword(user.getPassword());
			em.getTransaction().commit();
			em.close();
			state = true;
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public Boolean deleteUser(int userId) {
		//delete user detail
		Boolean state=false;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			User user = em.find(User.class, userId);
			em.remove(user);
			em.getTransaction().commit();
			em.close();
			state=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return state;

	}

	@Override
	public User loginUser(int userId, String password) {
		//login user and return the user obj
		User user = null;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<User> query = em.createQuery("from User u where userId= :id and password= :passwd", User.class);
			query.setParameter("id", userId);
			query.setParameter("passwd", password);
			List<User> users = query.getResultList();
			if(users.size()>0)
				user = users.get(0);
			em.getTransaction().commit();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User searchUser(int userId) {
		//return user profile
		EntityManager em = emf.createEntityManager();
		User user = em.find(User.class, userId);
		em.close();
		return user;	
	}

	@Override
	public Boolean createBus(Bus bus) {
		//register bus
		Boolean state = false;
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(bus);
			em.getTransaction().commit();
			em.close();
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public Boolean updateBus(Bus bus) {
		//update bus details
		Boolean state = false;
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Bus dbus = em.find(Bus.class, bus.getBusId());
			dbus.setBusname(bus.getBusname());
			dbus.setBusType(bus.getBusType());
			dbus.setSource(bus.getSource());
			dbus.setDestination(bus.getDestination());
			dbus.setPrice(bus.getPrice());
			dbus.setTotalSeats(bus.getTotalSeats());
			em.getTransaction().commit();
			em.close();
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public Bus searchBus(int busId) {
		//search bus
		EntityManager em = emf.createEntityManager();
		Bus bus = em.find(Bus.class, busId);
		em.close();
		return bus;
	}

	@Override
	public Boolean deletebus(int busId) {
		//delete bus 
		Boolean state=false;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Bus bus = em.find(Bus.class, busId);
			em.remove(bus);
			em.getTransaction().commit();
			em.close();
			state=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public Admin adminLogin(int adminId, String password) {
		//admin login and return admin obj
		Admin admin = null;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Admin> query = em.createQuery("from Admin a where adminId= :id and password= :passwd", Admin.class);
			query.setParameter("id", adminId);
			query.setParameter("passwd", password);
			List<Admin> adminLi = query.getResultList();
			if(adminLi.size()>0)
				admin = adminLi.get(0);
			em.getTransaction().commit();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}

	@Override
	public Boolean writeFeedback(Feedback feed) {
		//persist feedback in table
		Boolean state = false;
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(feed);
			em.getTransaction().commit();
			em.close();
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public List<Feedback> viewFeedbac() {
		//get all feedback
		List<Feedback> feed=null;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Feedback> query = em.createQuery("from Feedback ", Feedback.class);
			feed = query.getResultList();
			em.getTransaction().commit();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return feed;
	}

	@Override
	public Ticket bookTicket(Ticket ticket) {
		//book ticket and decrement the seats from availability column
		Availability availability=null;
		ticket.setBookingDate(new java.util.Date());
		Ticket bookedTicket=null;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Availability> availQuery=em.createQuery("from Availability a where availDate= :aDate and busId= :bId",Availability.class);
			
			
			//book ticket
			em.persist(ticket);
			
			//update num of cancelled ticket
			availQuery.setParameter("aDate", ticket.getJourneyDate());
			availQuery.setParameter("bId", ticket.getBusId());
			
			List<Availability> availList = availQuery.getResultList();
			if(availList.size()>0)
				availability = availList.get(0);
			availability.setAvailSeats(availability.getAvailSeats()-ticket.getNumofseats());
			
			em.getTransaction().commit();
			em.close();
			
			return ticket;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bookedTicket;
	}

	@Override
	public Boolean cancelTicket(int bookingId, int userId) {
		//cancel ticket and update the availability column
		Boolean state=false;
		Ticket ticket=null;
		Availability availability=null;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Ticket> query = em.createQuery("from Ticket t where user_id= :uid and bookingId= :bid", Ticket.class);
			TypedQuery<Availability> availQuery=em.createQuery("from Availability a where availDate= :aDate and busId= :bId",Availability.class);
			query.setParameter("uid", userId);
			query.setParameter("bid", bookingId);
			
			List<Ticket> ticketList = query.getResultList();
			if(ticketList.size()>0) {
				ticket = ticketList.get(0);
				em.remove(ticket);
			
				//increase num of cancelled ticket
				availQuery.setParameter("aDate", ticket.getJourneyDate());
				availQuery.setParameter("bId", ticket.getBusId());
			
				List<Availability> availList = availQuery.getResultList();
				availability = availList.get(0);
				availability.setAvailSeats(availability.getAvailSeats()+ticket.getNumofseats());
				state=true;
			
			}
			em.getTransaction().commit();
			em.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public Ticket getTicket(int bookingId) {
		//get ticket
		Ticket ticket=null;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Ticket> query = em.createQuery("from Ticket t where bookingId= :bid", Ticket.class);
			query.setParameter("bid", bookingId);
			List<Ticket> ticketLi = query.getResultList();
			if(ticketLi.size()>0)
				ticket = ticketLi.get(0);
			em.getTransaction().commit();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}

	@Override
	public List<Ticket> getAllTicket(int userId) {
		//get all ticket of particular user
		List<Ticket> ticketLi=null;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Ticket> query = em.createQuery("from Ticket t where user_id= :uid", Ticket.class);
			query.setParameter("uid", userId);
			ticketLi = query.getResultList();
			em.getTransaction().commit();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ticketLi;	
	}

	@Override
	public List<Availability> checkAvailability(String source, String destination, Date date) {
		//get list of bus availability
		List<Availability> availList=new ArrayList<Availability>();
		List<Availability> resulList=null;
		List<Bus> busList=null;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Bus> query = em.createQuery("from Bus b where source= :busSource and destination= :busDestination", Bus.class);
			query.setParameter("busSource", source);
			query.setParameter("busDestination", destination);
			busList = query.getResultList();
			TypedQuery<Availability> availQuery=em.createQuery("from Availability a where busId= :bid and availDate= :aDate",Availability.class);
			if(busList.size()>0) {
				for(Bus bus : busList) {
					
					
					availQuery.setParameter("bid", bus.getBusId());
					availQuery.setParameter("aDate", date);
					
					//get the result from table 
					resulList=availQuery.getResultList();
					
					//store the Availability in availList
					availList.addAll(resulList);
					
					
					
				}
			}
			em.getTransaction().commit();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return availList;

	}

	@Override
	public Integer checkAvailability(int busId, Date date) {
		//check bus availability
		Integer availSeats=0;
		Availability availability=null;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Availability> query = em.createQuery("from Availability a where bus_id= :bid and availDate= :date", Availability.class);
			query.setParameter("bid", busId);
			query.setParameter("date", date);
			List<Availability> avaiList = query.getResultList();
			if(avaiList.size()>0) {
				availability  = avaiList.get(0);
				availSeats = availability.getAvailSeats();
			}
			em.getTransaction().commit();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return availSeats;
	}

	@Override
	public List<Ticket> getTicketByBus(int busId, Date date) {
		//get all ticket of particular
		List<Ticket> ticketLi=null;
		try{
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Ticket> query = em.createQuery("from Ticket t where bus_id= :bid and journeyDate= :jDate", Ticket.class);
			query.setParameter("bid", busId);
			query.setParameter("jDate", date);
			ticketLi = query.getResultList();
			em.getTransaction().commit();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ticketLi;	
	}

	@Override
	public Boolean setBusAvailability(Availability availability) {
		//set bus availability
		Boolean state = false;
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(availability);
			em.getTransaction().commit();
			em.close();
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}



}
