package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> addUser(@RequestBody User user) {
		return userService.saveUser(user);
		
	}
	@GetMapping
	public List<User> findAllUsers(@RequestParam(name = "CreditCard",required = false) String filter){
		return userService.getUsers(filter);
	}
	@GetMapping("{username}")
	public User findUserByUsername(@PathVariable String username) {
		return userService.getByUsername(username);
	}
}
