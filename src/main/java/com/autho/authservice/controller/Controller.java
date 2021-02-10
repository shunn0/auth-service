package com.autho.authservice.controller;

import java.util.List;

import javax.management.BadAttributeValueExpException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autho.authservice.model.request.CodeDescRequest;
import com.autho.authservice.model.request.ListRequest;
import com.autho.authservice.model.request.LoginRequest;
import com.autho.authservice.model.request.SignupRequest;
import com.autho.authservice.model.responses.ListResponse;
import com.autho.authservice.model.responses.LoginResponse;
import com.autho.authservice.model.responses.PermissionResp;
import com.autho.authservice.model.responses.Response;
import com.autho.authservice.model.responses.RoleResp;
import com.autho.authservice.model.responses.UserPermissionResp;
import com.autho.authservice.model.responses.UserResp;
import com.autho.authservice.repo.PermissionDAO;
import com.autho.authservice.repo.RoleDAO;
import com.autho.authservice.repo.UserDAO;
import com.autho.authservice.utils.TokenManager;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

	@Autowired
	UserDAO userDAO;

	@Autowired
	PermissionDAO permissionDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	TokenManager tokenManager;


	@GetMapping("/api/all")
	public String home() {
		return "Welcome";
	}


	@PostMapping("/api/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
		try {
			LoginResponse loginResponse = userDAO.handleLogin(loginRequest);
			return ResponseEntity.ok(loginResponse);
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/api/signup")
	public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) throws Exception {
		try {
			LoginResponse loginResponse = userDAO.handleUserSignup(signupRequest);
			return ResponseEntity.ok(loginResponse);
		} catch (BadAttributeValueExpException be) {
			return new ResponseEntity<>(be.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/users")
	@Secured("ROLE_VIEW_USERS")
	public ResponseEntity<?> getAllUser() throws Exception {
		List<UserResp> userList = userDAO.getAllUsers();
		return ResponseEntity.ok(new ListResponse(userList));
	}

	@PostMapping("/api/permissions")
	@PreAuthorize("hasRole('ROLE_CREATE_PERMISSION')")
	public ResponseEntity<?> createPermission(@RequestBody CodeDescRequest commonRequest) throws Exception {
		String responseMsg = permissionDAO.createPermission(commonRequest);
		return ResponseEntity.ok(new Response(responseMsg));
	}

	@GetMapping("/api/permissions")
	@Secured("ROLE_VIEW_PERMISSIONS")
	public ResponseEntity<?> getAllPermissions() throws Exception {
		List<PermissionResp> permissionList = permissionDAO.getAllPermissions();
		return ResponseEntity.ok(new ListResponse(permissionList));
	}

	@PostMapping("/api/roles")
	@PreAuthorize("hasRole('ROLE_CREATE_ROLE')")
	public ResponseEntity<?> createRole(@RequestBody CodeDescRequest commonRequest) throws Exception {
		String responseMsg = roleDAO.createRole(commonRequest);
		return ResponseEntity.ok(new Response(responseMsg));
	}

	@GetMapping("/api/roles")
	@Secured("ROLE_VIEW_ROLES")
	public ResponseEntity<?> getAllRoles() throws Exception {
		List<RoleResp> roleList = roleDAO.getAllRoles();
		return ResponseEntity.ok(new ListResponse(roleList));
	}

	@PostMapping("/api/users/{userName}/roles")
	@PreAuthorize("hasRole('ROLE_UPDATE_USER')")
	public ResponseEntity<?> assignRoleToUser(@PathVariable String userName, @RequestBody ListRequest listContainer)
			throws Exception {
		List<CodeDescRequest> commonRequestList = listContainer.getCollection();
		String responseMsg = userDAO.assignRole(userName, commonRequestList);
		if ("SUCCESS".equals(responseMsg)) {
			return ResponseEntity.ok(new Response("Roles Successfully assigned to user " + userName));
		} else {
			return ResponseEntity.ok(new Response("User does not exists"));
		}
	}

	@GetMapping("/api/users/{userName}/roles")
	@Secured("ROLE_VIEW_USERS")
	public ResponseEntity<?> getUserRoles(@PathVariable String userName) throws Exception {
		List<RoleResp> roleList = userDAO.getUserRoles(userName);
		return ResponseEntity.ok(new ListResponse(roleList));
	}

	@PostMapping("/api/roles/{roleName}/permissions")
	@PreAuthorize("hasRole('ROLE_UPDATE_ROLE')")
	public ResponseEntity<?> assignPermissionsToRole(@PathVariable String roleName,
			@RequestBody ListRequest listContainer) throws Exception {
		List<CodeDescRequest> commonRequestList = listContainer.getCollection();
		String responseMsg = roleDAO.assignPermissionsToRole(roleName, commonRequestList);
		if ("SUCCESS".equals(responseMsg)) {
			return ResponseEntity.ok(new Response("Permissions Successfully assigned to Role " + roleName));
		} else {
			return ResponseEntity.ok(new Response("Role does not exists"));
		}
	}

	@GetMapping("/api/roles/{roleName}/permissions")
	@Secured("ROLE_VIEW_ROLES")
	public ResponseEntity<?> getRolePermissions(@PathVariable String roleName) throws Exception {
		List<PermissionResp> roleList = roleDAO.getRolePermissions(roleName);
		return ResponseEntity.ok(new ListResponse(roleList));
	}

	@PostMapping("/api/users/{userName}/permissions")
	@Secured({ "ROLE_VIEW_USERS", "ROLE_VIEW_PERMISSIONS" })
	public ResponseEntity<?> varifyUserPermission(@PathVariable String userName, @RequestBody ListRequest listContainer)
			throws Exception {
		List<CodeDescRequest> commonRequestList = listContainer.getCollection();
		List<UserPermissionResp> permissionList = userDAO.varifyUserPermissions(userName, commonRequestList);
		return ResponseEntity.ok(new ListResponse(permissionList));
	}
}
