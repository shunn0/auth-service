package com.autho.authservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autho.authservice.model.AuserDetails;
import com.autho.authservice.model.User;
import com.autho.authservice.repo.UserRepo;

@Service
public class AuserDetailsService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// Optional<User> user = userRepo.findByUserName(userName);
		// user.orElseThrow(() -> new UsernameNotFoundException("Not found: " +
		// userName));
		// return user.map(UserFacts::new).get();

		User user = userRepo.findByUserName(userName);
		if (user == null) {
			user = userRepo.findByEmail(userName);
		}
		if (user == null) {
			return null;
		}
		return new AuserDetails(user);
	}
}
