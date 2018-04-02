package com.springrestsecurityboilerplate.user;

import org.springframework.web.context.request.WebRequest;

import com.springrestsecurityboilerplate.registration.VerificationToken;
import com.springrestsecurityboilerplate.validation.EmailExistsException;
import com.springrestsecurityboilerplate.validation.UsernameExistsException;

public interface UserService {

	void registerUser(AppUser user, WebRequest request) throws EmailExistsException, UsernameExistsException;

	AppUser getUser(String verificationToken);

	void updateUser(AppUser user);

	void createVerificationToken(AppUser user, String token);

	VerificationToken getVerificationToken(String VerificationToken);

	void verifyToken(String token);
	
	void resendTokenByEmail(String email);

	void createResendVerificationToken(AppUser user, String token);
	
	boolean doesEmailExist(String email);
	
	public boolean doesUsernameExist(String username);
}
