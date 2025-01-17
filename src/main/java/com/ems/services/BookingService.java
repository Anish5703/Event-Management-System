package com.ems.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ems.entity.Booking;
import com.ems.entity.Event;
import com.ems.entity.PaymentStatusType;
import com.ems.entity.User;
import com.ems.exception.EventNotFoundException;
import com.ems.exception.UserNotFoundException;
import com.ems.repository.BookingRepository;




@Service
public class BookingService {
	
	@Autowired
	private BookingRepository bookingRepo;
	
	//book a event
	public Booking bookEvent(Event event,User user,int ticketCount) throws Exception
	{
		if(event == null)
			throw new EventNotFoundException();
		if(user == null)
			throw new UserNotFoundException();
		
		Booking newBooking = new Booking();
		newBooking.setEvent(event);
		newBooking.setUser(user);
		newBooking.setTicketCount(ticketCount);
		newBooking.setPaymentStatus(PaymentStatusType.pending);
		newBooking.setTotalAmount();
		newBooking.setTicketId();
		try {
		return bookingRepo.save(newBooking);
		}
		catch(Exception ex)
		{
			System.out.println("New Booking Failed :"+ex);
			return null;
		}
	}
	

}
