package com.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue,Integer>{
	
	Venue findVenueById(int id);
	Venue findVenueByName(String venueName);
	Venue findVenueByPhone(String phone);

}
