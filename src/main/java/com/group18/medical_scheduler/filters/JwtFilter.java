package com.group18.medical_scheduler.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.group18.medical_scheduler.services.TokenService;

public class JwtFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	public JwtFilter(final TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
	@Override
	protected void doFilterInternal(
			final HttpServletRequest request, 
			final HttpServletResponse response, 
			final FilterChain filterChain) throws ServletException, IOException {

		final String header = request.getHeader("Authorization");
		
		if (header == null || ! header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String token = header.substring(7);
		tokenService.authenticate(token);

		filterChain.doFilter(request, response);
	}
}
