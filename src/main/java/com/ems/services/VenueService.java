package com.ems.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.Venue;
import com.ems.repository.VenueRepository;

@Service
public class VenueService {

	@Autowired
	private VenueRepository venueRepository;
	
	//get all available venue list
	public List<Venue> getVenueList()
	{
		return venueRepository.findAll();
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
}
