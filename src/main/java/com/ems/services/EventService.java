package com.ems.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ems.entity.Event;
import com.ems.entity.User;
import com.ems.exception.*;
import com.ems.repository.EventRepository;
import com.ems.repository.UserRepository;
import com.ems.user.UserType;



@Service
public class EventService {

	@Autowired
	private UserRepository userRepository;
	//event repository
	@Autowired
	private EventRepository eventRepository;	
	
	//adding new event
	public Event addEvent(Event event,String cookieId)
	{
		User user = userRepository.findByCookieId(cookieId);
		if(user == null || user.getRole() == UserType.guest)
		{
			return null;
		}
		else
		{
			//setting host to the event
			event.setHost(user);
			//saving event to the repository
			return eventRepository.save(event);
		}
		
		
      }
	
	//get event by id
	public Event getEvent(int eventId) throws EventNotFoundException
	{
		Event event = eventRepository.findEventById(eventId);
		if(event != null)
			throw new EventNotFoundException("Event Not Found in Database");
		return event;
	}
	
	//get all available events 
	public List<Event> getEventList()
	{
		return eventRepository.findAll();
	}
	
	//update event 
	public Event updateEvent(Event event)
	{
		Event updatedEvent=null;
		try {
		updatedEvent = eventRepository.save(event);
		}
		catch(Exception ex)
		{
			System.out.println("Event Cannot Be Updated"+ex);
		}
			return updatedEvent;
	}
	
	//delete event and returns boolean values as a result of the operation
	public boolean deleteEvent(Event event)
	{
		try {
			eventRepository.delete(event);
			return true;
		}
		catch(Exception ex)
		{
			System.out.println("Event Not deleted :"+ex);
			return false;
		}
	}
	
	
}
