package com.autho.authservice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import com.autho.authservice.model.request.CodeDescRequest;
import com.autho.authservice.model.request.LoginRequest;
import com.autho.authservice.model.request.SignupRequest;
import com.autho.authservice.model.responses.LoginResponse;
import com.autho.authservice.model.responses.PermissionResp;
import com.autho.authservice.model.responses.RoleResp;
import com.autho.authservice.model.responses.UserPermissionResp;
import com.autho.authservice.model.responses.UserResp;
import com.autho.authservice.repo.PermissionDAO;
import com.autho.authservice.repo.RoleDAO;
import com.autho.authservice.repo.UserDAO;

@SpringBootTest
class AuthServiceApplicationTests {

	@Autowired
	UserDAO userDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	PermissionDAO permissionDAO;

	private String ADMIN_USER_NAME = "Admin";
	private String TEST_USER_NAME = "test";
	private String TEST_USER_EMAIL = "test@test.com";
	private String TEST_USER_PASS = "testtest";
	private String TEST_ROLE = "TESTROLE";
	private String SUCCESS_MSG = "SUCCESS";

	@Test
	public void testLogin() {
		LoginRequest loginReq = new LoginRequest("Admin", "admin");
		try {
			LoginResponse resp = userDAO.handleLogin(loginReq);
			assertNotNull(resp);
			assertNotNull(resp.getToken());
			assertNotEquals(resp.getToken(), "");
			assertEquals(resp.getEmail(), "admin@autho.com");
		} catch (BadCredentialsException e) {
			assertNull(e);
		} catch (Exception ex) {
			assertNull(ex);
		}

		loginReq = new LoginRequest("admin@autho.com", "admin");
		try {
			LoginResponse resp = userDAO.handleLogin(loginReq);
			assertNotNull(resp);
			assertNotNull(resp.getToken());
			assertNotEquals(resp.getToken(), "");
			assertEquals(resp.getUsername(), "Admin");
		} catch (BadCredentialsException e) {
			assertNull(e);
		} catch (Exception ex) {
			assertNull(ex);
		}
		// Wrong pass
		loginReq = new LoginRequest("Admin", "00000");
		try {
			LoginResponse resp = userDAO.handleLogin(loginReq);
			assertNotNull(resp);
		} catch (BadCredentialsException e) {
			assertNull(e);
		} catch (InternalAuthenticationServiceException ex) {
			assertNotNull(ex);
		} catch (Exception ex) {
			assertNull(ex);
		}

		// Wrong username
		loginReq = new LoginRequest("aaaaa", "admin");
		try {
			LoginResponse resp = userDAO.handleLogin(loginReq);
			assertNotNull(resp);
		} catch (BadCredentialsException e) {
			assertNotNull(e);
		} catch (InternalAuthenticationServiceException ex) {
			assertNull(ex);
		} catch (Exception ex) {
			assertNull(ex);
		}
	}

	@Test
	public void testUserRoles() {
		List<UserResp> userList = userDAO.getAllUsers();
		assertThat(userList, hasSize(1));
		try {
			LoginResponse lr = userDAO.handleUserSignup(signupRequestBuilder());
			assertNotNull(lr);
			assertNotNull(lr.getToken());
			assertNotNull(lr.getUsername());
			assertNotNull(lr.getRoles());
			assertThat(lr.getRoles(), hasSize(0));
			userList = userDAO.getAllUsers();
			assertThat(userList, hasSize(2));
		} catch (Exception ex) {
			assertNull(ex);
		}

		CodeDescRequest codeDescRequest = codeDescRequestBuilder();
		String msg = roleDAO.createRole(codeDescRequest);
		assertEquals(msg, "Role created successfully!");

		List<RoleResp> roles = roleDAO.getAllRoles();
		assertThat(roles, hasSize(2));

		List<CodeDescRequest> list = new ArrayList<CodeDescRequest>() {
			{
				add(codeDescRequest);
			}
		};

		msg = userDAO.assignRole(TEST_USER_NAME + "__", list);
		assertEquals(msg, "USER_NOT_EXISTS");

		msg = userDAO.assignRole(TEST_USER_NAME, list);
		assertEquals(msg, SUCCESS_MSG);

		roles = userDAO.getUserRoles(TEST_USER_NAME);
		assertThat(roles, hasSize(1));

		msg = roleDAO.assignPermissionsToRole(TEST_ROLE, preparePermissionList());
		assertEquals(msg, SUCCESS_MSG);

		List<PermissionResp> perms = roleDAO.getRolePermissions(TEST_ROLE);
		assertThat(perms, hasSize(2));
	}

	@Test
	public void testPermission() {
		List<PermissionResp> perms = permissionDAO.getAllPermissions();
		assertThat(perms, hasSize(8));

		permissionDAO.createPermission(new CodeDescRequest("NEW_PERM", "new perm"));

		perms = permissionDAO.getAllPermissions();
		assertThat(perms, hasSize(9));

		List<UserPermissionResp> userPerms = userDAO.varifyUserPermissions(ADMIN_USER_NAME, preparePermissionList());
		for (UserPermissionResp resp : userPerms) {
			if ("NEW_PERM".equals(resp.getPermissionCode())) {
				assertFalse(resp.getIsAllowed());
			} else if ("CREATE_USER".equals(resp.getPermissionCode())) {
				assertTrue(resp.getIsAllowed());
			}
		}
	}

	private SignupRequest signupRequestBuilder() {
		return new SignupRequest(TEST_USER_NAME, TEST_USER_PASS, TEST_USER_EMAIL);
	}

	private CodeDescRequest codeDescRequestBuilder() {
		return new CodeDescRequest(TEST_ROLE, "Test role");
	}

	private List<CodeDescRequest> preparePermissionList() {
		List<CodeDescRequest> permissionList = new ArrayList<CodeDescRequest>();
		permissionList.add(new CodeDescRequest("NEW_PERM", "new perm"));
		permissionList.add(new CodeDescRequest("CREATE_USER", "Create new User"));
		return permissionList;
	}

}
