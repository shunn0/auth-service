package com.autho.authservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autho.authservice.model.Role;;

public interface RoleRepo extends JpaRepository<Role, String> {
	Optional<Role> findByRoleName(String roleName);
}
