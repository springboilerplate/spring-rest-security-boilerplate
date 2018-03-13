package com.springrestsecurityboilerplate.user;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.springrestsecurityboilerplate.registration.VerificationToken;
import com.springrestsecurityboilerplate.validation.EmailExistsException;
import com.springrestsecurityboilerplate.validation.UsernameExistsException;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void saveUser(@RequestBody AppUser user, WebRequest request) {

		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userService.registerUser(user, request);
		} catch (EmailExistsException e) {
			System.out.println(e.getMessage());
			// System.out.println(e);
		}

		catch (UsernameExistsException e) {
			System.out.println(e.getMessage());
		}

	}

	@RequestMapping(value = "/confirm/{token}", method = RequestMethod.GET)
	public void confirmRegistration(@PathVariable("token") String token) {

		userService.verifyToken(token);

	}

	@RequestMapping(value = "/resend/{email:.+}", method = RequestMethod.GET)
	public void resendVerificationCode(@PathVariable("email") String email) {

		userService.resendTokenByEmail(email);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void testFunction() {

		System.out.println("Test function is executed");

	}

	@RequestMapping(value = "/currentuser", method = RequestMethod.GET)
	public void test() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(currentPrincipalName);

	}

	@RequestMapping(value = "/currentuser2", method = RequestMethod.GET)
	public void test2() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(currentPrincipalName);

		AppUser currentUser = null;
		try {

			currentUser = userRepository.findByUsername(currentPrincipalName);
		} catch (Exception e) {
			// TODO: handle exception
		}

		String foundUsername = "";

		if (currentUser != null) {
			foundUsername = currentUser.getUsername();
		}

		if (foundUsername.equals(currentPrincipalName)) {
			System.out.println("DB contains this user (currentuser2) ");

		} else
			System.out.println("DB does not contain this user (currentuser2) ");

	}

}
