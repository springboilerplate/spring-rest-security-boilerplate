package com.springrestsecurityboilerplate;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.springrestsecurityboilerplate.user.User;
import com.springrestsecurityboilerplate.user.UserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>, Serializable {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MessageSource messages;

	@Autowired
	private UserService service;

	@Autowired
	private Environment env;

	// @Autowired
	// Mailer mailer;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	private RabbitTemplate template;

	// @Autowired
	// private DirectExchange direct;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		service.createVerificationToken(user, token);

		RegistrationToken registrationToken = new RegistrationToken(event, user, token);
		// amqpTemplate.convertAndSend("email-exchange", "registration-token",
		// registrationToken);

		template.convertAndSend("email-direct", "registration", registrationToken);
		// amqpTemplate.convertAndSend("email-exchange", "registration-token",
		// registrationToken);
		// mailer.registrationTokenEmail(event,user,token);
		System.out.println("after the template");
	}

	//

}