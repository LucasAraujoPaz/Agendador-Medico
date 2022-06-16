package com.group18.medical_scheduler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.group18.medical_scheduler.interfaces.CRUDService;
import com.group18.medical_scheduler.models.User;
import com.group18.medical_scheduler.repositories.UserRepository;

@Service
public class UserService implements CRUDService<User> {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public CrudRepository<User, Integer> getRepository() {
		return userRepository;
	}	
	
	@Override
	public User create(final User user) {

		final var encodedPassword = new BCryptPasswordEncoder()
				.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		return CRUDService.super.create(user);
	}
	
	public User findByEmail(final String email) {
		return userRepository
				.findByEmail(email)
				.orElseThrow();
	}
	
	public User getLoggedInUser() {
		final var authentication = SecurityContextHolder.getContext().getAuthentication();
		return findByEmail(authentication.getName());
	}
}
