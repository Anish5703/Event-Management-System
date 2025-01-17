package com.ems.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

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
	
	//getting directory from application.properties
	@Value("${event.upload-dir}")
	private String uploadDirectory;
	
	//adding new event
	public Event addEvent(Event event)
	{
		try {
	    return eventRepository.save(event);
		}
		catch(Exception e)
		{
			System.out.println("Unable to create new event"+e);
			return null;
		}
		
      }
    
	
	//get event by id
	public Event getEvent(int eventId) throws EventNotFoundException
	{
		Event event = eventRepository.findEventById(eventId);
		if(event == null)
			throw new EventNotFoundException();
		return event;
	}
	
	
	//get all available events 
	public List<Event> getAllEventList()
	{
		return eventRepository.findAll();
	}
	
	//get specific hosts event list
	public List<Event> getHostEventList(String username)
	{
		List<Event> eventList = new ArrayList<>();
		List<Event> allEventList = eventRepository.findAll();
		if(allEventList != null)
		{
			for(Event event : allEventList)
			{
				if(event.getHost().getUsername().equals(username))
				{
					eventList.add(event);
				}
			}
		}
		return eventList;
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
	
	//generate event image new url
	public String generateEventImageId(MultipartFile file)
	{
		try {
			 // Ensure directory exists
            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);  // Create directory if not exists
            }
			
		String originalFileName = file.getOriginalFilename();
		//get unique file name
		String uniqueFileName = System.currentTimeMillis()+"_"+originalFileName;
		//set file name with  the default directory
		Path fileNameWithPath = Paths.get(uploadDirectory,uniqueFileName);
		//save file to the directory
        Files.write(fileNameWithPath, file.getBytes());
		return uniqueFileName;
		}
		catch(Exception ex)
		{
			System.out.println("Image File Not Uploaded ");
			ex.printStackTrace();
			return "";
		}
		
	}
	
	
}
