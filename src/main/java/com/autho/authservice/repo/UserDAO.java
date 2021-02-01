package com.autho.authservice.repo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.BadAttributeValueExpException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.autho.authservice.model.AuserDetails;
import com.autho.authservice.model.Role;
import com.autho.authservice.model.User;
import com.autho.authservice.model.request.CodeDescRequest;
import com.autho.authservice.model.request.LoginRequest;
import com.autho.authservice.model.request.SignupRequest;
import com.autho.authservice.model.responses.LoginResponse;
import com.autho.authservice.model.responses.RoleResp;
import com.autho.authservice.model.responses.UserPermissionResp;
import com.autho.authservice.model.responses.UserResp;
import com.autho.authservice.services.AuserDetailsService;
import com.autho.authservice.utils.Common;
import com.autho.authservice.utils.PasswordEncoderGenerator;
import com.autho.authservice.utils.TokenManager;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserDAO {

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AuserDetailsService userDetailService;

	@Autowired
	TokenManager tokenManager;

	public LoginResponse handleLogin(LoginRequest loginRequest) throws Exception {
		try {
			Optional<User> userOptional = userRepo.findByUserNameOrEmail(loginRequest.getUsername(),
					loginRequest.getUsername());
			if (userOptional.isPresent() && userOptional.get().getIsActive()) {
				final UserDetails userDetails = new AuserDetails(userOptional.get());
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(userDetails.getUsername(), loginRequest.getPassword()));
				String jwt = tokenManager.generateToken(userDetails);
				return buildLoginResponse(jwt, userOptional.get());
			} else {
				throw new BadCredentialsException("Incorrect username, email or password");
			}
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username, email or password", e);
		}
	}

	private LoginResponse buildLoginResponse(String token, User user) {
		List<String> roles = user.getUserRoles().stream().map(e -> e.getRoleName()).collect(Collectors.toList());
		LoginResponse loginResponse = new LoginResponse(token, user.getUserName(), user.getEmail(), roles);
		return loginResponse;
	}

	@Transactional
	public LoginResponse handleUserSignup(SignupRequest signupRequest) throws Exception {
		Optional<User> userOptional = userRepo.findByUserNameOrEmail(signupRequest.getUsername(),
				signupRequest.getEmail());
		if (userOptional.isPresent()) {
			throw new BadAttributeValueExpException("User already exists");
		}
		User user = createNewUser(signupRequest.getUsername(), signupRequest.getPassword(), signupRequest.getEmail(),
				signupRequest.getUsername());
		UserDetails userDetails = new AuserDetails(user);
		user.setUserRoles(new HashSet<Role>());
		String jwt = tokenManager.generateToken(userDetails);
		return buildLoginResponse(jwt, user);
	}

	@Transactional
	public String assignRole(String userName, List<CodeDescRequest> commonRequestList) {
		User user = userRepo.findByUserName(userName);
		if (user == null) {
			return "USER_NOT_EXISTS";
		}
		for (CodeDescRequest request : commonRequestList) {
			String roleName = request.getCode();
			Optional<Role> role = roleRepo.findById(roleName);
			if (role.isPresent()) {
				user.getUserRoles().add(role.get());
			} else {
				Role newRole = new Role();
				newRole.setRoleName(roleName.toUpperCase());
				newRole.setDescription(request.getDescription());
				roleRepo.save(newRole);
				user.getUserRoles().add(newRole);
			}
		}
		userRepo.save(user);
		return "SUCCESS";
	}

	@Transactional
	private User createNewUser(String userName, String password, String email, String createdBy) {
		User user = new User();
		user.setUserName(userName);
		user.setSecret(PasswordEncoderGenerator.hashedSecret(password));
		user.setEmail(email);
		user.setIsActive(Boolean.TRUE);
		user.setCreatedAt(Common.getCurrentDateTime());
		user.setCreatedBy(createdBy);
		user = userRepo.save(user);
		return user;
	}

	public List<UserResp> getAllUsers() {
		List<User> userList = userRepo.findAll();
		return userList.stream().map(e -> new UserResp(e)).collect(Collectors.toList());
	}

	public List<RoleResp> getUserRoles(String userName) {
		User user = userRepo.findByUserName(userName);
		Set<Role> userRoles = user.getUserRoles();
		return userRoles.stream().map(e -> new RoleResp(e)).collect(Collectors.toList());
	}

	public List<UserPermissionResp> varifyUserPermissions(String userName, List<CodeDescRequest> permissionCommonList) {
		User user = userRepo.findByUserName(userName);
		if (user == null) {
			return new ArrayList<UserPermissionResp>();
		}
		Set<Role> roleSet = user.getUserRoles();

		Set<String> permissionSet = roleSet.stream().map(r -> r.getRolePermissions()).flatMap(rp -> rp.stream())
				.map(e -> e.getCode()).collect(Collectors.toSet());

		List<UserPermissionResp> userPermissionRespList = permissionCommonList.stream()
				.map(e -> new UserPermissionResp(e.getCode(), permissionSet.contains(e.getCode())))
				.collect(Collectors.toList());
		return userPermissionRespList;
	}

}
