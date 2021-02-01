package com.autho.authservice.model.responses;

import java.io.Serializable;

import com.autho.authservice.model.Role;

public class RoleResp implements Serializable {

	private String roleName;
	private String description;

	public RoleResp() {
	}

	public RoleResp(String roleName, String description) {
		this.roleName = roleName;
		this.description = description;
	}

	public RoleResp(Role role) {
		this.roleName = role.getRoleName();
		this.description = role.getDescription();
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
