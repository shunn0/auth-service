package com.autho.authservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autho.authservice.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	User findByUserName(String userName);

	User findByEmail(String email);

	Optional<User> findByUserNameOrEmail(String userName, String email);
}