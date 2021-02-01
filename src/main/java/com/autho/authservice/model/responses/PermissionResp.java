package com.autho.authservice.model.responses;

import java.io.Serializable;

import com.autho.authservice.model.Permission;

public class PermissionResp implements Serializable {
	private String permissionCode;
	private String description;

	public PermissionResp() {
	}

	public PermissionResp(String permissionCode, String description) {
		this.permissionCode = permissionCode;
		this.description = description;
	}

	public PermissionResp(Permission permission) {
		this.permissionCode = permission.getCode();
		this.description = permission.getDescription();
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public String getDescription() {
		return description;
	}

}
