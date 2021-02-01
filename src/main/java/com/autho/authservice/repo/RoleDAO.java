package com.autho.authservice.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.autho.authservice.model.Permission;
import com.autho.authservice.model.Role;
import com.autho.authservice.model.request.CodeDescRequest;
import com.autho.authservice.model.responses.PermissionResp;
import com.autho.authservice.model.responses.RoleResp;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RoleDAO {

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	PermissionRepo permissionRepo;

	@Transactional
	public String createRole(CodeDescRequest request) {
		Role role = new Role();
		role.setRoleName(request.getCode());
		role.setDescription(request.getDescription());
		roleRepo.save(role);
		return "Role created successfully!";
	}

	public List<RoleResp> getAllRoles() {
		List<Role> roles = roleRepo.findAll();
		return roles.stream().map(e -> new RoleResp(e)).collect(Collectors.toList());
	}

	@Transactional
	public String assignPermissionsToRole(String roleName, List<CodeDescRequest> commonRequestList) {
		Optional<Role> roleOptional = roleRepo.findByRoleName(roleName);
		if (roleOptional.isPresent()) {
			Role role = roleOptional.get();
			for (CodeDescRequest request : commonRequestList) {
				Optional<Permission> permOptional = permissionRepo.findById(request.getCode());
				if (permOptional.isPresent()) {
					role.getRolePermissions().add(permOptional.get());
				} else {
					Permission newPerm = new Permission();
					newPerm.setCode(request.getCode().toUpperCase());
					newPerm.setDescription(request.getDescription());
					permissionRepo.save(newPerm);
					role.getRolePermissions().add(newPerm);
				}
			}
			roleRepo.save(role);
			return "SUCCESS";
		} else {
			return "Role dose not exists";
		}
	}

	public List<PermissionResp> getRolePermissions(String roleName) {
		Optional<Role> roleOptional = roleRepo.findById(roleName);
		if (roleOptional.isPresent()) {
			Set<Permission> permissionSet = roleOptional.get().getRolePermissions();
			return permissionSet.stream().map(e -> new PermissionResp(e)).collect(Collectors.toList());
		} else {
			return new ArrayList<PermissionResp>();
		}
	}

}
