package com.example.demo.model;

public class Payment {
	private String creditCardNum;
	private int ammount;
	
	public Payment() {
		
	}
	public Payment(String creditCardNum,int ammount) {
		this.creditCardNum = creditCardNum;
		this.ammount = ammount;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public int getAmmount() {
		return ammount;
	}
	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}
	
	
	
}
