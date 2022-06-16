package com.group18.medical_scheduler.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

		final String header = Optional.ofNullable(request.getHeader("Authorization")).orElse("");

		final Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[] {});
	
		final String cookie = Arrays.stream(cookies)
				.filter(aCookie -> aCookie.getName().equals("Authorization"))
				.findAny()
				.map(Cookie::getValue)
				.orElse("");

		boolean headerIsOk = header.startsWith("Bearer ");
		boolean cookieIsOk = cookie.startsWith("Bearer_");
		
		if ( ! (headerIsOk || cookieIsOk)) {
			filterChain.doFilter(request, response);
			return;
		}

		final String token = (headerIsOk ? header : cookie).substring(7);
		tokenService.authenticate(token);

		filterChain.doFilter(request, response);
	}
}
