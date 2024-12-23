package com.ems.services;
import com.ems.exception.*;
import com.ems.user.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ems.cookies.CookieGenerator;
import com.ems.entity.User;
import com.ems.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	//authenticate user by cookie
	public User loginByCookie(HttpServletRequest req,HttpServletResponse resp)
	{
		String cookieId = CookieGenerator.getCookieId(req, resp);
		if( cookieId != null)
		{
			User user = userRepository.findByCookieId(cookieId);
			return (user != null) ?  user :  null;
		}
		else
			return null;
	}
	
	//authenticate user
	public User authenticateUser(String email,String password,
			HttpServletRequest req,HttpServletResponse resp)
	{
		
		if(userService.isEmailExists(email) == true)
		{
			User user = userRepository.findByEmail(email);     //validating if email exists
			if(user.getPassword().equals(password))
			{
				  CookieGenerator cg = new CookieGenerator();
				  String cookieId = cg.generateID();           //generating new cookie id
				  cg.setCookieId(req, resp, cookieId);         //setting cookie id in browser
				  user.setCookieId(cookieId);                  //setting cookie id in db
				  userRepository.save(user);                   //updating user db with new cookie id
				return user;
			}
			else
				return null;
		}
		else
			return null;
	}
	
	//identify user role through cookieId
	public UserType identifyUserRole(HttpServletResponse resp ,HttpServletRequest req) throws Exception
	{
		String cookieId = CookieGenerator.getCookieId(req,resp);
		if(cookieId == null)
			throw new UserNotFoundException();
		else
		{
		User user = userRepository.findByCookieId(cookieId);
		if(user == null)
			throw new UserNotFoundException();
		else
		   return user.getRole();
		}
			
	}
	
	//logout user profile
	public boolean logoutProfile(HttpServletRequest req, HttpServletResponse resp)
	{
		User user = this.loginByCookie(req,resp);
		if(user != null)
		{
			CookieGenerator cg = new CookieGenerator();
			cg.setCookieId(req, resp, null);
			return true;
		}
		else
			return false;
	}
}
