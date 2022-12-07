package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Payment;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> makePayment(@RequestBody Payment payment) {
		return userService.makePayment(payment);
		
	}
}
