package com.pavan.springsecurityjwt.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.springsecurityjwt.model.JwtRequest;
import com.pavan.springsecurityjwt.model.JwtResponse;
import com.pavan.springsecurityjwt.model.UserModel;
import com.pavan.springsecurityjwt.pojo.UserEntity;
import com.pavan.springsecurityjwt.service.CustomeUserDetailService;
import com.pavan.springsecurityjwt.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class JwtController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomeUserDetailService customeUserDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping(path = "/register")
	public ResponseEntity<UserModel> registerUser(@RequestBody UserModel userModel) {
		return new ResponseEntity<>(customeUserDetailService.regitser(userModel), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));

		UserDetails details = customeUserDetailService.loadUserByUsername(jwtRequest.getUserName());

		String jwtToken = jwtUtil.generateToken(details);

		JwtResponse jwtResponse = new JwtResponse(jwtToken);

		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
	}

	@GetMapping("/current-user")
	public UserDetails getCurrentUser(Principal principal) {
		UserDetails details = this.customeUserDetailService.loadUserByUsername(principal.getName());
		return  details;
	}

}
