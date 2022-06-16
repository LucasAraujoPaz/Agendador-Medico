package com.group18.medical_scheduler.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record LoginDTO(
		@Email @NotBlank String email,
		@NotBlank String password) {}