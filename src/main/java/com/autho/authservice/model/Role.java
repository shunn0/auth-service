package com.autho.authservice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	private String roleName;
	private String description;

	@ManyToMany(mappedBy = "userRoles")
	Set<User> users;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "roleName"), inverseJoinColumns = @JoinColumn(name = "permissionCode"))
	private Set<Permission> rolePermissions;

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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Permission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(Set<Permission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

}
