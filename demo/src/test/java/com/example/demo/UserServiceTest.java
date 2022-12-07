package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.model.Payment;
import com.example.demo.model.User;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PaymentRepository paymentRepository;
	
	@Autowired
	private UserService userService;
	
	private User user;
	
	private Payment payment;
	

	@Test
	public void getUsersWithCreditCardFilterEqualsNull() {
		
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		User user2 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum(null);
		
		when(userRepository.getAllUsers()).thenReturn(Stream
				.of(user1,user2)
				.collect(Collectors.toList()));
		assertEquals(2,userService.getUsers(null).size());
	}
	
	@Test
	public void getUsersWithCreditCardFilterEqualsYes() {
		
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");

		
		when(userService.getUsers("yes")).thenReturn(Stream
				.of(user1)
				.collect(Collectors.toList()));
		assertEquals(1,userService.getUsers("yes").size());
	}
	
	@Test
	public void getUsersWithCreditCardFilterEqualsNo() {
		
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum(null);

		when(userService.getUsers("no")).thenReturn(Stream
				.of(user1)
				.collect(Collectors.toList()));
		assertEquals(1,userService.getUsers("no").size());
	}
	
	@Test
	public void getByUsername() {
		
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum(null);
		
		when(userRepository.getByUsername("user1")).thenReturn(user1);
		assertEquals(user1,userService.getByUsername("user1"));
	}
	
	@Test
	public void getByCreditCardNum() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		when(userRepository.getByCreditCardNum(user1.getCreditCardNum())).thenReturn(user1);
		assertEquals(user1,userService.getUserByCreditCardNum("1234567891234567"));
	}
	
	@Test
	public void saveUserWithNoErrors() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password1");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.CREATED);
		
		when(userRepository.save(user1))
		.thenReturn(user1);
		assertEquals(response,userService.saveUser(user1));
	}
	
	@Test
	public void saveUserWithPasswordErrorNoUppercase() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("password1");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		when(userRepository.save(user1))
		.thenReturn(user1);
		assertEquals(response,userService.saveUser(user1));
	}
	
	@Test
	public void saveUserWithPasswordErrorNoNumber() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		when(userRepository.save(user1))
		.thenReturn(user1);
		assertEquals(response,userService.saveUser(user1));
	}
	
	@Test
	public void saveUserWithPasswordSizeLessThan8() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Pas1");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		when(userRepository.save(user1))
		.thenReturn(user1);
		assertEquals(response,userService.saveUser(user1));
	}
	
	@Test
	public void creditCardNumLengthNot16() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password1");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("123");
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		when(userRepository.save(user1))
		.thenReturn(user1);
		assertEquals(response,userService.saveUser(user1));
	}
	
	@Test
	public void invalidEmail() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password1");
		user1.setEmail("user1@.commmmm");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		when(userRepository.save(user1))
		.thenReturn(user1);
		assertEquals(response,userService.saveUser(user1));
	}
	
	
	@Test
	public void invalidDateFormat() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password1");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000/01-01");
		user1.setCreditCardNum("1234567891234567");
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		when(userRepository.save(user1))
		.thenReturn(user1);
		assertEquals(response,userService.saveUser(user1));
	}
	
	@Test
	public void underAge() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password1");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2010-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.FORBIDDEN);
		
		when(userRepository.save(user1))
		.thenReturn(user1);
		assertEquals(response,userService.saveUser(user1));
	}
	
	
	@Test
	public void usernameAlreadyExists() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password1");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.CONFLICT);
		
		when(userRepository.getByUsername(user1.getUsername()))
		.thenReturn(user1);

		assertEquals(response,userService.saveUser(user1));
	}
	
	@Test
	public void successfulPayment() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password1");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		Payment payment = new Payment();
		payment.setCreditCardNum("1234567891234567");
		payment.setAmmount(100);
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.CREATED);
		
		when(userRepository.getByCreditCardNum(payment.getCreditCardNum()))
		.thenReturn(user1);

		assertEquals(response,userService.makePayment(payment));
	}
	
	@Test
	public void makePaymentWithoutExistingCard() {
		Payment payment = new Payment();
		payment.setCreditCardNum("1234567891234567");
		payment.setAmmount(100);
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.NOT_FOUND);
		
		when(userRepository.getByCreditCardNum(payment.getCreditCardNum()))
		.thenReturn(null);

		assertEquals(response,userService.makePayment(payment));
	}
	@Test
	public void makePaymentMoreThan3Digits() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("Password1");
		user1.setEmail("user1@gmail.com");
		user1.setDateOfBirth("2000-01-01");
		user1.setCreditCardNum("1234567891234567");
		
		Payment payment = new Payment();
		payment.setCreditCardNum("1234567891234567");
		payment.setAmmount(1000);
		
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		when(userRepository.getByCreditCardNum(payment.getCreditCardNum()))
		.thenReturn(user1);

		assertEquals(response,userService.makePayment(payment));
	}
}
