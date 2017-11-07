package com.springrestsecurityboilerplate.user;

import org.springframework.web.context.request.WebRequest;

import com.springrestsecurityboilerplate.VerificationToken;
import com.springrestsecurityboilerplate.validation.EmailExistsException;
import com.springrestsecurityboilerplate.validation.UsernameExistsException;

public interface UserService {

	void registerUser(User user, WebRequest request) throws EmailExistsException, UsernameExistsException;

	User getUser(String verificationToken);

	void updateUser(User user);

	void createVerificationToken(User user, String token);

	VerificationToken getVerificationToken(String VerificationToken);

	void verifyToken(String token);
	
	void resendTokenByEmail(String email);

	void createResendVerificationToken(User user, String token);
}
