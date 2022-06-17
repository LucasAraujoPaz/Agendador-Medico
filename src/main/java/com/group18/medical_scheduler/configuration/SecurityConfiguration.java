package com.group18.medical_scheduler.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.group18.medical_scheduler.filters.JwtFilter;
import com.group18.medical_scheduler.services.TokenService;
import com.group18.medical_scheduler.services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
	prePostEnabled = true
)
@SuppressWarnings("deprecation")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userService::findByEmail)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
			//.antMatchers("/**").permitAll() // ALLOW FREE ACCESS
			.antMatchers("/*", "/error", "/h2-console/**",
					"/templates/**", "/scripts/**", "/styles/**").permitAll()
			.antMatchers(HttpMethod.POST, "/login", "/api/users").permitAll()
			.antMatchers("/api/**").authenticated()
			.anyRequest().authenticated().and()
    	.cors().and()
    	.headers().frameOptions().disable().and()
    	.csrf().disable()
    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    	.addFilterBefore(new JwtFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
