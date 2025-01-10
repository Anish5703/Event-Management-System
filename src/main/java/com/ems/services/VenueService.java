package com.ems.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.User;
import com.ems.entity.Venue;
import com.ems.repository.VenueRepository;

@Service
public class VenueService {

	@Autowired
	private VenueRepository venueRepository;
	
	@Autowired
	private AuthenticationService authService;
	
	//get all available venue list
	public List<Venue> getAllVenueList()
	{
		return venueRepository.findAll();
	}
	
	
	//get all venue added by the host
	public List<Venue> getHostVenueList(String username)
	{
		List<Venue> allVenueList = venueRepository.findAll();
		List<Venue> hostVenueList = new ArrayList<>();
		if(allVenueList != null)
		{
	    for(Venue venue : allVenueList)
	    {
	    	if(venue.getAddedBy().equals(username))
	    	{
	    	hostVenueList.add(venue);
	    	System.out.println("Username"+username+"\nList : "+venue.getName());
	    	}
	    }
	    return hostVenueList;
		}
		else
			return null;
		
	}
	
	//check if venue phone number already exists
	public boolean isPhoneExists(String phone)
	{
		return (venueRepository.findVenueByPhone(phone) != null) ? true : false;
	}
	
	//check if venue name already exists
	public boolean isNameExists(String name)
	{
		return (venueRepository.findVenueByName(name) != null) ? true : false;
	}
	
	//add new venue
	public Venue addVenue(Venue venue)
	{
		Venue newVenue = venueRepository.save(venue);
		return ( newVenue != null) ? newVenue : null; 
	}
	
	//update venue
	public Venue updateVenue(Venue venue)
	{
		System.out.println("Update Venue Line1:"+venue);
		if(venueRepository.findVenueById(venue.getId()) != null)
		{
			System.out.println("Update Venue Line2:"+venue);
			return this.addVenue(venue);
		}
		else
			return null;
	}
	
	//get Venue object by id
	public Venue getVenueById(int venueId)
	{
		return venueRepository.findVenueById(venueId);
	}
	
	// delete venue
	public boolean deleteVenue(Venue venue)
	{
		try {
		venueRepository.delete(venue);
		return true;
		}
		catch(Exception e)
		{
			System.out.println("Failed to delete venue");
			return false;
		}
	}
}
