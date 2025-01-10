package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ems.services.AuthenticationService;
import com.ems.services.CategoryService;
import com.ems.services.EventService;

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
	
	@GetMapping("/guest/home")
	public String showGuestHomepage(Model model,
			HttpServletRequest req,HttpServletResponse resp)throws Exception
	{
		//Layer 1 Validation if the user is host otherwise it throws exception   
		authService.guestAuthorization(req, resp);
model.addAttribute("categoryList",categoryService.getCategoryList());		
model.addAttribute("eventList",eventService.getAllEventList());
				return "/guest/homepage";
	}

}
