package com.group18.medical_scheduler.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.group18.medical_scheduler.interfaces.Identifiable;

@Entity
@Table(name = "_user")
public class User implements UserDetails, Identifiable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@NotNull
	@OneToMany(mappedBy = "user", orphanRemoval = true)
	@Column(nullable = false)
	private Collection<Task> tasks;
	
	@Email
	@NotBlank
	@Column(nullable = false, unique = true)
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank
	@Size(min = 1)
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(nullable = false)
	@JsonProperty(value = "authority")
	private Authority authority;

	User() {}
	
	public User(
			final String email,
			final String password,
			final Authority authority) {
		this.email = email;
		this.password = password;
		this.authority = authority;
		this.tasks = new ArrayList<>();
	}

	@Override
	public Integer getId() {
		return this.id;
	}
	public String getEmail() {
		return this.email;
	}
	public Collection<Task> getTasks() {
		return tasks;
	}
	@JsonIgnore
	@Override
	public String getUsername() {
		return this.email;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Set.of(new SimpleGrantedAuthority(this.authority.name()));
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}
