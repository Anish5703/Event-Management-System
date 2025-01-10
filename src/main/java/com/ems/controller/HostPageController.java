package com.ems.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ems.entity.Event;
import com.ems.entity.User;
import com.ems.entity.Venue;
import com.ems.services.AuthenticationService;
import com.ems.services.CategoryService;
import com.ems.services.EventService;
import com.ems.services.UserService;
import com.ems.services.VenueService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HostPageController {

	@Autowired
	private VenueService venueService;
	
	@Autowired 
	private AuthenticationService authService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired 
	private UserService userService;
	
	//Homepage 
	@GetMapping("/host/home")
	public String showHostHomePage(HttpServletRequest req,
			HttpServletResponse resp,Model model) throws Exception
	{
		//Layer 1 Validation if the user is host otherwise it throws exception   
				authService.hostAuthorization(req, resp);
		model.addAttribute("categoryList",categoryService.getCategoryList());		
		model.addAttribute("eventList",eventService.getAllEventList());
				
		return "/host/homepage.html";
	}

	/**          Event     */
	
	//event page
	@GetMapping("/host/event")
	public String showHostEventPage(Model model,
			HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		//Layer 1 Validation if the user is host otherwise it throws exception   
				authService.hostAuthorization(req, resp);
		//get host username	
	    String username = authService.getUsernameByCookie(req, resp);
		
		model.addAttribute("eventList",eventService.getHostEventList(username));
		model.addAttribute("event",new Event());
		return "/host/event.html";
	}
	
	//add event form
	@GetMapping("/host/event/add")
	public String showHostEventAddPage(Model model,
			HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		//Layer 1 Validation if the user is host or it throws exception   
				authService.hostAuthorization(req, resp);	
				
		model.addAttribute("categoryList",categoryService.getCategoryList());
		model.addAttribute("venueList",venueService.getAllVenueList());
		model.addAttribute("event",new Event());
		return "/host/addEvent.html";
	}
	
	//save new event
	@PostMapping("/host/event/save")
	public String saveNewEvent(@ModelAttribute("event")Event event,
			@RequestParam(name="eventImage")MultipartFile file,
			@CookieValue("id")String cookieId,@RequestParam(name="category")String categoryId,
			HttpServletRequest req,HttpServletResponse resp,Model model) throws Exception
	{
		//Layer 1 Validation if the user is host or it throws exception   
		authService.hostAuthorization(req, resp);
		
		if(event != null)
		{
			//setting event image id 
			String imageId = eventService.generateEventImageId(file);
			event.setImageId(imageId);
			
			//setting host name
			event.setHost(authService.getUserByCookie(req, resp));
			
			//addinng new event to the database 
			Event newEvent = eventService.addEvent(event);
			if(newEvent != null)
				return "redirect:/host/event";
			else
				return "redirect:/host/event/add";
		}
		
		return "redirect:/host/event/add";
	}
	
	//show event edit page 
	@GetMapping("/host/event/edit/{id}")
	public String showEventEditPage(@PathVariable("id")String eventId,
			HttpServletRequest req,HttpServletResponse resp,Model model) throws Exception
	{
		
		//Layer 1 Validation if the user is host or it throws exception   
		authService.hostAuthorization(req, resp);
		
		Event event = eventService.getEvent(Integer.parseInt(eventId));
		
		//Layer 2 Validation if the user is the creator of the event otherwise throws Exception
		authService.ownerAuthorization(req, resp,event.getHost().getUsername());
		
		model.addAttribute("categoryList",categoryService.getCategoryList());
		model.addAttribute("venueList",venueService.getAllVenueList());
		model.addAttribute("event",event);
		
		return "/host/editEvent.html";
	}
	
	//Request Update Eevent
	@PostMapping("/host/event/update/{id}")
	public String updateEventPage(@PathVariable("id")String eventId,
			@ModelAttribute("event")Event event,@RequestParam(name="eventImage")MultipartFile file,
			HttpServletRequest req,HttpServletResponse resp,Model model) throws Exception
	{
		//Layer 1 Validation if the user is host or it throws exception   
		authService.hostAuthorization(req, resp);
		
		// retrieving event from database
		        Event dbEvent = eventService.getEvent(Integer.parseInt(eventId));
		        
		//Layer 2 Validation if the user is owner or it throws exception
		        authService.ownerAuthorization(req, resp, dbEvent.getHost().getUsername());
		        
		        //filling remaining fields
		        event.setId(dbEvent.getId());
		        event.setHost(dbEvent.getHost());
		        //filling image id
		        if(file == null)
		        	event.setImageId(dbEvent.getImageId());
		        else
		        {
					//setting new image id 
					String imageId = eventService.generateEventImageId(file);
					event.setImageId(imageId);
		        }
		        //updating event in database
				Event updatedEvent = eventService.updateEvent(event);
				
                return "redirect:/host/event";
		
		
	}
	
	//Delete Existing Event
	@GetMapping("/host/event/delete/{id}")
	public String deleteEventPage(@PathVariable("id")String eventId,
	HttpServletRequest req,HttpServletResponse resp,Model model) throws Exception
	{
		//Layer 1 Validation if the user is host or it throws exception   
				authService.hostAuthorization(req, resp);
		
		// retrieving event from database
		        Event dbEvent = eventService.getEvent(Integer.parseInt(eventId));
		        
		//Layer 2 Validation if the user is owner or it throws exception
		        authService.ownerAuthorization(req, resp, dbEvent.getHost().getUsername());
		        
		       boolean isDeleted = eventService.deleteEvent(dbEvent);
		       
		       if(isDeleted == false)
		    	   return "redirect:/host/home";
		       else
		    	   return "redirect:/host/event";
				
	}
	
	/**    Venue     */
	
	//all venue page
	@GetMapping("host/allvenue")
	public String showAllVenuePage(Model model,
			HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		//Layer 1 Validation if the user is host it throws exception   
				authService.hostAuthorization(req, resp);
		
		model.addAttribute("venueList",venueService.getAllVenueList());
		return "host/allvenue.html";
	}
	
	
	//host venue page
	@GetMapping("host/venue")
	public String showHostVenuePage(Model model,
			HttpServletRequest req, HttpServletResponse resp ) throws Exception
	{
		//Layer 1 Validation if the user is host it throws exception   
				authService.hostAuthorization(req, resp);
		
		String username = authService.getUsernameByCookie(req, resp);
		model.addAttribute("venueList",venueService.getHostVenueList(username));
		return "/host/venue.html";
	}
	
	//venue add form 
	@GetMapping("host/venue/add")
	public String showAddVenuePage(Model model,
			HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		//Layer 1 Validation if the user is host it throws exception   
				authService.hostAuthorization(req, resp);
		
		model.addAttribute("venue",new Venue());
		return "/host/addVenue";
	}
	
	//Save new venue
	@PostMapping("/host/venue/save")
	public String saveNewVenue(@ModelAttribute("venue")Venue venue,Model model,
			HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		//Layer 1 Validation if the user is host it throws exception   
		authService.hostAuthorization(req, resp);
		
		//check if venue already exists
		if((venueService.isPhoneExists(venue.getPhone()) == false) 
		|| (venueService.isNameExists(venue.getName()) == false))
		{
			venue.setAddedBy(authService.getUserByCookie(req, resp).getUsername());
			Venue newVenue = venueService.addVenue(venue);
			if(newVenue == null)
			model.addAttribute("addStatus","Something went wrong with database");
			else
				model.addAttribute("addStatus","New Data Added Successfully");
		}
		else
		{
			//error msg if phone number already exists
			if(venueService.isPhoneExists(venue.getPhone()) == true)
				model.addAttribute("addStatus","Phone Number Duplication Found");
			else
		    //error msg if venue name already exists
				model.addAttribute("addStatus","Venue Name Duplication Found");
		}
		return "redirect:/host/venue";
	}
	
	//edit venue page
	@GetMapping("/host/venue/edit/{venueId}")
	public String editVenue(@PathVariable("venueId")String venueId,Model model,
			HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		//Layer 1 Validation if the user is host otherwise throws Exception   
				authService.hostAuthorization(req, resp);
				
		//Get Venue Object by id
		Venue venue= venueService.getVenueById(Integer.parseInt(venueId));
		
		if(venue != null)
		{
		//Layer 2 Validation if the user is the creator of venue otherwise throws Exception
		authService.ownerAuthorization(req, resp,venue.getAddedBy());
		
        model.addAttribute("venue",venue);
        return "/host/editVenue.html";
		}
		else
		return "redirect:/host/venue";
		
	}
	
	//update existing venue
	@PostMapping("/host/venue/update/{id}")
	public String updateVenue(@PathVariable("id")String venueId,@ModelAttribute("venue")Venue venue,Model model,
			HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		//Layer 1 Validation if the user is host it throws exception   
		authService.hostAuthorization(req, resp);
        
		
		if(venue != null)
		{
			venue.setId(Integer.parseInt(venueId));
			venue.setAddedBy(authService.getUsernameByCookie(req, resp));
			//update venue in database
			venueService.updateVenue(venue);
			
			return "redirect:/host/venue";
			
		}
		return "redirect:/host/home";
		
		
	}
	
	//delete existing venue 
    @GetMapping("/host/venue/delete/{venueId}")
	public String deleteVenue(@PathVariable("venueId")String venueId,Model model,
			HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
        //Layer 1 Validation if the user is host it throws exception   
		authService.hostAuthorization(req, resp);
		
		//Get Venue Object by id
		Venue venue= venueService.getVenueById(Integer.parseInt(venueId));
		
		if(venue != null)
		{
		//Layer 2 Validation if the user is the creator of venue
		authService.ownerAuthorization(req, resp,venue.getAddedBy());
		
		boolean isDelete = venueService.deleteVenue(venue);
		if(isDelete == true)
			return "/host/venue";
		else
			return "redirect:/host/home";
		
		}
		else
		return "redirect:/host/venue";
		
		
	}

	
	
	//bookings list
	@GetMapping("/host/bookings")
	public String showHostBookingsPage()
	{
		return "/host/bookings.html";
	}
    
	//user profile page
	@GetMapping("/host/profile")
	public String showHostProfilePage(HttpServletRequest req,HttpServletResponse resp,
			Model model) throws Exception
	{
		model.addAttribute("user",authService.getUserByCookie(req, resp));
		return "/host/profile.html";
	}
	
	//edit user profile
	@GetMapping("/host/profile/edit")
	public String editHostProfile(HttpServletRequest req,HttpServletResponse resp,
			Model model) throws Exception
	{
        //Layer 1 Validation if the user is host it throws exception   
		authService.hostAuthorization(req, resp);
		model.addAttribute("user",authService.getUserByCookie(req, resp));
		return "/host/editprofile.html";
	}
	//update user profile
	@PostMapping("/host/profile/update")
	public String updateHostProfile(@ModelAttribute("user")User user,
			Model model,HttpServletResponse resp,HttpServletRequest req) throws Exception
	{
        //Layer 1 Validation if the user is host it throws exception   
		authService.hostAuthorization(req, resp);
		
		//Layer 2 Validation 
		authService.ownerAuthorization(req, resp,user.getUsername());
		
		User updatedUser = userService.updateUser(user);
		
		
		if(updatedUser != null)
			return "redirect:/login";
		else
			return "redirect:/host/profile";		

	}
	
	//logout user
	@GetMapping("/host/profile/logout")
	public String logoutHostProfile(HttpServletRequest req,HttpServletResponse resp,
			Model model)
	
	{
		boolean isLogout = authService.logoutProfile(req, resp);
		if(isLogout == true)
			return "redirect:/home";
		else
			return "redirect:/host/home";
	}
}
