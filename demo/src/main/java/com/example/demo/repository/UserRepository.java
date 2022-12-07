package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.User;





@Repository
public class UserRepository {
	public ArrayList<User> userList = new ArrayList<User>();
		
	public List<User> getAllUsers(){
		return userList;
	}
	
	
	public List<User> getdUsersWithCreditCard(){
		ArrayList<User> filterList = new ArrayList<User>();
		for(int i=0;i<userList.size();i++) {
			if(userList.get(i).getCreditCardNum()!=null) {
				filterList.add(userList.get(i));
			}
		}
		return filterList;
	}
	
	public List<User> getdUsersWithoutCreditCard(){
		ArrayList<User> filterList = new ArrayList<User>();
		for(int i=0;i<userList.size();i++) {
			if(userList.get(i).getCreditCardNum()==null) {
				filterList.add(userList.get(i));
			}
		}
		return filterList;
	}
	
	
	
	
	
	public User getByUsername(String username) {
		for(int i=0;i<userList.size();i++) {
			if(userList.get(i).getUsername().equals(username)) {
				return userList.get(i);
			}
		}
		return null;
	}
	
	public User getByCreditCardNum(String creditCardNum) {
		for(int i=0;i<userList.size();i++) {
			if(userList.get(i).getCreditCardNum().equals(creditCardNum)) {
				return userList.get(i);
			}
		}
		return null;
	}
	
	
	public User save(User u) {
		User user = new User();
		user.setUsername(u.getUsername());
		user.setPassword(u.getPassword());
		user.setEmail(u.getEmail());
		user.setDateOfBirth(u.getDateOfBirth());
		user.setCreditCardNum(u.getCreditCardNum());
		
		userList.add(user);
		
		return user;
	}
}
