package com.ems.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.cookies.CookieGenerator;
import com.ems.entity.User;
import com.ems.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {

	@Autowired 
	private UserRepository userRepository;
	
	//find user by email
	public User getByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
	//check if email exists
	public boolean isEmailExists(String email)
	{
		if(userRepository.findByEmail(email) != null)
			return true;
		else
			return false;
	}
	
	//find user by username
	public User getByUsername(String username)
	{
		return userRepository.findByUsername(username);
	}
	
	//check if username exists
	public boolean isUsernameExists(String username)
	{
		if(userRepository.findByUsername(username) != null)
			return true;
		else
			return false;
	}
	
    //add new user to the database
	public User addUser(User user)
	{
		return userRepository.save(user); 
	}

}
