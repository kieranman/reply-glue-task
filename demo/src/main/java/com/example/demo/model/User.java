package com.example.demo.model;

public class User {
	private String username;
	private String password;
	private String email;
	private String dateOfBirth;
	private String creditCardNum; 
	
	public User() {
		
	}
	public User(String username,String password, String email, String dateOfBirth, String creditCardNum) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.creditCardNum = creditCardNum;
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
}
