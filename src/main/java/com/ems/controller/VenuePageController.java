package com.ems.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ems.entity.Venue;
import com.ems.repository.VenueRepository;
import com.ems.services.VenueService;

@Controller
public class VenuePageController {
	
	@Autowired
	private VenueRepository venueRepository;
	
	@Autowired 
	private VenueService venueService;
	
	//Venue page
	@GetMapping("/venue")
	public String getVenuePage()
	{
		return "venue.html";
	}
	
	/*
	//adding venue
	@PostMapping("/venue/add")
	public String addVenue(@ModelAttribute Venue venue,Model model)
	{
		
		if((venueService.isPhoneExists(venue.getPhone()) == false) 
		|| (venueService.isNameExists(venue.getName()) == false))
		{
			Venue newVenue = venueService.addVenue(venue);
			if(newVenue == null)
			model.addAttribute("addStatus","Something went wrong with database");
			else
				model.addAttribute("addStatus","New Data Added Successfully");
		}
		else
		{
			//if phone number already exists
			if(venueService.isPhoneExists(venue.getPhone()) == true)
				model.addAttribute("addStatus","Phone Number Duplication Found");
			else
		    //if venue name already exists
				model.addAttribute("addStatus","Venue Name Duplication Found");
		}
		return "venue";
	}
	
	*/
	
	//Search for venue name
	@PostMapping("/venue/search")
	public String searchVenue(@RequestParam(name="venueName") String name,Model model)
	{
	   Venue venueByName = venueRepository.findVenueByName(name);
	   if(venueByName != null)
	   {
		   //return search venue
		   model.addAttribute("venueByName",venueByName);
		   model.addAttribute("searchStatus","Venue Found");
	   }
	   else
	   {
		   model.addAttribute("venueByName",venueByName);
		   model.addAttribute("searchStatus","No Venue Found");
	   }
	   return "venue";
	}
	
	//Edit the existing Venue 
	@PostMapping("/venue/edit")
	public String editVenue(@RequestParam(name="venueOldName") String oldName,
			@RequestParam(name="venueNewName") String newName,
			@RequestParam(name="address") String address,
			@RequestParam(name="phone") String phone,
			@RequestParam(name="description") String description,Model model)
	{
		Venue venueByName = venueRepository.findVenueByName(oldName);
		if(venueByName != null)
		{
			Venue venueByNewName = venueRepository.findVenueByName(newName);
			Venue venueByPhone = venueRepository.findVenueByPhone(phone);
			
			if(venueByNewName == null && venueByPhone == null)
			{
		   
			venueByName.setName(newName);
			venueByName.setAddress(address);
			venueByName.setPhone(phone);
			venueByName.setDescription(description);
			//updating database with new data on old entity
			venueRepository.save(venueByName);
			model.addAttribute("updateStatus","Update Successful");
			}
			else
			{
				if(venueByNewName == null)
					model.addAttribute("updateStatus","Phone Number Duplication Found");
				else
					model.addAttribute("updateStatus","Venue Name Duplication Found");
			}
			     
		}
		else
		{
		   model.addAttribute("updateStatus","Update Failed");
		}
		return "venue";
	}
	
	//Delete the existing venue
	@PostMapping("/venue/delete")
	public String deleteVenue(@RequestParam(name="venueName") String name,Model model)
	{
		Venue venueByName = venueRepository.findVenueByName(name);
		
		if(venueByName != null)
		{
			venueRepository.delete(venueByName);
			model.addAttribute("deleteStatus","Deletion Successful");
		}
		else
		{
			model.addAttribute("deleteStatus","Deletion Unsuccessful");
		}
		return "venue";
	}
	
	//list all the venue from database
	@GetMapping("/venue/list")
	public String listVenue(Model model)
	{
		List<Venue> venueList = venueRepository.findAll();
		model.addAttribute("venueList",venueList);
		if(venueList == null)
		{
			model.addAttribute("listStatus","Empty Venue List");
		}
		else
		{
			model.addAttribute("listStatus","Venue List");
		}
		return "venue";
	}

}
