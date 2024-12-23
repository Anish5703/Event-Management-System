
package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ems.cookies.CookieGenerator;
import com.ems.entity.User;
import com.ems.repository.UserRepository;
import com.ems.services.AuthenticationService;
import com.ems.services.UserService;
import com.ems.user.UserType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomePageController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationService authService;

	@GetMapping("/home")
	public String getHomepage() {
		return "homepage.html";
	}

	@GetMapping("/register")
	public String getSignup(Model model) {
		model.addAttribute("user",new User());
		return "registration.html";
	}
	
	//register new user
	@PostMapping("/register/user")
	public String registerUser(@ModelAttribute User user,
			@RequestParam(name="userRole")String role,Model model)
	{
		//check if email and username already exists
		if(userService.isEmailExists(user.getEmail()) ==true || userService.isUsernameExists(user.getUsername()) == true)
		{
			if(userService.isEmailExists(user.getEmail()) == true)
			model.addAttribute("emailStatus","Email already exists");
			if(userService.isUsernameExists(user.getUsername()) == true)
			model.addAttribute("usernameStatus","Username already exists");
			return "registration.html";  //return to the registration page
		}
		else
		{
			user.setRole(UserType.valueOf(role));     
			User newUser = userService.addUser(user);      //add user to the db
			if(newUser != null)                            //validate if user is add to the db
			{ 
				model.addAttribute("addStatus","User Added Successfully !");
				return "login";  //return to login page
			}
			
			else
			{
				model.addAttribute("addStatus","Failed to add new user");
				return "registration"; //return to the registration page
				
			}
		}

	}

	//User Login
	@GetMapping("/login")
	public String showLoginPage(HttpServletRequest req,HttpServletResponse resp,Model model)
	{
		
     User user = authService.loginByCookie(req, resp);
     if(user != null)
     {
    	 model.addAttribute("user",user);
    	 return "userHomepage.html";     //return to user homepage
     }
     else
        return "login.html";      //return to the log in page
	}
	


	
	//Validate Login
	@PostMapping("/login/user")
	public String validateLogin(@RequestParam(name="fullName")String username,
			@RequestParam(name="password")String password,
			HttpServletRequest req,HttpServletResponse resp,
			Model model) throws Exception
	{
		
		User user = authService.authenticateUser(username, password, req, resp);
		if(user == null)
		{
			model.addAttribute("loginStatus","Invalid credentials");
			return "login"; //return to the login page
		}
        
		if(authService.identifyUserRole(resp,req)==UserType.host)
			return "hostPage";

		return "userHomepage";
	}
	
	//Logout User from bowser
	@GetMapping("/logout")
	public String logoutUser(HttpServletRequest req, HttpServletResponse resp)
	{
		return (authService.logoutProfile(req, resp) == true) ? "login.html" : "homepage.html";

	}
}
