package com.group18.medical_scheduler.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group18.medical_scheduler.models.Authority;
import com.group18.medical_scheduler.models.LoginDTO;
import com.group18.medical_scheduler.models.User;
import com.group18.medical_scheduler.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	@PostMapping
	public User create(@RequestBody @Valid final LoginDTO user) {
		return userService.create(
				new User(user.email(), user.password(), Authority.USER));
	}
	
	@GetMapping("/me")
	public User getLoggedInUser() {
		return userService.getLoggedInUser();
	}

	@DeleteMapping("/me")
	public void deleteLoggedInUser() {
		userService.delete(userService.getLoggedInUser().getId());
	}
}
