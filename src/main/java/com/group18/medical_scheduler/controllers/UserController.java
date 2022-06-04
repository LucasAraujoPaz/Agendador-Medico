package com.group18.medical_scheduler.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group18.medical_scheduler.models.User;
import com.group18.medical_scheduler.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	@PostMapping
	public User create(@RequestBody @Valid User user) {
		return userService.create(user);
	}
	
	@GetMapping("/{email}")
	public User findByEmail(@PathVariable final String email) {
		return userService.findByEmail(email);
	}

	@GetMapping
	public Iterable<User> findAll() {
		return userService.findAll();
	}
	
	@DeleteMapping("/{email}")
	public void deleteByEmail(@PathVariable final String email) {
		userService.deleteByEmail(email);
	}	
}
