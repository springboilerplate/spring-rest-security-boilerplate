package com.springrestsecurityboilerplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springrestsecurityboilerplate.user.User;
import com.springrestsecurityboilerplate.user.UserRepository;

@SpringBootApplication
public class SpringRestSecurityBoilerplateApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestSecurityBoilerplateApplication.class, args);
	}
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
		User user = new User();
		user.setUsername("Destan");
		user.setPassword("123456");
		userRepository.save(user);
		
		
		
	}
}
