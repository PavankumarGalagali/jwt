package com.pavan.springsecurityjwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pavan.springsecurityjwt.service.CustomeUserDetailService;
import com.pavan.springsecurityjwt.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/*
 * all the requests will go through this filter
 * 
 * */
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private CustomeUserDetailService userDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	/*
	 * validating the jwt token
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// get the jwt token from header(authorization)
		String bearerToken = request.getHeader("Authorization");

		log.info("Bearer Token " + bearerToken);

		String token = null;
		String username = null;

		// check if token exist or it has bearer text
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			// extract token from bearertoken
			token = bearerToken.substring(7);
			log.info("token = " + token);

			// extract username from token
			username = jwtUtil.extractUsername(token);
			log.info("username " + username);

			// get userdetails for this user
			UserDetails userDetails = userDetailService.loadUserByUsername(username);
			log.info("userDetails + " + userDetails);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				if (jwtUtil.validateToken(token, userDetails)) {
					log.info("inside validator");

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				} 
			}

		} else {
			log.info("Invalid Token");
		}

		filterChain.doFilter(request, response);
	}

}
