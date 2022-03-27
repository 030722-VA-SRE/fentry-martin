package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.UserDTO;
import com.revature.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthService authServ;

	@Autowired
	public AuthController(AuthService authServ) {
		super();
		this.authServ = authServ;
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> login(@RequestParam("username")String username, @RequestParam("password")String password){
		
		// principal = null if login fails
		UserDTO principal = authServ.login(username, password);
		
		if(principal == null) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		String token = authServ.generateToken(principal);
		
		HttpHeaders hh = new HttpHeaders();
		
		// set tokenName, value
		hh.set("Authorization", token);
		
		return new ResponseEntity<>(principal, hh, HttpStatus.ACCEPTED);
	}
	
}
