package com.dev.bbs.app;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.dev.bbs.beans.Admin;
import com.dev.bbs.beans.Availability;
import com.dev.bbs.beans.Bus;
import com.dev.bbs.beans.Feedback;
import com.dev.bbs.beans.Ticket;
import com.dev.bbs.beans.User;
import com.dev.bbs.exception.BusNotFoundException;
import com.dev.bbs.exception.DeletionFailedException;
import com.dev.bbs.exception.LoginFailedException;
import com.dev.bbs.exception.RegsitrationFailedException;
import com.dev.bbs.exception.UpdationFailedException;
import com.dev.bbs.exception.UserNotFoundException;
import com.dev.bbs.services.ServiceDAO;
import com.dev.bbs.services.ServiceDAOImpl;

public class AdminApp {
	static ServiceDAO services = new ServiceDAOImpl();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Admin admin = null;
		try {
			admin = adminLogin();
			System.out.println("hello "+admin.getName());
		} catch (LoginFailedException e1) {
			e1.printStackTrace();
		}

		if (admin!=null) {
			System.out.println("Login Successful");
			Boolean b = true;
			while (b) {
				// options to perform different operation
				System.out.println(
						"*******************************************************************************************************************************");

				System.out.println("Enter a choice");
				System.out.println("1.Add Bus");
				System.out.println("2.Update Bus");
				System.out.println("3.Delete Bbs");
				System.out.println("4.Bus availability");
				System.out.println("5.Search User");
				System.out.println("6.Search Bus");
				System.out.println("7.View Feedback");
				System.out.println("8.Set Bus Availability");
				System.out.println("9.Check Booked Ticket");
				System.out.println("10.exit");
				int ch = Integer.parseInt(sc.next());
				switch (ch) {
				case 1:
					try {
						createBus();
					} catch (RegsitrationFailedException e1) {
						e1.printStackTrace();
					}
					break;
				case 2:
					try {
						updateBus();
					} catch (UpdationFailedException e1) {
						e1.printStackTrace();
					}
					break;
				case 3:
					try {
						deletebus();
					} catch (DeletionFailedException e1) {
						e1.printStackTrace();
					}
					break;
				case 4:
					checkAvailability();
					break;
				case 5:
					try {
						searchUser();
					} catch (UserNotFoundException e) {
						e.printStackTrace();
					}
					break;
				case 6:
					try {
						searchBus();
					} catch (BusNotFoundException e) {
						e.printStackTrace();
					}
					break;
				case 7:
					viewFeddback();
					break;
				case 8:
					try {
						setBusAvailability();
					} catch (BusNotFoundException e) {
						e.printStackTrace();
					}
					break;
				case 9:
					checkBookedTicket();
					break;
				case 10:
					b = false;
					break;
				default:
					System.out.println("Plz enter correct option");
				}
			}
		} else {
			System.out.println("Login failed");
		}

	}

	private static void checkBookedTicket() {
		int busId=0;
		boolean idValidate=true;
		while(idValidate){
			System.out.println("Enter the busId");
			String  sId=sc.next();
			Integer res=services.validateId(sId);
			if(res!=null){
				busId=res;
				idValidate=false;
			}else{
				System.out.println("Id should be Integer");
			}
		}
		
		System.out.println("Enter the date(yyyy-mm-dd)");
		String tempDate = sc.next();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = sdf.parse(tempDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Ticket> ticketAL=services.getTicketByBus(busId, date);
		if(ticketAL.size()>0){
			System.out.println("Tickets are:");
			for (Ticket ticket : ticketAL) {
				System.out.println(ticket);
				System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
			}
		}else{
			System.out.println("No Ticket found");
		}

	}

	private static void setBusAvailability() throws BusNotFoundException {
		Availability availability=new Availability();
		int busId=0;
		boolean idValidate=true;
		while(idValidate){
			System.out.println("Enter the busId");
			String  sId=sc.next();
			Integer res=services.validateId(sId);
			if(res!=null){
				busId=res;
				idValidate=false;
			}else{
				System.out.println("Id should be Integer");
			}
		}
		Bus bus=services.searchBus(busId);
		if(bus!=null){
			System.out.println(bus);
			availability.setBusId(busId);
			System.out.println("Enter the Available seats");
			availability.setAvailSeats(Integer.parseInt(sc.next()));
			System.out.println("Enter the date(yyyy-mm-dd)");
			String tempDate = sc.next();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			try {
				date = sdf.parse(tempDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			availability.setAvailDate(date);

			if(services.setBusAvailability(availability)){
				System.out.println("Successfully Set the availability");
			}
		}else{
			System.out.println("Failed to Set the availability");
			throw new BusNotFoundException("No Bus found");
		}


	}

	private static void viewFeddback() {
		List<Feedback> feedList = services.viewFeedbac();
		if(feedList.size()>0){
			System.out.println("Feedbacks are:");
			for (Feedback feed : feedList) {
				System.out.println(feed);
				System.out.println("-------------------------------------------------------------------------------------------------------------------");
			}
		}else{
			System.out.println("No feedback found");
		}

	}

	private static void searchBus() throws BusNotFoundException {
		int busId=0;
		boolean idValidate=true;
		while(idValidate){
			System.out.println("Enter the busId");
			String  sId=sc.next();
			Integer res=services.validateId(sId);
			if(res!=null){
				busId=res;
				idValidate=false;
			}else{
				System.out.println("Id should be Integer");
			}
		}
		Bus bus = services.searchBus(busId);
		if (bus != null) {
			System.out.println(bus);

		} else {
			throw new BusNotFoundException("No Bus found");
		}

	}

	public static void createBus() throws RegsitrationFailedException {
		Bus bus = new Bus();
		int busId=0;
		boolean idValidate=true;
		while(idValidate){
			System.out.println("Enter the busId");
			String  sId=sc.next();
			Integer res=services.validateId(sId);
			if(res!=null){
				busId=res;
				idValidate=false;
			}else{
				System.out.println("Id should be Integer");
			}
		}
		bus.setBusId(busId);
		System.out.println("Enter Bus name");
		bus.setBusname(sc.next());
		System.out.println("Enter source point");
		bus.setSource(sc.next());
		System.out.println("Enter Destination point");
		bus.setDestination(sc.next());
		System.out.println("Enter Bus type");
		bus.setBusType(sc.next());
		System.out.println("Total seats");
		bus.setTotalSeats(Integer.parseInt(sc.next()));
		System.out.println("Enter fare charge");
		bus.setPrice(sc.nextDouble());

		if (services.createBus(bus)) {
			System.out.println("Bus added Successfully");
		} else {
			throw new RegsitrationFailedException("Bus could not be added");
		}
	}

	public static Admin adminLogin() throws LoginFailedException {
		int adminId=0;
		boolean idValidate=true;
		while(idValidate){
			System.out.println("Enter the adminId");
			String  sId=sc.next();
			Integer res=services.validateId(sId);
			if(res!=null){
				adminId=res;
				idValidate=false;
			}else{
				System.out.println("Id should be Integer");
			}
		}
		System.out.println("Enter Admin password");
		String password = sc.next();
		Admin admin=services.adminLogin(adminId, password);
		if (admin!=null) {
			return admin;
		} else {
			throw new LoginFailedException("Admin login Failed");
		}

	}

	public static void updateBus() throws UpdationFailedException {
		Bus bus = new Bus();
		int busId=0;
		boolean idValidate=true;
		while(idValidate){
			System.out.println("Enter the busId");
			String  sId=sc.next();
			Integer res=services.validateId(sId);
			if(res!=null){
				busId=res;
				idValidate=false;
			}else{
				System.out.println("Id should be Integer");
			}
		}	
		bus.setBusId(busId);
		System.out.println("Enter Bus name");
		bus.setBusname(sc.next());
		System.out.println("Enter source point");
		bus.setSource(sc.next());
		System.out.println("Enter Destination point");
		bus.setDestination(sc.next());
		System.out.println("Enter Bus type");
		bus.setBusType(sc.next());
		System.out.println("Total seats");
		bus.setTotalSeats(Integer.parseInt(sc.next()));
		System.out.println("Enter fare charge");
		bus.setPrice(sc.nextDouble());

		if (services.updateBus(bus)) {
			System.out.println("Bus updated Successfully");
		} else {
			throw new UpdationFailedException("Bus Updation Failed");
		}

	}

	public static void deletebus() throws DeletionFailedException {
		int busId=0;
		boolean idValidate=true;
		while(idValidate){
			System.out.println("Enter the busId");
			String  sId=sc.next();
			Integer res=services.validateId(sId);
			if(res!=null){
				busId=res;
				idValidate=false;
			}else{
				System.out.println("Id should be Integer");
			}
		}
		System.out.println("Are you sure you want to delete\n1. Yes");
		if (Integer.parseInt(sc.next()) == 1) {
			if (services.deletebus(busId)) {
				System.out.println("Sucessfylly Deleted Bus data");
			} else {
				throw new DeletionFailedException("Bus deletion failed");
			}
		}

	}

	public static void checkAvailability() {

		System.out.println("Enter a choice");
		System.out.println("Check Availability by \n1.Bus id\n2.Source and Destination");
		int chAvail = Integer.parseInt(sc.next());
		if (chAvail == 1) {
			System.out.println("Enter the Bus id");
			int busId = Integer.parseInt(sc.next());
			System.out.println("Enter the date(yyyy-mm-dd)");
			String tempDate = sc.next();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			try {
				date = sdf.parse(tempDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Integer availSeats = services.checkAvailability(busId, date);
			if (availSeats != null) {
				System.out.println("Total available seats are: " + availSeats);
			}

		} else if (chAvail == 2) {
			System.out.println("Enter source point");
			String source = sc.next();
			System.out.println("Enter Destination point");
			String destination = sc.next();
			System.out.println("Enter the date(yyyy-mm-dd)");
			String tempDate = sc.next();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			try {
				date = sdf.parse(tempDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			List<Availability> avaiList = services.checkAvailability(source, destination, date);
			for (Availability avail : avaiList) {
				Bus bus = services.searchBus(avail.getBusId());
				System.out.println(bus);
				System.out.println("Available seats : " + avail.getAvailSeats());

			}
		}
	}

	public static void searchUser() throws UserNotFoundException {
		int userId=0;
		boolean idValidate=true;
		while(idValidate){
			System.out.println("Enter the userId");
			String  sId=sc.next();
			Integer res=services.validateId(sId);
			if(res!=null){
				userId=res;
				idValidate=false;
			}else{
				System.out.println("Id should be Integer");
			}
		}
		User user = services.searchUser(userId);
		if (user != null) {
			System.out.println(user);
		} else {
			throw new UserNotFoundException("No User Found");
		}

	}

}
