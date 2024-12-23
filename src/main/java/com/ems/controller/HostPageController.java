package com.ems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ems.entity.Event;
import com.ems.entity.Venue;

@Controller
public class HostPageController {

	
	//homepage 
	@GetMapping("/host/home")
	public String showHostHomePage()
	{
		return "/host/homepage.html";
	}

	/**          Event     */
	
	//event page
	@GetMapping("/host/event")
	public String showHostEventPage()
	{
		return "/host/event.html";
	}
	
	//add event form
	@GetMapping("/host/event/add")
	public String showHostEventAddPage(Model model)
	{
		model.addAttribute("event",new Event());
		return "/host/addEvent.html";
	}
	
	//save new event
	@PostMapping("/host/event/save")
	public String saveNewEvent(@ModelAttribute("event")Event event,Model model)
	{
		return "";
	}
	
	/**    Venue     */
	
	//venue page
	@GetMapping("host/venue")
	public String showHostVenuePage()
	{
		return "/host/venue.html";
	}
	
	//venue add form 
	@GetMapping("host/venue/add")
	public String showAddVenuePage()
	{
		return "/host/addVenue";
	}
	
	//venue Edit Page
	@PostMapping("/host/venue/save")
	public String saveNewVenue(@ModelAttribute("venue")Venue venue)
	{
		return "/host/venue";
	}
	//bookings list
	@GetMapping("host/bookings")
	public String showHostBookingsPage()
	{
		return "/host/bookings.html";
	}
    
	//user profile page
	@GetMapping("host/profile")
	public String showHostProfilePage()
	{
		return "/host/profile.html";
	}
}
