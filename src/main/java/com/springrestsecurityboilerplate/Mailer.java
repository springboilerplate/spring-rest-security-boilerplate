package com.springrestsecurityboilerplate;

import java.io.Serializable;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.springrestsecurityboilerplate.user.AppUser;

public class Mailer implements Serializable {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MessageSource messages;

	@Autowired
	private Environment env;

	@RabbitListener(queues = "#{resendTokenMailQueue.name}")
	public void onResendVerificationToken(ResendToken resendToken) {
		System.out.println("onResendVerificationToken calisti");
		if (resendToken != null)
			resendVerificationToken(resendToken.getUser(), resendToken.getOldToken());
	}

	public void resendVerificationToken(AppUser user, VerificationToken token) {

		final SimpleMailMessage email = constructResendVerificationTokenEmail(user, token);
		// mailSender.send(email);
		System.out.println(email);

	}

	private final SimpleMailMessage constructResendVerificationTokenEmail(final AppUser user,
			final VerificationToken newToken) {

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Resend Registration Token");
		email.setText("New token is " + newToken.getToken());
		email.setFrom(env.getProperty("support.email"));
		return email;

	}

	@RabbitListener(queues = "#{registrationTokenMailQueue.name}")
	public void onRegistrationToken(RegistrationToken registrationToken) throws InterruptedException {
		System.out.println("onRegistrationToken calisti");
		registrationTokenEmail(registrationToken.getEvent(), registrationToken.getUser(), registrationToken.getToken());
	}

	public void registrationTokenEmail(OnRegistrationCompleteEvent event, AppUser user, String token) {

		final SimpleMailMessage email = constructRegistrationEmailMessage(event, user, token);
		// mailSender.send(email);
		System.out.println(email);

	}

	private final SimpleMailMessage constructRegistrationEmailMessage(final OnRegistrationCompleteEvent event,
			final AppUser user, final String token) {

		final String recipientAddress = user.getEmail();
		final String subject = "Registration Confirmation";
		// final String confirmationUrl = event.getAppUrl() +
		// "/registrationConfirm.html?token=" + token;
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
