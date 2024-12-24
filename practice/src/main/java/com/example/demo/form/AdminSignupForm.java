package com.example.demo.form;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AdminSignupForm implements Serializable {

	@NotBlank
	private String lastName;

	@NotBlank
	private String firstName;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;
}