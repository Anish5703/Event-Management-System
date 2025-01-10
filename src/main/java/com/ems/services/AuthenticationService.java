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
	
	/*authentication for user login 
	  Can be login by both email and username
	 */
	
	public User authenticateUserLogin(String loginName,String password,
			HttpServletRequest req,HttpServletResponse resp)
	{
		//validating if email exists or the username exists
		if(userService.isEmailExists(loginName) == true || userService.isUsernameExists(loginName))
		{
			User user = userRepository.findByEmail(loginName);  
			if(user==null)
				user = userRepository.findByUsername(loginName);
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
		User user = userRepository.findByCookieId(cookieId);
		if(user == null)
			throw new UserNotFoundException();
		else
		   return user.getRole();
			
	}
	
	//get User by cookie 
	public User getUserByCookie(HttpServletRequest req,HttpServletResponse resp) throws Exception
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
		   return user;
		}
	}
	
	//get Username by cookie
    public String getUsernameByCookie(HttpServletRequest req,HttpServletResponse resp)
    {
    	String cookieId = CookieGenerator.getCookieId(req, resp);
    	if(cookieId != null)
    	     return userRepository.findByCookieId(cookieId).getUsername();
    	else
    		return null;
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
	
	//Host Authorization
	public void hostAuthorization(HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		if(this.identifyUserRole(resp, req) != UserType.host)
			throw new OnlyHostIsAuthorizedException();
	}
	
	//Guest Authorization
	public void guestAuthorization(HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		if(this.identifyUserRole(resp, req) != UserType.guest)
			throw new OnlyGuestIsAuthorizedException();
	}
	
	// Owner Authorization
	public void ownerAuthorization(HttpServletRequest req,HttpServletResponse resp,
			String username) throws Exception
	{
		if(!this.getUsernameByCookie(req, resp).equals(username))
			throw new OwnerAuthorizationFailedException();
	}
}
