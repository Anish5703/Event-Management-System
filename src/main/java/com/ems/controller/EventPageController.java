package com.ems.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ems.entity.Event;
import com.ems.entity.Venue;
import com.ems.repository.CategoryRepository;
import com.ems.repository.EventRepository;
import com.ems.repository.VenueRepository;
import com.ems.services.CategoryService;
import com.ems.services.EventService;
import com.ems.services.VenueService;

@Controller
public class EventPageController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired 
	private EventService eventService;
	
	@Autowired
	private VenueService venueService;

//display event page 
	@GetMapping("/event")
	public String showEventPage(Model model) {
		model.addAttribute("updatedEvent",new Event());
		model.addAttribute("event",new Event());
		return "event";  //event dashboard page
	}

// add event
	@PostMapping("/event/add")
	public String addEvent(@ModelAttribute Event event, Model model,@CookieValue(name="id") String cookieId){
         
		Event newEvent = eventService.addEvent(event,cookieId);
      // verifying if data saved in database
		if(newEvent != null)
			model.addAttribute("addStatus","Event Created Successfully");
		else
			model.addAttribute("addStatus","Event Creation Failed");
		return "event.html"; //
	}

//event update
	@GetMapping("event/edit")
	public String editEvent(@ModelAttribute Event event,Model model)
	{
		//Revalidating event with database
		Event editingEvent = eventService.getEvent(event.getId());
		
		if(editingEvent != null)
		{
	    model.addAttribute("editStatus","Data Retrieved ! Ready to edit");
		model.addAttribute("event",editingEvent);
		return "event.html";    //return to event editing page
	     }
		else
		{
			model.addAttribute("venuesList",venueService.getVenueList());
			model.addAttribute("categoryList",categoryService.getCategoryList());
			model.addAttribute("editStatus","Event Cannot be Found");
			return "event.html";  //return back to dashboard page
		}
	}
	


//update event
	@PostMapping("/event/edit/update")
	public String saveEditEvent(@ModelAttribute Event updatedEvent, Model model) {

		// updating event in the database
		Event event = eventService.updateEvent(updatedEvent);
		
		if(event != null)
			model.addAttribute("updateStatus","Event Updated Successfully");
		else
			model.addAttribute("updateStatus","Event Update Failed");
		
		return "event.html";  //return to the dashboard page
	}
	
//Search for event name 
	@GetMapping("/event/search")
	public String searchEvent(@RequestParam(name="eventName")String eventName,Model model)
	{
		List<Event> eventList = eventService.getEventList();
		
		if(eventList != null)
		{
			model.addAttribute("eventListByName",eventList);
			model.addAttribute("searchStatus","Search Results are:");
		}
		else
			model.addAttribute("searchStatus","No events to show");
		
		return "event";
	}
	
	
	
//List all events available in database 
	@GetMapping("/event/list")
	public String listEvent(Model model)
	{		
		List<Event> eventList = eventService.getEventList();
		
		if(eventList != null)
		{
			model.addAttribute("eventList",eventList);
			model.addAttribute("listStatus","Event Lists are:");
		}
		else
			model.addAttribute("listStatus","No events to show");
			
		
		return "event"; 
	}
	
//Delete existing event from database 
	@PostMapping("/event/delete")
	public String deleteEvent(@ModelAttribute Event event,Model model)
	{
      boolean isDeleted = eventService.deleteEvent(event);
      if(isDeleted == true)
    	  model.addAttribute("deleteStatus","Event Deleted successfully");
      else
    	  model.addAttribute("deleteStatus","Event Delete Failed");
		
		return "event.html";
	}
}
