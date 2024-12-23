package com.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name="venue_tbl")

public class Venue {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column (unique=true)
	private String name;
	
	private String address;
	
	@Column (unique=true)
	private String phone;
	
	private String description;
	
	public Venue()
	{
		
	}
	
	public Venue(String venueName, String address, String phone,String description)
	{
		this.name = venueName;
		this.address = address;
		this.phone = phone;
		this.description = description;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String venueName) {
		this.name = venueName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDiscription() {
		return description;
	}

	public void setDiscription(String discription) {
		this.description = discription;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
