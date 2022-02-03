package com.pavan.springsecurityjwt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pavan.springsecurityjwt.model.RoleModel;
import com.pavan.springsecurityjwt.pojo.RoleEntity;
import com.pavan.springsecurityjwt.repos.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleModel createRole(RoleModel roleModel) {
		RoleEntity roleEntity = new RoleEntity();
		BeanUtils.copyProperties(roleModel, roleEntity);
		RoleEntity roleEntity2 = roleRepository.save(roleEntity);

		BeanUtils.copyProperties(roleEntity2, roleModel);

		return roleModel;
	}

	@Override
	public List<RoleModel> fetchAllRoles() {

		List<RoleModel> roleModels = new ArrayList<>();
		List<RoleEntity> roleEntities = roleRepository.findAll();
		RoleModel roleModel = null;
		for (RoleEntity re : roleEntities) {
			roleModel = new RoleModel();
			BeanUtils.copyProperties(re, roleModel);
			roleModels.add(roleModel);
		}

		return roleModels;
	}

	@Override
	public RoleModel findRoleById(Long roleId) {
		RoleEntity roleEntity = roleRepository.findById(roleId).get();
		RoleModel roleModel = new RoleModel();
		BeanUtils.copyProperties(roleEntity, roleModel);
		return roleModel;
	}

	@Override
	public void deleteRole(Long roleId) {
		roleRepository.deleteById(roleId);
	}
}
