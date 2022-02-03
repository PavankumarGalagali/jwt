package com.pavan.springsecurityjwt.service;

import java.util.List;

import com.pavan.springsecurityjwt.model.RoleModel;

public interface RoleService {
	public RoleModel createRole(RoleModel roleModel);

	public List<RoleModel> fetchAllRoles();

	public RoleModel findRoleById(Long roleId);
	
	public void deleteRole(Long roleId);
}
