package com.autho.authservice.repo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.autho.authservice.model.Permission;
import com.autho.authservice.model.request.CodeDescRequest;
import com.autho.authservice.model.responses.PermissionResp;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PermissionDAO {

	@Autowired
	PermissionRepo permissionRepo;

	@Transactional
	public String createPermission(CodeDescRequest commonRequest) {
		Permission permission = new Permission();
		permission.setCode(commonRequest.getCode().toUpperCase());
		permission.setDescription(commonRequest.getDescription());

		permissionRepo.save(permission);

		return "Permission created successfully!";
	}

	public List<PermissionResp> getAllPermissions() {
		List<Permission> permissions = permissionRepo.findAll();
		return permissions.stream().map(e -> new PermissionResp(e)).collect(Collectors.toList());
	}

}
