package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Payment;

@Repository
public class PaymentRepository {

	public Payment makePayment(Payment p) {
		Payment payment = new Payment();
		payment.setCreditCardNum(p.getCreditCardNum());
		payment.setAmmount(p.getAmmount());
		return payment;
	}
}
