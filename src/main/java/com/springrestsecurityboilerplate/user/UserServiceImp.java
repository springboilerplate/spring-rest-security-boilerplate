package com.springrestsecurityboilerplate.user;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.springrestsecurityboilerplate.mail.Mailer;
import com.springrestsecurityboilerplate.registration.OnRegistrationCompleteEvent;
import com.springrestsecurityboilerplate.registration.ResendToken;
import com.springrestsecurityboilerplate.registration.VerificationToken;
import com.springrestsecurityboilerplate.registration.VerificationTokenRepository;
import com.springrestsecurityboilerplate.validation.EmailExistsException;
import com.springrestsecurityboilerplate.validation.UsernameExistsException;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	VerificationTokenRepository tokenRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// @Autowired
	// Mailer mailer;

	@Autowired
	private RabbitTemplate template;

	@Override
	public void registerUser(AppUser user, WebRequest request) throws EmailExistsException, UsernameExistsException {

		if (doesEmailExist(user.getEmail())) {
			// System.out.println("Existed email");
			throw new EmailExistsException(user.getEmail());
		} else if (doesUsernameExist(user.getUsername())) {
			throw new UsernameExistsException(user.getUsername());
		}

		else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setCreationDate(new Date());
			user.setIsActive(false);
			user.setActivationDate(null);

			userRepository.save(user);
			// eventPublisher.publishEvent(new
			// OnRegistrationCompleteEvent(user));
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
			System.out.println("Registered!");
		}
	}

	public boolean doesEmailExist(String email) {
		AppUser user = userRepository.findByEmail(email);

		boolean doesUserExistByEmail = user != null;
		return doesUserExistByEmail;
	}

	public boolean doesUsernameExist(String username) {
		AppUser user = userRepository.findByUsername(username);

		boolean doesUserExistByUsername = user != null;
		return doesUserExistByUsername;
	}

	@Override
	public AppUser getUser(String verificationToken) {
		AppUser user = tokenRepository.findByToken(verificationToken).getUser();
		return user;
	}

	@Override
	public VerificationToken getVerificationToken(String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	@Override
	public void createVerificationToken(AppUser user, String token) {
		VerificationToken myToken = new VerificationToken(user, token);
		user.setToken(myToken);
		tokenRepository.save(myToken);
	}

	@Override
	public void updateUser(AppUser user) {
		userRepository.save(user);

	}

	@Override
	public void verifyToken(String token) {

		VerificationToken verificationToken = getVerificationToken(token);

		if (verificationToken == null) {

			System.out.println("invalid token");

		} else {
			AppUser user = verificationToken.getUser();
			Calendar cal = Calendar.getInstance();
			if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
				System.out.println("Expired token!");
			} else {

				if (user.getIsActive() == true) {
					System.out.println("This user is already active");
				}

				else {

					user.setActivationDate(new Date());
					user.setIsActive(true);
					user.setToken(null);
					updateUser(user);
					tokenRepository.delete(verificationToken);

				}
			}

		}

	}

	@Override
	public void resendTokenByEmail(String email) {

		AppUser user = userRepository.findByEmail(email);

		if (user != null && user.getIsActive() == false) {
			String token = UUID.randomUUID().toString();
			createResendVerificationToken(user, token);

		} else {
			System.out.println("There is no account with that e-mail or User is already active");
		}

	}

	@Override
	public void createResendVerificationToken(AppUser user, String token) {

		VerificationToken oldToken = user.getToken();
		oldToken.updateToken(token);
		tokenRepository.save(oldToken);
		ResendToken resendToken = new ResendToken(user, oldToken);
		// amqpTemplate.convertAndSend("email-exchange", "resend-token",
		// resendToken);
		template.convertAndSend("email-direct", "resend-token", resendToken);
		// mailer.resendVerificationToken(user, oldToken);
	}

}