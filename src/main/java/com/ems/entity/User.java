package com.ems.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.ems.user.UserType;

import jakarta.persistence.*;


@Entity
@Table(name="user_tbl")


public class User {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(unique = true)
	private String username;
    
    private String name;

	private String password;
	@Column(unique = true)
	private String email;
	private String phone;
	private String gender;
	
	@Enumerated(value = EnumType.STRING)
	private UserType userRole;
	
	@Column(unique = true)
	private String cookieId;
	
	 @CreationTimestamp
	  private Instant createdAt;
	 
	
	public UserType getRole() {
		return userRole;
	}


	public void setRole(UserType role) {
		this.userRole = role;
	}


	public Instant getCreatedAt() {
		return createdAt;
	}


	//default constructor
	public User()
	{
		super();
	}
	
	//param constructor

	public User(String username,String name, String password, String email, String phone,UserType userRole, String gender) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.userRole = userRole;
		this.gender = gender;
	}
	
	public int getId() {
		return id;
	}

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public UserType getUserRole() {
		return userRole;
	}
	public void setUserRole(UserType userRole) {
		this.userRole = userRole;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getCookieId() {
		return cookieId;
	}


	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}
}