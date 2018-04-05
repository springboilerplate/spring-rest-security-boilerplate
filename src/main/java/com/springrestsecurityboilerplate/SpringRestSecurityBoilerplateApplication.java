package com.springrestsecurityboilerplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springrestsecurityboilerplate.user.AppUser;
import com.springrestsecurityboilerplate.user.UserRepository;

@SpringBootApplication
public class SpringRestSecurityBoilerplateApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	// @Autowired
	// Rolev2Repository roleRepository;

	// True for sending mail with a real e-mail address which should be configured
	// in the application properties , false for not sending
	public static final boolean SEND_MAIL = false;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringRestSecurityBoilerplateApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub

	}
}
