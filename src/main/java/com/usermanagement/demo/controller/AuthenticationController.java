package com.usermanagement.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.demo.model.User;

import com.usermanagement.demo.service.UserServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("auth/v1")
public class AuthenticationController {

	private Map<String ,String> map = new HashMap<>();
	
	private UserServiceImpl userServiceImpl;

	@Autowired
	public AuthenticationController(UserServiceImpl userServiceImpl) {
		super();
		this.userServiceImpl = userServiceImpl;
	}
	
	@PostMapping(value="/addUser", consumes="application/json; charset=utf-8")
	public ResponseEntity<?> addUser(@RequestBody User user){
		 if(userServiceImpl.addUser(user)!=null) {
			 return new ResponseEntity<User>(user,HttpStatus.OK);
		 }
		return new ResponseEntity<String>("user not inserted", HttpStatus.CONFLICT);
	}
	
	
	public String generateToken(String username,String password) throws ServletException, Exception {
		String jwtToken="";
		if(username==null||password==null) {
			throw new ServletException("Please send valid username and password");
		}
		boolean flag = userServiceImpl.validateUser(username, password);
		if(!flag) {
			throw new ServletException("Invalid Credentials");
		}
		else {
			jwtToken=Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+30000)).signWith(SignatureAlgorithm.ES256,"secret key").compact();
		}
		return jwtToken;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> doLogin(@RequestBody User user){
		try {
			String jwtToken = generateToken(user.getUsername(), user.getPassword());
			map.put("message", "User successfully logged in!");
			map.put("token",jwtToken);
		}
		catch(Exception e) {
			map.put("message", e.getMessage());
			map.put("token", null);
			return new ResponseEntity<>(map,HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
