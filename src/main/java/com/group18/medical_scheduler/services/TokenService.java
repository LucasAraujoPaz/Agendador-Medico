package com.group18.medical_scheduler.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.group18.medical_scheduler.models.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.minutes-to-expire}")
	private long minutesToExpire = 30;
	@Autowired
	private UserService userService;

	public String generateToken(final User user) {

		String token = Jwts.builder()
				.setIssuer("Medical Scheduler")
				.setSubject(user.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 
						minutesToExpire * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
		
		return token;
	}

	public void authenticate(final String token) {

		final String email = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();

		final var user = userService.findByEmail(email);

		final var authentication = new UsernamePasswordAuthenticationToken(
				user, user.getPassword(), user.getAuthorities());

		Assert.state( ! user.getUsername().isBlank(), "Empty user name.");

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
