package com.pavan.springsecurityjwt.controlleradvice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pavan.springsecurityjwt.model.CommonResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@RestControllerAdvice
public class SpringSecurityControllerAdvice {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<CommonResponse> handleBadCredentialExp(BadCredentialsException exception) {
		CommonResponse response = new CommonResponse(true, "Please Check Credentials", exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<String> handleUsernameNotFoundException() {
		return new ResponseEntity<>("User not found", HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<String> handleSignatureException() {
		return new ResponseEntity<>("Invalid Token", HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<CommonResponse> handleNoSuchElementException(NoSuchElementException exception) {
		CommonResponse commonResponse = new CommonResponse(true, "Failure", exception.getMessage());
		return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<CommonResponse> handleExpiredJwtException(ExpiredJwtException exception) {
		CommonResponse commonResponse = new CommonResponse(true, "Failure", "Please Login Again");
		return new ResponseEntity<>(commonResponse, HttpStatus.REQUEST_TIMEOUT);
	}
}
