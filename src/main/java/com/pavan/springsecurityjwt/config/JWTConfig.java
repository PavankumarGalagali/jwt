package com.pavan.springsecurityjwt.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pavan.springsecurityjwt.filter.JwtRequestFilter;
import com.pavan.springsecurityjwt.service.CustomeUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // this helps to add method level authorization security
public class JWTConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomeUserDetailService userDetailService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private JwtAuthEntryPoint jwtAuthEntryPoint;

	/*
	 * this method is used to say how we want to manage the authentication process
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	/*
	 *	with  this method we will controll which endpoints are permitted and which are not permitted
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		http
			.csrf()
			.disable()
			.cors()
			.disable()
			.authorizeRequests().antMatchers("/api/login" , "/api/register").permitAll()
			.anyRequest().authenticated() // all other requests need to be authenticated , authentication is performed by configure(AuthenticationManagerBuilder auth)
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // every request should be independent of each other , server does not have to keep track of session 
	 
		http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticateManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
}
