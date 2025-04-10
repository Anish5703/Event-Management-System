
package com.ems.entity;
import java.time.LocalDateTime;

import com.ems.repository.VenueRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
 
@ Entity
@Table(name="event_tbl")
public class Event{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	 private User host;
	 
	 @ManyToOne
	 private Venue venue;
	 
	@ManyToOne
	private Category category;
	
	private String name;
	
	private LocalDateTime reg_start_dateTime;
	
    private LocalDateTime reg_end_dateTime;
    
    private LocalDateTime event_start_dateTime;
    
    private LocalDateTime event_end_dateTime; 
     
    private int guestCapacity;
    
    private int ticketPrice;
    
    @Enumerated(value = EnumType.STRING)
    private EventStatusType status;
    
    private String imageId;
    
    private String description;    
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getReg_start_dateTime() {
		return reg_start_dateTime;
	}

	public void setReg_start_dateTime(LocalDateTime reg_open_dateTime) {
		this.reg_start_dateTime = reg_open_dateTime;
	}

	public LocalDateTime getReg_end_dateTime() {
		return reg_end_dateTime;
	}

	public void setReg_end_dateTime(LocalDateTime reg_end_dateTime) {
		this.reg_end_dateTime = reg_end_dateTime;
	}

	public LocalDateTime getEvent_start_dateTime() {
		return event_start_dateTime;
	}

	public void setEvent_start_dateTime(LocalDateTime event_dateTime) {
		this.event_start_dateTime = event_dateTime;
	}

	public LocalDateTime getEvent_end_dateTime() {
		return event_end_dateTime;
	}

	public void setEvent_end_dateTime(LocalDateTime event_end_dateTime) {
		this.event_end_dateTime = event_end_dateTime;
	}



	public int getGuestCapacity() {
		return guestCapacity;
	}

	public void setGuestCapacity(int guestCapacity) {
		this.guestCapacity = guestCapacity;
	}

	public int getTicketPrice() {
		return ticketPrice;
	}
	

	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}
    
	public Venue getVenue() {
		return venue;
	}
    public void setVenue(Venue venue)
    {
    	this.venue = venue;
    }
    public Category getCategory() {
    	return this.category;
    }
    public void setCategory(Category category) {
    	this.category = category;
    }
    //Get Event Current Status 
    public EventStatusType getStatus()
    {
       LocalDateTime currDateTime = LocalDateTime.now();
       if(currDateTime.isAfter(reg_start_dateTime) && currDateTime.isBefore(reg_end_dateTime))
    		   return EventStatusType.registration_active;
       else
       {
    	   if(currDateTime.isAfter(reg_end_dateTime) && currDateTime.isBefore(event_start_dateTime))
    		   return EventStatusType.registration_closed;
    	   else
    	   {
    		   if (currDateTime.isAfter(event_start_dateTime) && currDateTime.isBefore(event_end_dateTime))
    			   return EventStatusType.event_active;
    		   else 
    			   {
    			   if(currDateTime.isAfter(event_end_dateTime))
    		           return EventStatusType.event_closed;   
    			   else
    			       return EventStatusType.unknown;

    			   }
    	   }
       }
    }
    
    public String getImageId() {
       return this.imageId;
    }
    
    public void setImageId(String imageId)
    {
    	this.imageId = imageId;
    }
    

}