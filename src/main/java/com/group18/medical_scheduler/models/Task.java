package com.group18.medical_scheduler.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.group18.medical_scheduler.interfaces.Identifiable;

@Entity
public class Task implements Identifiable {

	@Id
	@GeneratedValue
	private Integer id;
	
	@NotNull
	@ManyToOne(optional = false)
	private User user;
	
	@NotBlank
	@Column(nullable = false)
	private String description;
	
	private LocalDateTime dueDate;
	
	Task() {}
	
	public Task(
			final User user,
			final Integer id,
			final String description,
			final LocalDateTime dueDate) {
		this.user = user;
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
	}

	@Override
	public Integer getId() {
		return id;
	}
	public User getUser() {
		return user;
	}
	public String getDescription() {
		return description;
	}
	public LocalDateTime getDueDate() {
		return dueDate;
	}
}
