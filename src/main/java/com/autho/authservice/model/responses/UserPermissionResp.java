package com.autho.authservice.model.responses;

import java.io.Serializable;

public class UserPermissionResp implements Serializable {
	private String permissionCode;
	private Boolean isAllowed;

	public UserPermissionResp() {
	}

	public UserPermissionResp(String permissionCode, Boolean isAllowed) {
		this.permissionCode = permissionCode;
		this.isAllowed = isAllowed;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public Boolean getIsAllowed() {
		return isAllowed;
	}

	public void setIsAllowed(Boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

}
