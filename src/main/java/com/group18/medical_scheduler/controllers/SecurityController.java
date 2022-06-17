package com.group18.medical_scheduler.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group18.medical_scheduler.models.LoginDTO;
import com.group18.medical_scheduler.models.User;
import com.group18.medical_scheduler.services.TokenService;

@RestController
@RequestMapping("/api")
public class SecurityController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Valid final LoginDTO loginDTO) {
		
		final String token = authenticate(loginDTO.email(), loginDTO.password());
		
		return ResponseEntity.ok(token);
	}
	
	private String authenticate(final String email, final String password) {

		final var authenticationToken = 
				new UsernamePasswordAuthenticationToken(email, password);

		final Authentication authentication = authenticationManager
				.authenticate(authenticationToken);
		final var user = (User) authentication.getPrincipal();
		Assert.state( ! user.getUsername().isBlank(), "Empty user name.");
		
		return tokenService.generateToken(user);
	}
}
