package com.springrestsecurityboilerplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.springrestsecurityboilerplate.user.User;

@Service
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MessageSource messages;

	@Autowired
	private Environment env;

	public void resendVerificationToken(User user, VerificationToken token) {

		final SimpleMailMessage email = constructResendVerificationTokenEmail(user, token);
		mailSender.send(email);
		System.out.println(email);

	}

	private final SimpleMailMessage constructResendVerificationTokenEmail(final User user,
			final VerificationToken newToken) {

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Resend Registration Token");
		email.setText("New token is " + newToken.getToken());
		email.setFrom(env.getProperty("support.email"));
		return email;

	}

}
