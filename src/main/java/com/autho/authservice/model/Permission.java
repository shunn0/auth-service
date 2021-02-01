package com.autho.authservice.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission {

	@Id
	private String permissionCode;
	private String description;

	@ManyToMany(mappedBy = "rolePermissions")
	Set<Role> roles;

	public String getCode() {
		return permissionCode;
	}

	public void setCode(String code) {
		this.permissionCode = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
