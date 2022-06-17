package com.group18.medical_scheduler.models;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group18.medical_scheduler.interfaces.Identifiable;

@Entity
public class Task implements Identifiable {

	@Id
	@GeneratedValue
	private Integer id;
	
	@NotBlank
	@Column(nullable = false)
	private String description;
	
	@NotNull
	@Column(nullable = false)
	private ZonedDateTime dueDate;
	
	@JsonIgnore
	@ManyToOne(optional = false)
	private User user;
	
	Task() {}
	
	public Task(
			final Integer id,
			final String description,
			final ZonedDateTime dueDate,
			final User user) {
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
		this.user = user;
	}

	@Override
	public Integer getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public ZonedDateTime getDueDate() {
		return dueDate;
	}
	public void setUser(final User user) {
		this.user = user;
	}
}