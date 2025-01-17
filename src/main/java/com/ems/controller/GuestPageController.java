package com.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ems.entity.Booking;
import com.ems.entity.Event;
import com.ems.entity.User;
import com.ems.services.AuthenticationService;
import com.ems.services.BookingService;
import com.ems.services.CategoryService;
import com.ems.services.EventService;
import com.ems.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class GuestPageController {
	@Autowired 
	private  AuthenticationService authService;
	
	@Autowired 
	private CategoryService categoryService;
	
	@Autowired 
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookingService bookingService;
	
	
	//homepage for guest
	@GetMapping("/guest/home")
	public String showGuestHomepage(Model model,
			HttpServletRequest req,HttpServletResponse resp)throws Exception
	{
		//Layer 1 Validation if the user is host otherwise it throws exception   
		authService.guestAuthorization(req, resp);
		
model.addAttribute("categoryList",categoryService.getCategoryList());		
model.addAttribute("eventList",eventService.getAllEventList());
return "/guest/homepage.html";
	}

	//event page for guest
	@GetMapping("/guest/event")
	public String showGuestEventPage(Model model, 
			HttpServletRequest req,HttpServletResponse resp)throws Exception
	{
		//Layer 1 Validation if the user is host otherwise it throws exception   
		authService.guestAuthorization(req, resp);
		
model.addAttribute("categoryList",categoryService.getCategoryList());		
model.addAttribute("eventList",eventService.getAllEventList());
				return "/guest/event.html";
	}
	
    //select  number of  ticket page for guest
	@GetMapping("/guest/join/{id}")
	public String selectTicketQuantity(@PathVariable("id")int eventId,HttpServletRequest req,
			HttpServletResponse resp,Model model)throws Exception
	{
		//Layer 1 Validation if the user is host otherwise it throws exception   
				authService.guestAuthorization(req, resp);
				
			model.addAttribute("event",eventService.getEvent(eventId));
				
			return "/guest/bookEvent.html";	
	}
	//book event
	@PostMapping("/guest/book/{id}")
	public String ShowEventBookingPage(@PathVariable("id")int eventId,
			@RequestParam(name="ticketCount")int ticketCount,
			HttpServletRequest req,
			HttpServletResponse resp,Model model) throws Exception
	{
		//Layer 1 Validation if the user is host otherwise it throws exception   
		authService.guestAuthorization(req, resp);
		
		User user = authService.getUserByCookie(req, resp);
		Event event = eventService.getEvent(eventId);
		Booking booking = bookingService.bookEvent(event, user,ticketCount);
		model.addAttribute("booking",booking);
		System.out.println(booking);
		return "/guest/reciept.html";
	}
	//show guest event booking list page
	@GetMapping("/guest/bookings")
	public String showGuestBookingListPage(HttpServletRequest req,
			HttpServletResponse resp,Model model) throws Exception
	{
		//Layer 1 Validation if the user is host otherwise it throws exception   
				authService.guestAuthorization(req, resp);
				
				User user = authService.getUserByCookie(req, resp);
	       
	       model.addAttribute("bookingList",bookingService.getGuestBookingList(user));
	       return "/guest/bookings.html";
		
	}
	
	//user profile page
	@GetMapping("/guest/profile")
	public String showGuestProfilePage(HttpServletRequest req,HttpServletResponse resp,
			Model model) throws Exception
	{
		model.addAttribute("user",authService.getUserByCookie(req, resp));
		return "/guest/profile.html";
	}
	
	//edit user profile
	@GetMapping("/guest/profile/edit")
	public String editGuestProfile(HttpServletRequest req,HttpServletResponse resp,
			Model model) throws Exception
	{
        //Layer 1 Validation if the user is host it throws exception   
		authService.guestAuthorization(req, resp);
		
		model.addAttribute("user",authService.getUserByCookie(req, resp));
		return "/guest/editprofile.html";
	}
	//update user profile
	@PostMapping("/guest/profile/update")
	public String updateGuestProfile(@ModelAttribute("user")User user,
			Model model,HttpServletResponse resp,HttpServletRequest req) throws Exception
	{
        //Layer 1 Validation if the user is host it throws exception   
		authService.guestAuthorization(req, resp);
		
		//Layer 2 Validation 
		authService.ownerAuthorization(req, resp,user.getUsername());
		
		User updatedUser = userService.updateUser(user);
		
		
		if(updatedUser != null)
			return "redirect:/login";
		else
			return "redirect:/guest/profile";		

	}
	
	//logout user
	@GetMapping("/guest/profile/logout")
	public String logoutGuestProfile(HttpServletRequest req,HttpServletResponse resp,
			Model model)
	
	{
		boolean isLogout = authService.logoutProfile(req, resp);
		if(isLogout == true)
			return "redirect:/home";
		else
			return "redirect:/guest/home";
	}
	
}
