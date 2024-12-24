package com.example.demo.form;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AdminSigninForm implements Serializable {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;
}