package com.pavan.springsecurityjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.springsecurityjwt.model.CommonResponse;
import com.pavan.springsecurityjwt.model.RoleModel;
import com.pavan.springsecurityjwt.pojo.RoleEntity;
import com.pavan.springsecurityjwt.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping(path = "/roles", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<CommonResponse> createRole(@RequestBody RoleModel roleModel) {
		CommonResponse commonResponse = new CommonResponse(false, "Success", roleService.createRole(roleModel));
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);
	}

	@GetMapping(path = "/roles", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<CommonResponse> getAllRoles() {
		CommonResponse commonResponse = new CommonResponse(false, "Success", roleService.fetchAllRoles());
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);
	}

	@GetMapping(path = "/roles/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<CommonResponse> getRole(@PathVariable Long id) {
		CommonResponse commonResponse = new CommonResponse(false, "Success", roleService.findRoleById(id));
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);
	}

	@DeleteMapping(path = "/roles/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<CommonResponse> deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
		CommonResponse commonResponse = new CommonResponse(false, "Success", null);
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);
	}
}
