package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Payment;
import com.example.demo.model.User;

import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public boolean validEmail(String email) {
		boolean valid = EmailValidator.getInstance().isValid(email);
		if(valid == false) {
		
			return false;
		}
		return true;
	}
	
	
	public boolean validDate(String dateOfBirth) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-mm-dd");
		sdfrmt.setLenient(false);
		try {
			Date birthDate = sdfrmt.parse(dateOfBirth);
			}
		
		catch(ParseException e) {
			return false;
		}
		return true;
	}
	
	public boolean validAge(String dateOfBirth) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-mm-dd");
		sdfrmt.setLenient(false);
		try {
			Date birthDate = sdfrmt.parse(dateOfBirth);
			Date currentDate = sdfrmt.parse("2022-12-06");
			long difference_In_Time= currentDate.getTime() - birthDate.getTime();
			long difference_In_Years= (difference_In_Time/ (1000l * 60 * 60 * 24 * 365));
			if(difference_In_Years<18) {
				return false;
			}
			}
		
		catch(ParseException e) {
			return false;
		}
		return true;
	}
	
	public boolean validPassword(String pass) {
		if(pass.length()>=8) {
			int upperCounter=0;
			int numberCounter=0;
			
			StringBuilder sb = new StringBuilder();
			for(char c:pass.toCharArray()) {
				if(Character.isDigit(c)) {
					numberCounter++;
					}
				else if(Character.isUpperCase(c)) {
					upperCounter++;
					}
				}
			if(upperCounter<1 || numberCounter <1) {
				return false;
				
			}
			}
		else {
			return false;
		}
		return true;
		
	}
	
	
	
	public ResponseEntity<?> saveUser(User user) {
		//check email
		if(validEmail(user.getEmail())==false) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// Check username for alphanumerical
		else if (!user.getUsername().matches("^.*[a-zA-Z0-9].*$")) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		
		//checks if username exists
		else if(getByUsername(user.getUsername())!=null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		//checks if empty and if its less than 16 digits
		else if(user.getCreditCardNum()!=null && user.getCreditCardNum().length()!=16) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		//checks format of date
		else if(validDate(user.getDateOfBirth())==false) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		//checks if underage
		else if(validAge(user.getDateOfBirth())==false) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		
		// Check password for length ,upper and numbers
		// if >= 8 characters then we test for numbers and uppcase
		else if(validPassword(user.getPassword())==false) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		
		userRepository.save(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
	
	public ResponseEntity<?> makePayment(Payment payment) {
		if(payment.getCreditCardNum()!=null && payment.getCreditCardNum().length()!=16) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else if(payment.getAmmount()/1000 >0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else if(getUserByCreditCardNum(payment.getCreditCardNum())==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
	
	
	
	public List<User> getUsers(String filter){
		
		if(filter==null) {
			return userRepository.getAllUsers();
		}
		else if(filter.equals("yes")) {
			return userRepository.getdUsersWithCreditCard();
		}
		else if(filter.equals("no")) {
			return userRepository.getdUsersWithoutCreditCard();
		}
		else {
			return userRepository.getAllUsers();
		}

	}
	public User getByUsername(String username) {
		return userRepository.getByUsername(username);
	}
	
	public User getUserByCreditCardNum(String creditCardNum) {
		return userRepository.getByCreditCardNum(creditCardNum);
		
	}
}
