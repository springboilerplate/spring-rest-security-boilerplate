package com.springrestsecurityboilerplate;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.springrestsecurityboilerplate.user.User;
import com.springrestsecurityboilerplate.user.UserRepository;

@SpringBootApplication
public class SpringRestSecurityBoilerplateApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;
	
	

	public static void main(String[] args) {
		SpringApplication.run(SpringRestSecurityBoilerplateApplication.class, args);
	}

	

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub

		// User user = new User();
		// user.setUsername("Destan");
		// user.setPassword("123456");
		// userRepository.save(user);

	}
}
