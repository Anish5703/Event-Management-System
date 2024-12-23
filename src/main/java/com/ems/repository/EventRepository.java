package com.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	
	Event findEventById(int id);
   	List<Event> findEventByName(String name);
	List<Event> findEventByVenueId(int venueId);
	
//	<Optional> List<Event> findEventByNameLike(String name);
}
