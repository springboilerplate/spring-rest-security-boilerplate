package com.springrestsecurityboilerplate;

import java.util.UUID;

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
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MessageSource messages;

	@Autowired
	private UserService service;

	@Autowired
	private Environment env;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		service.createVerificationToken(user, token);

		final SimpleMailMessage email = constructEmailMessage(event, user, token);
		System.out.println(email);
		 mailSender.send(email);
	}

	//

	private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user,
			final String token) {

		final String recipientAddress = user.getEmail();
		final String subject = "Registration Confirmation";
//		final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
		// final String message = messages.getMessage("message.regSucc", null,
		// event.getLocale());
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText("Token is " + token); // just sending token
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

}