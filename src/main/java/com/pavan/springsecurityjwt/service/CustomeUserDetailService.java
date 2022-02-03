package com.pavan.springsecurityjwt.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pavan.springsecurityjwt.model.RoleModel;
import com.pavan.springsecurityjwt.model.UserModel;
import com.pavan.springsecurityjwt.pojo.RoleEntity;
import com.pavan.springsecurityjwt.pojo.UserEntity;
import com.pavan.springsecurityjwt.repos.RoleRepository;
import com.pavan.springsecurityjwt.repos.UserRepository;

@Service
public class CustomeUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper mapper;

	@Transactional
	public UserModel regitser(UserModel userModel) {
		UserEntity userEntity = mapper.map(userModel, UserEntity.class);

		Set<RoleEntity> entities = new HashSet<>();
		userModel.getRoles().forEach(role -> {
			long id = role.getId();
			RoleEntity entity = roleRepository.findById(id).get();
			entities.add(entity);
		});
		userEntity.setRoles(entities);

		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword())); // encoding password

		userEntity = userRepository.save(userEntity);
		userModel = mapper.map(userEntity, UserModel.class);

		Set<RoleModel> roles = new HashSet<>();

		userEntity.getRoles().forEach(role -> {
			RoleModel rm = new RoleModel(role.getId(), role.getRoleName());
			roles.add(rm);
		});
		userModel.setRoles(roles);
		return userModel;
	}

	/*
	 * this method is used to validate the user existence
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByUsername(username);

		if (userEntity == null) {
			throw new UsernameNotFoundException("user does not exist");
		}

		UserModel userModel = new UserModel();

		BeanUtils.copyProperties(userEntity, userModel);
		Set<RoleModel> roles = new HashSet<>();

		userEntity.getRoles().forEach(role -> {
			RoleModel rm = new RoleModel(role.getId(), role.getRoleName());
			roles.add(rm);
		});
		userModel.setRoles(roles);

		return userModel;

		/*
		 * if (username.equals("Pavan")) { return new User("Pavan", "qwerty", new
		 * ArrayList<>()); } else { throw new
		 * UsernameNotFoundException("Username does not exist"); }
		 */
	}

}
