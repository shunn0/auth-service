package com.autho.authservice.model.request;

import java.io.Serializable;

public class SignupRequest implements Serializable {

	private String username;
	private String password;
	private String email;

	public SignupRequest() {

	}

	public SignupRequest(String userName, String password, String email) {
		this.username = userName;
		this.password = password;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
