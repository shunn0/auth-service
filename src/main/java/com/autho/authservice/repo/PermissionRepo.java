package com.autho.authservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autho.authservice.model.Permission;
import com.autho.authservice.model.User;

public interface PermissionRepo extends JpaRepository<Permission, String> {
	User findByPermissionCode(String permissionCode);
}