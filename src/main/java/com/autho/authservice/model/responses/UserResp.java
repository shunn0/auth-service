package com.autho.authservice.model.responses;

import java.io.Serializable;

import com.autho.authservice.model.User;

public class UserResp implements Serializable {

	private String userName;
	private String email;
	private Boolean isActive;

	public UserResp() {

	}

	public UserResp(String userName, String email, Boolean isActive) {
		this.userName = userName;
		this.email = email;
		this.isActive = isActive;
	}

	public UserResp(User user) {
		this.userName = user.getUserName();
		this.email = user.getEmail();
		this.isActive = user.getIsActive();
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
